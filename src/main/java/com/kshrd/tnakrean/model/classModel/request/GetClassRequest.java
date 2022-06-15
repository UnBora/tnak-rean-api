package com.kshrd.tnakrean.model.classModel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassRequest {
    Integer class_id;
    String class_name;
}
