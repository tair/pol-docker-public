package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.Partner;
import org.phoenix.paywall.service.IPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/partner") 
@CrossOrigin
public class PartnerController {
	
	@Autowired
	private IPartnerService partnerService;

	@GetMapping(path="")
	public @ResponseBody List<Partner> getAll() {
		return partnerService.getAll();
	}
}