package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.IpRange;
import org.phoenix.paywall.model.filter.IpRangeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/iprange") 
@CrossOrigin
public class IPRangeController {
	
	@Autowired
	private org.phoenix.paywall.service.IIPRangeService ipRangeService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<IpRange> getByFilter(@RequestBody IpRangeFilter filter) throws Exception {
		System.out.println("Filter: " + filter.getPartyName());
		return ipRangeService.getByFilter(filter);
	}
}

