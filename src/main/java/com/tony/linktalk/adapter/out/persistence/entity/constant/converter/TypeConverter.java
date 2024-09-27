package com.tony.linktalk.adapter.out.persistence.entity.constant.converter;

import com.tony.linktalk.adapter.out.persistence.entity.constant.post.MediaType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TypeConverter implements AttributeConverter<MediaType, MediaType> {

    @Override
    public MediaType convertToDatabaseColumn(MediaType mediaType) {
        return null;
    }

    @Override
    public MediaType convertToEntityAttribute(MediaType mediaType) {
        return null;
    }
}
