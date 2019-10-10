package com.omelchenkoaleks.multithreading._008_async_task_attr;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

public class AsyncTaskAttrActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private MyTask mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_008_async_task_attr);

        mInfoTextView = findViewById(R.id.parameter_text_vew);
    }

    public void parameterOnClick(View view) {
        mMyTask = new MyTask();
        mMyTask.execute("file_path_1", "file_path_2", "file_path_3", "file_path_4");
    }

    private class MyTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mInfoTextView.setText(getString(R.string.begin));
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                int count = 0;
                for (String url : strings) {
                    // загржаем файл
                    downloadFile(url);
                    // выводим промежуточные результаты
                    publishProgress(++count);
                }
                // разъединяемся
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mInfoTextView.setText("Downloaded " + values[0] + " files");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mInfoTextView.setText(getString(R.string.end));
        }

        private void downloadFile(String url) throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
