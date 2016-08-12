package cn.opensrc.androidcommon;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cn.opensrc.comnlib.interfaces.OnBitmapBlurListener;
import cn.opensrc.comnlib.utils.ImgUtils;

/**
 * Author:       sharp
 * Created on:   8/3/16 5:05 PM
 * Description:
 * Revisions:
 */
public class TestUtilsAty extends AppCompatActivity{

    private ImageView ivOri;
    private ImageView ivBlured;
    private Bitmap oriBitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_testuitls);

        ivOri = (ImageView) findViewById(R.id.ivOri);
        ivBlured = (ImageView) findViewById(R.id.ivBlured);
        oriBitmap = ImgUtils.getBitmapFromRes(this,R.mipmap.w1);
        ivOri.setImageBitmap(oriBitmap);

    }

    public void startBlur(View v){
        ImgUtils.blurBitmapPixelWithJni(oriBitmap, 50, new OnBitmapBlurListener() {
            @Override
            public void onComplete(Bitmap bluredBitmap) {
                ivBlured.setImageBitmap(bluredBitmap);
            }
        });
    }
}
