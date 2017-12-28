package cn.edu.zstu.sunshine.utils;


import android.hardware.Camera;

/**
 * Created by CooLoongWu on 2017-12-28 15:31.
 */

public class FlashUtil {

    private static FlashUtil utils;
    private static Camera camera;
    public static boolean mIsOpen = true;

    //使用单例模式在这里初始化相机
    public static FlashUtil getInstance() {
        if (utils == null) {
            utils = new FlashUtil();
        }
        try {
            if (camera == null) {
                camera = Camera.open();
            }
        } catch (Exception e) {
            if (camera != null) {
                camera.release();
            }
            camera = null;
        }
        return utils;
    }

    //参考二维码工具的闪光灯
    public void switchFlash() {
        try {
            Camera.Parameters parameters = camera.getParameters();
            if (mIsOpen) {
                if (parameters.getFlashMode().equals("torch")) {
                    return;
                } else {
                    parameters.setFlashMode("torch");
                }
            } else {
                if (parameters.getFlashMode().equals("off")) {
                    return;
                } else {
                    parameters.setFlashMode("off");
                }
            }
            camera.setParameters(parameters);
        } catch (Exception e) {
            release();
        }
        mIsOpen = !mIsOpen;
    }

    public void turnOn() {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH))
            return;
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
    }

    public void turnOff() {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF))
            return;
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
    }

    //页面销毁的时候调用此方法
    public void release() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        camera = null;
    }

}
