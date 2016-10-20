package cn.opensrc.androidcommon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.opensrc.comnlib.utils.PhoneUtils;

/**
 * Author:       sharp
 * Created on:   8/3/16 5:05 PM
 * Description:
 * Revisions:
 */
public class TestUtilsAty extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_testuitls);
        Log.d("cpu", PhoneUtils.getCpuName());
    }

}
