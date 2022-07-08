package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface SubmittableWorkRepository {
    // select all
    @Select("SELECT * FROM submittable_work")
    @Result(property = "submittable_work_id", column = "id")
    List<SubmittableWorkResponse> getAll();

    // get by id
    @Select("SELECT * FROM submittable_work WHERE id = #{id}")
    @Result(property = "submittable_work_id", column = "id")
    SubmittableWorkResponse getById(int id);

    // insert
    @Insert("INSERT INTO submittable_work (class_materials_detail_id,assigned_date,deadline,classroom_id,class_id,score) " +
            "VALUES (#{submittableWork.class_materials_detail_id}, #{submittableWork.assigned_date}, #{submittableWork.deadline},#{submittableWork.classroom_id},#{submittableWork.class_id},#{submittableWork.score})")
    boolean insertSubmittableWork(@Param("submittableWork") SubmittableWorkRequest submittableWorkRequest);

    // update deadline
    @Select("UPDATE submittable_work SET deadline =  #{update.deadline}  WHERE id = #{update.submittable_work_id} Returning *")
    @Result(property = "submittable_work_id", column = "id")
    SubmittedWorkResponse updateSubmittableWork(@Param("update") SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest);

    //delete
    @Select("DELETE FROM submittable_work WHERE id = #{submittable_work_id} Returning *")
    @Result(property = "submittable_work_id", column = "id")
    SubmittableWorkResponse delete(Integer submittable_work_id);

    // get by Class Material Detail Type
    @Select("SELECT * FROM submittable_work WHERE class_materials_detail_id = #{id}")
    @Result(property = "submittable_work_id", column = "id")
    List<SubmittableWorkResponse> getSubmittableWorkByClassMaterialDetailType(Integer id);


    @Select("SELECT cm.title as title, cm.description, cm.content, sw.deadline, sw.score " +
            " FROM class_materials cm" +
            " INNER JOIN class_materials_detail cmd on cm.id = cmd.class_material_id" +
            " INNER JOIN submittable_work sw on cmd.id = sw.class_materials_detail_id" +
            " WHERE sw.class_id = #{classId}" +
            " AND sw.classroom_id = #{classRoomId}" +
            " AND sw.id not in (select submittable_work_id from submitted_work where student_id = #{studentId})" +
            " AND sw.deadline - #{currentTime} >= INTERVAL '0'" +
            " AND sw.deadline - #{currentTime} <= INTERVAL '2 Days'")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(@Param("studentId") Integer studentId, @Param("classId") Integer classId, @Param("classRoomId") Integer classRoomId, @Param("currentTime") Timestamp currentTime);

    // get By ClassId
    @Select("SELECT saw.class_id, saw.id, material_id, title, description, score, assigned_date,deadline,\n" +
            "(SELECT count(s.class_material_id) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = material_id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id " +
            "JOIN class_materials_detail cmd ON clm.id = cmd.class_material_id AND cmd.class_id = cmf.class_id AND cmd.classroom_id = cmf.classroom_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id AND saw.class_id = cmd.class_id AND saw.classroom_id = cmd.classroom_id\n" +
            "WHERE (class_materials_type_id = 3 OR class_materials_type_id = 4 OR class_materials_type_id = 5) AND saw.classroom_id = #{classroom_id} AND saw.class_id = #{class_id}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "total_comment", column = "count")
    List<SubmittableWorkByClassResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id);

    // update Class Classroom
    @Select("UPDATE submittable_work SET class_id = #{class_id}, classroom_id = #{classroom_id} WHERE id = #{submittable_work_id} returning *")
    @Result(property = "submittable_work_id", column = "id")
    SubmittableWorkUpdateClassClassroomRequest updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest);

    @Select("SELECT EXISTS(SELECT id FROM class_materials_detail WHERE id = #{class_materials_detail_id})")
    boolean findClassMaterialsDetailId(Integer class_materials_detail_id);

    @Select("SELECT EXISTS(SELECT id FROM classroom WHERE id = #{classroom_id})")
    boolean findClassroomId(Integer classroom_id);

    @Select("SELECT EXISTS(SELECT id FROM class WHERE id = #{class_id})")
    boolean findClassId(Integer class_id);

    @Select("SELECT EXISTS(SELECT id FROM submittable_work WHERE id = #{submittable_work_id})")
    boolean findSubmittableId(Integer submittable_work_id);

    @Select("SELECT EXISTS(SELECT submittable_work_id FROM submitted_work WHERE submittable_work_id = #{submittable_work_id})")
    boolean findSubmittableIdInSubmiitedWork(Integer submittable_work_id);

    // get By TeacherUserId
    @Select("SELECT f.created_by ,saw.class_id, saw.id, material_id, title, description, score, assigned_date,deadline,\n" +
            "(SELECT count(s.class_material_id) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = material_id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id " +
            "JOIN class_materials_detail cmd ON clm.id = cmd.class_material_id AND cmd.class_id = cmf.class_id AND cmd.classroom_id = cmf.classroom_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id AND saw.class_id = cmd.class_id AND saw.classroom_id = cmd.classroom_id\n" +
            "WHERE f.created_by = #{user_id} AND (class_materials_type_id = 3 OR class_materials_type_id = 4 OR class_materials_type_id = 2) ")
    @Result(property = "total_comment", column = "count")
    @Result(property = "submittable_work_id", column = "id")
    List<SubmittableWorkByTeacherResponse> getByTeacherUserId(Integer user_id);
}