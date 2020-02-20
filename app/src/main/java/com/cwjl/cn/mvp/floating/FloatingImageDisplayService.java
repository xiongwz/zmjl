package com.cwjl.cn.mvp.floating;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cwjl.cn.ContextApplication;
import com.cwjl.cn.R;
import com.cwjl.cn.utils.PathHolder;
import com.cwjl.cn.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by dongzhong on 2018/5/30.
 */

public class FloatingImageDisplayService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private View displayView;

    private List<String> images = new ArrayList<>();
    private int imageIndex = 0;
    private boolean mIsFloating;

    private Handler changeImageHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = Util.getScreenWidth(this);
        layoutParams.height = Util.getScreenHeight(this);
//        layoutParams.x = 300;
//        layoutParams.y = 300;

        changeImageHandler = new Handler(this.getMainLooper(), changeImageCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mIsFloating) {
            windowManager.removeView(displayView);
            mIsFloating = false;
        }
        images = getFilesAllName(PathHolder.FILE_CACHE_ZIP);
        imageIndex = 0;
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    public static List<String> getFilesAllName(String path){
        //传入指定文件夹的路径
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> imagePaths = new ArrayList<>();
        for(int i = 0; i < files.length; i++){
            if(checkIsImageFile(files[i].getPath())){
                imagePaths.add(files[i].getPath());
            }

        }
        return imagePaths;
    }

    /**
     * 判断是否是照片
     */
    public static boolean checkIsImageFile(String fName){
        boolean isImageFile = false;
        //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if(fileEnd.equals("jpg") || fileEnd.equals("png") || fileEnd.equals("gif")
                || fileEnd.equals("jpeg")|| fileEnd.equals("bmp")){
            isImageFile = true;
        }else{
            isImageFile = false;
        }
        return isImageFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            displayView = layoutInflater.inflate(R.layout.image_display, null);
            displayView.setOnTouchListener(new FloatingOnTouchListener());
            ImageView imageView = displayView.findViewById(R.id.image_display_imageview);
            if (!TextUtils.isEmpty(images.get(imageIndex))) {
                Glide.with(this).load(images.get(imageIndex)).error(R.mipmap.default_square).placeholder(R.mipmap.default_square).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable current = resource.getCurrent();
                        imageView.setImageDrawable(current);
                    }
                });
            }
            windowManager.addView(displayView, layoutParams);

            changeImageHandler.sendEmptyMessageDelayed(0, 100);
        }
    }

    private Handler.Callback changeImageCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                imageIndex++;
                if (imageIndex >= images.size()) {
                    imageIndex = 0;
                }
                if (displayView != null) {
//                    Glide.with(FloatingImageDisplayService.this).load(new File(images.get(imageIndex))).error(R.mipmap.default_square).into((ImageView) displayView.findViewById(R.id.image_display_imageview));
//                    ((ImageView) displayView.findViewById(R.id.image_display_imageview)).setImageResource(images.get(imageIndex));
                    Glide.with(FloatingImageDisplayService.this).load(new File(images.get(imageIndex))).error(R.mipmap.default_square).placeholder(R.mipmap.default_square).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            Drawable current = resource.getCurrent();
                            ((ImageView) displayView.findViewById(R.id.image_display_imageview)).setImageDrawable(current);
                            mIsFloating = true;
                        }
                    });
                }

                changeImageHandler.sendEmptyMessageDelayed(0, 100);
            }
            return false;
        }
    };

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
