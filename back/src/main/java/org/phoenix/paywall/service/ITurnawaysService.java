package org.phoenix.paywall.service;

import org.phoenix.paywall.model.Turnaways;
import org.phoenix.paywall.model.filter.UsageFilter;

import java.util.List;

public interface ITurnawaysService {
	List<Turnaways> getByFilter(UsageFilter filter);
}