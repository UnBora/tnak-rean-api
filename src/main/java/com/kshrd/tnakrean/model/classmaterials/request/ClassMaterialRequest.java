package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMaterialRequest {
    @NotBlank(message = "{validation.title.notEmpty}")
    private String title;
    @NotBlank(message = "{validation.description.notEmpty}")
    private String description;
    @Valid
    private ClassMaterialContent classMaterialContent;
    @JsonIgnore
    private LocalDateTime created_date = LocalDateTime.now(ZoneOffset.of("+07:00"));
}
