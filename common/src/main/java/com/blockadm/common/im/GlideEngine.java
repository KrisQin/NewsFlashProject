/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blockadm.common.im;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.blockadm.common.im.call.ImageEngine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestListener;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class GlideEngine implements ImageEngine {


    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap() // some .jpeg files are actually gif
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        Glide.with(context)
                .load(uri)
                .asBitmap() // some .jpeg files are actually gif
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }


    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asGif()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }


    @Override
    public boolean supportAnimatedGif() {
        return true;
    }


    public static void loadImage(ImageView imageView, String filePath, RequestListener listener) {
        Glide.with(TUIKit.getAppContext())
                .load(filePath)
                .listener(listener)
                .into(imageView);
    }


    public static void loadImage(ImageView imageView, Uri uri) {
        if (uri == null)
            return;
        Glide.with(TUIKit.getAppContext())
                .load(uri)
                .into(imageView);
    }


}
