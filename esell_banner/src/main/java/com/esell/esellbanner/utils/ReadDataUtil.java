package com.esell.esellbanner.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.esell.esellbanner.BannerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取易信发目录下的媒体文件
 */
public class ReadDataUtil {

    private List<BannerBean> mediaList = new ArrayList<>();
    private String saveMediaName, saveMediaId;
    private Context context;

    public List<BannerBean> getMediaList() {
        return mediaList;
    }

    public ReadDataUtil(Context context) {
        this.context = context;
    }

    /**
     * 同步数据
     */
    public void synchronousData() {
        /*File mediaPath = ToolUtils.getMediaPath(context);
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
        }*/

        //测试
        getLaunchData();
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

    /**
     * 获取启动广告的数据，通过本地json
     */
    private void getLaunchData() {
        File jsonFile = ToolUtils.getJsonPath(context);
        if (jsonFile != null) {
            String jsonStr = ToolUtils.readFile(jsonFile);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("ad-launcher");
                if (jsonArray.length() > 0) {
                    if (contrastId(jsonArray)) {
                        BannerBean bean;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            bean = new BannerBean();
                            JSONObject o = (JSONObject) jsonArray.get(i);
                            bean.setMediaId(o.getString("id"));
                            bean.setMediaType(o.getString("material-type"));
                            bean.setDuration(o.getInt("duration"));
                            bean.setMediaPath(o.getString("saveFilePath"));
                            //-------保存数据-------
                            mediaList.add(bean);
//                            Log.d("soyao", bean.toString());
                        }
                    }
                } else {
//                    Log.d("soyao", "数据为空");
                    //清空数组
                    if (!mediaList.isEmpty()) {
                        mediaList.clear();
                    }
                    //清空标记
                    if (!TextUtils.isEmpty(saveMediaId)) {
                        saveMediaId = "";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拿id对比，id一样就是同一个，id不一样就是更新了
     */
    private boolean contrastId(JSONArray jsonArray) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = (JSONObject) jsonArray.get(i);
            stringBuilder.append(o.getString("id"));
        }

        if (TextUtils.equals(stringBuilder.toString(), saveMediaId)) {//一致，表示没变
            return false;
        } else {
            saveMediaId = stringBuilder.toString();
            return true;
        }
    }
}
