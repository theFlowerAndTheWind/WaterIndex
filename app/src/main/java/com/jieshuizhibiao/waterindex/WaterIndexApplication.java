package com.jieshuizhibiao.waterindex;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.jieshuizhibiao.waterindex.utils.NetworkStateReceiver;
import com.jieshuizhibiao.waterindex.utils.datacache.XCCacheManager;
import com.jieshuizhibiao.waterindex.utils.image.GlideImageLoader;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by WanghongHe on 2018/12/3 11:26.
 */

public class WaterIndexApplication extends Application {

    public final static String TAG = "WaterIndexApplication";
    private static WaterIndexApplication application;
    private NetworkStateReceiver networkStateReceiver;

    public static WaterIndexApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。
         *
         *     第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         *
         *         输出详细的Bugly SDK的Log；
         *         每一条Crash都会被立即上报；
         *         自定义日志将会在Logcat中输出。
         *
         *     建议在测试阶段建议设置成true，发布时设置为false。
         */
        CrashReport.initCrashReport(getApplicationContext(), "df884df1b9", true);//todo  正式发布
        application = this;

        //初始化网络receiver
        registerReceiver();

        //初始化并配置缓存策略
        cacheStrategy();

        //初始化imagePicker
        initSelectPicter();
    }

    private void initSelectPicter() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setMultiMode(false);  //单选
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * MEMORY_FIRST = 1000 MEMORY_ONLY = 2000 DISK_ONLY = 3000
     */
    private void cacheStrategy() {
        XCCacheManager.getInstance(application,1000).init(application);
    }

    private void registerReceiver() {
        networkStateReceiver = new NetworkStateReceiver();
        //创建意图过滤器
        IntentFilter filter = new IntentFilter();
        //添加动作，监听网络
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, filter);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
            MultiDex.install(this);
        }
    }
}
