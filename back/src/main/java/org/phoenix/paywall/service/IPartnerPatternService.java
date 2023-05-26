package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.PartnerPattern;

public interface IPartnerPatternService {
	
	PartnerPattern getById(int id);
	
	List<PartnerPattern> getAll();
}