package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByClassClassroomStudentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByMaterialIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByTeacherResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentRepository {
    // get all
    @Select("SELECT * FROM comment")
    @Result(property = "comment_id",column = "id")
    List<CommentResponse> getAll();

    // get by id
    @Select("SELECT * FROM comment WHERE id = #{id}")
    @Result(property = "comment_id",column = "id")
    CommentResponse getById(Integer id);

    // delete by id
    @Select("DELETE FROM comment WHERE id = #{id} Returning *")
    CommentResponse deleteById(Integer id);

    // insert
    @Insert("INSERT INTO comment (student_id, class_materials_detail_id, comment, comment_date) " +
            "VALUES ((SELECT s.id FROM student s JOIN users u on  u.id = s.user_id WHERE u.id = #{userId}),#{insert.class_materials_detail_id},#{insert.comment},#{insert.comment_date}) ")
    Boolean insert(@Param("insert") CommentInsertRequest commentInsertRequest, Integer userId);

    // update
    @Select("UPDATE comment " +
            "SET student_id = #{student_id}, comment_date = #{comment_date}, class_materials_detail_id = #{class_materials_detail_id}, comment = #{comment} " +
            "WHERE id = #{comment_id} Returning *")
    @Result(property = "comment_id",column = "id")
    CommentUpdateRequest update(CommentUpdateRequest commentUpdateRequest);

    // get by Class Classroom Student
    @Select("SELECT c.*, d.class_id, d.classroom_id, d.class_material_id " +
            "FROM comment c " +
            "JOIN class_materials_detail d ON c.class_materials_detail_id = d.id " +
            "JOIN classroom_detail cl ON d.class_id = cl.class_id " +
            "JOIN class_materials_detail l ON cl.classroom_id = l.classroom_id " +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND student_id = #{classroom_id}")
    @Result(property = "comment_id",column = "id")
    List<CommentByClassClassroomStudentResponse> getByClassClassroomStudent(Integer classroom_id, Integer class_id, Integer student_id);

    // get By MaterialId
    @Select("SELECT c.*, s.class_material_id FROM comment c " +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id " +
            "WHERE class_material_id = #{class_material_id}")
    @Result(property = "comment_id",column = "id")
    List<CommentByMaterialIdResponse> getByMaterialId(Integer class_material_id);

    // get By Student Id
    @Select("SELECT * FROM comment WHERE student_id = " +
            "(SELECT s.id FROM student s JOIN users u on  u.id = s.user_id WHERE u.id = #{userId}) ")
    @Result(property = "comment_id",column = "id")
    List<CommentResponse> getByStudentId(Integer userId);

    @Select("SELECT c.*, m.created_by, d.class_material_id FROM comment c " +
            "JOIN class_materials_detail d ON c.class_materials_detail_id = d.id " +
            "JOIN class_materials m ON d.class_material_id = m.id " +
            "WHERE created_by = #{userId}")
    @Result(property = "comment_id",column = "id")
    @Result(property = "teacher_id",column = "created_by")
    List<CommentByTeacherResponse> getByTecherId(Integer userId);

    //
    @Select("SELECT EXISTS(SELECT * FROM class_materials_detail WHERE id = #{class_materials_detail_id})")
    boolean ifMaterialsDetailIdExist(Integer class_materials_detail_id);

    //
    @Select("SELECT EXISTS(SELECT * FROM student WHERE user_id = #{userId})")
    boolean ifUserIdExist(Integer userId);
}
