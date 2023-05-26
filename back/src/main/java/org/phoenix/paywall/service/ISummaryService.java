package org.phoenix.paywall.service;

import org.phoenix.paywall.model.Summary;
import org.phoenix.paywall.model.filter.SummaryFilter;

import java.util.List;

public interface ISummaryService {
	List<Summary> getByFilter(SummaryFilter filter);
}