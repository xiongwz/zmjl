package com.cwjl.cn.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.cwjl.cn.pub.ViewPagerActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;

/**
 * Created by lenovo on 2016/11/25.
 */

public class IntentUtils {
    public static final int ACION_TAKE_PHOTO = 0;
    public static final int ACION_RECORD_VIDEO = ACION_TAKE_PHOTO + 1;
    public static final int ACION_RECORD_AUDIO = ACION_RECORD_VIDEO + 1;

    public static void callPhone(String telephoneNumber, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void installAPK(Context context, File apk){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(FileUtils.getUriForFile(context,apk),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /* 卸载apk */
    public static void uninstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }

    /**
     * 录像
     */
    public static void playVideo(Activity mContext, String photoSaveName) {
        File file = new File(photoSaveName);
        if (!file.exists()) {
            AvToast.makeText(mContext, "文件不存在");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(FileUtils.getUriForFile(mContext,new File(photoSaveName)), "video/mp4");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("videoPath", photoSaveName);
        mContext.startActivity(intent);
    }

    /**
     * 图片
     */
    public static void playImage(Activity mContext, String photoSaveName) {
        File file = new File(photoSaveName);
        if (!file.exists()) {
            AvToast.makeText(mContext, "文件不存在");
            return;
        }
        List<String> imagePaths = new ArrayList<>();
        imagePaths.add(photoSaveName);
        Intent intent = new Intent(mContext, ViewPagerActivity.class);
        intent.putExtra("pics", (Serializable) imagePaths);
        intent.putExtra("isloact", true);
        mContext.startActivity(intent);


    }

    public static void openFile(String apkPath, Context context) {
        File file=new File(apkPath);
        if(!file.exists()){
            AvToast.makeText(context,"apk文件不存在");
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(FileUtils.getUriForFile(context,new File(apkPath)),getMIMEType(file)
               );
        context.startActivity(intent);
    }
    private static String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");  //获取后缀名前的分隔符"."在fName中的位置。
        if(dotIndex < 0){
            return type;
        }

        String end=fName.substring(dotIndex,fName.length()).toLowerCase();  /* 获取文件的后缀名*/
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable={
            //{后缀名，MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",      "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz",     "application/x-gzip"},
            {".h",      "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",     "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rc",     "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",     "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",      "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };

}
