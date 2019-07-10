package com.blockadm.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * <p> 图片加载工具类</p>
 *
 * @name ImageUtils
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ImageUtils {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean isValidContextForGlide( Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }


    /**
     * 默认加载
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadImageView(String path, ImageView mImageView) {
        if (isValidContextForGlide(mImageView.getContext())){
            Glide.with(mImageView.getContext())
                    .load(path)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImageView);
        }

    }

    public static void loadImageWithError(String path, int errorRes, ImageView mImageView) {
        if (mImageView.getContext()!=null){
            Glide.with(mImageView.getContext())
                    .load(path)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(errorRes)
                    .into(mImageView);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadImagefileWithError(File file, int errorRes, ImageView mImageView) {
        if (isValidContextForGlide(mImageView.getContext())){
            Glide.with(mImageView.getContext())
                    .load(file)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(errorRes)
                    .into(mImageView);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadImagefile(File file, ImageView mImageView) {
        if (isValidContextForGlide(mImageView.getContext())){
            Glide.with(mImageView.getContext())
                    .load(file)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImageView);
        }


    }

    /**
     * 设置加载中以及加载失败图片
     */
    public static void loadImageWithLoading(String path, ImageView mImageView, int lodingImage, int errorRes) {
        Glide.with(mImageView.getContext()).load(path).placeholder(lodingImage).
                error(errorRes).into(mImageView);
    }

    /**
     * 设置加载动画
     * api也提供了几个常用的动画：比如crossFade()
     */
    public static void loadImageViewAnim(String path, int anim, ImageView mImageView) {
        Glide.with(mImageView.getContext()).load(path).animate(anim).into(mImageView);
    }


    /**
     * 加载为bitmap
     *
     * @param path     图片地址
     * @param listener 回调
     */
    public static void loadBitMap(String path, final onLoadBitmap listener,Context context) {
        Glide.with(context).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onReady(bitmap);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onFailed();
            }
        });
    }

    /**
     * 显示加载进度
     *
     * @param path       图片地址
     * @param mImageView 图片控件
     * @param loadView   加载view
     */
    public static void loadImageWithProgress(String path, final ImageView mImageView, final View loadView, int errorRes) {
        Glide.with(mImageView.getContext()).load(path).error(errorRes).into(new GlideDrawableImageViewTarget(mImageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                loadView.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                loadView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 清除view上的图片
     *
     * @param view 视图
     */
    public static void clearImageView(View view) {
        Glide.clear(view);
    }

    /**
     * 清理磁盘缓存需要在子线程中执行
     */
    public static void GuideClearDiskCache(Context mContext) {
        Glide.get(mContext).clearDiskCache();
    }

    /**
     * 清理内存缓存可以在UI主线程中进行
     */
    public static void GuideClearMemory(Context mContext) {
        Glide.get(mContext).clearMemory();
    }

    /**
     * 加载bitmap回调
     */
    public interface onLoadBitmap {
        void onReady(Bitmap resource);

        void onFailed();
    }


    public static Bitmap getFirstFrameDrawable(final String url){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(url,new HashMap<String, String>());
        return mmr.getFrameAtTime();
    }

    public static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
// TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }



    public static class GlideRoundTransform extends BitmapTransformation {
        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;
            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }


    }


    /**
     * createBitmap (Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter)
     */

    /**
     * 获取scrollview的截屏
     */
    public static Bitmap splitScreenShot(ScrollView scrollView,int resId) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            View view = scrollView.getChildAt(i);
            h += view.getHeight();
            scrollView.getChildAt(i).setBackgroundResource(resId);
        }
        int width =  scrollView.getWidth();

        bitmap = Bitmap.createBitmap(width, h, Bitmap.Config.RGB_565);

        int ww = bitmap.getWidth();
        int hh = bitmap.getHeight();

        L.d(" (int) (ww * 0.2): "+(int) (ww * 0.2));
        L.d(" (int)(ww * 0.8): "+(int)(ww * 0.8));


        final Canvas canvas = new Canvas(bitmap);
        canvas.clipRect((int) (ww * 0.1),0,(int) (ww * 0.9),hh);
        scrollView.draw(canvas);

//        Bitmap bitmap1 = Bitmap.createBitmap(bitmap,(int) (ww * 0.3),0,(int)(ww * 0.3),h);
        return bitmap;
    }



    /**
     * 获取scrollview的截屏
     */
    public static Bitmap scrollViewScreenShot(ScrollView scrollView,int resId) {

//       return splitScreenShot(scrollView,resId);
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            View view = scrollView.getChildAt(i);
            h += view.getHeight();
            scrollView.getChildAt(i).setBackgroundResource(resId);
        }
       int width =  scrollView.getWidth();
        bitmap = Bitmap.createBitmap(width, h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * @param bmp 获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static String saveBmp2Gallery(Bitmap bmp, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"Camera"+File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName+ ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
}
         //通知相册更新
          MediaStore.Images.Media.insertImage(ContextUtils.getBaseApplication().getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        ContextUtils.getBaseApplication().sendBroadcast(intent);
        Toast.makeText(ContextUtils.getBaseApplication(),"图片保存成功",Toast.LENGTH_SHORT).show();
        return fileName;

    }

    public static Bitmap compressPixel(String filePath){
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = 2;

        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);
                    BitmapFactory.decodeStream(inputStream, null, options);
                    inputStream.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }finally {
            return bmp;
        }
    }

    public static  File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/"+System.currentTimeMillis()+".jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**

     */
    public static Bitmap createBitmap2(View v,int width,int height ) {
   /*     Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;*/

        //测量使得view指定大小
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        v.measure(measuredWidth, measuredHeight);
        //调用layout方法布局后，可以得到view的尺寸大小
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}