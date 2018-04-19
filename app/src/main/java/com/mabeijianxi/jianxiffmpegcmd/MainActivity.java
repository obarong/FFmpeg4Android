package com.mabeijianxi.jianxiffmpegcmd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int RC_RW_EXSTORAGE = 1;

    static {
//        System.loadLibrary("avutil");
//        System.loadLibrary("fdk-aac");
//        System.loadLibrary("avcodec");
//        System.loadLibrary("avformat");
//        System.loadLibrary("swscale");
//        System.loadLibrary("swresample");
//        System.loadLibrary("avfilter");
        System.loadLibrary("jxffmpegrun");
        System.loadLibrary("x264-148");
    }

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.pb);

        Log.d(TAG, "onCreate: getFFmpegConfig=" + getFFmpegConfig());
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }
    }

    private void requestPermission() {
        //1. 检查是否已经有该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //2. 权限没有开启，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_RW_EXSTORAGE);
        }else{
            //权限已经开启，做相应事情
            Log.i(TAG, "requestPermission: 权限已经开启");

        }
    }

    //3. 接收申请成功或者失败回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_RW_EXSTORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被用户同意,做相应的事情
                Log.i(TAG, "onRequestPermissionsResult: 权限被用户同意");

            } else {
                //权限被用户拒绝，做相应的事情
                Log.i(TAG, "onRequestPermissionsResult: 权限被用户拒绝");
                finish();

            }
        }
    }

    public void onClick(View v) {
        pb.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String basePath = Environment.getExternalStorageDirectory().getPath();

//                String cmd_transcoding = String.format("ffmpeg -i %s -c:v libx264 %s  -c:a libfdk_aac %s",
//                        basePath+"/"+"girl.mp4",
//                        "-crf 40",
//                        basePath+"/"+"my_girl.mp4");

                String cmd_transcoding = String.format("ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -t 5 %s",
                        basePath + "/" + System.currentTimeMillis() + "rtsp.mp4");

                Log.d(TAG, "cmd_transcoding=" + cmd_transcoding);

                int i = jxFFmpegCMDRun(cmd_transcoding);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "ok了", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "run: OK!");
                    }
                });
            }
        }).start();
    }

    /**
     * 命令运行
     *
     * @param cmd
     * @return
     */
    public int jxFFmpegCMDRun(String cmd) {
        String regulation = "[ \\t]+";
        final String[] split = cmd.split(regulation);

        return ffmpegRun(split);
    }

    public native int ffmpegRun(String[] cmd);

    /**
     * 获取ffmpeg编译信息
     *
     * @return
     */
    public native String getFFmpegConfig();
}



