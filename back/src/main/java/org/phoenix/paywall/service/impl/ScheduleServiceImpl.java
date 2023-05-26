package org.phoenix.paywall.service.impl;
import java.util.List;
import org.phoenix.paywall.repository.ScheduleRepository;
import org.phoenix.paywall.model.Schedule;
import org.phoenix.paywall.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements IScheduleService{

    @Autowired
    ScheduleRepository scheduleRepository;
    
    @Override
	public List<Schedule> getAllSchedules() {
        List<Schedule> schedule = scheduleRepository.findAll();
		return schedule;
	}
	
}