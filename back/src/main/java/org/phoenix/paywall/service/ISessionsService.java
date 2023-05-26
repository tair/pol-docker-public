package org.phoenix.paywall.service;

import org.phoenix.paywall.model.Sessions;
import org.phoenix.paywall.model.filter.SessionsFilter;

import java.util.List;

public interface ISessionsService {
	List<Sessions> getByFilter(SessionsFilter filter);
}