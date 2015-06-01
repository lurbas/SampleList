package com.lucasurbas.samplelist.viewstate;

import android.os.Bundle;
import android.os.Parcelable;

import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;
import com.lucasurbas.samplelist.viewinterface.IBaseLoadingListView;

/**
 * Created by M30 on 2015-05-31.
 */
public abstract class BaseLoadingListViewState<M extends Parcelable, V extends IBaseLoadingListView<M>> implements RestoreableViewState<V> {

    private final String KEY_STATE = "key_base_state";
    private final String KEY_IS_REFRESHING = "key_base_is_refreshing";
    private final String KEY_DATA = "key_base_data";

    public static final int STATE_INIT = 0;
    public static final int STATE_LOADED = 1;
    public static final int STATE_ERROR = 2;

    private int state = 0;
    private boolean isRefreshing;
    private M data;

    @Override
    public void saveInstanceState(Bundle out) {
        out.putInt(KEY_STATE, state);
        out.putBoolean(KEY_IS_REFRESHING, isRefreshing);
        out.putParcelable(KEY_DATA, data);
    }

    @Override
    public RestoreableViewState<V> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        state = in.getInt(KEY_STATE);
        isRefreshing = in.getBoolean(KEY_IS_REFRESHING, false);
        data = in.getParcelable(KEY_DATA);
        return this;
    }

    @Override
    public void apply(V view, boolean b) {
        switch (state) {
            case STATE_INIT:
                view.showInitState();
                break;

            case STATE_LOADED:
                view.showLoadedSate();
                break;

            case STATE_ERROR:
                view.showErrorState();
                break;
        }
        view.setRefreshing(isRefreshing);
        if(data != null) {
            view.setData(data);
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setRefreshing(boolean isRefreshing) {
        this.isRefreshing = isRefreshing;
    }

    public void setData(M data) {
        this.data = data;
    }
}
