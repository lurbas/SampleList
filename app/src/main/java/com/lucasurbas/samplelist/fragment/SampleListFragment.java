package com.lucasurbas.samplelist.fragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.lucasurbas.samplelist.R;
import com.lucasurbas.samplelist.adapter.ItemsAdapter;
import com.lucasurbas.samplelist.model.Items;
import com.lucasurbas.samplelist.presenter.SampleListPresenter;
import com.lucasurbas.samplelist.ui.GridSpacingDecoration;
import com.lucasurbas.samplelist.viewinterface.ISampleListView;
import com.lucasurbas.samplelist.viewstate.SampleListViewState;

import dagger.ObjectGraph;


/**
 * A placeholder fragment containing a simple view.
 */
public class SampleListFragment extends BaseLoadingListFragment<Items, ISampleListView, SampleListPresenter> implements ISampleListView {

    private ItemsAdapter adapter;

    public static Fragment newInstance() {
        Fragment fragment = new SampleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.columns)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingDecoration(8));

        adapter = getObjectGraph().get(ItemsAdapter.class);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setData(Items data) {
        super.setData(data);
        adapter.setItems(data);
    }

    @Override
    public ViewState createViewState() {
        return new SampleListViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.loadData();
    }

    @Override
    public SampleListPresenter createPresenter() {
        return getObjectGraph().get(SampleListPresenter.class);
    }
}
