package qr.hiram.qreader.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import qr.hiram.qreader.R;
import qr.hiram.qreader.models.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{
    private ArrayList<Article> articles;
    private int resource;
    private Activity activity;

    public ArticleAdapter(ArrayList<Article> articles, int resource, Activity activity) {
        this.articles = articles;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, final int position) {
        final Article article = articles.get(position);

        holder.descriptionCard.setText(article.getDescription());
        holder.colorCard.setText(article.getColor());
        holder.priceCard.setText("$" + article.getPrice());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, String.valueOf(position), Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                articles.remove(position);
                String json = gson.toJson(articles);
                editor.putString("cartlist", json);
                editor.apply();
            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
    private TextView descriptionCard, priceCard, colorCard;
    private ImageView imageCard;
    private CardView cardView;
    public ArticleViewHolder(View itemView) {
        super(itemView);
        descriptionCard = itemView.findViewById(R.id.tv_description);
        priceCard = itemView.findViewById(R.id.tv_price);
        colorCard = itemView.findViewById(R.id.tv_color);
        imageCard = itemView.findViewById(R.id.iv_icon);
        cardView = itemView.findViewById(R.id.cardview_articles);

    }
}

}
