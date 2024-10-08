package org.copper.auth.dto.response.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class ErrorField {
    private Integer code;
    private Map<String, String> fieldErrors;
}

