package com.subaxpress.lib.shared;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class UtilFilter {

    public static MappingJacksonValue allExcept(Object object, String filterId, String... properties) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.
                serializeAllExcept(properties);

        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterId, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    public static MappingJacksonValue showOnly(Object object, String filterId, String... properties) {
        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
            SimpleBeanPropertyFilter.filterOutAllExcept(properties);

        FilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter(filterId, simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}
