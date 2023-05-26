package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.MeterBlacklist;

public interface IMeterBlacklistService {
	
	MeterBlacklist getById(int id);
	
	List<MeterBlacklist> getAll();
}