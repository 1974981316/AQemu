package com.swk.aqemu.utils;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showToast(String str){
        Toast.makeText(this,(CharSequence)str,0).show();
    }
}
