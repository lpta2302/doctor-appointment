package com.nhom1.doctor_service.common;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.Builder;

@Builder
public record PageResponse<T>(
    List<T> content,    
    int pageNumber,     
    int pageSize,       
    long totalElements, 
    int totalPages,     
    boolean isFirst,    
    boolean isLast      
) {
    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
    }
    public static <T> PageResponse<T> fromPage(Page<?> page, List<T> content) {
        return new PageResponse<>(
            content,
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
    }
}
