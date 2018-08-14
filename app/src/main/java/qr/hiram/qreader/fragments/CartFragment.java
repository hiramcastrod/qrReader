package qr.hiram.qreader.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import qr.hiram.qreader.R;
import qr.hiram.qreader.adapters.ArticleAdapter;
import qr.hiram.qreader.models.Article;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    DatabaseReference QRS;
    RecyclerView rvArticles;
    SharedPreferences sharedPreferences;
    ArrayList<Article> articleList;
    private SwipeRefreshLayout swipeRefreshLayout;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        ArticleAdapter articleAdapter = new ArticleAdapter(getCartList(), R.layout.article_view_holder, getActivity());
        rvArticles.setAdapter(articleAdapter);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        QRS = FirebaseDatabase.getInstance().getReference("Qrs");
        rvArticles = view.findViewById(R.id.recyclerview_cart);
        sharedPreferences = getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCartList();
                ArticleAdapter articleAdapter = new ArticleAdapter(getCartList(), R.layout.article_view_holder, getActivity());
                rvArticles.setAdapter(articleAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(linearLayoutManager);

    ArticleAdapter articleAdapter = new ArticleAdapter(getCartList(), R.layout.article_view_holder, getActivity());
    rvArticles.setAdapter(articleAdapter);

        return view;
    }

    public ArrayList<Article> getCartList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartlist", null);
        Type type = new TypeToken<ArrayList<Article>>() {}.getType();
        articleList = gson.fromJson(json, type);

        if(articleList == null){
            articleList = new ArrayList<>();
        }
        return articleList;
    }

}
