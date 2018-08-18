package com.xuebusi.framework.core;

import java.io.Serializable;

public class ParamMapperInfo implements Serializable {

    private String paramName;

    private Class<?> paramType;

    public ParamMapperInfo() {
    }

    public ParamMapperInfo(String paramName, Class<?> paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Class<?> getParamType() {
        return paramType;
    }

    public void setParamType(Class<?> paramType) {
        this.paramType = paramType;
    }
}
