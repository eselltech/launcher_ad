package com.esell.esellbanner.utils;

import android.content.Context;
import android.text.TextUtils;

import com.esell.esellbanner.BannerBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取易信发目录下的媒体文件
 */
public class ReadDataUtil {

    private List<BannerBean> mediaList = new ArrayList<>();
    private String saveMediaName;
    private Context context;

    public List<BannerBean> getMediaList() {
        return mediaList;
    }

    public ReadDataUtil(Context context){
        this.context = context;
    }

    public void synchronousData() {
        File mediaPath = ToolUtils.getMediaPath(context);
        if (mediaPath != null) {
            File[] fs = mediaPath.listFiles();
            if (fs.length > 0) {
                if (contrast(mediaPath.list())) {
                    BannerBean bean;
                    String type;
                    for (File file : fs) {
                        bean = new BannerBean();
                        type = ToolUtils.getMediaType(file.getAbsolutePath());
                        if (!TextUtils.isEmpty(type)) {
                            bean.setMediaPath(file.getAbsolutePath());
                            bean.setMediaType(type);
                            mediaList.add(bean);
                        }
                    }
                }
            } else {
                mediaList.clear();
            }
        }
    }

    /**
     * 对比，看看是否是最新的媒体文件
     */
    private boolean contrast(String[] names) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : names) {
            stringBuilder.append(name);
        }

        if (TextUtils.equals(stringBuilder.toString(), saveMediaName)) {//一致，表示没变
            return false;
        } else {
            saveMediaName = stringBuilder.toString();
            return true;
        }
    }

}
