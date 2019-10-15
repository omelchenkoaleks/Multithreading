package com.omelchenkoaleks.multithreading._014_async_task_loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeAsyncLoader extends AsyncTaskLoader<String> {
    private static final String TAG = "AsyncLoader";
    private final int PAUSE = 10;

    public final static String ARGS_TIME_FORMAT = "time_format";
    public final static String TIME_FORMAT_SHORT = "h:mm:ss a";
    public final static String TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss";

    private String mFormat;

    public TimeAsyncLoader(Context context, Bundle args) {
        super(context);
        Log.d(TAG, hashCode() + " TimeAsyncLoader");
        if (args != null) {
            mFormat = args.getString(ARGS_TIME_FORMAT);
        }
        if (TextUtils.isEmpty(mFormat)) {
            mFormat = TIME_FORMAT_SHORT;
        }

    }

    @Override
    public String loadInBackground() {
        Log.d(TAG, hashCode() + " loadInBackground");

        try {
            TimeUnit.SECONDS.sleep(PAUSE);
        } catch (InterruptedException e) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }
}
