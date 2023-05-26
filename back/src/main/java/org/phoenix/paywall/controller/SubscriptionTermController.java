package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.SubscriptionTerm;
import org.phoenix.paywall.service.ISubscriptionTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/subscriptionTerm") 
@CrossOrigin
public class SubscriptionTermController {
	
	@Autowired
	private ISubscriptionTermService subscriptionTermService;

	@GetMapping(path="")
	public @ResponseBody List<SubscriptionTerm> getAll() {
		return subscriptionTermService.getAll();
	}

	@GetMapping(path="/{id}")
	public @ResponseBody SubscriptionTerm getById(@PathVariable(value = "id") int id) {
		return subscriptionTermService.getById(id);
	}
}
