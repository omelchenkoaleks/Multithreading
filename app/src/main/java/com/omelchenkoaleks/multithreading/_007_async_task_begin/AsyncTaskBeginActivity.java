package com.omelchenkoaleks.multithreading._007_async_task_begin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

import static com.omelchenkoaleks.multithreading.R.string.*;

public class AsyncTaskBeginActivity extends AppCompatActivity {
    private TextView mBeginTextView;
    private MyBeginTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_007_async_task_begin);

        mBeginTextView = findViewById(R.id.begin_text_view);
    }

    public void beginOnClick(View view) {
        mTask = new MyBeginTask();
        mTask.execute();
    }

    /*
        <Void, Void, Void> - означает, что мы не используем параметры.
     */
    private class MyBeginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBeginTextView.setText(getString(begin));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBeginTextView.setText(getString(R.string.end));
        }
    }
}
