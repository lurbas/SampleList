package com.lucasurbas.samplelist.viewinterface;

import android.os.Parcelable;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.lucasurbas.samplelist.viewstate.BaseLoadingListViewState;

/**
 * Created by M30 on 2015-05-31.
 */
public interface IBaseLoadingListView<M extends Parcelable> extends MvpView {

    public void showInitState();

    public void showLoadedSate();

    public void showErrorState();

    public void showToastError();

    public void setRefreshing(boolean isRefreshing);

    public void setData(M data);

    public BaseLoadingListViewState getBaseViewState();
}
