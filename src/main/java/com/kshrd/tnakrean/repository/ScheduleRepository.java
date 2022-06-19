package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ScheduleRepository {

    @Select("SELECT s.subject as sub , d.day , ses.time from teacher_schedule t " +
            "INNER JOIN subject s on t.subject_id = s.id " +
            "INNER JOIN weekly_schedule ws on t.id = ws.teacher_schedule_id " +
            "INNER JOIN day_schedule ds on ws.day_schedule_id = ds.id " +
            "INNER JOIN session ses on ds.session_id = ses.id " +
            "INNER JOIN day_of_week d on ds.day_of_week_id = d.id WHERE t.teacher_id = #{id}")
    @Result(property = "subject", column = "sub")
    List<ScheduleResponse> getScheduleByTeacherId(Integer id);

    @Select("SELECT s.subject as sub , d.day , ses.time from teacher_schedule t " +
            "INNER JOIN subject s on t.subject_id = s.id " +
            "INNER JOIN weekly_schedule ws on t.id = ws.teacher_schedule_id " +
            "INNER JOIN day_schedule ds on ws.day_schedule_id = ds.id " +
            "INNER JOIN session ses on ds.session_id = ses.id " +
            "INNER JOIN day_of_week d on ds.day_of_week_id = d.id WHERE t.teacher_id = #{id}")
    @Result(property = "subject", column = "sub")
    List<ScheduleResponse> getScheduleByClassId(Integer id);

}
