package cn.bmob.v4;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.bmob.BmobPro;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.ThumbnailListener;
import com.bmob.btp.callback.UploadBatchListener;
import com.bmob.btp.callback.UploadListener;
import com.diandi.klob.sdk.util.CacheUtils;
import com.diandi.klob.sdk.util.FileUtils;
import com.diandi.klob.sdk.util.L;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.bmob.v3.datatype.BmobFile;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-02-04  .
 * *********    Time : 09:31 .
 * *********    Project name : diandy1.2.0 .
 * *********    Version : 1.0
 * *********    Copyright  © 2014, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * 文件管理
 */
public class FileManager {
    private final static String TAG = "FileManager";
    private static volatile FileManager mInstance;
    private static Context mContext;
    private static Object o = new Object();

    private FileManager() {
    }

    public static FileManager getInstance(Context context) {
        if (mInstance == null)
            synchronized (o) {
                if (mInstance == null) {
                    mInstance = new FileManager();
                }
                mInstance.start(context);
            }
        return mInstance;
    }



    public void start(Context context) {
        mContext = context;
    }

    public void upload(String filePath, final DUploadListener listener) {
        BmobProFile.getInstance(mContext).upload(filePath, new UploadListener() {


            @Override
            public void onSuccess(String fileName, String url, BmobFile bmobFile) {
                listener.onSuccess(fileName, bmobFile.getFileUrl(mContext), bmobFile);
                L.d(TAG, "  fileName  " + fileName + " url   " + bmobFile.getFileUrl(mContext));
            }

            @Override
            public void onProgress(int i) {
                listener.onProgress(i);
                L.d(TAG, "  progress " + i);
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.e(TAG, "  statuscode  " + i + " errormsg   " + s);
            }
        });
    }

