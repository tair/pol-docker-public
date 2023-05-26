package org.phoenix.paywall.payload.request;
import jakarta.validation.constraints.NotBlank;

public class ScheduleRequest {

    @NotBlank
    private String scheduler;
    @NotBlank
    private String schedulePeriod;
    @NotBlank
    private String partyType;
    @NotBlank
    private String selectedParty;
    @NotBlank
    private String ipRange;
    @NotBlank
    private String partnerType;
    @NotBlank
    private String startDate;
	@NotBlank
    private String selectedPartyName;

	public String getScheduler() {
		return scheduler;
	}
	public void setScheduler(String scheduler) {
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
	public String getSelectedParty() {
		return selectedParty;
	}
	public void setSelectedParty(String selectedParty) {
		this.selectedParty = selectedParty;
	} 
	public String getSelectedPartyName() {
		return selectedPartyName;
	}
	public void setSelectedPartyName(String selectedPartyName) {
		this.selectedPartyName = selectedPartyName;
	} 
    public String getEmail() {
		return ipRange;
	}
	public void setEmail(String ipRange) {
		this.ipRange = ipRange;
	} 
    public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
    public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}  

}
