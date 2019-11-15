package com.dicoding.picodiploma.myentertainmentlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.dicoding.picodiploma.myentertainmentlist.db.TVShowHelper;
import com.dicoding.picodiploma.myentertainmentlist.entity.Movie;
import com.dicoding.picodiploma.myentertainmentlist.entity.TVShow;
import com.dicoding.picodiploma.myentertainmentlist.helper.MappingHelper;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.MyTVShowRecyclerViewAdapter;
import com.dicoding.picodiploma.myentertainmentlist.ui.main.TVShowDetailViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dicoding.picodiploma.myentertainmentlist.Main2Activity.ACTION_STATUS;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavTvShowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavTvShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavTvShowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FAV_TVSHOW_LIST = "fav_tvshow_list";


    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private BroadcastReceiver actionReceiver;

//    private OnFragmentInteractionListener mListener;
    private FavTvShowFragment.OnFragmentInteractionListener mListener;
    private TVShowFragment.OnListFragmentInteractionListener tvListener;
    private TVShowDetailViewModel tvShowDetailViewModel;

    private TVShowHelper tvShowHelper;
    private Cursor curTVShows;
    ArrayList<TVShow> tvShowArrayList= new ArrayList<>();
    HashMap<Integer, Integer> hashMap = new HashMap();


    public FavTvShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavTvShowFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static FavTvShowFragment newInstance(String param1, String param2) {
//        FavTvShowFragment fragment = new FavTvShowFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static FavTvShowFragment newInstance() {
        FavTvShowFragment fragment = new FavTvShowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FAV_TVSHOW_LIST, tvShowArrayList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tvShowHelper = TVShowHelper.getInstance(getContext());
        tvShowHelper.open();
        curTVShows = tvShowHelper.queryAll();
        tvShowArrayList.addAll(MappingHelper.mapTVShowCursorToArrayList(curTVShows));
        for (int ii=0; ii<tvShowArrayList.size(); ii++) {
            hashMap.put (tvShowArrayList.get(ii).getTVShow_id(), ii);
        }
        curTVShows.close();

        View view = inflater.inflate(R.layout.fragment_tvshow_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        tvListener = new TVShowFragment.OnListFragmentInteractionListener() {
            @Override
            public void onListFragmentInteraction(TVShow item) {
                Intent moveToDetailIntent = new Intent(getContext(), TVShowDetailActivity.class);
                moveToDetailIntent.putExtra(TVShowDetailActivity.TVSHOW_DETAIL, item);
                startActivity(moveToDetailIntent);
            }
        };

        final MyTVShowRecyclerViewAdapter myTVShowRecyclerViewAdapter
                = new MyTVShowRecyclerViewAdapter(getContext(), tvListener);

        recyclerView.setAdapter(myTVShowRecyclerViewAdapter);
        myTVShowRecyclerViewAdapter.setData(tvShowArrayList);
        myTVShowRecyclerViewAdapter.notifyDataSetChanged();

        tvShowDetailViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(TVShowDetailViewModel.class);

        tvShowDetailViewModel.setTVShowArrayList(tvShowArrayList);
        tvShowDetailViewModel.getTVShowArrayList().observe (this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(ArrayList<TVShow> tvShows) {
                tvShowArrayList.clear();
                tvShowArrayList.addAll(tvShows);
                myTVShowRecyclerViewAdapter.setData(tvShowArrayList);
                myTVShowRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        if (savedInstanceState != null) {
            myTVShowRecyclerViewAdapter.setData(savedInstanceState.<TVShow>getParcelableArrayList(FAV_TVSHOW_LIST));
            myTVShowRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            tvShowDetailViewModel.setTVShowArrayList(tvShowArrayList);
        }

        actionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tvShowDetailViewModel.setTVShowArrayList(tvShowArrayList);
                Log.d("broadcastbroadcast", "receive");
            }
        };
        IntentFilter actionIntentFilter = new IntentFilter(ACTION_STATUS);
        getActivity().registerReceiver(actionReceiver, actionIntentFilter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
