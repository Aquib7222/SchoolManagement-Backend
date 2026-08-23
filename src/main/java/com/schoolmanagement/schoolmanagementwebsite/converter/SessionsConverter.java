package com.schoolmanagement.schoolmanagementwebsite.converter;

import com.schoolmanagement.schoolmanagementwebsite.enums.Sessions;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SessionsConverter
        implements AttributeConverter<Sessions, String> {

    @Override
    public String convertToDatabaseColumn(Sessions session) {

        if (session == null) {
            return null;
        }

        return session.getValue();
    }

    @Override
    public Sessions convertToEntityAttribute(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return Sessions.fromValue(value);
    }
}