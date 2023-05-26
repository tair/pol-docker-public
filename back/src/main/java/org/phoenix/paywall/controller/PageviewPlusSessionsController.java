package org.phoenix.paywall.controller;

import org.phoenix.paywall.model.Pageview_Plus_Session;
import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.Turnaways;
import org.phoenix.paywall.model.Usage;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.service.ISessionsService;
import org.phoenix.paywall.service.IUsageService;
import org.phoenix.paywall.service.ITurnawaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/pageview_sessions")
@CrossOrigin(origins = "*")
public class PageviewPlusSessionsController {

	@Autowired
	private IUsageService usageService;
	@Autowired
	private ISessionsService sessionsService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Pageview_Plus_Session> getByFilter(@RequestBody UsageFilter filter) throws Exception {	
		List<Pageview_Plus_Session> records = new ArrayList<>();
		try {
			List<Usage> usage_records = usageService.getByFilter(filter);
			SessionsFilter sessionsFilter = new SessionsFilter(filter);
			// System.out.println("Partner - " + sessionsFilter.getPartner());
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