package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.Turnaways;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.model.filter.UsageFilter;
import org.phoenix.paywall.service.ITurnawaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/turnaways")
@CrossOrigin(origins = "*")
public class TurnawaysController {

	@Autowired
	private ITurnawaysService turnawaysService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Turnaways> getByFilter(@RequestBody UsageFilter filter) throws Exception {
		List<Turnaways> turnaways = turnawaysService.getByFilter(filter);
		System.out.println("Turnaways found: " + turnaways.size());
		return turnaways;
	}
}