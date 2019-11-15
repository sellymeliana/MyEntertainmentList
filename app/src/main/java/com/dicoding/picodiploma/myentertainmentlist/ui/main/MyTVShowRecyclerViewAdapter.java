package com.dicoding.picodiploma.myentertainmentlist.ui.main;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.myentertainmentlist.TVShowFragment.OnListFragmentInteractionListener;
import com.dicoding.picodiploma.myentertainmentlist.R;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTVShowRecyclerViewAdapter extends RecyclerView.Adapter<MyTVShowRecyclerViewAdapter.ViewHolder> {

    private List<TVShow> tvValues;
    public final OnListFragmentInteractionListener mListener;
    private final Context context;

    public MyTVShowRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        this.context = context;
        tvValues = new ArrayList<>();
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

        holder.tvItem = tvValues.get(position);

        CircularProgressDrawable circularProgressDrawable=new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(15f);
        circularProgressDrawable.start();

        holder.txtName.setText(holder.tvItem.getTitle());
        holder.txtDescription.setText(holder.tvItem.getDescription());// (HtmlCompat.fromHtml(holder.tvItem.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + holder.tvItem.getPoster())
                .placeholder(circularProgressDrawable)
                .into(holder.imgPhoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.tvItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TVShow tvItem;

        public  final TextView txtName;
        public  final TextView txtDescription;
        public  final ImageView imgPhoto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtName = view.findViewById(R.id.txt_title);
            txtDescription = view.findViewById(R.id.txt_description);
            imgPhoto = view.findViewById(R.id.img_poster);
        }
    }

    public void setData (List<TVShow> items) {
        if(items.equals(null))
            return;

        tvValues = items;
    }
}
