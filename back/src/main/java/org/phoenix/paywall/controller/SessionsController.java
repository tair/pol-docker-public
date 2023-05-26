package org.phoenix.paywall.controller;

import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.filter.SessionsFilter;
import org.phoenix.paywall.service.ISessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/sessions")
@CrossOrigin(origins = "*")
public class SessionsController {

	@Autowired
	private ISessionsService sessionsService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Sessions> getByFilter(@RequestBody SessionsFilter filter) throws Exception {
		return sessionsService.getByFilter(filter);
	}
}