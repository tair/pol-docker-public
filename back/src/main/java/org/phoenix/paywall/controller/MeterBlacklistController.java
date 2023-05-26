package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.MeterBlacklist;
import org.phoenix.paywall.service.IMeterBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/meterBlacklist") 
@CrossOrigin
public class MeterBlacklistController {
	
	@Autowired
	private IMeterBlacklistService meterBlacklistService;

	@GetMapping(path="")
	public @ResponseBody List<MeterBlacklist> getAll() {
		return meterBlacklistService.getAll();
	}
}