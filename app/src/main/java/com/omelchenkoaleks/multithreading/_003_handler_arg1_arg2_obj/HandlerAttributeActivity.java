package com.omelchenkoaleks.multithreading._003_handler_arg1_arg2_obj;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HandlerAttributeActivity extends AppCompatActivity {
    private static final String TAG = "HandlerAttribute";

    private final int STATUS_NONE = 0; // нет подключения
    private final int STATUS_CONNECTING = 1; // подключаемся
    private final int STATUS_CONNECTED = 2; // подключено
    private final int STATUS_DOWNLOAD_START = 3; // загрузка началась
    private final int STATUS_DOWNLOAD_END = 4; // загрузка закончена
    private final int STATUS_DOWNLOAD_NONE = 5; // нет файлов для загрузки
    private final int STATUS_DOWNLOAD_FILE = 6; // файл загружен

    private TextView mResultTextView;
    private ProgressBar mProgressBar;
    private Button mConnectButton;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_003_handler_attribute);

        mResultTextView = findViewById(R.id.result_attr_text_view);
        mConnectButton = findViewById(R.id.connect_attr_button);
        mProgressBar = findViewById(R.id.attr_progress_bar);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {

                    case STATUS_NONE:
                        mConnectButton.setEnabled(true);
                        mResultTextView.setText("Not connected!!!");
                        mProgressBar.setVisibility(View.GONE);
                        break;

                    case STATUS_CONNECTING:
                        mConnectButton.setEnabled(false);
                        mResultTextView.setText("Connecting ...");
                        break;

                    case STATUS_CONNECTED:
                        mResultTextView.setText("Connected ...");
                        break;

                    case STATUS_DOWNLOAD_START:
                        mResultTextView.setText("Start download " + msg.arg1 + " files");
                        mProgressBar.setMax(msg.arg1);
                        mProgressBar.setProgress(0);
                        mProgressBar.setVisibility(View.VISIBLE);
                        break;

                    case STATUS_DOWNLOAD_FILE:
                        mResultTextView.setText("Downloading. Left to load " + msg.arg2 + " files ...");
                        mProgressBar.setProgress(msg.arg1);
                        saveFile((byte[]) msg.obj);
                        break;

                    case STATUS_DOWNLOAD_END:
                        mResultTextView.setText("Downloading complete!");
                        break;

                    case STATUS_DOWNLOAD_NONE:
                        mResultTextView.setText("No files for download )))");
                        break;

                    default:
                        break;
                }
            }
        };

        mHandler.sendEmptyMessage(STATUS_NONE);
    }

    public void attrOnClick(View view) {

        Thread thread = new Thread(new Runnable() {
            Message msg;
            byte[] file;
            Random random = new Random();

            @Override
            public void run() {

                try {

                    // устанавливаем подключение
                    mHandler.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(1);

                    // подключение установлено
                    mHandler.sendEmptyMessage(STATUS_CONNECTED);

                    // определяем количество файлов
                    TimeUnit.SECONDS.sleep(1);
                    int filesCount = random.nextInt(5);

                    if (filesCount == 0) {
                        // сообщаем, что файлов для загрузки нет
                        mHandler.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
                        // и отключаемся
                        TimeUnit.MILLISECONDS.sleep(1500);
                        mHandler.sendEmptyMessage(STATUS_NONE);
                        return;
                    }

                    /*
                        загрузка начинается
                        создаем сообщение с информацией о количестве файлов
                     */
                    msg = mHandler.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                    // отправляем
                    mHandler.sendMessage(msg);

                    for (int i = 1; i <= filesCount; i++) {
                        // загружаем файл
                        file = downloadFile();
                        // создаем сообщение о порядоковом номере файла, количеством оставшихся, и сам файл
                        msg = mHandler.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesCount - 1, file);
                        // отправляем
                        mHandler.sendMessage(msg);
                    }

                    // загрузка завершена
                    mHandler.sendEmptyMessage(STATUS_DOWNLOAD_END);

                    // отключаемся
                    TimeUnit.MILLISECONDS.sleep(1500);
                    mHandler.sendEmptyMessage(STATUS_NONE);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    // эмуляция загрузки файлов - ожидание 2 секунды и возвращает массив из 1024 байтов
    private byte[] downloadFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

    // метод заглушка - пока ничего не делает
    private void saveFile(byte[] file) {

    }
}
