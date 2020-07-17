package com.tensun.lineupentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIItemListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Method;

public class CameraActivity extends AppCompatActivity {

    private SurfaceView surfaceview;
    private SurfaceHolder surfaceholder;
    private Camera camera2;
    Camera.Parameters parameters;
    private ImageView ivBack,ivPhoto;
//    private ImageView ivMan;
//    private ImageView ivWoman;
    private String sex="man";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        EventBus.getDefault().register(this);

        ivBack = findViewById(R.id.iv_back);
        ivPhoto = findViewById(R.id.iv_photo);

        surfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceholder = surfaceview.getHolder();
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceholder.addCallback(new surfaceholderCallbackFont());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, AvaterActivity.class);
                intent.putExtra("sex", sex);
                startActivity(intent);
            }
        });
        String[] words = new String[]{"boy", "girl"};
        DialogUIUtils.showSingleChoose(this, "Choose gender", 0, words, true, false, new DialogUIItemListener() {
            @Override
            public void onItemClick(CharSequence text, int position) {
                Log.e("!!!!", position+"");
                sex = position == 0 ? "man" : "woman";
            }
        }).show();
    }
    class surfaceholderCallbackFont implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 获取camera对象
            int cameraCount = Camera.getNumberOfCameras();
            if (cameraCount == 2) {
                camera2 = Camera.open(1);
            } else if (cameraCount == 0) {
                return;
            } else {
                camera2 = Camera.open(0);
            }
            try {
                // 设置预览监听
                camera2.setPreviewDisplay(holder);
                Camera.Parameters parameters = camera2.getParameters();

                if (CameraActivity.this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait");
                    camera2.setDisplayOrientation(90);
                    parameters.setRotation(90);
                } else {
                    parameters.set("orientation", "landscape");
                    camera2.setDisplayOrientation(0);
                    parameters.setRotation(0);
                }
                camera2.setParameters(parameters);
                // 启动摄像头预览
                camera2.startPreview();
                System.out.println("camera.startpreview");

            } catch (IOException e) {
                e.printStackTrace();
                camera2.release();
                System.out.println("camera.release");
            }
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera2.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        parameters = camera2.getParameters();
//                        parameters.setPictureFormat(PixelFormat.JPEG);
//                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 1连续对焦
//                        setDispaly(parameters, camera2);
                        camera2.setParameters(parameters);
                        camera2.startPreview();
                        camera2.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
                        camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦。
                    }
                }
            });

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        // 控制图像的正确显示方向
        private void setDispaly(Camera.Parameters parameters, Camera camera) {
            if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
                setDisplayOrientation(camera, 90);
            } else {
                parameters.setRotation(90);
            }

        }
        // 实现的图像的正确显示
        private void setDisplayOrientation(Camera camera, int i) {
            Method downPolymorphic;
            try {
                downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
                if (downPolymorphic != null) {
                    downPolymorphic.invoke(camera, new Object[]{i});
                }
            } catch (Exception e) {
                Log.e("Came_e", "图像出错");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String close) {
        Log.e("!!!!", close);
        if (close.equals("close")) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
