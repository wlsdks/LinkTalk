package com.tony.linktalk.adapter.out.persistence.entity.constant.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TypeConverter implements AttributeConverter {

    @Override
    public Object convertToDatabaseColumn(Object object) {
        return null;
    }

    @Override
    public Object convertToEntityAttribute(Object object) {
        return null;
    }

}
