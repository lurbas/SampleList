package com.lucasurbas.samplelist;

import android.content.Context;

import com.lucasurbas.samplelist.adapter.ItemsAdapter;
import com.lucasurbas.samplelist.constant.Url;
import com.lucasurbas.samplelist.presenter.SampleListPresenter;
import com.lucasurbas.samplelist.request.CsvItemsConverter;
import com.lucasurbas.samplelist.request.SpreadsheetApi;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by M30 on 2015-05-31.
 */
@Module(
        library = true,
        injects = {
                SampleListPresenter.class, ItemsAdapter.class
        })

public class InjectionModule {

    private Context applicationContext;

    public InjectionModule(Context context) {
        this.applicationContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    Picasso providesPicasso() {
        return Picasso.with(applicationContext);
    }

    @Provides
    @Singleton
    public SpreadsheetApi providesSpreadsheetApi() {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setCache(new Cache(applicationContext.getCacheDir(), 10 * 1024 * 1024));

        RestAdapter restAdapter = new RestAdapter.Builder().setClient(new OkClient(client))
                .setEndpoint(Url.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new CsvItemsConverter())
                .build();

        return restAdapter.create(SpreadsheetApi.class);
    }
}

