package com.dicoding.picodiploma.myentertainmentlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.myentertainmentlist.dummy.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MyTVShowRecyclerViewAdapter;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.TVShowViewModel;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TVShowFragment extends Fragment {

    private ArrayList<TVShow> tvShows = new ArrayList<>();

    RecyclerView recyclerView;
    MyTVShowRecyclerViewAdapter myTVShowRecyclerViewAdapter;
    private ProgressBar progressBar;

    private static final String MOVIE_LIST = "movie_list";

    private TVShowViewModel tvShowViewModel;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TVShowFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TVShowFragment newInstance() {
        TVShowFragment fragment = new TVShowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

//        prepare();
//        addItem();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_LIST, tvShows);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow_list, container, false);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TVShowViewModel.class);

        progressBar = view.findViewById(R.id.progressBar);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        myTVShowRecyclerViewAdapter = new MyTVShowRecyclerViewAdapter(getContext(), mListener);
        recyclerView.setAdapter(myTVShowRecyclerViewAdapter);

        ((MainActivity)getActivity()).setOnTVActivityListener(new MainActivity.OnTVActivityListener() {
            @Override
            public void onTVActivityRefreshListener() {
                Log.d("refreshMovies", "onTVActivityRefreshListener");
                tvShowViewModel.setTVShow();
                showLoading(true);
            }
        });

        if (savedInstanceState != null) {
            myTVShowRecyclerViewAdapter.setData(savedInstanceState.<TVShow>getParcelableArrayList(MOVIE_LIST));
            myTVShowRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            tvShowViewModel.setTVShow();
            showLoading(true);
        }

        tvShowViewModel.getTVShow().observe(this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> tvShowsParam) {
                if (tvShowsParam != null) {
                    tvShows.clear();
                    tvShows.addAll(tvShowsParam);
                    myTVShowRecyclerViewAdapter.setData(tvShows);
                    myTVShowRecyclerViewAdapter.notifyDataSetChanged();
                    showLoading(false);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TVShow item);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

