package com.kshrd.tnakrean.model.submittableWork.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkRequest {
    Integer class_materials_detail_id;
    Date assigned_date;
    Date deadline;
}
