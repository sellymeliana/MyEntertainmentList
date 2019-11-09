package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.myentertainmentlist.R;
import com.dicoding.picodiploma.myentertainmentlist.TVShowFragment.OnListFragmentInteractionListener;
import com.dicoding.picodiploma.myentertainmentlist.dummy.TVShow;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTVShowRecyclerViewAdapter extends RecyclerView.Adapter<MyTVShowRecyclerViewAdapter.ViewHolder> {

    private final List<TVShow> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context context;


    public MyTVShowRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        this.context = context;
        mValues = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tvshow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtName.setText(holder.mItem.getTitle());
        holder.txtDescription.setText(HtmlCompat.fromHtml(holder.mItem.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));

//        holder.imgPhoto.setImageResource(holder.mItem.getPoster());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + holder.mItem.getPoster())
                .into(holder.imgPhoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TVShow mItem;

        public  TextView txtName;
        public  TextView txtDescription;
        public  ImageView imgPhoto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtName = view.findViewById(R.id.txt_title_tv);
            txtDescription = view.findViewById(R.id.txt_description_tv);
            imgPhoto = view.findViewById(R.id.img_poster_tv);
        }
    }

    public void setData (List<TVShow> items) {
        if(items.equals(null))
            return;

        mValues.clear();
        mValues.addAll(items);
    }

}
