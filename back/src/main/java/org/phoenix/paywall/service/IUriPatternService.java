package org.phoenix.paywall.service;

import java.util.List;

import org.phoenix.paywall.model.UriPattern;

public interface IUriPatternService {
	
	UriPattern getById(int id);
	
	List<UriPattern> getAll();
}