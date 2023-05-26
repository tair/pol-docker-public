package org.phoenix.paywall;
import org.phoenix.paywall.model.Schedule;
import org.phoenix.paywall.model.Usage;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.phoenix.paywall.model.Pageview_Plus_Session;
import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.service.ISessionsService;
import org.phoenix.paywall.service.IUsageService;
import com.amazonaws.services.simpleemail.model.*;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Calendar;
import org.phoenix.paywall.repository.ScheduleRepository;
import java.time.YearMonth;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@EnableScheduling
public class ScheduleScanner {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
	private JdbcTemplate jdbcPrimaryTemplate;

    private static String AWS_ACCESS_KEY;
    private static String AWS_SECRET_KEY;
    private static String AWS_REGION;

    ScheduleScanner(@Value("${phoenix.app.awsAccessKey}") String auto_awsAccessKey, @Value("${phoenix.app.awsSecretKey}") String auto_awsSecretKey, @Value("${phoenix.app.awsRegion}") String auto_awsRegion) {
        AWS_ACCESS_KEY = auto_awsAccessKey;
        AWS_SECRET_KEY = auto_awsSecretKey;
        AWS_REGION = auto_awsRegion;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void scanSchedule() throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //current date
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDateTo = dateTime.format(formatter);
        logger.info("Schedule Scanner is starting for date and time: {}", formattedDateTo);

        String sql = "SELECT * FROM Schedule WHERE nextDateTrigger = '" + formattedDateTo + "'";
        List<Schedule> Schedules = null;
        Schedules = jdbcPrimaryTemplate.query(sql, new BeanPropertyRowMapper<>(Schedule.class));
        Integer scheduleCount = Schedules.size();
        // UsageFilter{dateFrom='null', dateTo='null', partyId='30743', partyType='organization', partner='null', ipRange=null, paidType=null, pageHitMin=0, pageHitMax=0}
        for (Schedule entry : Schedules) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/YYYY");
            UsageFilter filter = new UsageFilter();
            String formattedDateStart;
            String formattedDateEnd;
            String dateForEmail;
            if(entry.getSchedulePeriod().equalsIgnoreCase("monthly")){
                //one month back
                LocalDateTime startDateTimeMonth = LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
                LocalDateTime endDateTimeMonth = LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
                formattedDateStart = startDateTimeMonth.format(formatter2);
                formattedDateEnd = endDateTimeMonth.format(formatter2);
                dateForEmail = "Monthly";
            }else{
                //one week back
                LocalDateTime startDateTimeYear = LocalDateTime.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear());
                LocalDateTime endDateTimeYear = LocalDateTime.now().minusYears(1).with(TemporalAdjusters.lastDayOfYear());
                formattedDateStart = startDateTimeYear.format(formatter2);
                formattedDateEnd = endDateTimeYear.format(formatter2);
                dateForEmail = "Yearly";
            }
            filter.setDateFrom(formattedDateStart);
            filter.setDateTo(formattedDateEnd);
            filter.setPartyId(entry.getSelectedParty());
            filter.setPartner(entry.getPartnerType());
            filter.setPartyType(entry.getPartyType());
            List<String> list = new ArrayList<>();            
            filter.setIpRange(list);
            filter.setPaidType(list);
            // System.out.println(filter);
            List<Pageview_Plus_Session> data;
            data = getByFilter(filter);
            String FROM_EMAIL = "subscriptions@phoenixbioinformatics.org";
            String TO_EMAIL = entry.getEmail();

            StringWriter stringWriter = new StringWriter();
            stringWriter.write("ip,ipLong,total_views,paid_content_count,total_turnaways,session_count,partyName,month,year\n");
            for (Pageview_Plus_Session pageview : data) {
              stringWriter.write(String.format("%s,%d,%d,%d,%d,%d,%s,%d,%d\n",
                pageview.getIp(),
                pageview.getIpLong(),
                pageview.getTotal_views(),
                pageview.getPaid_content_count(),
                pageview.getTotal_turnaways(),
                pageview.getSession_count(),
                pageview.getPartyName(),
                pageview.getMonth(),
                pageview.getYear()));
            }

            String partyNameForEmail = entry.getSelectedPartyName();
            String partnerNameForEmail = entry.getPartnerType();
            sendEmail(data, FROM_EMAIL, TO_EMAIL, dateForEmail, partyNameForEmail, partnerNameForEmail, formattedDateStart, formattedDateEnd);
            
