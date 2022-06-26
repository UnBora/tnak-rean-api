package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.UpComingSubmittableWorkResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmittableWorkRepository {
    // select all
    @Select("SELECT * FROM submittable_work")
    @Result(property = "submittable_work_id" , column = "id")
    List<SubmittableWorkResponse> getAll();

    // get by id
    @Select("SELECT * FROM submittable_work WHERE id = #{id}")
    @Result(property = "submittable_work_id" , column = "id")
    SubmittableWorkResponse getById(int id);

    // insert
    @Insert("INSERT INTO submittable_work (class_materials_detail_id,assigned_date,deadline,classroom_id,class_id,score) " +
            "VALUES (#{submittableWork.class_materials_detail_id}, #{submittableWork.assigned_date}, #{submittableWork.deadline},#{submittableWork.classroom_id},#{submittableWork.class_id},#{submittableWork.score})")
    boolean insertSubmittableWork(@Param("submittableWork") SubmittableWorkRequest submittableWorkRequest);

    // update deadline
    @Select("UPDATE submittable_work SET deadline =  #{update.deadline}  WHERE id = #{update.submittable_work_id} Returning *")
    @Result(property = "submittable_work_id" , column = "id")
    SubmittedWorkResponse updateSubmittableWork(@Param("update") SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest);

    //delete
    @Delete("DELETE FROM submittable_work WHERE id = #{id}")
    Boolean delete(int id);

    // get by Class Material Detail Type
    @Select("SELECT * FROM submittable_work WHERE class_materials_detail_id = #{id}")
    @Result(property = "submittable_work_id" , column = "id")
    List<SubmittableWorkResponse> getSubmittableWorkByClassMaterialDetailType(Integer id);


    @Select("SELECT cm.title, cm.description, cm.content, sw.assigned_date, sw.deadline " +
            "FROM class_materials cm" +
            " INNER JOIN class_materials_detail cmd on cm.id = cmd.class_material_id" +
            " INNER JOIN submittable_work sw on cmd.id = sw.class_materials_detail_id" +
            " INNER JOIN class c on cmd.class_id = c.id" +
            " INNER JOIN student s on c.id = s.class_id" +
            " INNER JOIN submitted_work w on s.id = w.student_id" +
            " WHERE s.id = #{studentId}" + " AND s.class_id = #{classId}" +
            " AND s.classroom_id = #{classRoomId}" + " AND w.status = 0" +
            " AND sw.deadline - NOW() >= INTERVAL '0 Days' " +
            " AND sw.deadline - NOW() <= INTERVAL '2 Days'")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(Integer studentId, Integer classRoomId, Integer classId);

    // get By ClassId And ClassId
    @Select("SELECT * FROM submittable_work WHERE class_id = #{class_id} AND classroom_id = #{classroom_id}")
    @Result(property = "submittable_work_id" , column = "id")
    List<SubmittableWorkResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id);

    // update Class Classroom
    @Select("UPDATE submittable_work SET class_id = #{class_id} WHERE id = #{submittable_work_id} returning *")
    @Result(property = "submittable_work_id" , column = "id")
    SubmittedWorkResponse updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest);

}