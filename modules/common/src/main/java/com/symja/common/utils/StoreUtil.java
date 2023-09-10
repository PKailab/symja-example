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

package com.symja.common.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.symja.common.logging.DLog;

/**
 * Created by Duy on 10-Jul-17.
 */

public class StoreUtil {
    private static final String TAG = "StoreUtil";

    public static void gotoPlayStore(Activity context, String appId) {
        if (DLog.DEBUG)
            DLog.d(TAG, "gotoPlayStore() called with: context = [" + context + "], appId = [" + appId + "]");
        Uri uri = Uri.parse(String.format("market://details?id=%s", appId));
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
            if (DLog.DEBUG) DLog.e(e);
            try {
                Uri link = Uri.parse("http://play.google.com/store/apps/details?id=" + appId);
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                context.startActivity(intent);
            } catch (Exception e2) {
                if (DLog.DEBUG) DLog.e(e2);
            }
        }
    }


    public static void gotoPlayStore(Activity context, String appId, int requestCode) {
        Uri uri = Uri.parse("market://details?id=" + appId);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            String uriString = "http://play.google.com/store/apps/details?id=" + appId;
            openBrowser(context, uriString, requestCode);
        }
    }


    public static void openBrowser(Activity context, String uriString, int request) {
        try {
            Uri link = Uri.parse(uriString);
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            context.startActivityForResult(intent, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openBrowser(Context context, String uriString) {
        try {
            Uri link = Uri.parse(uriString);
            Intent intent = new Intent(Intent.ACTION_VIEW, link);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void emailToDevelopers(Activity activity) {
//   TODO: Implement emailToDevelopers     try {
//            int versionCode = BuildConfig.VERSION_CODE;
//            String versionName = BuildConfig.VERSION_NAME;
//            String feedbackSubject = "Feedback for Calculator N+";
//            feedbackSubject = feedbackSubject + " (" + versionName + "-" + versionCode + ")";
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tranleduy1233@gmail.com", "axelclk@gmail.com"});
//            intent.putExtra(Intent.EXTRA_SUBJECT, feedbackSubject);
//            activity.startActivity(Intent.createChooser(intent, "Send mail..."));
//        } catch (ActivityNotFoundException ex) {
//            ViewUtils.showToast(activity, "There are no email clients installed.", ViewUtils.LENGTH_SHORT);
//        }
        // TODO: Implement emailToDevelopers
    }

    public static boolean isAppInstalled(Context context, String appId) {
        try {
            context.getPackageManager().getApplicationInfo(appId, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
