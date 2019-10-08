package com.omelchenkoaleks.multithreading._004_handler_delayd_remove_callback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DelayedActivity extends AppCompatActivity {
    private static final String TAG = "DelayedActivity";

    private Handler mHandler;

    /*
        Handler может может использовать для обработки сообщений объект,
        реализующий интерфейс Handler.Callback.
     */
    Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "what: " + msg.what);
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Теперь Handler поручает обрабатывать сообщения, полученному объекту Handler.Callback.
        mHandler = new Handler(mCallback);

        sendMessage();
    }

    private void sendMessage() {
        Log.d(TAG, "sendMessage: running");

        /*
            Отсчет задержки начинается после помещения в очередь,
            а не после обработки предыдущего сообщения.
            Т.е. эти сообщения по отношению друг к другу сработают с интервалом в одну секунду
         */
        mHandler.sendEmptyMessageDelayed(1, 1000);
        mHandler.sendEmptyMessageDelayed(2, 2000);
        mHandler.sendEmptyMessageDelayed(3, 3000);
    }
}
