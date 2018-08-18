package com.xuebusi.framework.util;

import com.xuebusi.framework.ioc.ClassHelper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class FileUtil {

    /**
     * 递归读取文件目录下所有的文件
     *
     * @param fileDir
     * @param fileList
     * @return
     */
    public static List<File> readFileDir(String fileDir, List<File> fileList) {
        File[] files = new File(fileDir).listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                fileList.add(file);
            } else if (file.isDirectory()) {
                readFileDir(file.getAbsolutePath(), fileList);
            }
        }
        return fileList;
    }

    /**
     * 读取指定包下的所有文件
     *
     * @param packageName
     * @return
     */
    public static List<File> readFileDir(String packageName) {
        String urlPath = packageName.replaceAll("\\.", "/");
        List<File> list = new ArrayList<>();
        try {
            Enumeration<URL> urlEnum = ClassHelper.getClassLoader().getResources(urlPath);
            while (urlEnum.hasMoreElements()) {
                String path = urlEnum.nextElement().getPath();
                readFileDir(path, list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
