package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.submittedWork.request.*;
import com.kshrd.tnakrean.model.submittedWork.response.SubmittedWorkResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmittedWorkRepository {
    //get all
    @Select("SELECT * FROM submitted_work")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    @Result(property = "studentResult", column = "student_result", typeHandler = JsonTypeHandler.class)
    List<SubmittedWorkResponse> getAllSubmittedWork();

    // get by student id
    @Select("SELECT * FROM submitted_work WHERE student_id = #{studentId}")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    @Result(property = "studentResult", column = "student_result", typeHandler = JsonTypeHandler.class)
    List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId);

    // insert student work
    @Insert("INSERT INTO submitted_work(student_id,submittable_work_id,submitted_date,student_work)" +
            "VALUES(#{submWork.student_id},#{submWork.submittable_work_id},#{submWork.submitted_date},#{submWork.studentWork,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    boolean addSubmittedWork(@Param("submWork") SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest);

    // update result
    @Update("UPDATE submitted_work SET student_result = #{update.studentResult, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{update.id}")
    boolean updateResult(@Param("update") SubmittedWorkUpdateResultRequest submittedWorkUpdateResultRequest);

    @Result(property = "studentResult", column = "student_result", typeHandler = JsonTypeHandler.class)

    // update student work
    @Update("UPDATE submitted_work SET student_work = #{update.studentWork, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{update.id}")
    boolean updateSubmittedWork(@Param("update") SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)

    //delete by id
    @Delete("DELETE FROM submitted_work WHERE id = #{id}")
    void deleteSubmittedWorkId(int id);

    //update status
    @Update("UPDATE submitted_work SET status = #{update.status} WHERE id = #{update.id}")
    boolean updateStatus(@Param("update") SubmittedWorkUpdateStatusRequest submittedWorkUpdateStatusRequest);

    // delete by student id
    @Delete("DELETE FROM submitted_work WHERE student_id = #{id}")
    void deleteByStudentId(Integer id);
}
