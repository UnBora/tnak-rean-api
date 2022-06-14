package com.kshrd.tnakrean.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWork {
    Integer id;
    Integer class_materials_detail_id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date assigned_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date deadline;
}
