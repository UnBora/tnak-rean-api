package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchClassResponse {
    Integer class_id;
    @NotBlank(message = "{validation.className.notEmpty}")
    String class_name;
    String image;
}
