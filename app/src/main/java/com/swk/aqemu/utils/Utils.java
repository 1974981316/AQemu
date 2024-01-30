package com.swk.aqemu.utils;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class Utils {
    public static File copyAssetFile(AppCompatActivity context,String fileName,String copyPath) {
        File temp = new File(copyPath);
        if(!temp.exists()){
            temp.mkdir();
        }
        InputStream inPut = null;
        OutputStream outPut = null;
        String newFileName = null;
        try {
            inPut = context.getAssets().open(fileName);
            newFileName = copyPath + fileName;
            outPut = new FileOutputStream(newFileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inPut.read(buffer)) != -1) {
                outPut.write(buffer, 0, read);
            }
            inPut.close();
            outPut.flush();
            outPut.close();
            return new File(newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
