package com.omelchenkoaleks.multithreading._012_async_task_rotation;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

//        mTask = new RotationTask();
//        // смотрим hashCod текущего объекта AsyncTask
//        Log.d(TAG, "onCreate: RotationTask: " + mTask.hashCode());
//        mTask.execute();


        /*
            смотри лог - хотя мы вернули доступ к старому таску, он продолжает работать где-то
            в памяти со старым экземпляром активити (потому-что объект внутреннего класса
            содержит скрытую ссылку на объект внешнего класса (в данном случае RotationActivity) !!!
         */
        mTask = (RotationTask) getLastCustomNonConfigurationInstance();
        if (mTask == null) {
            mTask = new RotationTask(this);
            mTask.execute();
        }

        // передаем в таск ссылку на текущую активити
        mTask.link(this);

        Log.d(TAG, "onCreate: mTask: " + mTask.hashCode());
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // удаляем из таски ссылку на старое RotationActivity
        mTask.unLink();
        return mTask;
    }

    /*
        делаем внутренний класс static - теперь он никак не связан с объектом внешнего класса и
        не содержит скрытую ссылку на него !!!

        Но нам нужно получить доступ к объектам (RotationActivity) - для этого создадим ссылку.
        Для этого в RotationTask опишем объект, он и будет ссылаться на RotationActivity. А мы будем
        этой ссылкой управлять - когда создается новое RotationActivity, мы будем давать ссылку на него в RotationTask.
     */
    static class RotationTask extends AsyncTask<String, Integer, Void> {

        private Context mContext;
        private RotationActivity mRotationActivity;

        RotationTask(RotationActivity activity) {
            mContext = activity.getApplicationContext();
            mRotationActivity = activity;
        }

        // получаем ссылку на RotationActivity
        private void link(RotationActivity activity) {
            mRotationActivity = activity;
        }

        // обнуляем ссылку
        private void unLink() {
            mRotationActivity = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRotationActivity.mRotationTextView.setText(mContext.getString(R.string.begin));
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d(TAG, "i = " + i
                            + ", mTask: " + this.hashCode()
                            + ", RotationActivity: " + this.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mRotationActivity.mRotationTextView.setText("i = " + values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRotationActivity.mRotationTextView.setText(mContext.getString(R.string.end));
        }
    }
}
