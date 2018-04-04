package com.zheng.common.util;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Jar工具类
 * @author Joy
 * @date 2018/3/22
 */
public class JarUtil {

    public static synchronized void decompress(String fileName, String outputPath){
        // 保证输出路径为目录
        if(!outputPath.endsWith(File.separator)){
            outputPath += File.separator;
        }
        // 如果不存在输出目录，则创建
        File dir = new File(outputPath);
        dir.mkdirs();
        // 解压到输出目录
        JarFile jf = null;
        try {
            jf = new JarFile(fileName);
            for(Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements(); ){
                JarEntry je = e.nextElement();
                String outFileName = outputPath + je.getName();
                File f = new File(outFileName);
                if(je.isDirectory()){
                    f.mkdirs();
                }else {
                    File pf = f.getParentFile();
                    pf.mkdirs();
                    InputStream in = jf.getInputStream(je);
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                    byte[] buffer = new byte[2048];
                    int nBytes;
                    while ((nBytes = in.read(buffer)) > 0){
                        out.write(buffer, 0, nBytes);
                    }
                    out.flush();
                    out.close();
                    in.close();
                }
            }
        } catch (Exception e) {
            System.out.println("解压" + fileName + "出错！" + e.getMessage());
        }finally {
            if(jf!=null){
                try {
                    jf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