            LocalDateTime changeNextTriggerDate;
            if(entry.getSchedulePeriod().equalsIgnoreCase("monthly")){
                changeNextTriggerDate = LocalDateTime.now().plusMonths(1);
            }else{
                changeNextTriggerDate = LocalDateTime.now().plusYears(1);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, changeNextTriggerDate.getYear());
            calendar.set(Calendar.MONTH, changeNextTriggerDate.getMonthValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, changeNextTriggerDate.getDayOfMonth());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date changeNextTriggerDateFinal = calendar.getTime();
            entry.setNextDateTrigger(changeNextTriggerDateFinal);
            scheduleRepository.save(entry);
        }

        logger.info("Schedule Scanner is completed. Found these many entries: {}", scheduleCount);
        
    }

    public static void sendEmail(List<Pageview_Plus_Session> data, String fromEmail, String toEmail, String dateForEmail, String selectedPartyName, String partnerNameForEmail, String formattedDateStart, String formattedDateEnd) throws IOException {
        // Encode the data as CSV
        byte[] csvBytes = encode(data);
        String base64 = Base64.getEncoder().encodeToString(csvBytes);
        if(formattedDateStart == formattedDateEnd){
            // Parse the string to create a YearMonth object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth yearMonth = YearMonth.parse(formattedDateStart, formatter);
            // Get the first date of the month
            LocalDate reformattedDateStart = yearMonth.atDay(1);
            LocalDate reformattedDateEnd = yearMonth.atEndOfMonth();

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateStart = reformattedDateStart.format(outputFormatter);
            formattedDateEnd = reformattedDateEnd.format(outputFormatter);
        }else{
            // Parse the string to create a YearMonth object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            YearMonth yearMonth = YearMonth.parse(formattedDateStart, formatter);
            YearMonth yearMonth2 = YearMonth.parse(formattedDateEnd, formatter);
            // Get the first date of the month
            LocalDate reformattedDateStart = yearMonth.atDay(1);
            LocalDate reformattedDateEnd = yearMonth2.atEndOfMonth();
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateStart = reformattedDateStart.format(outputFormatter);
            formattedDateEnd = reformattedDateEnd.format(outputFormatter);
        }
        // "Oregon State University – TAIR Usage – 1.1.2021 to 12.31.2022"
        // Create a raw email message
        String emailBody = "<html><p>Dear Librarian"  + ",</p><p>Here are your usage statistics. Please email <a href='mailto:subscriptions@phoenixbioinformatics.org'>subscriptions@phoenixbioinformatics.org</a> for any questions or concerns.</p><p>Kind Regards,<br>Phoenix Bioinformatics</p></html>";
        String boundary = "NextPart";
        String contentTypeHtml = "Content-Type: text/html\r\n";
        String contentHtml = "\r\n" + emailBody + "\r\n";
        String contentTypeCsv = "Content-Type: text/csv\r\n";
        String subject = selectedPartyName + " - " + partnerNameForEmail.toUpperCase() + " Usage - " + formattedDateStart + " to " + formattedDateEnd;
        String fileName = subject.replace(" ", "");
        String contentDispositionCsv = "Content-Disposition: attachment; filename=" + fileName + ".csv\r\n";
        String contentTransferEncodingCsv = "Content-Transfer-Encoding: base64\r\n";
        String contentCsv = "\r\n" + base64 + "\r\n";
        String rawMessageData = "From: " + fromEmail + "\r\n" +
                                "To: " + toEmail + "\r\n" +
                                "Subject: "+ subject + "\r\n" +
                                "MIME-Version: 1.0\r\n" +
                                "Content-Type: multipart/mixed; boundary=" + boundary + "\r\n" +
                                "\r\n" +
                                "--" + boundary + "\r\n" +
                                contentTypeHtml +
                                contentHtml +
                                "--" + boundary + "\r\n" +
                                contentTypeCsv +
                                contentDispositionCsv +
                                contentTransferEncodingCsv +
                                contentCsv +
                                "--" + boundary + "--";
    
        RawMessage rawMessage = new RawMessage().withData(ByteBuffer.wrap(rawMessageData.getBytes()));
    
        // Create a SendRawEmailRequest and set the raw message and destination
        SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest()
                .withRawMessage(rawMessage)
                .withDestinations(toEmail)
                .withSource(fromEmail);
    
        // Create an SES client and send the email
        AmazonSimpleEmailService sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)))
                .withRegion(AWS_REGION)
                .build();
        try {
            sesClient.sendRawEmail(rawEmailRequest);
        } catch (AmazonSimpleEmailServiceException e) {
            // handle the exception here
            System.out.println("Error sending email: " + e.getMessage());
        }
    
    }
    

    private static byte[] encode(List<Pageview_Plus_Session> data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos);

        // Write CSV header
        writer.write("ip,ipLong,total_views,paid_content_count,total_turnaways,session_count,partyName,month,year\n");

        // Write data
        for (Pageview_Plus_Session entry : data) {
            writer.write(String.format("%s,%d,%d,%d,%d,%d,%s,%d,%d\n", 
                    entry.getIp(), entry.getIpLong(), entry.getTotal_views(), entry.getPaid_content_count(),
                    entry.getTotal_turnaways(), entry.getSession_count(), entry.getPartyName(), entry.getMonth(),
                    entry.getYear()));
        }

        writer.flush();
        writer.close();

        // Return the encoded CSV as a byte array
        return baos.toByteArray();
    }

    @Autowired
	private IUsageService usageService;

    @Autowired
	private ISessionsService sessionsService;

	public List<Pageview_Plus_Session> getByFilter(UsageFilter filter) throws Exception {	
		List<Pageview_Plus_Session> records = new ArrayList<>();
		try {
			List<Usage> usage_records = usageService.getByFilter(filter);
			SessionsFilter sessionsFilter = new SessionsFilter(filter);
			List<Sessions> session_records = sessionsService.getByFilter(sessionsFilter);
			Map<String, Sessions> session_records_mapped = new HashMap<String, Sessions>();

			for (Sessions i : session_records) {
				String unique_ip_by_date = i.getIpLong().toString() + "_" + Integer.toString(i.getMonth());
				session_records_mapped.put(unique_ip_by_date, i);
			}

			for (Usage record : usage_records) {
				Pageview_Plus_Session mod_rec = new Pageview_Plus_Session(record);
				String match_id = record.getIpLong().toString() + "_" + Integer.toString(record.getMonth());
				Sessions matching_session = session_records_mapped.get(match_id);
				if (matching_session != null) {
					mod_rec.setSession_count(matching_session.getSession_count());
				} else {
					System.out.println("No session record found " + record.getIp());
				}
				records.add(mod_rec);
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}
		return records;
    }

}
