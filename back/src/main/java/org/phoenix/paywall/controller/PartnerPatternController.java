package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.PartnerPattern;
import org.phoenix.paywall.service.IPartnerPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/partnerPattern") 
@CrossOrigin
public class PartnerPatternController {
	
	@Autowired
	private IPartnerPatternService partnerPatternService;

	@GetMapping(path="")
	public @ResponseBody List<PartnerPattern> getAll() {
		return partnerPatternService.getAll();
	}
}