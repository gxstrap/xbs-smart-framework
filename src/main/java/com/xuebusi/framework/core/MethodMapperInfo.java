package com.xuebusi.framework.core;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

public class MethodMapperInfo implements Serializable {

    private String methodName;

    private Object returnType;

    private Method method;

    private List<ParamMapperInfo> paramMapperInfoList;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getReturnType() {
        return returnType;
    }

    public void setReturnType(Object returnType) {
        this.returnType = returnType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<ParamMapperInfo> getParamMapperInfoList() {
        return paramMapperInfoList;
    }

    public void setParamMapperInfoList(List<ParamMapperInfo> paramMapperInfoList) {
        this.paramMapperInfoList = paramMapperInfoList;
    }
}
