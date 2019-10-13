package com.omelchenkoaleks.multithreading._010_async_task_cancel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.TimeUnit;

public class CancelActivity extends AppCompatActivity {
    private static final String TAG = "Cancel";

    private TextView mCancelTextView;

    private CancelTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_010_cancel);

        mCancelTextView = findViewById(R.id.cancel_text_view);
    }

    public void cancelOnClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_start_button:
                mTask = new CancelTask();
                mTask.execute();
                break;
            case R.id.cancel_button:
                cancelTask();
                break;
            default:
                break;
        }
    }

    private void cancelTask() {
        if (mTask == null) return;
        Log.d(TAG, "cancelTask: cancel result:" + mTask.cancel(false));
    }

    private class CancelTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCancelTextView.setText(getString(R.string.begin));
            Log.d(TAG, "onPreExecute: " + getString(R.string.begin));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    Log.d(TAG, "doInBackground isCancelled " + isCancelled());
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "doInBackground: Interrupted");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCancelTextView.setText(getString(R.string.end));
            Log.d(TAG, "onPostExecute: " + getString(R.string.end));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mCancelTextView.setText(getString(R.string.cancel));
            Log.d(TAG, "onCancelled: " + getString(R.string.cancel));
        }
    }
}
