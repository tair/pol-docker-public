package org.phoenix.paywall.controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.phoenix.paywall.service.IScheduleService;
import org.phoenix.paywall.model.Schedule;
import org.phoenix.paywall.payload.request.ScheduleRequest;
import org.phoenix.paywall.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.phoenix.paywall.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private IScheduleService scheduleService;

    @PostMapping(path = "/addschedule")
    public ResponseEntity<?> addSchedule(@RequestBody ScheduleRequest scheduleRequest) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate;

        try {
            parsedStartDate = sdf.parse(scheduleRequest.getStartDate());
        } catch (ParseException e) {
            throw new Exception("Invalid date format provided for startDate");
        }
        Schedule schedule = new Schedule(
            scheduleRequest.getScheduler(),
            parsedStartDate,
			parsedStartDate,
            scheduleRequest.getSchedulePeriod(),
            scheduleRequest.getPartyType(),
			scheduleRequest.getSelectedParty(),
            scheduleRequest.getPartnerType(),
            scheduleRequest.getEmail(),
            scheduleRequest.getSelectedPartyName()
        );
        scheduleRepository.save(schedule);
        return ResponseEntity.ok(new MessageResponse("Schedule Added!"));
    }

    @GetMapping(path = "/getschedules")
    public @ResponseBody List<Schedule> getSchedule() throws Exception {
        return scheduleService.getAllSchedules();
    }
}
