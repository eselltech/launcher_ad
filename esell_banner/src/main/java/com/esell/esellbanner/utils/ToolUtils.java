package com.esell.esellbanner.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.esell.esellbanner.BannerConfig;

import java.io.File;

public class ToolUtils {

    /**
     * 获取媒体路径(不存在的话，不创建)
     *
     * @param context
     * @return
     */
    public static File getMediaPath(Context context) {
        File storagePath;
        String externalStorageState = Environment.getExternalStorageState();
        if (TextUtils.equals(Environment.MEDIA_MOUNTED, externalStorageState)) {
            storagePath = Environment.getExternalStorageDirectory();
        } else {
            storagePath = context.getFilesDir();
        }

        File yiXinFaFile = new File(storagePath, BannerConfig.CACHE_DIR_YIXINFA);
        if (yiXinFaFile.exists()) {
            return yiXinFaFile;
        }

        return null;
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
}
