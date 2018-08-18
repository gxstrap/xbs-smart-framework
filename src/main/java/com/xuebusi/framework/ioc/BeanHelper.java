package com.xuebusi.framework.ioc;

import com.xuebusi.framework.annotation.*;
import com.xuebusi.framework.core.MethodMapperInfo;
import com.xuebusi.framework.core.ParamMapperInfo;
import com.xuebusi.framework.util.PropsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class BeanHelper {

    /**
     * 存放bean实例
     */
    private static Map<Class<?>, Object> beanMap = new HashMap<>();
    private static Map<Class, Map<String, MethodMapperInfo>> requestMappingMap = new HashMap<>();
    private static Map<String, MethodMapperInfo> methodMapperInfoMap = new HashMap<>();

    static {
        getBeanInstanceMap(beanMap);
        setAutowired(beanMap, requestMappingMap, methodMapperInfoMap);
    }

    /**
     * 获取IOC容器中的所有的Bean实例集合
     *
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 根据Class获取bean的实例
     * @param clazz
     * @return
     */
    public static Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取所有类和方法上的请求url映射
     * @return
     */
    public static Map<Class, Map<String, MethodMapperInfo>> getRequestMappingMap() {
        return requestMappingMap;
    }

    /**
     * 获取所有的bean实例集合
     *
     * @return
     */
    public static List<Object> getBeanList() {
        return (List<Object>) getBeanMap().values();
    }

    /**
     * 获取指定包下的所有
     *
     * @return
     */
    private static Map<Class<?>, Object> getBeanInstanceMap(Map<Class<?>, Object> beanMap) {
        return getBeanInstanceMap(PropsUtil.getPackageName(), beanMap);
    }

    /**
     * 将指定包下的所有类实例化之后封装到一个Map集合
     *
     * @param packageName
     * @return
     */
    private static Map<Class<?>, Object> getBeanInstanceMap(String packageName, Map<Class<?>, Object> beanMap) {
        try {
            List<Class<?>> classList = ClassHelper.getClassList(packageName);
            for (Class<?> clazz : classList) {
                if (clazz.isAnnotationPresent(Controller.class)
                        || clazz.isAnnotationPresent(Service.class)
                        || clazz.isAnnotationPresent(Repository.class)
                        || clazz.isAnnotationPresent(Component.class)
                        )
                    beanMap.put(clazz, clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanMap;
    }

    /**
     * 实例化添加了 Autowired 注解的类
     *
     * @param beanInstanceMap
     */
    public static void setAutowired(Map<Class<?>, Object> beanInstanceMap, Map<Class, Map<String, MethodMapperInfo>> requestMappingMap, Map<String, MethodMapperInfo> methodMapperInfoMap) {
        Set<Class<?>> keySet = beanInstanceMap.keySet();
        try {
            for (Class<?> clazz : keySet) {
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    requestMappingMap.put(clazz, getMethodMapping(clazz, methodMapperInfoMap));
                }
                Object instance = beanInstanceMap.get(clazz);
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Class<?> fieldBeanClass = field.getType();
                        Object fieldBeanInstance = beanInstanceMap.get(fieldBeanClass);
                        if (fieldBeanInstance != null) {
                            field.setAccessible(true);
                            field.set(instance, fieldBeanInstance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定类的方法和请求路径的映射集合
     *
     * @param clazz
     * @return
     */
    private static Map<String, MethodMapperInfo> getMethodMapping(Class<?> clazz, Map<String, MethodMapperInfo> methodMapperInfoMap) {
        RequestMapping clazzAnnotation = clazz.getAnnotation(RequestMapping.class);
        String clazzMapping = clazzAnnotation.value();
        if (!clazzMapping.startsWith("/")) {
            clazzMapping = "/" + clazzMapping;
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                String methodMapping = methodAnnotation.value();
                if (!methodMapping.startsWith("/")) {
                    methodMapping = "/" + methodMapping;
                }

                MethodMapperInfo methodMapperInfo = new MethodMapperInfo();
                methodMapperInfo.setMethod(method);
                methodMapperInfo.setMethodName(method.getName());

                List<ParamMapperInfo> paramMapperInfoList = new ArrayList<>();
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    paramMapperInfoList.add(new ParamMapperInfo(parameter.getName(), parameter.getType()));
                }

                methodMapperInfo.setParamMapperInfoList(paramMapperInfoList);
                methodMapperInfoMap.put(clazzMapping + methodMapping, methodMapperInfo);
            }
        }
        return methodMapperInfoMap;
    }

    /**
     * 根据请求url获取对应的方法
     * @param requestUri
     * @return
     */
    public static MethodMapperInfo getMappingMethod(String requestUri) {
        return BeanHelper.methodMapperInfoMap.get(requestUri);
    }
}
