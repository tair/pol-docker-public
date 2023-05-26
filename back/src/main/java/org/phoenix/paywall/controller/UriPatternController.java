package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.UriPattern;
import org.phoenix.paywall.service.IUriPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/uriPattern") 
@CrossOrigin
public class UriPatternController {
	
	@Autowired
	private IUriPatternService uriPatternService;

	@GetMapping(path="")
	public @ResponseBody List<UriPattern> getAll() {
		return uriPatternService.getAll();
	}
}