package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateTitleDesRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface ClassMaterialRepository {

    @Select("SELECT EXISTS(SELECT id FROM users WHERE id = #{created_by})")
    Boolean checkCreatedBy(Integer created_by);
    @Select("SELECT EXISTS(SELECT id FROM class_materials_type WHERE id = #{class_materials_type_id})")
    Boolean checkMaterialsTypeId(Integer class_materials_type_id);

    @Select("SELECT EXISTS(SELECT * FROM class_materials WHERE id = #{id})")
    Boolean checkMaterialsId(Integer id);

    @Select("SELECT EXISTS(SELECT id FROM classroom WHERE id = #{classroom_id})")
    Boolean findClassroomId(Integer classroom_id);

    @Select("SELECT EXISTS(SELECT id FROM class_materials WHERE id = #{id})")
    boolean findMaterialId(Integer id);

    @Select("SELECT EXISTS(SELECT class_material_id FROM class_materials_detail WHERE class_material_id = #{id})")
    boolean findMaterialIdInMaterialsDetail(Integer id);

    @Select("SELECT EXISTS(SELECT id FROM class WHERE id = #{class_id})")
    boolean findClassId(Integer class_id);
    @Select("SELECT * FROM class_materials_type WHERE id = #{id}")
    ClassMaterialType getClassMaterialTypeById(int id);
    // get all
    @Select("SELECT * FROM class_materials")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getAllClassMaterial();

    //get by id
    @Select("SELECT * FROM class_materials WHERE id = #{id}")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse getClassMaterials(int id);

    // insert course
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{classMaterial.created_date},#{user_id},#{classMaterial.title},#{classMaterial.description},1, " +
            "#{classMaterial.classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    boolean insertCourse(@Param("classMaterial") ClassMaterialRequest classMaterialRequest, Integer user_id);

//    @Select("SELECT * FROM class_materials ORDER BY id DESC LIMIT 1")
//    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
//    ClassMaterialResponse selectClass(Integer id);

    // update title and description
    @Select("UPDATE class_materials SET title = #{classMaterialUpdateRequest.title} ," + " description = #{classMaterialUpdateRequest.description} " +
            "WHERE id = #{classMaterialUpdateRequest.class_material_id} returning *")
    @Result(property = "class_material_id",column = "id")
    ClassMaterialUpdateTitleDesRequest updateClassMaterial(@Param("classMaterialUpdateRequest") ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest);

    @Select("SELECT * FROM class_materials WHERE id=#{id}")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse selectResponseAfterUpdate(int id);

    // delete by id
    @Select("DELETE FROM class_materials WHERE id = #{id} returning *")
    ClassMaterialResponse deleteById(@Param("id") Integer id);

    // Get All Class Material By teacherUserId and ClassMaterialTypeId
    @Select("SELECT * FROM class_materials WHERE created_by = #{created_by} AND class_materials_type_id = #{class_materials_type_id}")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);

    // Get All Class Material By teacher User Id
    @Select("SELECT cm.id,cmd.id as class_materials_detail_id, title,description,created_by,cmd.class_id\n" +
            "(SELECT count(*) FROM comment c \n" +
            "WHERE class_materials_detail_id = cmd.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_type cmd ON cm.class_materials_type_id = cmd.id\n" +
            "JOIN class_materials_detail cmd ON cmd.class_material_id = cm.id " +
            "WHERE created_by = #{user_id} AND class_materials_type_id = 1")
    @Result(property = "total_comment", column = "count")
    @Result(property = "material_id", column = "id")
    List<ClassMaterialByTeacherResponse> getAllClassMaterialByTeacherUserId(@Param("user_id") Integer user_id);

    // Get All Class Material class_materials_type_id
    @Select("SELECT * FROM class_materials WHERE class_materials_type_id = #{class_materials_type_id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    @Result(property = "class_material_id",column = "id")
    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    //Get All Class Material Course By teacher id and Class Id
    @Select("SELECT class_id, material_id, title,description,clm.created_by ,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = clm.id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id\n" +
            "WHERE class_materials_type_id = 1 AND clm.created_by = #{teacher_id} AND class_id = #{class_id}")
    @Result(property = "total_comment", column = "count")
    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id);
    // update content
    @Select("UPDATE class_materials SET content = #{classMaterialContent, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{class_material_id} returning *")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id", column = "id")
    ClassMaterialUpdateContentRequest updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

    // get By ClassId
    @Select("SELECT d.classroom_id, d.class_id, t.type, m.* FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE class_id = #{class_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByClassIdResponse> getByClassId(@Param("class_id") Integer class_id);

    // get By ClassId And ClassroomId
    @Select("SELECT cmd.class_id,cmd.id as class_materials_detail_id, class_material_id,cmd.id,title,description,created_by ,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "WHERE class_materials_detail_id = cmd.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_type cmt ON cm.class_materials_type_id = cmt.id\n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "WHERE class_materials_type_id = 1 AND cmd.class_id = #{class_id} AND cmd.classroom_id = #{classroom_id} ")
    @Result(property = "total_comment", column = "count")
    @Result(property = "material_id", column = "class_material_id")
    List<ClassMaterialByClassIdAndClassroomIdResponse> getByClassIdAndClassroomId(Integer class_id, Integer classroom_id);

    // get By MaterialType And ClassId
    @Select("SELECT d.classroom_id, d.class_id, t.type, m.* FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE class_materials_type_id = #{class_materials_type_id} AND class_id = #{class_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByClassIdAndMaterialTypeResponse> getByMaterialTypeAndClassId(Integer class_id, Integer class_materials_type_id);

    // get by (student)user id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE user_id = #{user_id}")
    @Result(property = "student_user_id", column = "user_id")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "classMaterialType", column = "class_materials_type_id" , one = @One(select = "getClassMaterialTypeById"))
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id);

    // get by (student)user_id class_id classroom_id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND user_id = #{user_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "student_user_id", column = "user_id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id);

    // assign Course
    @Select("SELECT EXISTS(SELECT id FROM class_materials_detail WHERE class_id = #{class_id} AND class_material_id = #{class_material_id}) ")
    boolean findClassIdANDMaterialIdInMD(int class_id, int class_material_id);
    @Insert("INSERT INTO class_materials_detail (class_material_id,class_id,classroom_id)\n" +
            "VALUES (#{class_material_id},#{class_id},#{class_room_id})")
    boolean assignCourse(int class_material_id, int class_room_id, int class_id);

    // create course in classId
    @Select("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{createdDate},#{user_id},#{title},#{description},1, " +
            "#{content,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler}) RETURNING id")
   // @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    Integer creatNewCourse(Integer user_id, String title, String description, Timestamp createdDate, ClassMaterialContent content);

    @Select("INSERT INTO class_materials_detail (class_material_id,class_id,classroom_id)\n" +
            "VALUES (#{materialId},#{class_id},#{classroom_id})")
    Integer createCourseByClass(Integer materialId, int classroom_id, int class_id);

    // set material to folder
    @Select("SELECT EXISTS(SELECT material_id FROM material_folder WHERE folder_id = #{folder_id} AND material_id = #{material_id})\n")
    boolean findFolderIdAndMaterialIdInMF(int folder_id, int material_id);
    @Insert("INSERT INTO material_folder (folder_id,material_id)\n" +
            "VALUES (#{folder_id},#{material_id}) ")
    boolean setMaterialToFolder(int folder_id, int material_id);

    // get Course By FolderId with Teacher id
    @Select("SELECT  DISTINCT cmd.id,material_id,created_by,class_materials_type_id,title,description,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "WHERE class_materials_detail_id = cmd.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN material_folder mf on cm.id = mf.material_id\n" +
            "JOIN class_materials_detail cmd ON cmd.class_material_id = cm.id " +
            "JOIN class_materials_type mt on cm.class_materials_type_id = mt.id\n" +
            "WHERE mt.id = 1 AND created_by = #{user_id} AND folder_id = #{folder_id}")
    @Result(property = "total_comment", column = "count")
    @Result(property = "class_materials_detail_id", column = "id")
    List<ClassMaterialByTeacherResponse> CourseMaterialByFolderId(int folder_id,int user_id);

    // get Course Material By FolderId In Class
    @Select("SELECT DISTINCT cmd.id,material_id,created_by,class_materials_type_id,title,description,class_id,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "WHERE class_materials_detail_id = cmd.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN material_folder mf on cm.id = mf.material_id\n" +
            "JOIN class_materials_type mt on cm.class_materials_type_id = mt.id\n" +
            "JOIN class_materials_detail cmd on cm.id = cmd.class_material_id\n" +
            "WHERE mt.id = 1 AND class_id = #{class_id} AND classroom_id = #{classroom_id} AND folder_id = #{folder_id}")
    @Result(property = "total_comment", column = "count")
    @Result(property = "class_materials_detail_id", column = "id")

    List<ClassMaterialByClassIdAndClassroomIdResponse> getCourseMaterialByFolderIdInClass(Integer folder_id, Integer class_id,Integer classroom_id);

    // get Course By StudentId
    @Select("SELECT DISTINCT cmd.class_id,cmd.id as class_materials_detail_id, class_material_id, title, description, created_by,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "WHERE class_materials_detail_id = cmd.id) \n" +
            "FROM class_materials cm \n" +
            "JOIN class_materials_type cmt ON cm.class_materials_type_id = cmt.id\n" +
            "JOIN class_materials_detail cmd ON cm.id = cmd.class_material_id\n" +
            "JOIN student st ON cmd.class_id = st.class_id\n" +
            "WHERE class_materials_type_id = 1 AND user_id = #{user_id}")
    @Result(property = "total_comment", column = "count")
    List<CourseByStudentIdResponse> getCourseByStudentId(Integer user_id);

    // delete By Created By And MaterialId
    @Select("SELECT EXISTS(SELECT FROM class_materials WHERE id = #{material_id} AND created_by = #{teacher_id})")
    Boolean findMaterialIdWithCreatedBy(Integer material_id, Integer teacher_id);
    @Delete("DELETE FROM class_materials WHERE id = #{material_id} AND created_by = #{teacher_id} ")
    Boolean deleteByCreatedByAndMaterialId(Integer material_id, Integer teacher_id);

    @Select("SELECT EXISTS(SELECT id FROM folder WHERE id = #{folder_id})")
    Boolean findFolderId(int folder_id);
}
