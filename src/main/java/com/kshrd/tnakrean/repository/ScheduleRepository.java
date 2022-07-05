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
            "INNER JOIN day_of_week d on ds.day_of_week_id = d.id " +
            "WHERE classroom_id = #{classroomId} AND class_id = #{classId}")
    @Result(property = "subject", column = "sub")
    List<ScheduleResponse> getScheduleByClassId(Integer classroomId, Integer classId);

    // getSchedule ByTeacher Day Class Classroom
    @Select("SELECT ds.day_of_week_id, subject, time, d.day,classroom_id,class_id from teacher_schedule t \n" +
            "INNER JOIN subject s on t.subject_id = s.id \n" +
            "INNER JOIN weekly_schedule ws on t.id = ws.teacher_schedule_id \n" +
            "INNER JOIN day_schedule ds on ws.day_schedule_id = ds.id \n" +
            "INNER JOIN session ses on ds.session_id = ses.id \n" +
            "INNER JOIN day_of_week d on ds.day_of_week_id = d.id \n" +
            "WHERE teacher_id = #{user_id} AND day_of_week_id = #{dayId} AND classroom_id = #{classroomId} AND class_id = #{classId} ")
    List<ScheduleResponse> getScheduleByTeacherDayClassClassroom(Integer classId, Integer classroomId, Integer dayId, Integer user_id);
}
