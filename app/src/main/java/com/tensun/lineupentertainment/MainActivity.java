package com.tensun.lineupentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.listener.DialogUIListener;
import com.tensun.lineupentertainment.socket.IClient;
import com.tensun.lineupentertainment.socket.SocketClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private ImageView ivNow;
    private ImageView ivLater;
    private ImageView ivQ,ivConnect;
    private TextView tvState;

    private boolean isCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        ivNow = findViewById(R.id.iv_now);
        ivLater = findViewById(R.id.iv_later);
        ivQ = findViewById(R.id.iv_q);
        ivConnect = findViewById(R.id.iv_connect);
        tvState = findViewById(R.id.tv_state);

        ivNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheck = !isCheck;
                if (isCheck) {
                    ivNow.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_br_1_sel));
                    ivLater.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_2_unsel));
                    ivQ.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_bt_2_sel));
                } else {
                    ivNow.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_bt_unsel));
                    ivLater.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_2));
                    ivQ.setImageDrawable(getResources().getDrawable(R.drawable.bg_book_bt_2_unsel));
                }
            }
        });

        ivQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    App.getInstance().run10min();
                    Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ivConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.showAlert(MainActivity.this, "连接数字大屏", "",
                        "请输入大屏IP", "10005", "连接", "取消",
                        false, true, false, new DialogUIListener() {
                            @Override
                            public void onPositive() {
                            }

                            @Override
                            public void onNegative() {

                            }

                            @Override
                            public void onGetInput(CharSequence input1, CharSequence input2) {
                                super.onGetInput(input1, input2);
                                Log.e("onGetInput","input1:" + input1 + "--input2:" + input2);
                                if (!Utils.isIP(input1.toString())) {
                                    Toast.makeText(MainActivity.this,"请输入正确的IP地址",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (!"".equals(input2.toString())) {
                                    if (!Utils.isInteger(input2.toString())||Integer.parseInt(input2.toString())>65536) {
                                        Toast.makeText(MainActivity.this, "请输入正确的端口号", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                tvState.setText("Connecting...");
                                App.getInstance().tcpConnect(input1.toString(),
                                        "".equals(input2.toString()) ? 10005:Integer.parseInt(input2.toString()));

                            }
                        }).show();
            }
        });
    }

    //接收数据解析
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String message) {
        tvState.setText(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
