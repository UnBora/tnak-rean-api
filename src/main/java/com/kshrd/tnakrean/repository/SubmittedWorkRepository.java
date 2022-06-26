package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByMaterialIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByStudentIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmittedWorkRepository {
    //get all
    @Select("SELECT * FROM submitted_work")
    @Results(id = "submitted", value = {
            @Result(property = "studentWork", column = "student_work" , typeHandler = JsonTypeHandler.class),
            @Result(property = "submitted_work_id", column = "id")
    })
    List<SubmittedWorkResponse> getAllSubmittedWork();

    // get by student id
    @Select("SELECT * FROM submitted_work WHERE student_id = #{studentId}")
    @ResultMap("submitted")
    List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId);

    // update student work
    @Insert("INSERT INTO submitted_work(student_id,status,submittable_work_id,submitted_date,student_work)" +
            "VALUES(#{submWork.student_id},0,#{submWork.submittable_work_id},#{submWork.submitted_date},#{submWork.studentWork,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    boolean addSubmittedWork(@Param("submWork") SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest);

    @Result(property = "studentResult", column = "student_result", typeHandler = JsonTypeHandler.class)

    // update student work
    @Update("UPDATE submitted_work SET student_work = #{update.studentWork, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{update.id}")
    boolean updateSubmittedWork(@Param("update") SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)

    //delete by id
    @Delete("DELETE FROM submitted_work WHERE id = #{id}")
    Boolean deleteSubmittedWorkId(int id);

    // delete by student id
    @Delete("DELETE FROM submitted_work WHERE student_id = #{id}")
    void deleteByStudentId(Integer id);

    // get by id
    @Select("SELECT * FROM submitted_work WHERE id = #{id}")
    @ResultMap("submitted")
    List<SubmittedWorkResponse> getById(Integer id);

    // get by student_id and class_id
    @Select("SELECT * " +
            "FROM submitted_work s " +
            "JOIN submittable_work b ON s.submittable_work_id = b.id " +
            "WHERE class_id = #{class_id} AND student_id = #{student_id}")
           // "WHERE student_id = #{student_id} AND class_id = #{class_id}")
    @ResultMap("submitted")
    List<SubmittedWorkByStudentIdAndClassIdResponse> getByStudentIdAndClassId(Integer student_id, Integer class_id);

    // update score
    @Update("UPDATE submitted_work SET status = 2, student_score = #{student_score} " +
            "WHERE id = #{id} AND student_id = #{student_id}")
    Boolean insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest);

    // get By ClassMaterialId
    @Select("SELECT w.*, d.class_material_id  FROM submitted_work w " +
            "JOIN submittable_work a ON w.submittable_work_id = a.id " +
            "JOIN class_materials_detail d ON d.id = a.class_materials_detail_id " +
            "JOIN class_materials c ON c.id = d.class_material_id " +
            "WHERE class_material_id = #{class_material_id}")
    @ResultMap("submitted")
    List<SubmittedWorkByMaterialIdResponse> getByClassMaterialId(Integer class_material_id);
}
