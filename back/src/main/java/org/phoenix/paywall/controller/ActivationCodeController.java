package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.ActivationCode;
import org.phoenix.paywall.model.filter.ActivationCodeFilter;
import org.phoenix.paywall.payload.request.ActivationCodeRequest;
import org.phoenix.paywall.repository.ActivationCodeRepository;
import org.phoenix.paywall.service.IActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/activationCode") 
@CrossOrigin
public class ActivationCodeController {
	
	@Autowired
	private IActivationCodeService activationCodeService;

	@Autowired ActivationCodeRepository activationCodeRepository;

	@PostMapping(path = "/filter")
	public @ResponseBody List<ActivationCode> getByFilter(@RequestBody ActivationCodeFilter filter) throws Exception {
		return activationCodeService.getByFilter(filter);
	}

	@PostMapping(path = "/delete")
	public @ResponseBody boolean deleteByFilter(@RequestBody ActivationCodeRequest req) throws Exception {
		return activationCodeService.deleteByFilter(Integer.parseInt(req.getActivationCodeId()));
	}

	@PostMapping(path = "/create")
	public @ResponseBody boolean create(@RequestBody ActivationCode req) throws Exception {
		req.setDeleteMarker(false);
		
		return activationCodeRepository.createActivationCode(req);
	}
}

