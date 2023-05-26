package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.Usage;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.service.IUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/usage")
@CrossOrigin(origins = "*")
public class UsageController {

	@Autowired
	private IUsageService usageService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Usage> getByFilter(@RequestBody UsageFilter filter) throws Exception {
		return usageService.getByFilter(filter);
	}
}