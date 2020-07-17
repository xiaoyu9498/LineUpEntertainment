package com.tensun.lineupentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AvaterActivity extends AppCompatActivity {

    private GifImageView gvAvater;
    private ImageView ivBack,ivBtQueue;
    private String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avater);

        gvAvater = findViewById(R.id.iv_avater);
        ivBack = findViewById(R.id.iv_back);
        ivBtQueue = findViewById(R.id.iv_bt_queue);
        final Intent intent = getIntent();
        sex=intent.getStringExtra("sex");

        try {
//            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ic_avater_boy);
            gvAvater.setImageDrawable(sex.equals("man")
                    ? new GifDrawable(getResources(), R.drawable.ic_avater_boy)
                    :new GifDrawable(getResources(), R.drawable.ic_avater_girl));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivBtQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("close");
                App.getInstance().sendTcp("start_"+sex);
                Intent intent1 = new Intent(AvaterActivity.this, InteractionActivity.class);
                intent1.putExtra("sex", sex);
                startActivity(intent1);
                finish();
            }
        });
    }

}
