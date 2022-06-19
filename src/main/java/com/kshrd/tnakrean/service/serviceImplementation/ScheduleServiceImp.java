package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;
import com.kshrd.tnakrean.repository.ScheduleRepository;
import com.kshrd.tnakrean.service.serviceInter.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImp implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleResponse> getScheduleByTeacherId(Integer id) {
        return scheduleRepository.getScheduleByTeacherId(id);
    }

    @Override
    public List<ScheduleResponse> getScheduleByClassId(Integer id) {
        return scheduleRepository.getScheduleByClassId(id);
    }
}
