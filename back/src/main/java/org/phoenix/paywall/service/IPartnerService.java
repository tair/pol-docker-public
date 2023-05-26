package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.Partner;

public interface IPartnerService {
	
	Partner getById(int id);
	
	List<Partner> getAll();
}