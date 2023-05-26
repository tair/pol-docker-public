package org.phoenix.paywall.controller;

import java.util.List;

import org.phoenix.paywall.model.Party;
import org.phoenix.paywall.model.PartyType;
import org.phoenix.paywall.model.filter.PartyFilter;
import org.phoenix.paywall.repository.PartyRepository;
import org.phoenix.paywall.service.IPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/party")
@CrossOrigin(origins = "*")
public class PartyController {

	@Autowired
	private IPartyService partyService;

	@Autowired
	private PartyRepository partyRepo;

	@GetMapping(path = "")
	public @ResponseBody List<Party> getAll() {
		return partyService.getAll();
	}

	@GetMapping(path = "/orgs")
	public @ResponseBody List<Party> getAllOrgs() {
		return partyRepo.getAllOrgs();
	}

	@GetMapping(path = "/{type}")
	public @ResponseBody List<Party> getByType(@PathVariable(value = "type") String type) {
		// System.out.println("type: " + type);
		return partyService.getByType(type);
	}

	@GetMapping(path = "/types")
	public @ResponseBody List<PartyType> getTypes() {
		return partyService.getTypes();
	}

	@PostMapping(path = "/filter")
	public @ResponseBody List<Party> getByFilter(@RequestBody PartyFilter filter) throws Exception {
		return partyService.getByFilter(filter);
	}
}
