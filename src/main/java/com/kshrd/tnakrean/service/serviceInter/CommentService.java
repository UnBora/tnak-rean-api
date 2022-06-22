package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getAll();

    CommentResponse getById(Integer id);

    Boolean deleteById(Integer id);

    Boolean insert(CommentInsertRequest commentInsertRequest);

    Boolean update(CommentUpdateRequest commentUpdateRequest);
}
