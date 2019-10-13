package com.omelchenkoaleks.multithreading._011_async_task_status;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

/*
    Всегда можно определить, в каком сотоянии сейчас находится задача.
    Для этого используются статусы:

    PENDING - задача еще не запущена
    RUNNING - задача в работе
    FINISHED - метод onPostExecute отработал, т.е. задача успешно завершена
 */
public class AsyncTaskStatusActivity extends AppCompatActivity {
    private TextView mStatusTextView;
    private StatusTask mStatusTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_011_async_task_status);

        mStatusTextView = findViewById(R.id.status_async_task_text_view);
    }

    public void startStatusOnClick(View view) {
        switch (view.getId()) {
            case R.id.start_status_async_task_button:
                startTask();
                break;
            case R.id.status_async_task_button:
                showStatus();
                break;
            default:
                break;
        }
    }

    private void startTask() {
        // 1 тест =  без метода execute() задача еще не запущена
        mStatusTask = new StatusTask();
        // 2 тест =  запускаем задачу
        mStatusTask.execute();
        // 3 тест = для тестирования сразу отменяем задачу
        mStatusTask.cancel(false);
    }

    private void showStatus() {
        if (mStatusTask != null)
            if (mStatusTask.isCancelled())
                Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();
            else
                // выводим статус в тост методом getStatus() - смотрим:
                Toast.makeText(this, mStatusTask.getStatus().toString(), Toast.LENGTH_SHORT).show();
    }

    private class StatusTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStatusTextView.setText(getString(R.string.begin));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    if (isCancelled()) return null;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mStatusTextView.setText(getString(R.string.end));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mStatusTextView.setText(getString(R.string.cancel));
        }
    }
}
