package com.swk.aqemu;

import androidx.annotation.NonNull;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.Window;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialFadeThrough;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.swk.aqemu.utils.BaseActivity;
import java.io.File;
import com.swk.aqemu.utils.ZipUtil;
import net.lingala.zip4j.exception.ZipException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.swk.aqemu.databinding.ActivityMainBinding;
import com.swk.aqemu.fragment.VmsFragment;
import com.swk.aqemu.fragment.ImageStoreFragment;
import com.swk.aqemu.fragment.ImageFactoryFragment;

public class MainActivity extends BaseActivity {
  private ActivityMainBinding binding;
  String zip_file_path;
  String data_files_path;
  String qemu_dir;
  BottomNavigationView bnv;
  Toolbar toolbar;
  Fragment vmsFragment = new VmsFragment();
  Fragment imageStoreFragment = new ImageStoreFragment();
  Fragment imageFactoryFragment = new ImageFactoryFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    // TODO: Configure a return transition in the backwards direction.

    super.onCreate(savedInstanceState);
    zip_file_path = this.getExternalFilesDir("qemu-8.0.2.zip").getAbsolutePath();
    data_files_path = this.getFilesDir().getAbsolutePath();
    qemu_dir = data_files_path + "/qemu/";

    // Inflate and get instance of binding
    binding = ActivityMainBinding.inflate(getLayoutInflater());

    // set content view to binding's root
    setContentView(binding.getRoot());
    toolbar = binding.toolbar;
    setSupportActionBar(toolbar);
        getWindow().setNavigationBarColor(MaterialColors.getColor(this,R.attr.colorSurfaceContainer,0));

    replaceFragment(new VmsFragment());
    bnv = binding.bnv;
    bnv.setSelectedItemId(R.id.item_1);
    bnv.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {

          @Override
          // 处理导航栏子项的点击事件
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            binding.collapsingToolbarLayout.setTitle(menuItem.getTitle());

            if (itemId == R.id.item_1) {
              replaceFragment(vmsFragment); // id为tab_one则第一项被点击，遂用HomeFragment替换空Fragment
              menuItem.setChecked(true);
            } else if (itemId == R.id.item_2) {
              replaceFragment(imageStoreFragment); // id为tab_one则第一项被点击，遂用HomeFragment替换空Fragment
              menuItem.setChecked(true);
            } else if (itemId == R.id.item_3) {
              replaceFragment(imageFactoryFragment); // id为tab_one则第一项被点击，遂用HomeFragment替换空Fragment
              menuItem.setChecked(true);
            }
            return false;
          }
        });
    File f = new File(zip_file_path);
    if (f.exists()) {
      f.delete();
    }
  }

  public void replaceFragment(Fragment fragment) {
    MaterialFadeThrough anim = new MaterialFadeThrough();
    fragment.setEnterTransition(anim);
    fragment.setExitTransition(anim);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.main_fragment_container, fragment);
    transaction.commit();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
  }
}
