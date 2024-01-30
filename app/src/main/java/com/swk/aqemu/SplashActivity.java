package com.swk.aqemu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import android.widget.TextView;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.swk.aqemu.utils.BaseActivity;
import com.swk.aqemu.utils.Utils;
import java.io.File;
import com.swk.aqemu.utils.ZipUtil;
import com.swk.aqemu.utils.CompressStatus;
import java.io.IOException;
import net.lingala.zip4j.exception.ZipException;
import androidx.appcompat.app.AppCompatActivity;
import com.swk.aqemu.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {
  ActivitySplashBinding binding;
  TextView progresstext;
  CircularProgressIndicator progressbar;
  String zip_file_path;
  String data_files_path;
  String qemu_dir;
  Bundle animBundle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
   // getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        
    
        

    super.onCreate(savedInstanceState);
    zip_file_path = this.getFilesDir().getAbsolutePath() + "/qemu-8.0.2.zip";
    data_files_path = this.getFilesDir().getAbsolutePath();
    qemu_dir = data_files_path + "/qemu/";

    // Inflate and get instance of binding
    binding = ActivitySplashBinding.inflate(getLayoutInflater());

    // set content view to binding's root
    setContentView(binding.getRoot());
    // showToast(zip_file_path);

    progresstext = binding.progresstext;
    progressbar = binding.progressbar;
    animBundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
    if (checkQemu()) {
      new Handler(Looper.getMainLooper())
          .postDelayed(
              new Runnable() {

                @Override
                public void run() {

                  startActivity(new Intent(SplashActivity.this, MainActivity.class));
                  finish();
                }
              },
              1000);
    } else {

      /*try {
          new AssetCopyer(SplashActivity.this).copy();
      } catch (IOException e) {
          e.printStackTrace();
          showToast(e.getMessage());
      }*/
      progresstext.setText("正在准备安装");
      new Handler(Looper.getMainLooper())
          .postDelayed(
              new Runnable() {

                @Override
                public void run() {
                  Utils.copyAssetFile(SplashActivity.this, "qemu-8.0.2.zip", data_files_path + "/");
                  try {
                    Thread.sleep(1000);
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                  }

                  File zipFile = new File(zip_file_path);
                  try {
                    ZipUtil.unZipFileWithProgress(zipFile, data_files_path, handler, false);
                  } catch (ZipException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                  }
                }
              },
              1000);

      // showToast(this.getExternalFilesDir("").getAbsolutePath()+"/");

    }
  }

  private Handler handler =
      new Handler() {
        public void handleMessage(Message msg) {
          switch (msg.what) {
            case CompressStatus.START:
              progresstext.setText("开始安装");
              break;
            case CompressStatus.HANDLING:
              Bundle b = msg.getData();
              progressbar.setIndeterminate(false);
              // progressbar.setProgress(b.getInt(CompressStatus.PERCENT));
              progressbar.setProgressCompat(b.getInt(CompressStatus.PERCENT), true);

              progresstext.setText("正在安装Qemu");
              break;
            case CompressStatus.COMPLETED:
              progresstext.setText("正在设置权限");
              progressbar.setIndeterminate(true);
              new File(zip_file_path).delete();
              new Handler(Looper.getMainLooper())
                  .postDelayed(
                      new Runnable() {

                        @Override
                        public void run() {
                          chmodAllFiles(new File(qemu_dir + "bin/"));
                          chmodAllFiles(new File(qemu_dir + "libexec/"));
                          progresstext.setText("安装完成");
                          startActivity(
                              new Intent(SplashActivity.this, MainActivity.class));
                          finish();
                        }
                      },
                      1000);
              break;
            case CompressStatus.ERROR:
              progresstext.setText("解压失败");
              break;
          }
        }
        ;
      };

  boolean checkQemu() {
    if (new File(qemu_dir).exists()) {

      if (new File(qemu_dir + "share/qemu/vgabios-vmware.bin").exists()) {
        return true;
      } else {
        return false;
      }

    } else {
      return false;
    }
  }

  public void chmodAllFiles(File file) {
    if (file.exists()) {
      File[] file_list = file.listFiles();
      Runtime runtime = Runtime.getRuntime();
      for (File listFile : file_list) {
        if (listFile.isDirectory()) {
          // 目录
          try {
            runtime.exec("chmod 0744 " + listFile);
          } catch (IOException e) {
            e.printStackTrace();
            showToast(e.getMessage());
          }

          // 如果为目录使用递归再遍历里面的文件或目录
          chmodAllFiles(listFile);
        } else {
          // 文件
          // progresstext.setText("设置权限: "+listFile);
          try {
            runtime.exec("chmod 0744 " + listFile);
          } catch (IOException e) {
            e.printStackTrace();
            showToast(e.getMessage());
          }
        }
      }
    }
  }
}
