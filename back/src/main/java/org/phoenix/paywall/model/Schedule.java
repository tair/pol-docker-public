package org.phoenix.paywall.model;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Schedule {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
	private String scheduler;
	private Date startDate;
	private Date nextDateTrigger;
    private String schedulePeriod;
    private String partyType;
    private String selectedParty;
    private String email;
	private String partnerType;
	private String selectedPartyName;

	public Schedule() {
	}
	public Schedule(String scheduler, Date startDate, Date nextDateTrigger, String schedulePeriod,  String partyType, String selectedParty, String partnerType, String email, String selectedPartyName) {
		this.scheduler = scheduler;
		this.startDate = startDate;
		this.nextDateTrigger = nextDateTrigger;
		this.schedulePeriod = schedulePeriod;
		this.partyType = partyType;
		this.selectedParty = selectedParty;
		this.partnerType = partnerType;
		this.email = email;
		this.selectedPartyName = selectedPartyName;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getscheduler() {
		return scheduler;
	}
	public void setscheduler(String scheduler) {
		this.scheduler = scheduler;
	}
	public String getSchedulePeriod() {
		return schedulePeriod;
	}
	public void setSchedulePeriod(String schedulePeriod) {
		this.schedulePeriod = schedulePeriod;
	}
	public String getPartyType() {
		return partyType;
	}
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}  
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}  
	public Date getNextDateTrigger() {
		return nextDateTrigger;
	}
	public void setNextDateTrigger(Date nextDateTrigger) {
		this.nextDateTrigger = nextDateTrigger;
	}  
	public String getSelectedParty() {
		return selectedParty;
	}
	public void setSelectedParty(String selectedParty) {
		this.selectedParty = selectedParty;
	} 
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	} 
	public String getSelectedPartyName() {
		return selectedPartyName;
	}
	public void setSelectedPartyName(String selectedPartyName) {
		this.selectedPartyName = selectedPartyName;
	} 
	@Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", scheduler='" + scheduler + '\'' +
                ", startDate=" + startDate +
				", nextDateTrigger=" + nextDateTrigger +
                ", schedulePeriod='" + schedulePeriod + '\'' +
                ", partyType='" + partyType + '\'' +
                ", selectedParty='" + selectedParty + '\'' +
                ", email='" + email + '\'' +
                ", partnerType='" + partnerType + '\'' +
				", selectedPartyName='" + selectedPartyName + '\'' +
                '}';
    }

}
