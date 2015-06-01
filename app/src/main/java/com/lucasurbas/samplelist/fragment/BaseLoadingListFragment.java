package com.lucasurbas.samplelist.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.dagger1.Injector;
import com.hannesdorfmann.mosby.dagger1.viewstate.Dagger1MvpViewStateFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;
import com.lucasurbas.samplelist.R;
import com.lucasurbas.samplelist.viewinterface.IBaseLoadingListView;
import com.lucasurbas.samplelist.viewstate.BaseLoadingListViewState;

import butterknife.InjectView;
import dagger.ObjectGraph;

/**
 * Created by M30 on 2015-05-31.
 */
public abstract class BaseLoadingListFragment<M extends Parcelable, V extends IBaseLoadingListView<M>, P extends MvpPresenter<V>> extends Dagger1MvpViewStateFragment<V, P> implements IBaseLoadingListView<M> {

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    @InjectView(R.id.errorView)
    View errorView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_loading_list;
    }

    @Override
    public void showInitState() {
        ((BaseLoadingListViewState) getViewState()).setState(BaseLoadingListViewState.STATE_INIT);
        //recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadedSate() {
        ((BaseLoadingListViewState) getViewState()).setState(BaseLoadingListViewState.STATE_LOADED);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorState() {
        ((BaseLoadingListViewState) getViewState()).setState(BaseLoadingListViewState.STATE_ERROR);
        //recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToastError(){
        Toast.makeText(getActivity(), R.string.t_no_connection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRefreshing(final boolean isRefreshing) {
        ((BaseLoadingListViewState) getViewState()).setRefreshing(isRefreshing);
        if (swipeRefreshLayout.isRefreshing() != isRefreshing) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(isRefreshing);
                }
            });
        }
    }

    @Override
    public void setData(M data) {
        ((BaseLoadingListViewState) getViewState()).setData(data);
    }

    @Override
    public BaseLoadingListViewState getBaseViewState() {
        return (BaseLoadingListViewState) getViewState();
    }

    @Override
    public ObjectGraph getObjectGraph() {
        if(this.getActivity() == null) {
            throw new NullPointerException("Activity is null");
        } else if(!(this.getActivity().getApplication() instanceof Injector)) {
            throw new IllegalArgumentException(this.getActivity() + " must implement " + Injector.class.getCanonicalName() + "," + " because as default it uses Activity.getObjectGraph(). " + "However, you can override this behaviour by overriding this method.");
        } else {
            return ((Injector)this.getActivity().getApplication()).getObjectGraph();
        }
    }
}

