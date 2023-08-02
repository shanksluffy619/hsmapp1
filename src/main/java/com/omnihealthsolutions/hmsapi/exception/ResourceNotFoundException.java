package com.omnihealthsolutions.hmsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourcename;

    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourcename, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s:%d",resourcename,fieldName,fieldValue));
        this.resourcename = resourcename;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourcename() {
        return resourcename;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }
}
