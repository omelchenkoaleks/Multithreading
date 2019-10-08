package com.omelchenkoaleks.multithreading._002_handler_want;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

public class WantActivity extends AppCompatActivity {
    private static final String TAG = "WantActivity";

    private final int STATUS_NONE = 0; // нет подключения
    private final int STATUS_CONNECTING = 1; // подключаемся
    private final int STATUS_CONNECTED = 2; // подключено

    private TextView mConnectTextView;
    private ProgressBar mProgressBar;
    private Button mConnectButton;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_002_want);

        mConnectTextView = findViewById(R.id.connect_text_view);
        mProgressBar = findViewById(R.id.connect_progress_bar);
        mConnectButton = findViewById(R.id.connect_button);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {

                    case STATUS_NONE:
                        mConnectButton.setEnabled(true);
                        mConnectTextView.setText("NOT CONNECTED");
                        break;

                    case STATUS_CONNECTING:
                        mConnectButton.setEnabled(false);
                        mProgressBar.setVisibility(View.VISIBLE);
                        mConnectTextView.setText("CONNECTING");
                        break;

                    case STATUS_CONNECTED:
                        mProgressBar.setVisibility(View.GONE);
                        mConnectTextView.setText("CONNECTED");
                        break;

                    default:
                        break;
                }
            }
        };

        mHandler.sendEmptyMessage(STATUS_NONE);
    }

    public void connectOnClick(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    /*
                        метод sendEmptyMessage - сам создает сообщение Message, заполняет
                        его атрибут want и отправляет его в очередь
                     */

                    // устанавливаем подключение
                    mHandler.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(2);

                    // установлено
                    mHandler.sendEmptyMessage(STATUS_CONNECTED);

                    // выполняется какая-то работа
                    TimeUnit.SECONDS.sleep(3);

                    // разрываем подключение
                    mHandler.sendEmptyMessage(STATUS_NONE);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
