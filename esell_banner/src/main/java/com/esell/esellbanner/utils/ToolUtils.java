package com.esell.esellbanner.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.esell.esellbanner.BannerConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ToolUtils {

    /**
     * 获取媒体路径(不存在的话，不创建)
     *
     * @param context
     * @return
     */
    public static File getMediaPath(Context context) {
        return getPath(context, BannerConfig.CACHE_DIR_YIXINFA);
    }

    public static File getJsonPath(Context context) {
        return getPath(context, BannerConfig.JSON_PATH);
    }

    /**
     * 获取媒体类型
     *
     * @param path
     * @return
     */
    public static String getMediaType(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.contains(".jpg") || path.contains(".png") || path.contains(".gif")) {
                return BannerConfig.IMAGE;
            }
            if (path.contains(".mp4")) {
                return BannerConfig.VIDEO;
            }
        }
        return null;
    }


    private static File getPath(Context context, String child) {
        File storagePath;
        String externalStorageState = Environment.getExternalStorageState();
        if (TextUtils.equals(Environment.MEDIA_MOUNTED, externalStorageState)) {
            storagePath = Environment.getExternalStorageDirectory();
        } else {
            storagePath = context.getFilesDir();
        }

        File yiXinFaFile = new File(storagePath, child);
        if (yiXinFaFile.exists()) {
            return yiXinFaFile;
        }

        return null;
    }


    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        String content = "";
        FileInputStream fis = null;
        BufferedReader br = null;
        if (file.exists()) {
            try {
                //获取指定文件对应的输入流
                fis = new FileInputStream(file);
                //将指定输入流包装成BufferReader
                br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder();
                String line;
                //循环读取文件内容
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                fis.close();
                content = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content;
    }
}
