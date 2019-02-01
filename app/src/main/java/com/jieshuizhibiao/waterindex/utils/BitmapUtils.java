package com.jieshuizhibiao.waterindex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by WanghongHe on 2018/10/26.
 * 三星手机拍照显示横屏的问题
 */

public class BitmapUtils {

    private static final String TAG = "BitmapUtils";
    private static final long MB = 1024*1024;
    /**
     * 手机型号
     * @return BRAND
     */
    public static String getBrand(){
        String brand = "";
        try {
            brand = android.os.Build.BRAND;
            Log.i(TAG,"手机型号:"+brand);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"手机型号为获取到");
        }
        if(TextUtils.isEmpty(brand)){
            brand="华为";
        }
        return brand;
    }

    /**
     *  获得照片角度。
     * @param path path
     * @return degree
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    public static Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        cropWidth /= 2;
        int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, w / 3, 0, cropWidth, cropHeight, null, false);
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    public static Bitmap toturn(Bitmap img){
        Matrix matrix = new Matrix();
        matrix.postRotate(+90); /*翻转90度*/
        int width = img.getWidth();
        int height =img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }


    /**
     * 偏移效果
     * @param origin 原图
     * @return 偏移后的bitmap
     */
    public static Bitmap skewBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.postSkew(-0.6f, -0.3f);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


    /**
     * 图片转换成字节数组
     * @param bm 图片对象
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 字节数组转换成图片
     *
     * @param intent
     *            Intent对象
     * @return 图片对象
     */
    public static Bitmap Bytes2Bitmap(Intent intent) {
        byte[] buff = intent.getByteArrayExtra("bitmap");
        Bitmap bm = BitmapFactory.decodeByteArray(buff, 0, buff.length);
        return bm;
    }

    /**
     * 截屏方法
     * @param activity 上下文
     * @return bitmap
     */
    public static Bitmap shot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Display display = activity.getWindowManager().getDefaultDisplay();
        view.layout(0, 500, display.getWidth() - 200, display.getHeight() - 250);
        Bitmap bitmap = view.getDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(bitmap);
        // return Bitmap.createBitmap(bmp, 100,100, 500, 500);
        return bmp;
    }
    /**
     * 截取指定view的视图
     * @param v 要截取的view对象
     * @return Bitmap对象
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus(); // 清除视图焦点
        v.setPressed(false);// 将视图设为不可点击

        boolean willNotCache = v.willNotCacheDrawing(); // 返回视图是否可以保存他的画图缓存
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation //将视图在此操作时置为透明
        int color = v.getDrawingCacheBackgroundColor(); // 获得绘制缓存位图的背景颜色
        v.setDrawingCacheBackgroundColor(0); // 设置绘图背景颜色
        if (color != 0) { // 如果获得的背景不是黑色的则释放以前的绘图缓存
            v.destroyDrawingCache(); // 释放绘图资源所使用的缓存
        }
        v.buildDrawingCache(); // 重新创建绘图缓存，此时的背景色是黑色
        Bitmap cacheBitmap = v.getDrawingCache(); // 将绘图缓存得到的,注意这里得到的只是一个图像的引用
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap); // 将位图实例化
        // Restore the view //恢复视图
        v.destroyDrawingCache();// 释放位图内存
        v.setWillNotCacheDrawing(willNotCache);// 返回以前缓存设置
        v.setDrawingCacheBackgroundColor(color);// 返回以前的缓存颜色设置
        return bitmap;
    }

    /**
     * 保存图片到指定路径的方法
     *
     * @param path 图片保存的相对路径
     * @param name 图片的名字
     * @param bitmap 要保存的图片
     * @throws IOException 读写图片文件出现的异常信息
     */
    public static void save(String path,String name, Bitmap bitmap) throws IOException {
        File file = new File( path , name);
        // 若图片文件在SD卡的文件夹不存在
        if (!file.getParentFile().exists()) {
            // 创建该文件夹
            file.getParentFile().mkdirs();
        }
        // 若文件不存在，则创建
        if (!file.exists()) {
            file.createNewFile();
        }
        // 创建文件输出流
        FileOutputStream out = new FileOutputStream(file);
        // 保存图片至SD卡指定文件夹
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

    /**
     * 获得指定路径的图片
     *
     * @param path 图片的本地路径
     * @param name 图片的名字
     * @return 图片对象
     * @throws IOException
     */
    public static Bitmap getBitmap(String path,String name) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        File file = new File(path,name);
        if (file.exists()&&(file.length()/MB)>1)
        {
            options.inSampleSize = 2;
        }
        Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        return imageBitmap;
    }

    public static Bitmap getBitmap(String path){
        Bitmap imageBitmap=null;
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            File file = new File(path);
            if (file.exists()&&(file.length()/MB)>1)
            {
                options.inSampleSize = 2;
            }
            imageBitmap = BitmapFactory.decodeFile(path,options);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return imageBitmap;
    }

    /***
     * 图片的缩放方法（图片按照给定宽高缩放）
     * @param newWidth ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return 可用的图片 bitmap对象
     */
    public static Bitmap zoomImage(Bitmap bm, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bm.getWidth();
        float height = bm.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static String getSDPath(){
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(hasSDCard){
            return Environment.getExternalStorageDirectory().toString();
        }else {
            return Environment.getDownloadCacheDirectory().toString();
        }
    }

    /**
     * 图片压缩缩放倍数（较为合适）
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return nJustDecodeBounds，将该参数设置为 true后，decodeFiel并不会分配内存空间，但是可以计算出原始图片的长宽，
     * 调用 options.outWidth/outHeight获取出图片的宽高，然后通过一定的算法，即可得到适合的 inSampleSize
     */
    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    public static Bitmap zipBitmap(Context context,int id) {
        BitmapFactory.Options options = options = new BitmapFactory.Options();
        // 设置了此属性一定要记得将值设置为false
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(),id,options);
            options.inSampleSize = caculateInSampleSize(options,128,128);
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                /* 下面两个字段需要组合使用 */
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(context.getResources(),id, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            LogUtils.e(TAG, "OutOfMemoryError");
        }
        return bitmap;
    }

}
