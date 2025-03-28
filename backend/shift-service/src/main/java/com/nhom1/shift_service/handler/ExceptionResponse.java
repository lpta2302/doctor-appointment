package com.nhom1.shift_service.handler;

import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponse(
    Integer bussinessErrorCode,
    String bussinessErrorDescription,
    String error,
    Set<String> validationErrors,
    Map<String, String> errors
) {
    
}
