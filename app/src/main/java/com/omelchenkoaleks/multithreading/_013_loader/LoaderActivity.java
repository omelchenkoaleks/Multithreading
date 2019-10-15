package com.omelchenkoaleks.multithreading._013_loader;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.ContentObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.omelchenkoaleks.multithreading.R;


/*
    Loader - объект, который должен уметь асинхронно выполнять какую-либо задачу. Привязан к
    некоторым lifecycle-методам Activity или Fragment-ов.
    LoaderManager - встроен в Activity и Fragment. Управляет объектами Loader. Он их
    создает, хранит, уничтожает, и стартует или останавливает.
    LoaderCallback - используется LoaderManager-м для взаимодействия с Loader-м.
 */
public class LoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = "Loader";
    public static final int LOADER_TIME_ID = 1;

    private TextView mTimeTextView;
    private RadioGroup mTimeFormatRadioGroup;
    private static int mLastCheckedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_013_loader);

        mTimeTextView = findViewById(R.id.time_text_view);
        mTimeFormatRadioGroup = findViewById(R.id.time_format_radio_group);

        Bundle bundle = new Bundle();
        bundle.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());


        /*
            Важное замечание! Все рассмотренные здесь примеры работают при условии,
            что initLoader вызывается в onCreate.
         */

        // получаем объект LoaderManager, который с помощью своего метода initLoader создаст и вернет нам Loader
        getLoaderManager().initLoader(LOADER_TIME_ID, bundle, this);
        mLastCheckedId = mTimeFormatRadioGroup.getCheckedRadioButtonId();
    }

    private String getTimeFormat() {
        String result = TimeLoader.TIME_FORMAT_SHORT;
        switch (mTimeFormatRadioGroup.getCheckedRadioButtonId()) {
            case R.id.short_radio_button:
                result = TimeLoader.TIME_FORMAT_SHORT;
                break;
            case R.id.long_radio_button:
                result = TimeLoader.TIME_FORMAT_LONG;
                break;
        }
        return result;
    }

    public void getTimeOnClick(View view) {
        Loader<String> loader;
        int id = mTimeFormatRadioGroup.getCheckedRadioButtonId();
        /*
            проверяем, если последний лоадер был создан с учетом того же формата, то просто
            получаем лоадер методом getLoader по ID
            если же формат другой, то нам нужен новый лоадер
            методом forceLoad() запускаем работу
         */
        if (id == mLastCheckedId) {
            loader = getLoaderManager().getLoader(LOADER_TIME_ID);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat());
            loader = getLoaderManager().restartLoader(LOADER_TIME_ID, bundle, this);
            mLastCheckedId = id;
        }
        loader.forceLoad();
    }

    public void observerOnClick(View view) {
        Log.d(TAG, "observerOnClick");
        Loader<String> loader = getLoaderManager().getLoader(LOADER_TIME_ID);
        final ContentObserver observer = loader.new ForceLoadContentObserver();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                observer.dispatchChange(false);
            }
        }, 5000);
    }

    // вызывается, когда требуется создать новый лоадер
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<String> loader = null;
        if (id == LOADER_TIME_ID) {
            loader = new TimeLoader(this, args);
            Log.d(TAG, "onCreateLoader: " + loader.hashCode());
        }
        return loader;
    }

    // срабатывает, когда лоадер закончил свою работу и вернул результат
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(TAG, "onLoadFinished for loader: " + loader.hashCode() + ", result = " + data);
        mTimeTextView.setText(data);
    }

    // срабатывает, когда LoaderManager собрался уничтожать лоадер
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset for loader: " + loader.hashCode());
    }
}
