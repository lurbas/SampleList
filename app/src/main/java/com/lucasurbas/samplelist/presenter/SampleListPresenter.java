package com.lucasurbas.samplelist.presenter;

import android.os.Handler;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.lucasurbas.samplelist.constant.Url;
import com.lucasurbas.samplelist.model.Item;
import com.lucasurbas.samplelist.model.Items;
import com.lucasurbas.samplelist.request.SpreadsheetApi;
import com.lucasurbas.samplelist.viewinterface.ISampleListView;
import com.lucasurbas.samplelist.viewstate.BaseLoadingListViewState;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by M30 on 2015-05-31.
 */
public class SampleListPresenter extends MvpBasePresenter<ISampleListView> {

    private SpreadsheetApi spreadsheetApi;

    @Inject
    public SampleListPresenter(SpreadsheetApi spreadsheetApi) {
        this.spreadsheetApi = spreadsheetApi;
    }

    public void loadData() {
        if (isViewAttached()) {
            getView().setRefreshing(true);
        }

        spreadsheetApi.getSpreadsheet(Url.VALUE_KEY, Url.VALUE_SINGLE, Url.VALUE_GID, Url.VALUE_OUTPUT, new Callback<Items>() {
            @Override
            public void success(Items items, Response response) {
                if (isViewAttached()) {
                    getView().setRefreshing(false);
                    getView().showLoadedSate();
                    getView().setData(items);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (isViewAttached()) {
                    getView().setRefreshing(false);
                    getView().showToastError();
                    int state = getView().getBaseViewState().getState();
                    if (state != BaseLoadingListViewState.STATE_LOADED) {
                        getView().showErrorState();
                    }
                }
            }
        });
    }
}
