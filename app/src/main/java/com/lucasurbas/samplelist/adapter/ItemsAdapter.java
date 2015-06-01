package com.lucasurbas.samplelist.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucasurbas.samplelist.R;
import com.lucasurbas.samplelist.model.Item;
import com.lucasurbas.samplelist.model.Items;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by M30 on 2015-05-31.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Items items;
    private Picasso picasso;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvDesc;
        public ImageView ivImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemLayoutView.findViewById(R.id.tv_desc);
            ivImage = (ImageView) itemLayoutView.findViewById(R.id.iv_image);
        }
    }

    @Inject
    public ItemsAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    public void setItems(Items items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Item i = items.getList().get(position);

        viewHolder.tvTitle.setText(i.getTitle());
        viewHolder.tvDesc.setText(Html.fromHtml(i.getDescription()));
        if (i.getImageUrl() != null && !i.getImageUrl().isEmpty()) {
            picasso.load(i.getImageUrl()).placeholder(R.color.light_grey).error(R.drawable.thumbnail_default).fit().into(viewHolder.ivImage);
        } else {
            viewHolder.ivImage.setImageResource(R.drawable.thumbnail_default);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.getList().size();
    }
}

