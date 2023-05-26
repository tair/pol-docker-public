package org.phoenix.paywall.service.impl;

import java.util.List;

import org.phoenix.paywall.model.UriPattern;
import org.phoenix.paywall.repository.UriPatternRepository;
import org.phoenix.paywall.service.IUriPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UriPatternServiceImpl implements IUriPatternService {
	
	@Autowired
	UriPatternRepository UriPatternRepository;
	
	@Override
	public UriPattern getById(int id) {
		return UriPatternRepository.findByPatternId(id);
	}

	@Override
	public List<UriPattern> getAll() {
		return UriPatternRepository.findAll();
	}
	
}