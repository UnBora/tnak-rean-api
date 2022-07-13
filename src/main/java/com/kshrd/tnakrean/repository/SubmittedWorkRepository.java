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
    @Select("SELECT EXISTS(SELECT id FROM submittable_work WHERE id = #{submittable_work_id} )")
    boolean checkIfSubmiitableIdExist(Integer submittable_work_id);
    @Select("SELECT EXISTS(SELECT id FROM submitted_work WHERE id = #{id})")
    boolean findSubmittedId(Integer id);
    //get all
    @Select("SELECT * FROM submitted_work")
    @Results(id = "submitted", value = {
            @Result(property = "studentWork", column = "student_work" , typeHandler = JsonTypeHandler.class),
            @Result(property = "submitted_work_id", column = "id")
    })
    List<SubmittedWorkResponse> getAllSubmittedWork();

    // get by student id
    @Select("")
    List<SubmittedWorkResponse> getSubmittedByStudentId(Integer material_id, Integer user_id);

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

    // update score
    @Select("UPDATE submitted_work SET status = 1, student_score = #{student_score} " +
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

    // get Result By ClassId and Material_id
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
    List<SubmittedWorkResultByClassResponse> getResultByClassId(Integer class_id, Integer material_id);

    // get NotGraded By ClassId
    @Select("SELECT cm.created_by,class_material_id,saw.class_id, title,submittable_work_id,user_id,name,sw.id,sw.status " +
            "FROM submitted_work sw " +
            "JOIN submittable_work saw ON sw.submittable_work_id = saw.id " +
            "JOIN class_materials_detail cmd ON saw.class_materials_detail_id = cmd.id AND cmd.class_id = saw.class_id\n" +
            "JOIN class_materials cm ON cmd.class_material_id = cm.id\n" +
            "JOIN student st ON sw.student_id = st.id \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "WHERE sw.status = 0 AND class_material_id = #{material_id} AND saw.class_id = #{class_id} AND cm.created_by = #{userId}")
    @Result(property = "submitted_work_id" , column = "id")
    List<SubmittedWorkNotGradedByClassResponse> getNotGradedByClassId(Integer class_id, Integer material_id,Integer userId);

    // view Student Work
    @Select("SELECT saw.class_id, sw.id,st.user_id, u.name, class_material_id , student_work,  title FROM submitted_work sw \n" +
            "JOIN submittable_work saw ON sw.submittable_work_id = saw.id\n" +
            "JOIN class_materials_detail cmd ON saw.class_materials_detail_id = cmd.id AND cmd.class_id = saw.class_id\n" +
            "JOIN class_materials cm ON cmd.class_material_id = cm.id\n" +
            "JOIN student st ON sw.student_id = st.id \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "WHERE sw.id = #{submitted_work_id}")
    @Result(property = "submitted_work_id" , column = "id")
    @Result(property = "studentWork", column = "student_work", typeHandler = JsonTypeHandler.class)
    List<StudentWorkBySubmittedWorkIdResponse> viewStudentWork(Integer submitted_work_id);

    // get Result By StudentId and MaterialId
    @Select("SELECT DISTINCT class_material_id,title,sw.id,submittable_work_id,st.user_id,u.name,u.gender,student_score,sw.status,saw.class_id,stc.user_id as login_id, " +
            "(CASE \n" +
            "     WHEN ( deadline - submitted_date > INTERVAL '0' ) THEN 'handed in '\n" +
            "\t\t WHEN (deadline - submitted_date < INTERVAL '0' ) THEN 'handed in late'\n" +
            " END) as ui_status\n" +
            "FROM submitted_work sw \n" +
            "JOIN submittable_work saw ON sw.submittable_work_id = saw.id\n" +
            "JOIN class_materials_detail cmd ON saw.class_materials_detail_id = cmd.id AND cmd.class_id = saw.class_id\n" +
            "JOIN class_materials cm ON cmd.class_material_id = cm.id\n" +
            "JOIN student st ON sw.student_id = st.id \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "JOIN student stc ON stc.class_id = cmd.class_id\n" +
            "WHERE cmd.class_material_id = #{material_id} AND stc.user_id = #{user_id} AND (sw.status = 1 OR sw.status = 2)")
    @Result(property = "submitted_work_id" , column = "id")
    @Result(property = "submitted_work_status" , column = "status")
    @Result(property = "login_student_id" , column = "login_id")
    List<SubmittedWorkResultByStudentIdResponse> getResultByStudentIdMaterialId(Integer material_id, Integer user_id);
}
