package com.kshrd.tnakrean.model.student.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    Integer user_id;
    @JsonIgnore
    String name;
    @JsonIgnore
    String username;
    @JsonIgnore
    String email;
}
