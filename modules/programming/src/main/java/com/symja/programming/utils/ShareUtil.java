/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.symja.programming.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.symja.programming.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Duy on 09-Aug-17.
 */

public class ShareUtil {

    public static void shareImage(@NonNull Context context, @NonNull Bitmap image) {
        File cacheDir = context.getCacheDir();
        File file = new File(cacheDir, System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            shareImage(context, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImage(Context context, File file) {
        Uri uri;
        uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setType("image/*");
        try {
            context.startActivity(Intent.createChooser(intent, "Share image"));
        } catch (Exception e) {
            ViewUtils.showToast(context, e.getMessage(), ViewUtils.LENGTH_LONG);
        }
    }

    @Deprecated
    public static void shareText(String text, Context context) {
        shareText(context, text);
    }

    public static void shareText(Context context, CharSequence text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        try {
            context.startActivity(Intent.createChooser(intent, "Share"));
        } catch (Exception e) {
            ViewUtils.showToast(context, e.getMessage(), ViewUtils.LENGTH_LONG);
        }
    }

    public static void shareApp(Activity context, String appId) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String url = String.format("http://play.google.com/store/apps/details?id=%s", appId);
            intent.putExtra(Intent.EXTRA_TEXT, url);
            intent.setType("text/plain");
            context.startActivity(Intent.createChooser(intent,
                    context.getString(R.string.share)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareThisApp(Activity context) {
        shareApp(context, context.getPackageName());
    }
}
