package qr.hiram.qreader.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        QRS = FirebaseDatabase.getInstance().getReference("Qrs");
        rvArticles = view.findViewById(R.id.recyclerview_cart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(linearLayoutManager);

        QRS.child("Articles").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataSnapshot.getValue();
                System.out.println(dataSnapshot.getClass());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        QRS.child("Articles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Article> articles = new ArrayList<>();
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    Article article = post.getValue(Article.class);
                    articles.add(new Article(article.getId(), article.getDescription(), article.getImage(), article.getColor(), article.getPrice()));
                }

                ArticleAdapter articleAdapter = new ArticleAdapter(articles, R.layout.article_view_holder, getActivity());
                rvArticles.setAdapter(articleAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
