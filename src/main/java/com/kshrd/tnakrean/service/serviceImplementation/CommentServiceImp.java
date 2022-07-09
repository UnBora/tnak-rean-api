package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.CommentRepository;
import com.kshrd.tnakrean.service.serviceInter.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    final CommentRepository commentRepository;

    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentResponse> getAll() {
        return commentRepository.getAll();
    }

    @Override
    public CommentResponse getById(Integer id) {
        return commentRepository.getById(id);
    }

    @Override
    public CommentResponse deleteById(Integer id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Boolean insert(CommentInsertRequest commentInsertRequest, Integer userId) {
        return commentRepository.insert(commentInsertRequest,userId);
    }

    @Override
    public CommentUpdateRequest update(CommentUpdateRequest commentUpdateRequest) {
        return commentRepository.update(commentUpdateRequest);
    }

    @Override
    public List<CommentByClassClassroomStudentResponse> getByClassClassroomStudent(Integer classroom_id, Integer class_id, Integer student_id) {
        return commentRepository.getByClassClassroomStudent(classroom_id,class_id,student_id);
    }

    @Override
    public List<CommentResponse> getByStudentId(Integer userId) {
        return commentRepository.getByStudentId(userId);
    }

    @Override
    public List<CommentByTeacherResponse> getByTecherId(Integer userId) {
        return commentRepository.getByTecherId(userId);
    }
    @Override
    public List<CommentByMaterialResponse> getByClassMaterialId(Integer material_id) {
        return commentRepository.getByClassMaterialId(material_id);
    }

}
