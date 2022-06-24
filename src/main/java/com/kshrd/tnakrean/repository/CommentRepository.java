package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByClassClassroomStudentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentRepository {
    // get all
    @Select("SELECT * FROM comment")
    List<CommentResponse> getAll();

    // get by id
    @Select("SELECT * FROM comment WHERE id = #{id}")
    CommentResponse getById(Integer id);

    // delete by id
    @Delete("DELETE FROM comment WHERE id = #{id}")
    Boolean deleteById(Integer id);

    // insert
    @Insert("INSERT INTO comment (student_id, comment_date, class_materials_detail_id, comment) " +
            "VALUES (#{student_id},#{comment_date},#{class_materials_detail_id},#{comment}) ")
    Boolean insert(CommentInsertRequest commentInsertRequest);

    // update
    @Update("UPDATE comment " +
            "SET student_id = #{student_id}, comment_date = #{comment_date}, class_materials_detail_id = #{class_materials_detail_id}, comment = #{comment} " +
            "WHERE id = #{id}")
    Boolean update(CommentUpdateRequest commentUpdateRequest);

    // get by Class Classroom Student
    @Select("SELECT c.*, d.class_id, d.classroom_id, d.class_material_id " +
            "FROM comment c " +
            "JOIN class_materials_detail d ON c.class_materials_detail_id = d.id " +
            "JOIN classroom_detail cl ON d.class_id = cl.class_id " +
            "JOIN class_materials_detail l ON cl.classroom_id = l.classroom_id " +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND student_id = #{classroom_id}")
    List<CommentByClassClassroomStudentResponse> getByClassClassroomStudent(Integer classroom_id, Integer class_id, Integer student_id);
}
