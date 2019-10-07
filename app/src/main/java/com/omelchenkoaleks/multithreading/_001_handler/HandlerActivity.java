package com.omelchenkoaleks.multithreading._001_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

public class HandlerActivity extends AppCompatActivity {
    private static final String TAG = "HandlerActivity";

    private TextView mInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_001_handler);

        mInfoTextView = findViewById(R.id.info_handler_text_view);
    }

    public void startOrTestOnClick(View view) {

        switch (view.getId()) {

            case R.id.start_handler_button:
//                /*
//                    Что здесь происходит? Мы полностью занимаем под свои нужды главный поток -
//                    методом downloadFile(). Пока он работает в цикле - все блокируется
//                    и не реагирует на нажатия !!!
//                 */
//                for (int i = 1; i <= 10; i++) {
//                    downloadFile();
//                    mInfoTextView.setText("number of uploaded files:" + i);
//                    Log.d(TAG, "number of uploaded files:" + i);
//                }
//                break;


                /*
                    Что здесь произойдет? Приложение упадет с ошибкой. Работа с View-компонентами
                    доступна только из основного потока. Новые потоки не имеют доступа к элементам
                    экрана !!!
                 */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            downloadFile();
                            mInfoTextView.setText("number of uploaded files:" + i);
                            Log.d(TAG, "number of uploaded files:" + i);
                        }
                    }
                });
                thread.start();
                break;

            case R.id.test_handler_button:
                Log.d(TAG, "Test");
                mInfoTextView.setText("Test");
                break;

            default:
                break;
        }
    }

    private void downloadFile() {
        // просто эмулирует закачку файла
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
