package com.kshrd.tnakrean.model.apiresponse;



import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseError {
    private Date timestamp;
    private String message;
    private String details;
}
