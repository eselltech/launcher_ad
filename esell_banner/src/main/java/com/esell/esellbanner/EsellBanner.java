package com.esell.esellbanner;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esell.esellbanner.utils.ReadDataUtil;

/**
 * by soyao
 * 易售banner view
 */

public class EsellBanner extends FrameLayout {

    private ImageView mImageView;
    private UniqueTimer mUniqueTimer;
    private Handler mHandler = new Handler();
    private ReadDataUtil mReadDataUtil;
    private int mIndex, mDuration = 15, mNowDuration;

    /**
     * 设置轮播时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public EsellBanner(Context context) {
        super(context);
        init(context);
    }

    public EsellBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EsellBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mReadDataUtil = new ReadDataUtil(context);
        mUniqueTimer = new UniqueTimer();

        mUniqueTimer.setOnTimingExecute(new UniqueTimer.OnTimingExecute() {
            @Override
            public void run() {
                //同步数据
                mReadDataUtil.synchronousData();
                //判断是否有数据
                if (mReadDataUtil.getMediaList().isEmpty()) {
                    clear();
                    mIndex = 0;
                    mNowDuration = 0;
                    return;
                }

                mNowDuration++;
                if (mNowDuration <= mDuration) return;

                mIndex++;
                if (mIndex >= mReadDataUtil.getMediaList().size()) {
                    mIndex = 0;
                }
                mNowDuration = 0;
                toMainUi();
            }
        });
        mUniqueTimer.startTimer();


        toMainUi();
    }

    private void toMainUi() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mReadDataUtil.getMediaList().isEmpty()) return;
                play(mReadDataUtil.getMediaList().get(mIndex));
            }
        });
    }

    /**
     * 没有数据清空栏目
     */
    private void clear() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                removeAllViews();
            }
        });
    }

    /**
     * 播放
     */
    private void play(BannerBean bannerBean) {
        if (TextUtils.equals(bannerBean.getMediaType(), BannerConfig.IMAGE)) {
            //播放图片
            if (mImageView == null) mImageView = new ImageView(getContext());
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(bannerBean.getMediaPath()).into(mImageView);
            checkAndAddImageView();
        } else if (TextUtils.equals(bannerBean.getMediaType(), BannerConfig.VIDEO)) {
            //播放视频

        }
    }

    private void checkAndAddImageView() {
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view instanceof ImageView) {
                    return;
                }
            }
        }
        addView(mImageView, getViewLayoutParams());
    }


    /**
     * 获取LayoutParams用于设置当前view
     *
     * @return
     */
    private LayoutParams getViewLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return layoutParams;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mUniqueTimer.stopTimer();
    }
}