    public void upload(String[] filePaths, final DUploadBatchListener listener) {
        BmobProFile.getInstance(mContext).uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(boolean b, String[] strings, String[] strings2, BmobFile[] files) {
                listener.onSuccess(b, strings, strings2, files);
                L.d(TAG, "  fileNames  " + strings + " urls   " + strings2);
            }

            @Override
            public void onProgress(int i, int i2, int i3, int i4) {
                listener.onProgress(i, i2, i3, i4);
                L.i(TAG, "index : " + i + "  total :" + i3 + "  totalPercent  " + i4);

            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.d(TAG, "  statuscode  " + i + " errormsg   " + s);

            }
        });

    }

    public void delete(String[] fileNames) {
        for (String name : fileNames) {
            delete(name);
        }
    }

    public void delete(String fileName) {
        BmobProFile.getInstance(mContext).deleteFile(fileName, new DeleteFileListener() {

            @Override
            public void onError(int errorcode, String errormsg) {
                // TODO Auto-generated method stub
                Log.i("bmob", "删除文件失败：" + errormsg + "(" + errorcode + ")");
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.i("bmob", "删除文件成功");
            }
        });
    }


    public void download(final String fileName, final DDownloadListener listener) {
        BmobProFile.getInstance(mContext).download(fileName, new DownloadListener() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
                L.d(TAG, " localpath  " + s);
                FileHelper fileHelper = new FileHelper(mContext);
                L.e("下载结果", fileHelper.copyFile(s, fileName));
            }

            @Override
            public void onProgress(String s, int i) {
                listener.onProgress(s, i);
                L.d(TAG, "" + i);
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.e(TAG, "  statuscode  " + i + " errormsg   " + s);
            }
        });
    }

    /**
     * thumbnailName的格式：
     * fileName+"_"+modelId
     * 如果fileName=f24af10cbc15476e9789afaf168c3b8e.jpg，modelId=1，那么thumbnailName=f24af10cbc15476e9789afaf168c3b8e.jpg_1。
     * C、目前Bmob提供的规格类型ID（modelId）有六种：
     * 1--指定宽，高自适应，等比例缩放；
     * 2--指定高，宽自适应，等比例缩放；
     * 3--指定最长边，短边自适应，等比例缩放；
     * 4--指定最短边，长边自适应，等比例缩放；
     * 5--指定最大宽高，等比例缩放；
     * --固定宽高，居中裁剪；
     *
     * @param fileName 文件名
     * @param modelId  缩略图的规格类型ID
     */
    public void submitThumbnailTask(String fileName, int modelId, final DThumbnailListener listener) {
        BmobProFile.getInstance(mContext).submitThumnailTask(fileName, modelId, new ThumbnailListener() {
            @Override
            public void onSuccess(String s, String s2) {
                listener.onSuccess(s, s2);
                L.i(TAG, " thumbnailName   :" + s + "   thumbnailUrl   " + s2);
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.d(TAG, "  statuscode  " + i + " errormsg   " + s);
            }
        });
    }

    public void getLocalThumbnail(String localPath, int modelId, final DLocalThumbnailListener listener) {
        BmobProFile.getInstance(mContext).getLocalThumbnail(localPath, modelId, new LocalThumbnailListener() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
                L.i(TAG, "生成后的缩略图路径 :" + s);
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.d(TAG, "  statuscode  " + i + " errormsg   " + s);

            }
        });

    }

    /**
     * @param localPath 图片的本地路径，需要带后缀名
     * @param modelId   开发者应事先在应用管理后台（文件管理-->自定义版本-->缩略图配置版本）完成基本的生成缩略图的规格配置
     * @param width     指定规格宽
     * @param height    指定规格高
     */
    public void getLocalThumbnail(String localPath, int modelId, int width, int height, final DLocalThumbnailListener listener) {
        BmobProFile.getInstance(mContext).getLocalThumbnail(localPath, modelId, width, height, new LocalThumbnailListener() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
                L.i(TAG, "生成后的缩略图路径 :" + s);
            }

            @Override
            public void onError(int i, String s) {
                listener.onError(i, s);
                L.d(TAG, "  statuscode  " + i + " errormsg   " + s);

            }
        });

    }

    /*public String signURL(String fileName, String fileUrl) {
        return BmobProFile.getInstance(mContext).signURL(fileName, fileUrl, Config.applicationId, 10000000, null);
    }*/




    public interface DUploadListener {
        //url 未签名 不可直接使用
        void onSuccess(String fileName, String url, BmobFile file);

        void onProgress(int progress);

        void onError(int statusCode, String errorMsg);
    }

    public interface DUploadBatchListener {

        public void onError(int statusCode, String errorMsg);

        /**
         * @param curIndex     表示当前第几个文件正在上传
         * @param curPercent   表示当前上传文件的进度值（百分比）
         * @param total        表示总的上传文件数
         * @param totalPercent 表示总的上传进度（百分比）
         */
        public void onProgress(int curIndex, int curPercent, int total, int totalPercent);

        public void onSuccess(boolean isFinish, String[] fileNames, String[] urls, BmobFile[] files);

    }

    public interface DDownloadListener {
        /**
         * @param fullPath 表示下载成功后文件的存储完整地址，和localpath的路径是一样的。
         */
        public void onSuccess(String fullPath);

        /**
         * @param localPath 表示当前下载文件的地址，当下载进度不为100的时候，这个地址下面的文件是不完整的，只有一部分。
         */
        public void onProgress(String localPath, int percent);

        void onError(int statusCode, String errorMsg);

    }

    public interface DThumbnailListener {
        public void onSuccess(String thumbnailName, String thumbnailUrl);

        void onError(int statusCode, String errorMsg);

    }

    public interface DLocalThumbnailListener {
        public void onSuccess(String thumbnailPath);

        void onError(int statusCode, String errorMsg);
    }

    public class FileHelper {

        private Context context;
        private String SDPATH = FileUtils.getFileDownloadPath();
        private String cachePath;

        public FileHelper(Context context) {
            this.context = context;
            cachePath = BmobPro.getInstance(context).getCacheDownloadDir() + "/";
        }


        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            L.e("info", "Not writable");
            return false;
        }

        public File createDir(String dirName) {
            File dir = new File(SDPATH + dirName);
            dir.mkdirs();
            return dir;
        }

        public boolean isFileExist(String fileName) {
            File file = new File(SDPATH + fileName);
            return file.exists();
        }

        public boolean isFileExist(String path, String fileName) {
            File file = new File(SDPATH + path + "/" + fileName);
            return file.exists();
        }

        public boolean writeFile(String fileName, InputStream input) {
            String fileAddress = SDPATH + fileName;
            boolean flag = false;
            OutputStream output = null;
            File file = new File(fileAddress);
            L.e(TAG, file);
            L.e(TAG, isExternalStorageWritable());
            L.e(TAG, file.exists());
            if (isExternalStorageWritable()) {
                try {
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[4 * 1024];
                    int length = 0;
                    while ((length = input.read(buffer)) != -1) {
                        output.write(buffer, 0, length);
                    }
                    flag = true;
                    output.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (output != null)
                        try {
                            output.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }
            return flag;
        }

        public boolean writeFile(String fileName, String path, InputStream input) {
            createDir(path);
            String fileAddress = SDPATH + "/" + path;

            boolean flag = false;
            OutputStream output = null;
            File file = new File(fileAddress, fileName);
            if (isExternalStorageWritable() && !(file.exists())) {
                try {
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[4 * 1024];
                    int length = 0;
                    while ((length = input.read(buffer)) != -1) {
                        output.write(buffer, 0, length);
                    }
                    output.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (output != null)
                        try {
                            output.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }

            }

            return flag;
        }


        public boolean copyFile(final String fromPath, final String toPath, final String filename) {
            final InputStream[] in = {null};
            final boolean[] flag = {false};
            in[0] = inputStream(cachePath + fromPath + "/" + filename);
            flag[0] = writeFile(filename, in[0]);
            return flag[0];
        }

        public boolean copyFile(final String filename) {
            final InputStream[] in = {null};
            final boolean[] flag = {false};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    in[0] = inputStream(cachePath + filename);
                    flag[0] = writeFile(filename, in[0]);
                }
            }).start();

            return flag[0];
        }

        public boolean copyFile(final String fromPath, final String filename) {
            final InputStream[] in = {null};
            final boolean[] flag = {false};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    in[0] = inputStream(fromPath);
                    flag[0] = writeFile(filename, in[0]);
                    new File(fromPath).delete();
                }
            }).start();
            return flag[0];
        }

        public InputStream inputStream(String fileName) {
            InputStream in = null;
            try {
                in = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return in;
        }

        public OutputStream outputStream(String fileName) {
            OutputStream out = null;
            try {
                out = new FileOutputStream(fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return out;
        }

    }

}
