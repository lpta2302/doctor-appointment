package com.nhom1.doctor_service.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PageGenerator {
    private final int DEFAULT_PAGE = 0;
    private final int DEFAULT_PAGE_SIZE = 10;
    private final Sort DEFAULT_SORT = Sort.unsorted();
}
