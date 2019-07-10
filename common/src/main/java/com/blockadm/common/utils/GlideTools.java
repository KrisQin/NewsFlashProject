package com.blockadm.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Glide工具类
 * Created by Administrator on 2018/5/25.
 */
public class GlideTools {


    /**
     * 使用Glide加载图片
     * @param context 上下文
     * @param url 路径
     * @param errorImgId 错误图片id
     * @param imageView 容器
     */
    public static void setImgByGlide(Context context, String url, int errorImgId, ImageView imageView){
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                //禁止内存缓存
                .skipMemoryCache(true)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //设置资源加载过程中的占位Drawable
                .placeholder(errorImgId)
                //加载错误
                .error(errorImgId)
                .into(imageView);
    }


    /**
     * 使用Glide加载图片
     * @param context 上下文
     * @param url 路径
     * @param imageView 容器
     */
    public static void setImgByGlideSource(Context context, String url, ImageView imageView){
        Glide.with(context.getApplicationContext()).load(url)
                .crossFade()
                //禁止内存缓存
                .skipMemoryCache(false)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }


    /**
     * 使用Glide加载图片
     * @param context 上下文
     * @param url 路径
     * @param imageView 容器
     */
    public static void setImgRoundByGlide(Context context, String url,int radius,  ImageView imageView){
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                //禁止内存缓存
                .skipMemoryCache(true)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new ImageUtils.GlideRoundTransform(context, radius))
                .into(imageView);
    }

    /**
     * 加载图片
     * @param context
     * @param url
     * @param errorImgId
     * @param imageView
     * @param isMemoryCache
     */
    public static void setImgByGlide(Context context,String url,int errorImgId,ImageView imageView,boolean isMemoryCache){
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                //内存缓存
                .skipMemoryCache(isMemoryCache)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //设置资源加载过程中的占位Drawable
                .placeholder(errorImgId)
                //加载错误
                .error(errorImgId)
                .into(imageView);
    }

    public static void setImgByGlide(Context context, Uri uri, int errorImgId, ImageView imageView, int w, int h){
        Glide.with(context.getApplicationContext()).load(uri)
                .asBitmap()
                .override(w,h)
                //禁止内存缓存
                .skipMemoryCache(false)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //设置资源加载过程中的占位Drawable
                .placeholder(errorImgId)
                //加载错误
                .error(errorImgId)
                .into(imageView);
    }


    /**
     * Glide 获得图片缓存路径，必须在子线程中运行
     * @param context
     * @param imgUrl
     * @return
     */
    public static String saveImageAndGetPathByGlide(Context context,String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(context)
                .load(imgUrl)
                .downloadOnly(500,500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void setImgByGlideDefualt(Context context, String url, int errorImgId, ImageView imageView){
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                //禁止内存缓存
                .skipMemoryCache(false)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //设置资源加载过程中的占位Drawable
                .placeholder(errorImgId)
                //加载错误
                .error(errorImgId)
                .into(imageView);
    }

    public interface GlideCallBack{

        void loadErrer(Exception e);
        void loadFinish(Bitmap bitmap);

    }

    /**
     * 拥有回调
     * @param context
     * @param url
     * @param errorImgId
     * @param w
     * @param h
     * @param callBack
     */
    public static void setImgByGlide(Context context, String url, int errorImgId,
                                     int w, int h, final GlideCallBack callBack){
        Glide.with(context.getApplicationContext()).load(url)
                .asBitmap()
                .override(w,h)
                //禁止内存缓存
                .skipMemoryCache(false)
                //去掉磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //设置资源加载过程中的占位Drawable
                .placeholder(errorImgId)
                //加载错误
                .error(errorImgId)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<Bitmap> target, boolean b) {
                        Log.i("Glide","onException----");
                        if(callBack != null){
                            callBack.loadErrer(e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, String s, Target<Bitmap> target, boolean b, boolean b1) {
                        Log.i("Glide","onResourceReady----");
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(callBack != null){
                            callBack.loadFinish(bitmap);
                        }
                    }
                });
    }

    /**
     * 清除Glide全部缓存，慎用
     * @param context
     */
    public static void clearCacheData(Context context) {
        //清理内存中的缓存。
        Glide.get(context).clearMemory();

        //清理硬盘中的缓存。
        Glide.get(context).clearDiskCache();
    }

}
