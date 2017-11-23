package com.wuzhong.codes.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作的工具类
 * 文件名,来源路径,目标路径,是否覆盖.
 * Created by Wuzhong on 2017-3-14.
 */

public class FileUtil {
    public static void copyAssetsToSdcard(Context mContext, boolean isCover, String souse, String dest) {
        File toCopyFile = new File(dest);
        if (isCover || (!isCover && !toCopyFile.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = mContext.getResources().getAssets().open(souse);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ee) {
                ee.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException eee) {
                        eee.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }
}
