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

import java.sql.Timestamp;
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

    @Select("SELECT cm.title as title, cm.description, cm.content, sw.deadline, sw.score, cm.id, " +
            " (SELECT count(*) FROM comment c " +
            " JOIN class_materials_detail s ON c.class_materials_detail_id = s.id " +
            " WHERE class_material_id = cm.id)" +
            " FROM class_materials cm" +
            " INNER JOIN class_materials_detail cmd on cm.id = cmd.class_material_id" +
            " INNER JOIN submittable_work sw on cmd.id = sw.class_materials_detail_id" +
            " WHERE sw.class_id = #{classId}" +
            " AND sw.classroom_id = #{classRoomId}" +
            " AND sw.id not in (select submittable_work_id from submitted_work where student_id = #{studentId})" +
            " AND sw.deadline - #{currentTime} >= INTERVAL '0'" +
            " AND sw.deadline - #{currentTime} <= INTERVAL '2 Days'")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "total_comments", column = "count")
    @Result(property = "material_id", column = "id")
    List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(@Param("studentId") Integer studentId, @Param("classId") Integer classId, @Param("classRoomId") Integer classRoomId, @Param("currentTime") Timestamp currentTime);

    // get classwork By ClassId
    @Select("SELECT saw.id,title,description,score,assigned_date,deadline,saw.class_id,class_material_id, " +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) " +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND saw.class_id = #{class_id} AND saw.classroom_id = #{classroom_id}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "material_id", column = "class_material_id")
    @Result(property = "total_comment", column = "count")
    List<SubmittableWorkByClassResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id);

    // update Class Classroom
    @Select("UPDATE submittable_work SET class_id = #{class_id}, classroom_id = #{classroom_id} WHERE id = #{submittable_work_id} returning *")
    @Result(property = "submittable_work_id", column = "id")
    SubmittableWorkUpdateClassClassroomRequest updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest);

    // get classwork By TeacherUserId
    @Select("SELECT DISTINCT cm.id,title,description,score,deadline,assigned_date,created_by,saw.class_id,class_name, " +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) " +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "JOIN class cl ON cl.id = saw.class_id " +
            "WHERE created_by = 1 AND (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5)")
    @Result(property = "total_comment", column = "count")
    @Result(property = "material_id", column = "id")
    List<SubmittableWorkByTeacherResponse> getByTeacherUserId(Integer user_id);


    // get By MaterialId
    @Select("SELECT DISTINCT saw.id,title,description,score,assigned_date,deadline,saw.class_id,class_material_id,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND cm.id = #{material_id}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "material_id", column = "class_material_id")
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

    // get By FolderId in ClassId
    @Select("SELECT DISTINCT saw.id,title,description,score,assigned_date,deadline,saw.class_id,class_material_id,folder_id,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "JOIN material_folder mf ON cm.id = mf.material_id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND saw.class_id = #{folderId} AND folder_id = #{folderId} AND saw.classroom_id = #{classroom_id}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "material_id", column = "class_material_id")
    @Result(property = "total_comment", column = "count")
    List<ClassWorkByFolderIDClassIDResponse> getByFolderIdClassId(Integer class_id, Integer classroom_id, Integer folderId);

    // get By FolderId in TeacherId
    @Select("SELECT DISTINCT saw.id,title,description,score,assigned_date,deadline,created_by,class_material_id,folder_id,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "JOIN material_folder mf ON cm.id = mf.material_id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND created_by = #{user_id} AND folder_id = #{folderId}")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "material_id", column = "class_material_id")
    @Result(property = "total_comment", column = "count")
    List<ClassWorkByFolderIDTeacherIDResponse> getByFolderIdTeacherId(Integer user_id, Integer folderId);

    // get ClassWork By StudentId
    @Select("SELECT DISTINCT cmd.class_id, class_material_id, title, description, created_by,saw.id,score,assigned_date,deadline,user_id,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = cm.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_type cmt ON cm.class_materials_type_id = cmt.id\n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN student st ON cmd.class_id = st.id\n" +
            "WHERE (cmt.id = 2 OR cmt.id = 3 OR cmt.id = 4 OR cmt.id = 4) AND user_id = #{user_id}")
    @Result(property = "total_comment", column = "count")
    @Result(property = "submittable_work_id", column = "id")
    @Result(property = "student_id", column = "user_id")
    List<ClassWorkByStudentIdResponse> getClassWorkByStudentId(Integer user_id);

    // get ClassWork have Result By ClassId
    @Select("SELECT DISTINCT cm.id,title,description,saw.class_id,submittable_work_id,created_by\n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "JOIN submitted_work sw ON saw.id = sw.submittable_work_id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND saw.class_id = #{class_id}")
    @Result(property = "class_material_id",column = "id")
    List<ClassWorkResultByClassIdResponse> getClassWorkResultByClassId(Integer class_id);

    // get ClassWork have Result By StudentId
    @Select("SELECT DISTINCT cm.id,title,created_by,description,score,assigned_date,deadline,saw.class_id,sw.id,submittable_work_id,st.user_id\n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN submittable_work saw ON cmd.id = saw.class_materials_detail_id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "JOIN submitted_work sw ON saw.id = sw.submittable_work_id\n" +
            "JOIN student st ON saw.class_id = st.class_id\n" +
            "WHERE (mt.id = 2 OR mt.id = 3 OR mt.id = 4 OR mt.id = 5) AND user_id = #{user_id}")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "student_logged_id",column = "user_id")
    List<ClassWorkResultByStudentIdResponse> getClassWorkResultByStudentId(Integer user_id);



    // create classwork
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{created_date},#{user_id},#{title},#{description},#{typeId}, " +
            "#{classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")
    Boolean createClassworks(Timestamp created_date, String title, String description, Integer user_id, int typeId, ClassMaterialContent classMaterialContent);
}