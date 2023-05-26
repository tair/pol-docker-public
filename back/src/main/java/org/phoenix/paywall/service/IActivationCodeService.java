package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.ActivationCode;
import org.phoenix.paywall.model.filter.ActivationCodeFilter;

public interface IActivationCodeService {
	
	List<ActivationCode> getByFilter(ActivationCodeFilter filter);

	boolean deleteByFilter(int activationCodeId);
}