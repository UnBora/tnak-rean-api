package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAll();

    CommentResponse getById(Integer id);

    CommentResponse deleteById(Integer id);

    Boolean insert(CommentInsertRequest commentInsertRequest,Integer userId);

    CommentUpdateRequest update(CommentUpdateRequest commentUpdateRequest);

    List<CommentByClassClassroomStudentResponse> getByClassClassroomStudent(Integer classroom_id, Integer class_id, Integer student_id);

    List<CommentResponse> getByStudentId(Integer userId);

    List<CommentByTeacherResponse> getByTecherId(Integer userId);

    CommentCountResponse getCountComment(Integer class_material_id, Integer class_id, Integer classroom_id);

    List<CommentByMaterialResponse> getByClassMaterialId(Integer material_id);
}
