package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByClassClassroomStudentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
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
    public Boolean deleteById(Integer id) {
        return commentRepository.deleteById(id);
    }

    @Override
    public Boolean insert(CommentInsertRequest commentInsertRequest) {
        return commentRepository.insert(commentInsertRequest);
    }

    @Override
    public Boolean update(CommentUpdateRequest commentUpdateRequest) {
        return commentRepository.update(commentUpdateRequest);
    }

    @Override
    public List<CommentByClassClassroomStudentResponse> getByClassClassroomStudent(Integer classroom_id, Integer class_id, Integer student_id) {
        return commentRepository.getByClassClassroomStudent(classroom_id,class_id,student_id);
    }

}
