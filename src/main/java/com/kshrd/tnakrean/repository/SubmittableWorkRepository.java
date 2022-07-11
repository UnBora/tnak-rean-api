package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper
@Repository
public interface SubmittableWorkRepository {
    @Select("SELECT EXISTS(SELECT id FROM class_materials_detail WHERE id = #{class_materials_detail_id})")
    boolean findClassMaterialsDetailId(Integer class_materials_detail_id);

    @Select("SELECT EXISTS(SELECT id FROM classroom WHERE id = #{classroom_id})")
    boolean findClassroomId(Integer classroom_id);

    @Select("SELECT EXISTS(SELECT id FROM class WHERE id = #{class_id})")
    boolean findClassId(Integer class_id);
    @Select("SELECT EXISTS(SELECT id FROM class_materials_detail WHERE class_id = #{class_id} AND #{class_material_id})")
    boolean findClassIdANDMaterialIdInMD(int class_id,int class_material_id);

    @Select("SELECT EXISTS(SELECT id FROM submittable_work WHERE id = #{submittable_work_id})")
    boolean findSubmittableId(Integer submittable_work_id);

    @Select("SELECT EXISTS(SELECT submittable_work_id FROM submitted_work WHERE submittable_work_id = #{submittable_work_id})")
    boolean findSubmittableIdInSubmiitedWork(Integer submittable_work_id);

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

    // get all By TeacherUserId
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
            "WHERE f.created_by = #{user_id} AND (class_materials_type_id = 3 OR class_materials_type_id = 4 OR class_materials_type_id = 5 OR class_materials_type_id = 2) ")
    @Result(property = "total_comment", column = "count")
    @Result(property = "submittable_work_id", column = "id")
    List<SubmittableWorkByTeacherResponse> getByTeacherUserId(Integer user_id);

    // get By MaterialId
    @Select("SELECT f.created_by, saw.class_id, saw.id, material_id, title, description, score, assigned_date,deadline, " +
            "(SELECT count(s.class_material_id) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = material_id) " +
            "FROM folder f " +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id " +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id " +
            "JOIN material_folder mf ON f.id = mf.folder_id " +
            "JOIN class_materials clm ON mf.material_id = clm.id " +
            "JOIN class_materials_detail cmd ON clm.id = cmd.class_material_id AND cmd.class_id = cmf.class_id AND cmd.classroom_id = cmf.classroom_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id AND saw.class_id = cmd.class_id AND saw.classroom_id = cmd.classroom_id \n" +
            "WHERE material_id = #{material_id}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "total_comment", column = "count")
    List<SubmittableWorkByMaterialResponse> getByClassMaterialId(Integer material_id);

    // get All By ClassId TeacherUserId
    @Select("SELECT clm.created_by,f.created_by ,saw.class_id, saw.id, material_id, title, description, score, assigned_date,deadline,\n" +
            "(SELECT count(s.class_material_id) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = material_id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id \n" +
            "JOIN class_materials_detail cmd ON clm.id = cmd.class_material_id AND cmd.class_id = cmf.class_id AND cmd.classroom_id = cmf.classroom_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id AND saw.class_id = cmd.class_id AND saw.classroom_id = cmd.classroom_id\n" +
            "WHERE clm.created_by = #{user_id} AND saw.class_id = #{class_id} AND (class_materials_type_id = 3 OR class_materials_type_id = 4 OR class_materials_type_id = 5) ")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "total_comment", column = "count")
    List<SubmittableWorkByClassIdTeacherIdResponse> getAllByClassIdTeacherUserId(Integer user_id, Integer class_id);

    // assign submittable
    @Select("insert into class_materials_detail" +
            "(classroom_id, class_material_id, class_id)\n" +
            "VALUES (#{class_room_id},#{class_material_id},#{class_id}) returning id")
    Integer insertInotClassMaterialDetail(int class_material_id, int class_room_id, int class_id);

    @Select("insert into submittable_work " +
            "(class_materials_detail_id, assigned_date, deadline, classroom_id, class_id, score)\n" +
            "VALUES (#{mdt}, #{assigndate}, #{deadline}, #{class_room_id}, #{class_id},#{score});")
    Integer insertInotSubmitableWork(Integer mdt,Timestamp assigndate, Timestamp deadline, int class_room_id, int class_id, float score);

    // create classworks
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{classMaterial.created_date},#{user_id},#{classMaterial.title},#{classMaterial.description},#{typeId}, " +
            "#{classMaterial.classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    boolean createQuiz(@Param("classMaterial") ClassMaterialRequest classMaterialRequest, Integer user_id,Integer typeId);

    //
    @Select("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{createdDate},#{user_id},#{title},#{description},#{material_type_id}, " +
            "#{content,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler}) RETURNING id")
    Integer createNewClasswork(int material_type_id, Integer user_id, String title, String description, Timestamp createdDate, ClassMaterialContent content);
    @Select("INSERT INTO class_materials_detail (class_material_id,class_id,classroom_id)\n" +
            "VALUES (#{materialId},#{class_id},#{classroom_id}) RETURNING id")
    Integer createClassworkInMaterialDetail(Integer materialId, int classroom_id, int class_id);
    @Select("INSERT INTO submittable_work (class_materials_detail_id,assigned_date,deadline,class_id,classroom_id,score)\n" +
            "VALUES (#{mdt},#{createdDate},#{deadline},#{classroom_id},#{class_id},#{score}) ")
    Integer createClassworkByClass(Integer mdt, Timestamp createdDate, Timestamp deadline, int classroom_id, int class_id, float score);
}