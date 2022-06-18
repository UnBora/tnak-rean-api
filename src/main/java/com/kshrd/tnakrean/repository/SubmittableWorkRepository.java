package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmittableWorkRepository {
    // select all
    @Select("SELECT * FROM submittable_work")
    List<SubmittableWorkResponse> getAll();

    // select by id
    @Select("SELECT * FROM submittable_work WHERE id = #{id}")
    SubmittableWorkResponse getById(int id);

    // insert
    @Insert("INSERT INTO submittable_work (class_materials_detail_id,assigned_date,deadline) " +
            "VALUES (#{submittableWork.class_materials_detail_id}, #{submittableWork.assigned_date}, #{submittableWork.deadline})")
    boolean insertSubmittableWork(@Param("submittableWork") SubmittableWorkRequest submittableWorkRequest);

    // update deadline
    @Update("UPDATE submittable_work SET deadline =  #{update.deadline}  WHERE id = #{update.id} ")
    boolean updateSubmittableWork(@Param("update") SubmittableWorkUpdateRequest submittableWorkUpdateRequest);

    //delete
    @Delete("DELETE FROM submittable_work WHERE id = #{id}")
    void delete(int id);
}