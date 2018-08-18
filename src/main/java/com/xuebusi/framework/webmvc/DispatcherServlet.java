package com.xuebusi.framework.webmvc;

import com.alibaba.fastjson.JSON;
import com.xuebusi.framework.core.MethodMapperInfo;
import com.xuebusi.framework.ioc.BeanHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init()=============" + config.getServletName());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMapping(req, resp);
    }

    private void doMapping(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        MethodMapperInfo methodMapperInfo = BeanHelper.getMappingMethod(requestURI);
        if (methodMapperInfo != null) {
            List<String> paramList = getQueryParams(req.getQueryString(), methodMapperInfo);
            if (methodMapperInfo != null) {
                doInvoke(methodMapperInfo, paramList, resp);
            }
        }
    }

    private List<String> getQueryParams(String queryString, MethodMapperInfo methodMapperInfo) {
        //List<ParamMapperInfo> paramMapperInfoList = methodMapperInfo.getParamMapperInfoList();

        List<String> paramList = new ArrayList<>();
        if (queryString != null && queryString.length() > 0) {
            String[] split = queryString.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    String splitArr = split[i];
                    if (splitArr != null && splitArr.length() > 0) {
                        String[] spl = splitArr.split("=");
                        if (spl != null && spl.length == 2) {
                            //String paramName = spl[0];
                            String paramVal = spl[1];
                            paramList.add(paramVal);
                            /*for (ParamMapperInfo paramMapperInfo : paramMapperInfoList) {
                                String pName = paramMapperInfo.getParamName();
                                Class<?> pType = paramMapperInfo.getParamType();
                                if (paramName.equals(pName) && ("java.lang.String".equals(pType.getTypeName()))) {
                                    paramList.add(paramVal);
                                }
                            }*/
                        }
                    }
                }
            }
        }
        return paramList;
    }

    private Object doInvoke(MethodMapperInfo methodMapperInfo, List<String> paramList, HttpServletResponse resp) {
        Method method = methodMapperInfo.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        try {
            Object result = method.invoke(BeanHelper.getBean(clazz), paramList.toArray());
            String jsonResult = JSON.toJSONString(result);
            System.out.println(jsonResult);
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write(jsonResult);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
