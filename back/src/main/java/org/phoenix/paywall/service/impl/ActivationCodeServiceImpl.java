package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.ActivationCode;
import org.phoenix.paywall.model.filter.ActivationCodeFilter;
import org.phoenix.paywall.repository.ActivationCodeRepository;
import org.phoenix.paywall.service.IActivationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivationCodeServiceImpl implements IActivationCodeService {
	
	@Autowired
	ActivationCodeRepository activationCodeRepository;
	
	@Override
	public List<ActivationCode> getByFilter(ActivationCodeFilter filter) {
		return activationCodeRepository.getByFilter(filter);
	}
	
	@Override
    public boolean deleteByFilter(int activationCodeId) {
        return activationCodeRepository.deleteByActivationId(activationCodeId);
    }
}