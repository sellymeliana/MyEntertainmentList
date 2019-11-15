package com.dicoding.picodiploma.myentertainmentlist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

import com.dicoding.picodiploma.myentertainmentlist.db.MovieHelper;
import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.helper.MappingHelper;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MovieDetailViewModel;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MyMovieRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavMovieFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavMovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MovieFragment.OnListFragmentInteractionListener movieListener;
    private MovieDetailViewModel movieDetailViewModel;

    private MovieHelper movieHelper;
    private Cursor curMovies;
    ArrayList<Movie> movieArrayList= new ArrayList<>();
    HashMap <Integer, Integer> hashMap = new HashMap();



    public FavMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavMovieFragment.
     */
    public static FavMovieFragment newInstance() {
        FavMovieFragment fragment = new FavMovieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("tag","oncreate");
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        curMovies = movieHelper.queryAll();
        movieArrayList.addAll(MappingHelper.mapMovieCursorToArrayList(curMovies));
        for (int ii=0; ii<movieArrayList.size(); ii++) {
            hashMap.put (movieArrayList.get(ii).getMovie_id(), ii);
        }
        curMovies.close();

        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        movieListener = new MovieFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(Movie item) {

            }
        };

        final MyMovieRecyclerViewAdapter myMovieRecyclerViewAdapter
                = new MyMovieRecyclerViewAdapter(getContext(), movieListener);

        recyclerView.setAdapter(myMovieRecyclerViewAdapter);
        myMovieRecyclerViewAdapter.setData(movieArrayList);
        myMovieRecyclerViewAdapter.notifyDataSetChanged();

        movieDetailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MovieDetailViewModel.class);

        movieDetailViewModel.setMovieArrayList(movieArrayList);
        movieDetailViewModel.getMovieArrayList().observe (this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                movieArrayList.clear();
                movieArrayList.addAll(movies);
                myMovieRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
    }

//    public void setData (List<Movie> items) {
//        if(items.equals(null))
//            return;
//        movieArrayList.clear();
//        movieArrayList.addAll(items);
//    }
}
