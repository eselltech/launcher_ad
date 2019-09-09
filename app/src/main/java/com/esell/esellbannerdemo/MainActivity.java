package com.esell.esellbannerdemo;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.esell.esellbanner.EsellBanner;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int RC_STORAGE = 0112;
//    private EsellBanner mEsellBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        mEsellBanner = findViewById(R.id.eb_content);
//        mEsellBanner.setDuration(5);

        checkAndApplyForPermission();
    }

    private void checkAndApplyForPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        //判断有没有权限
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 如果有权限了, 就做你该做的事情
            setContentView(R.layout.activity_main);
        } else {
            EasyPermissions.requestPermissions(this, "写上你需要用权限的理由, 是给用户看的", RC_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // 如果有权限了, 就做你该做的事情
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "必须有读写权限", Toast.LENGTH_SHORT).show();
        finish();
    }
}
