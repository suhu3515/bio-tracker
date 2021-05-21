package com.example.biotracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CommunityFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View root;
    FloatingActionButton buttonCreatePosts;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Posts> postsLists;
    SwipeRefreshLayout pullToRefresh;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(String param1, String param2)
    {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void openFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_community, container, false);
        buttonCreatePosts = root.findViewById(R.id.fab_add_posts);
        recyclerView = root.findViewById(R.id.recycler_posts);
        progressBar = root.findViewById(R.id.progress_community);
        pullToRefresh = root.findViewById(R.id.pulltorefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                postsLists.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                HomeActivity.getPosts();
                postsLists = HomeActivity.postsList;
                PostsAdapterActivity adapterActivity = new PostsAdapterActivity(getContext(),postsLists);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.setAdapter(adapterActivity);
                pullToRefresh.setRefreshing(false);
                openFragment(CommunityFragment.newInstance("",""));
            }
        });


        buttonCreatePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newPostIntent = new Intent(getContext(),NewPostActivity.class);
                startActivity(newPostIntent);

            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postsLists = new ArrayList<>();

        postsLists = HomeActivity.postsList;

        PostsAdapterActivity adapterActivity = new PostsAdapterActivity(getContext(),postsLists);
        recyclerView.setAdapter(adapterActivity);
        progressBar.setVisibility(View.GONE);

        return root;

    }
}
