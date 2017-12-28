package cn.edu.zstu.sunshine.interfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 文件下载的抽象类
 * Created by CooLoongWu on 2017-12-19 15:15.
 */

public abstract class OnDownloadCallback implements Callback {

    /**
     * 目标文件存储的文件夹路径
     */
    private String fileDir;
    /**
     * 目标文件存储的文件名
     */
    private String fileName;

    protected OnDownloadCallback(String fileDir, String fileName) {
        this.fileDir = fileDir;
        this.fileName = fileName;
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        saveFile(response);
    }

    public void onProgress(float progress, long total) {

    }

    public void onCompleted() {

    }

    private File saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                onProgress(finalSum * 1.0f / total, total);

                if (finalSum == total) {
                    onCompleted();
                }
//                OkHttpUtils.getInstance().getDelivery().execute(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        inProgress(finalSum * 1.0f / total,total,id);
//                    }
//                });
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
