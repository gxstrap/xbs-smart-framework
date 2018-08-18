package com.xuebusi.framework.ioc;

import com.xuebusi.framework.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassHelper {

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 读取指定包路径下所有的类
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClassList(String packageName) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            String urlPath = packageName.replaceAll("\\.", "\\\\");
            List<File> fileList = FileUtil.readFileDir(packageName);
            for (int i = 0; i < fileList.size(); i++) {
                String filePath = fileList.get(i).getPath();
                if (filePath.endsWith(".class")) {
                    String subFilePath = filePath.substring(filePath.lastIndexOf(urlPath), filePath.lastIndexOf("."));
                    String className = subFilePath.replaceAll("\\\\", ".");
                    Class<?> clazz = Class.forName(className);
                    classList.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }

}
