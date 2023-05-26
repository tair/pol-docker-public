package org.phoenix.paywall.controller;

import org.phoenix.paywall.model.Summary;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.SummaryFilter;
import org.phoenix.paywall.service.ISummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/summary")
@CrossOrigin(origins = "*")
public class SummaryController {

	@Autowired
	private ISummaryService summaryService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Summary> getByFilter(@RequestBody SummaryFilter filter) throws Exception {
		return summaryService.getByFilter(filter);
	}
}