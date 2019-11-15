package com.dicoding.picodiploma.myentertainmentlist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MovieViewModel;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MyMovieRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieFragment extends Fragment {

    private ArrayList<Movie> movies2=new ArrayList<>();

    RecyclerView recyclerView;
    MyMovieRecyclerViewAdapter myMovieRecyclerViewAdapter;

    private ProgressBar progressBar;

    private static final String MOVIE_LIST = "movie_list";

    private MovieViewModel movieViewModel;


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_LIST, movies2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);

//        ((MainActivity)getActivity()).setOnActivityListener(new MainActivity.OnActivityListener() {
//            @Override
//            public void onActivityRefreshListener() {
//                Log.d("refreshMovies", "onActivityRefreshListener");
//                movieViewModel.setMovies();
//                showLoading(true);
//            }
//        });

        progressBar = view.findViewById(R.id.progressBar);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        myMovieRecyclerViewAdapter = new MyMovieRecyclerViewAdapter(getContext(), mListener);
        recyclerView.setAdapter(myMovieRecyclerViewAdapter);

        if (savedInstanceState != null) {
            myMovieRecyclerViewAdapter.setData(savedInstanceState.<Movie>getParcelableArrayList(MOVIE_LIST));
            myMovieRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            Log.d("refreshMovies", "TRUE");
            movieViewModel.setMovies();
            showLoading(true);
        }

        movieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    Log.d("refreshMovies", "onChanged");
                    movies2.clear();
                    movies2.addAll(movies);
                    myMovieRecyclerViewAdapter.setData(movies2);
                    myMovieRecyclerViewAdapter.notifyDataSetChanged();
                    showLoading(false);
                }
            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d("refreshMovies", "onAttach");

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
        void onListFragmentInteraction(Movie item);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

