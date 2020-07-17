package com.tensun.lineupentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIItemListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class InteractionActivity extends AppCompatActivity {

    private GifImageView gvAvater;
    private ImageView ivGreet,ivEmoji;
    private RelativeLayout rlHi;
    private String sex;
    private boolean isIndex;
    private TextView tvEmoji;
    private LinearLayout llEmoji;
    private TextView tvEmoji1,tvEmoji2,tvEmoji3,tvEmoji4,tvEmoji5,tvEmoji6,tvEmoji7;
    private RelativeLayout rlAlarm;
    private ImageView ivBt;
    private long showTime = 3 * 1000;
    private boolean isSpeak;
    private Vibrator vibrator;//震动
    private TextView tvConsumers;
    private ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);
        EventBus.getDefault().register(this);

        sex = getIntent().getStringExtra("sex");
        gvAvater = findViewById(R.id.iv_avater);
        rlHi = findViewById(R.id.rl_hi);
        ivEmoji = findViewById(R.id.iv_emoji);
        ivGreet = findViewById(R.id.iv_greet);
        tvEmoji = findViewById(R.id.tv_emoji);
        llEmoji = findViewById(R.id.ll_emoji);
        tvEmoji1 = findViewById(R.id.tv_emoji_1);
        tvEmoji2 = findViewById(R.id.tv_emoji_2);
        tvEmoji3 = findViewById(R.id.tv_emoji_3);
        tvEmoji4 = findViewById(R.id.tv_emoji_4);
        tvEmoji5 = findViewById(R.id.tv_emoji_5);
        tvEmoji6 = findViewById(R.id.tv_emoji_6);
        tvEmoji7 = findViewById(R.id.tv_emoji_7);
        rlAlarm = findViewById(R.id.rl_alarm);
        ivBt = findViewById(R.id.iv_bt);
        tvConsumers = findViewById(R.id.tv_consumers);
        progressbar = findViewById(R.id.progressbar);

        vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);

        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(),
                    sex.equals("man") ?R.drawable.ic_boy_stand_normal :R.drawable.ic_girl_stand_normal);
            gvAvater.setImageDrawable(gifDrawable);
            mHandler.removeCallbacks(l);
            mHandler.postDelayed(l, gifDrawable.getDuration());
            Log.e("!!!time", gifDrawable.getDuration()+"zzz");
        } catch (IOException e) {
            e.printStackTrace();
        }

        gvAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isIndex = !isIndex;
                GifDrawable gifDrawable=null;

                if (isIndex) {
                    try {
                        gifDrawable=new GifDrawable(getResources(), sex.equals("man") ? R.drawable.ic_boy_dance_1 : R.drawable.ic_girl_dance_1);
                        App.getInstance().sendTcp(sex+"dance1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        gifDrawable=new GifDrawable(getResources(), sex.equals("man") ? R.drawable.ic_boy_dance_2 : R.drawable.ic_girl_dance_2);
                        App.getInstance().sendTcp(sex+"dance2");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                gvAvater.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(l);
                mHandler.postDelayed(l, gifDrawable.getDuration());
                Log.e("!!!time", gifDrawable.getDuration()+"zzz");

            }
        });

        ivEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llEmoji.getVisibility() == View.GONE) {
                    llEmoji.setVisibility(View.VISIBLE);
                } else {
                    llEmoji.setVisibility(View.GONE);
                }

            }
        });
        ivGreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                isSpeak = !isSpeak;
                if (isSpeak) {
                    tvEmoji.setText("Hi");
                    App.getInstance().sendTcp("text_Hi");
                } else {
                    tvEmoji.setText("Yo");
                    App.getInstance().sendTcp("text_Yo");
                }
                GifDrawable gifDrawable=null;

                try {
                    gifDrawable=new GifDrawable(getResources(), sex.equals("man") ? R.drawable.ic_boy_greet : R.drawable.ic_girl_greet);
                    App.getInstance().sendTcp(sex+"greet");

                    gvAvater.setImageDrawable(gifDrawable);
                    mHandler.removeCallbacks(l);
                    mHandler.postDelayed(l, gifDrawable.getDuration());
                    Log.e("!!!time", gifDrawable.getDuration()+"zzz");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });

        tvEmoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE04");
                App.getInstance().sendTcp("text_1");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE0D");
                App.getInstance().sendTcp("text_2");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE18");
                App.getInstance().sendTcp("text_3");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE0F");
                App.getInstance().sendTcp("text_4");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE1D");
                App.getInstance().sendTcp("text_5");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE2D");
                App.getInstance().sendTcp("text_6");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });
        tvEmoji7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHi.setVisibility(View.VISIBLE);
                tvEmoji.setText("\uD83D\uDE2C");
                App.getInstance().sendTcp("text_7");

                mHandler.removeCallbacks(r);
                mHandler.postDelayed(r, showTime);
            }
        });

        ivBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlAlarm.setVisibility(View.GONE);
                vibrator.cancel();
            }
        });
    }

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            rlHi.setVisibility(View.GONE);
            App.getInstance().sendTcp("gone_hi");

        }
    };

    Runnable l = new Runnable() {
        @Override
        public void run() {
            try {
                GifDrawable gifDrawable=null;

                if (sex.equals("man")) {

                    switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                        case 2:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_boy_stand_1);
                            App.getInstance().sendTcp("boy_stand_1");
                            break;
                        case 3:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_boy_stand_2);
                            App.getInstance().sendTcp("boy_stand_2");
                            break;
                        case 4:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_boy_stand_3);
                            App.getInstance().sendTcp("boy_stand_3");
                            break;
                        default:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_boy_stand_normal);
                            App.getInstance().sendTcp("boy_stand_normal");
                            break;
                    }

                } else {
                    switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                        case 2:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_girl_stand_1);
                            App.getInstance().sendTcp("girl_stand_1");
                            break;
                        case 3:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_girl_stand_2);
                            App.getInstance().sendTcp("girl_stand_2");
                            break;
                        case 4:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_girl_stand_3);
                            App.getInstance().sendTcp("girl_stand_3");
                            break;
                        default:
                            gifDrawable=new GifDrawable(getResources(), R.drawable.ic_girl_stand_normal);
                            App.getInstance().sendTcp("girl_stand_normal");
                            break;
                    }
                }
                gvAvater.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(l);
                mHandler.postDelayed(l, gifDrawable.getDuration());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String alarm) {
        if ("alarm".equals(alarm)) {
            rlAlarm.setVisibility(View.VISIBLE);
            vibrator.vibrate(new long[]{100,300}, 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Integer progress) {
        progressbar.setProgress(progress);
        if (progress == 20) {
            tvConsumers.setText("3 consumers are ahead of you");
        } else if (progress == 40) {
            tvConsumers.setText("2 consumers are ahead of you");
        } else if (progress == 60) {
            tvConsumers.setText("1 consumers are ahead of you");
        }else if (progress == 80) {
            tvConsumers.setText("0 consumers are ahead of you");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
