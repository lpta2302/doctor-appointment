package com.nhom1.clinic_service.handler;

import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponse(
    Integer businessErrorCode,
    String businessErrorDescription,
    String error,
    Set<String> validationErrors,
    Map<String, String> errors
) {
    
}
