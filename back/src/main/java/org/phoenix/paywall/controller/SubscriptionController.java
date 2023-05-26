package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.Subscription;
import org.phoenix.paywall.model.filter.SubscriptionFilter;
import org.phoenix.paywall.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/subscription")
@CrossOrigin
public class SubscriptionController {

	@Autowired
	private ISubscriptionService subscriptionService;

	@PostMapping(path = "/filter")
	public @ResponseBody List<Subscription> getByFilter(@RequestBody SubscriptionFilter filter) throws Exception {
		return subscriptionService.getByFilter(filter);
	}
}