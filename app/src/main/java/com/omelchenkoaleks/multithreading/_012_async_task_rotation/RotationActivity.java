package com.omelchenkoaleks.multithreading._012_async_task_rotation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

public class RotationActivity extends AppCompatActivity {
    private static final String TAG = "rotation";

    private TextView mRotationTextView;
    private RotationTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_012_rotation);

        // смотрим hashCode текущего объекта активити
        Log.d(TAG, "onCreate: RotationActivity: " + this.hashCode());

        mRotationTextView = findViewById(R.id.rotation_text_view);

        mTask = new RotationTask();
        // смотрим hashCod текущего объекта AsyncTask
        Log.d(TAG, "onCreate: RotationTask: " + mTask.hashCode());
        mTask.execute();
    }

    private class RotationTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRotationTextView.setText(getString(R.string.begin));
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d(TAG, "i = " + i
                            + ", mTask: " + this.hashCode()
                            + ", RotationActivity: " + RotationActivity.this.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mRotationTextView.setText("i = " + values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRotationTextView.setText(getString(R.string.end));
        }
    }
}
