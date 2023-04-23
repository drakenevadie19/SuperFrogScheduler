package edu.tcu.cs.superfrogscheduler.system.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BaseSearchDtoToPagination implements Converter<BaseSearchDto, Pagination> {

    @Override
    public Pagination convert(BaseSearchDto source) {
        return Pagination.createPagination(
                source.getPageNumber(),
                source.getPageSize(),
                source.getSortBy(),
                source.getSortDirection());
    }

}
