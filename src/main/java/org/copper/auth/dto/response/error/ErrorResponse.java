package org.copper.auth.dto.response.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse{
    private Integer code;
    private String message;
    private String timestamp;
    private String path;
    private String trace;
}
