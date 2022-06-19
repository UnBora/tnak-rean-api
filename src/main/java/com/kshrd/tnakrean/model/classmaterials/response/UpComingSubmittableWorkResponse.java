package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpComingSubmittableWorkResponse {
    String title;
    String description;
    StudentWork content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date assigned_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;

}
