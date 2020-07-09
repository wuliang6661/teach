package cn.teach.equip.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.teach.equip.api.DownloadResponseBody;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.constans.FileConfig;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/7/916:17
 * desc   :
 * version: 1.0
 */
public class FileUtils {


    public static Map<String, File> maps = new HashMap<>();


    /**
     * 获取指定目录内所有文件路径
     */
    public static Map<String, File> getAllFiles(String dirPath) {
        dirPath = StringUtils.isEmpty(dirPath) ? FileConfig.getMlFile() : dirPath;
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    getAllFiles(file.getAbsolutePath());
                } else {
                    maps.put(fileName, file);
                }
            }
        }
        return maps;
    }


    /**
     * 下载文件到指定目录
     */
    public static void downloadFile(String filePath, String fileName,
                                    String url, onProgressListener listener) {
        File file = new File(filePath, fileName);
        boolean existsFile = com.blankj.utilcode.util.FileUtils.createOrExistsFile(file);
        if (!existsFile) {
            ToastUtils.showShort("IO异常");
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (listener != null) {
                    listener.onProgress((Integer) msg.obj);
                }
            }
        };

        DownloadResponseBody.DownloadListener downloadListener = progress -> {
            Message message = Message.obtain();
            message.obj = Integer.valueOf(progress);
            handler.sendMessage(message);
        };
        HttpServerImpl.downLoad(url, downloadListener, file).subscribe(new Subscriber<ResponseBody>() {

            @Override
            public void onCompleted() {
                if (listener != null) {
                    listener.onProgress(100);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort("文件下载失败！");
                com.blankj.utilcode.util.FileUtils.deleteFile(file);
            }

            @Override
            public void onNext(ResponseBody integer) {


            }
        });
    }


    public interface onProgressListener {

        void onProgress(int progress);
    }

}
