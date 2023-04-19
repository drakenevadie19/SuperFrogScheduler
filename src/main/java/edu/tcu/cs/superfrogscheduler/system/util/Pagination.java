package edu.tcu.cs.superfrogscheduler.system.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private static final int MAX_SIZE = 50;

    private static final int MIN_SIZE = 20;

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private String sortBy;

    private String sortDirection;

    private List elements;

    public Pagination(int pageNumber, int pageSize, int totalPage, String sortBy, String sortDirection, List elements) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPage;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.elements = elements;
    }


    public static Pagination createPagination(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if(pageSize > MAX_SIZE) {
            pageSize = MAX_SIZE;
        }

        else if(pageSize < MIN_SIZE) {
            pageSize = MIN_SIZE;
        }

        return new Pagination(pageNumber, pageSize, 0, sortBy, sortDirection, new ArrayList<>());
    }

    public Pageable format() {
        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public void setResult(int totalPage, List elements) {
        this.setTotalPages(totalPage);
        this.setElements(elements);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List getElements() {
        return elements;
    }

    public void setElements(List elements) {
        this.elements = elements;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

}

