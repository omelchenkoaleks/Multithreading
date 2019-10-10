package com.omelchenkoaleks.multithreading._009_async_task_get;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.omelchenkoaleks.multithreading.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AsyncTaskGetActivity extends AppCompatActivity {
    private static final String TAG = "Get";

    private TextView mGetTextView;
    private GetTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_009_async_task_get);

        mGetTextView = findViewById(R.id.get_text_view);
    }

    public void getOnClick(View view) {
        switch (view.getId()) {
            case R.id.get_start_button:
                mTask = new GetTask();
                mTask.execute();
                break;
            case R.id.get_result_button:
                showResult();
                break;
            default:
                break;
        }
    }

    private void showResult() {
        if (mTask == null) {
            return;
        }
        int result = -1;
        try {
            Log.d(TAG, "showResult: Try to get result!!!");
            result = mTask.get();
            Log.d(TAG, "get returns " + result);
            Toast.makeText(this, "get returns " + result, Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class GetTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: Begin");
            mGetTextView.setText(getString(R.string.begin));
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, "End. Result = " + integer);
            mGetTextView.setText("End. Result = " + integer);
        }
    }
}
