package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {
    String name;
    String email;
    String gender;
}
