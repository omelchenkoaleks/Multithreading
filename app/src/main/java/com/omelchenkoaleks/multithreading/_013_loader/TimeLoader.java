package com.omelchenkoaleks.multithreading._013_loader;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
    Loader - является параметризированным классом, поэтому в скобках указываем,
    что именно будет возвращать лоадер после своей работы.
    В данном случае, лоадер будет возвращать строку со временем.
 */
public class TimeLoader extends Loader<String> {
    private static final String TAG = "Loader";

    private final int PAUSE = 10;

    public static final String ARGS_TIME_FORMAT = "time_format";
    public static final String TIME_FORMAT_SHORT = "h:mm:ss a";
    public static final String TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss";

    private String mFormat;

    private GetTimeTask mGetTimeTask;

    // читаем из Bundle данные о формате времени, если не пришли - задаем short(format)
    public TimeLoader(@NonNull Context context, Bundle args) {
        super(context);
        Log.d(TAG, hashCode() + " create TimeLoader");
        if (args != null) {
            mFormat = args.getString(ARGS_TIME_FORMAT);
        }
        if (TextUtils.isEmpty(mFormat)) {
            mFormat = TIME_FORMAT_SHORT;
        }
    }

    // вызывается при старте (onStart) Activity или фрагмента, к которому привязан Loader
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, hashCode() + " onStartLoading");
    }

    // вызывается при остановке (onStop) Activity или фрагмента, к которому привязан Loader
    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, hashCode() + " onStopLoading");
    }

    /*
        В этом методе кодим работу лоадера.
        В данном случае мы запускаем GetTimeTask, который
        будет для нас получать асиннхронно время.
     */
    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.d(TAG, hashCode() + " onForceLoad");
        if (mGetTimeTask != null) {
            mGetTimeTask.cancel(true);
        }
        mGetTimeTask = new GetTimeTask();
        mGetTimeTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mFormat);
    }

    // метод означает, что Loader становится неактивным
    @Override
    protected void onAbandon() {
        super.onAbandon();
        Log.d(TAG, hashCode() + " onAbandon");
    }

    // означает уничтожение Loader-a, вызывается при закрытии (onDestroy) Activity или фрагмента
    @Override
    protected void onReset() {
        super.onReset();
        Log.d(TAG, hashCode() + " onReset");
    }

    // GetTimeTask по окончании своей работы вызовет этот метод и передаст результат своей работы
    private void getResultFromTask(String result) {
        /*
            Стандартный метод лоадера, который оповещает слушателя, подключенного
            к лоадеру, что работа окончена и передает ему данные.
         */
        deliverResult(result);
    }

    // берет на вход формат даты и через паузу возвращает с помощью
    // нашего метода getResultFromTask в лоадер текущее время в этом формате
    class GetTimeTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, TimeLoader.this.hashCode() + " doInBackground");
            try {
                TimeUnit.SECONDS.sleep(PAUSE);
            } catch (InterruptedException e) {
                return null;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strings[0], Locale.getDefault());
            return simpleDateFormat.format(new Date());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, TimeLoader.this.hashCode() + " onPostExecute " + s);
            getResultFromTask(s);
        }
    }
}
