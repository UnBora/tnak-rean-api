package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.NotificationTypes;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import com.kshrd.tnakrean.model.classmaterials.json.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.spi.LocaleServiceProvider;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialResponse {
    private Integer id;
    private String title;
    private Integer created_by;
    private String description;
    private ClassMaterialType classMaterialType;
    private ClassMaterialContent classMaterialContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_date;
}
