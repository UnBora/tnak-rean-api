package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationClassRequest {
    @JsonIgnore
    Integer notification_type_id;
    Integer classId;
    @JsonIgnore
    Integer sender_id;
    @NotBlank(message = "{validation.noti.message}")
    String content;
    Integer action_id;
    @JsonIgnore
    LocalDateTime received_date = LocalDateTime.now(ZoneOffset.of("+07:00"));
}
