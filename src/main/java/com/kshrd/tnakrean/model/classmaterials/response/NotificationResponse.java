package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.NotificationTypes;
import com.kshrd.tnakrean.model.classmaterials.json.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Integer id;
    private String type;
    private Integer received_id;
    private Content content;
    private String action_on;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime received_date;
}
