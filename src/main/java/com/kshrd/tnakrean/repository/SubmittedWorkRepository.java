package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.*;
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

    // insert student work
    @Insert("INSERT INTO submitted_work(submitted_date,student_id,status,submittable_work_id,student_work)" +
            "VALUES(#{submWork.submitted_date},(SELECT s.id FROM student s JOIN users u on  u.id = s.user_id WHERE u.id = #{userId}),0,#{submWork.submittable_work_id},#{submWork.studentWork,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    @Result(property = "student_id", column = "userId")
    boolean addSubmittedWork(@Param("submWork") SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest,Integer userId);

    // update student work
    @Select("UPDATE submitted_work SET student_work = #{update.studentWork, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{update.submitted_work_id} RETURNING *")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    @Result(property = "submitted_work_id", column = "id")
    SubmittedWorkUpdateStudentWorkRequest updateSubmittedWork(@Param("update") SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

    //delete by id
    @Select("DELETE FROM submitted_work WHERE id = #{id} RETURNING *")
    SubmittedWorkResponse deleteSubmittedWorkId(int id);

    // get by id
    @Select("SELECT * FROM submitted_work WHERE id = #{id}")
    @ResultMap("submitted")
    SubmittedWorkResponse getById(Integer id);

    // get by student_id and class_id
    @Select("SELECT * " +
            "FROM submitted_work s " +
            "JOIN submittable_work b ON s.submittable_work_id = b.id " +
            "WHERE class_id = #{class_id} AND student_id = #{student_id}")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    @Result(property = "submitted_work_id", column = "id")
    List<SubmittedWorkByStudentIdAndClassIdResponse> getByStudentIdAndClassId(Integer student_id, Integer class_id);

    // update score
    @Select("UPDATE submitted_work SET status = 2, student_score = #{student_score} " +
            "WHERE id = #{submitted_work_id}  RETURNING * ")
    @Result(property = "submitted_work_id", column = "id")
    SubmittedWorkStudentScoreRequest insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest);

    // get By ClassMaterialId
    @Select("SELECT w.*, d.class_material_id  FROM submitted_work w " +
            "JOIN submittable_work a ON w.submittable_work_id = a.id " +
            "JOIN class_materials_detail d ON d.id = a.class_materials_detail_id " +
            "JOIN class_materials c ON c.id = d.class_material_id " +
            "WHERE class_material_id = #{class_material_id}")
    @Result(property = "submitted_work_id", column = "id")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    List<SubmittedWorkByMaterialIdResponse> getByClassMaterialId(Integer class_material_id);
    // get By Classroom Class Submittable
    @Select("SELECT w.* , d.class_id , d.classroom_id FROM submitted_work w " +
            "JOIN submittable_work a ON w.submittable_work_id = a.id " +
            "JOIN classroom_detail d ON a.class_id = d.class_id " +
            "JOIN submittable_work c ON d.classroom_id = c.classroom_id " +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND submittable_work_id = #{submittable_work_id}")
    @Result(property = "submitted_work_id", column = "id")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    List<SubmittedWorkByClassroomClassSubmittableResponse> getByClassroomClassSubmittable(Integer classroom_id, Integer class_id, Integer submittable_work_id);

    // get StuScore By Class Classroom
    @Select("SELECT m.title, d.class_material_id, sw.student_id, u.name as student_name, u.gender, sw.student_score, w.class_materials_detail_id, sw.submittable_work_id, sw.id as submitted_work_id, s.classroom_id, s.class_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id " +
            "JOIN submittable_work w ON d.id = w.class_materials_detail_id " +
            "JOIN submitted_work sw ON w.id = sw.submittable_work_id " +
            "JOIN student s ON s.id = sw.student_id " +
            "JOIN users u ON u.id = s.user_id " +
            "WHERE sw.status = 3 AND s.classroom_id = #{classroomId} AND s.class_id = #{classId} AND sw.submittable_work_id = #{submitted_work_id}")
    List<StudentScoreByClassroomIdAndClassIdResponse> getStuScoreByClassClassroom(Integer classroomId, Integer classId, Integer submitted_work_id);
    @Select("SELECT EXISTS(SELECT id FROM submittable_work WHERE id = #{submittable_work_id} )")
    boolean checkIfSubmiitableIdExist(Integer submittable_work_id);
    @Select("SELECT EXISTS(SELECT id FROM submitted_work WHERE id = #{id})")
    boolean findSubmittedId(Integer id);

    // get Result By ClassId
    @Select("SELECT (CASE \n" +
            "     WHEN ( deadline - submitted_date > INTERVAL '0' ) THEN 'handed in '\n" +
            "\t\t WHEN (deadline - submitted_date < INTERVAL '0' ) THEN 'handed in late'\n" +
            " END) as ui_status\n" +
            ", u.name,user_id,u.gender,student_score,class_material_id,title,submittable_work_id,sw.id,sw.status\n" +
            "FROM submitted_work sw \n" +
            "JOIN submittable_work saw ON sw.submittable_work_id = saw.id\n" +
            "JOIN class_materials_detail cmd ON saw.class_materials_detail_id = cmd.id AND cmd.class_id = saw.class_id\n" +
            "JOIN class_materials cm ON cmd.class_material_id = cm.id\n" +
            "JOIN student st ON sw.student_id = st.id \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "WHERE saw.class_id = #{class_id} AND cmd.class_material_id = #{material_id} AND (sw.status = 1 OR sw.status = 2)")
     @Result(property = "submitted_work_id" , column = "id")
    List<SubmittedWorkByClassResponse> getResultByClassId(Integer class_id, Integer material_id);
}
