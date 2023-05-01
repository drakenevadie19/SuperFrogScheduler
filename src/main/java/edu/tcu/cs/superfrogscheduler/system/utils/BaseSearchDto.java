package edu.tcu.cs.superfrogscheduler.system.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class BaseSearchDto {

    // sortBy validation will be provided by the child class
    private String sortBy;

    // set default value to ASC if not provided
    @NotNull
    @Pattern(regexp = "ASC|DSC",
            message = "sortDirection can only be ASC|DSC")
    private String sortDirection = "ASC";

    @NotNull
    private Integer pageNumber = 0;

    @NotNull
    private Integer pageSize = 5;

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

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
