package com.tensun.digitalscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tensun.digitalscreen.socket.IServer;
import com.tensun.digitalscreen.socket.SocketServer;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements IServer {

    /*
    通讯协议
    start_man,十分钟计时开始，人物出场
    disappear1，消失第一个顾客
    disappear2，消失第二个顾客
    disappear3，消失第三个顾客
    disappear4，消失第四个顾客
    play_hi,显示hi
    play_emoji1,显示第一个表情
    play_emoji2,显示第二个表情
    play_emoji3,显示第三个表情
    play_emoji4,显示第四个表情
    play_boy_action1,显示第一个动作
    play_boy_action2,显示第二个动作
    play_boy_action3,显示第三个动作
    play_boy_action4,显示第四个动作

     */
    private SocketServer mServer;
    private TextView tvIp;
    private GifImageView gvMy,gvOther1,gvOther2,gvOther3,gvOther4;
    private RelativeLayout rlEmoji,rl1,rl2,rl3,rl4,rl,rlEmoji1,rlEmoji2,rlEmoji3,rlEmoji4;
    private TextView tvEmoji,tvEmoji1,tvEmoji2,tvEmoji3,tvEmoji4;
    private String[] emoji = {"\uD83D\uDE04","\uD83D\uDE0D","\uD83D\uDE18","\uD83D\uDE0F","\uD83D\uDE1D","\uD83D\uDE2D","\uD83D\uDE2C"};
    private long showTime = 3 * 1000;
    private ImageView ivState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mServer = new SocketServer(10005, this);
        tvIp = findViewById(R.id.tv_ip);
        gvMy = findViewById(R.id.gv_my);
        ivState = findViewById(R.id.iv_state);
        gvOther1 = findViewById(R.id.gv_customer_1);
        gvOther2 = findViewById(R.id.gv_customer_2);
        gvOther3 = findViewById(R.id.gv_customer_3);
        gvOther4 = findViewById(R.id.gv_customer_4);
        tvEmoji = findViewById(R.id.tv_emoji);
        rlEmoji = findViewById(R.id.rl_emoji);
        rl1 = findViewById(R.id.rl_v_1);
        rl2 = findViewById(R.id.rl_v_2);
        rl3 = findViewById(R.id.rl_v_3);
        rl4 = findViewById(R.id.rl_v_4);
        rl = findViewById(R.id.rl_v);
        tvEmoji1 = findViewById(R.id.tv_emoji_1);
        tvEmoji2 = findViewById(R.id.tv_emoji_2);
        tvEmoji3 = findViewById(R.id.tv_emoji_3);
        tvEmoji4 = findViewById(R.id.tv_emoji_4);
        rlEmoji1 = findViewById(R.id.rl_emoji_1);
        rlEmoji2 = findViewById(R.id.rl_emoji_2);
        rlEmoji3 = findViewById(R.id.rl_emoji_3);
        rlEmoji4 = findViewById(R.id.rl_emoji_4);

        tvIp.setText("IP:"+Utils.getIpAddressString());

        mHandler.postDelayed(runShow1, 20 * 1000);
        mHandler.postDelayed(runShow2, 30 * 1000);
        mHandler.postDelayed(runShow3, 15 * 1000);
        mHandler.postDelayed(runShow4, 49 * 1000);

    }

    @Override
    public void rxData(final String data, String ip) {
        Log.e(ip, data);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (data) {
                    case "start":
                        rl.setVisibility(View.GONE);
                        rl1.setVisibility(View.VISIBLE);
                        rl2.setVisibility(View.VISIBLE);
                        rl3.setVisibility(View.VISIBLE);
                        rl4.setVisibility(View.VISIBLE);
                        break;
                    case "start_man":
                        rl.setVisibility(View.VISIBLE);
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_stand_normal ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "start_woman":
                        rl.setVisibility(View.VISIBLE);
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_stand_normal ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "disappear1":
                        rl1.setVisibility(View.GONE);
                        break;
                    case "disappear2":
                        rl2.setVisibility(View.GONE);
                        break;
                    case "disappear3":
                        rl3.setVisibility(View.GONE);
                        break;
                    case "disappear4":
                        rl4.setVisibility(View.GONE);
                        break;
                    case "disappear5":
                        rl.setVisibility(View.GONE);
                        break;
                    case "mandance1":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_dance_1 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "mandance2":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_dance_2 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "womandance1":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_dance_1 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "womandance2":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_dance_2 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "boy_stand_1":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_stand_1 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "boy_stand_2":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_stand_2 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "boy_stand_3":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_stand_3 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "boy_stand_normal":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_stand_normal ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "girl_stand_1":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_stand_1 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "girl_stand_2":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_stand_2 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "girl_stand_3":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_stand_3 ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "girl_stand_normal":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_stand_normal ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "mangreet":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_boy_greet ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "womangreet":
                        try {
                            gvMy.setImageDrawable(new GifDrawable(getResources(),R.drawable.ic_girl_greet ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "gone_hi":
                        rlEmoji.setVisibility(View.GONE);
                        break;
                }

                if ("text".equals(data.substring(0,4))) {
                    String str = data.split("_")[1];
                    Log.e("!!!data", str);

                    if ("Hi".equals(str)) {
                        tvEmoji.setText(str);
                    } else if ("Yo".equals(str)){
                        tvEmoji.setText(str);
                    }else {
                        tvEmoji.setText(emoji[Integer.parseInt(str)-1]);
                    }
                    rlEmoji.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    Handler mHandler = new Handler();
    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            rlEmoji1.setVisibility(View.GONE);
        }
    };
    Runnable runShow1 = new Runnable() {
        @Override
        public void run() {
            try {
                GifDrawable gifDrawable=null;

                switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                    case 1:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p1_stand_1);
                        break;
                    case 8:
                    case 9:
                        rlEmoji1.setVisibility(View.VISIBLE);
                        tvEmoji1.setText(emoji[2]);
                        mHandler.removeCallbacks(run1);
                        mHandler.postDelayed(run1,showTime);
                    case 2:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p1_stand_2);
                        break;
                    case 3:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p1_stand_3);
                        break;
                    case 11:
                    case 12:
                        rlEmoji1.setVisibility(View.VISIBLE);
                        tvEmoji1.setText("Hi");
                        mHandler.removeCallbacks(run1);
                        mHandler.postDelayed(run1,showTime);
                    case 10:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p1_stand_normal);
                        break;
                    case 5:
                    case 6:
                    case 7:
                        rlEmoji1.setVisibility(View.VISIBLE);
                        tvEmoji1.setText(emoji[4]);
                        mHandler.removeCallbacks(run1);
                        mHandler.postDelayed(run1,showTime);
                    default:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p1_stand_normal);
                        break;
                }
                gvOther1.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(runShow1);
                mHandler.postDelayed(runShow1, gifDrawable.getDuration());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            rlEmoji2.setVisibility(View.GONE);
        }
    };
    Runnable runShow2 = new Runnable() {
        @Override
        public void run() {
            try {
                GifDrawable gifDrawable=null;

                switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                    case 1:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p2_stand_1);
                        break;
                    case 8:
                    case 9:
                        rlEmoji2.setVisibility(View.VISIBLE);
                        tvEmoji2.setText(emoji[0]);
                        mHandler.removeCallbacks(run2);
                        mHandler.postDelayed(run2,showTime);
                    case 2:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p2_stand_2);
                        break;
                    case 3:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p2_stand_3);
                        break;
                    case 11:
                    case 12:
                        rlEmoji2.setVisibility(View.VISIBLE);
                        tvEmoji2.setText("Hi");
                        mHandler.removeCallbacks(run2);
                        mHandler.postDelayed(run2,showTime);
                    case 10:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p2_stand_normal);
                        break;
                    case 5:
                    case 6:
                    case 7:
                        rlEmoji2.setVisibility(View.VISIBLE);
                        tvEmoji2.setText(emoji[3]);
                        mHandler.removeCallbacks(run2);
                        mHandler.postDelayed(run2,showTime);
                    default:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p2_stand_normal);
                        break;
                }
                gvOther2.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(runShow2);
                mHandler.postDelayed(runShow2, gifDrawable.getDuration());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable run3 = new Runnable() {
        @Override
        public void run() {
            rlEmoji3.setVisibility(View.GONE);
        }
    };
    Runnable runShow3 = new Runnable() {
        @Override
        public void run() {
            try {
                GifDrawable gifDrawable=null;

                switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                    case 1:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p3_stand_1);
                        break;
                    case 8:
                    case 9:
                        rlEmoji3.setVisibility(View.VISIBLE);
                        tvEmoji3.setText(emoji[1]);
                        mHandler.removeCallbacks(run3);
                        mHandler.postDelayed(run3,showTime);
                    case 2:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p3_stand_2);
                        break;
                    case 3:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p3_stand_3);
                        break;
                    case 11:
                    case 12:
                        rlEmoji3.setVisibility(View.VISIBLE);
                        tvEmoji3.setText("Hi");
                        mHandler.removeCallbacks(run3);
                        mHandler.postDelayed(run3,showTime);
                    case 10:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p3_stand_normal);
                        break;
                    case 5:
                    case 6:
                    case 7:
                        rlEmoji3.setVisibility(View.VISIBLE);
                        tvEmoji3.setText(emoji[6]);
                        mHandler.removeCallbacks(run3);
                        mHandler.postDelayed(run3,showTime);
                    default:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p3_stand_normal);
                        break;
                }
                gvOther3.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(runShow3);
                mHandler.postDelayed(runShow3, gifDrawable.getDuration());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable run4 = new Runnable() {
        @Override
        public void run() {
            rlEmoji4.setVisibility(View.GONE);
        }
    };
    Runnable runShow4 = new Runnable() {
        @Override
        public void run() {
            try {
                GifDrawable gifDrawable=null;

                switch ((int) (1 + Math.random() * (50 - 1 + 1))) {
                    case 1:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p4_stand_1);
                        break;
                    case 8:
                    case 9:
                        rlEmoji4.setVisibility(View.VISIBLE);
                        tvEmoji4.setText(emoji[2]);
                        mHandler.removeCallbacks(run4);
                        mHandler.postDelayed(run4,showTime);
                    case 2:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p4_stand_2);
                        break;
                    case 3:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p4_stand_3);
                        break;
                    case 11:
                    case 12:
                        rlEmoji4.setVisibility(View.VISIBLE);
                        tvEmoji4.setText("Hi");
                        mHandler.removeCallbacks(run4);
                        mHandler.postDelayed(run4,showTime);
                    case 10:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p4_stand_normal);
                        break;
                    case 5:
                    case 6:
                    case 7:
                        rlEmoji4.setVisibility(View.VISIBLE);
                        tvEmoji4.setText(emoji[5]);
                        mHandler.removeCallbacks(run4);
                        mHandler.postDelayed(run4,showTime);
                    default:
                        gifDrawable=new GifDrawable(getResources(), R.drawable.ic_p4_stand_normal);
                        break;
                }
                gvOther4.setImageDrawable(gifDrawable);
                mHandler.removeCallbacks(runShow4);
                mHandler.postDelayed(runShow4, gifDrawable.getDuration());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void addClient(final String ip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivState.setImageResource(R.drawable.ic_connect);
            }
        });
    }

    @Override
    public void removeClient(final String ip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivState.setImageResource(R.drawable.ic_unconnect);
            }
        });
    }
}
