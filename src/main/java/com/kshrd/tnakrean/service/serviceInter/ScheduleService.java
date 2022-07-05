package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getScheduleByTeacherId(Integer id);

    List<ScheduleResponse> getScheduleByClassId(Integer classroomId, Integer classId);

    List<ScheduleResponse> getScheduleByTeacherDayClassClassroom(Integer classroomId, Integer classId, Integer dayId, Integer user_id);
}
