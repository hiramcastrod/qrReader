package qr.hiram.qreader.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.descriptionCard.setText(article.getDescription());
        holder.colorCard.setText(article.getColor());
        holder.priceCard.setText("$" + article.getPrice());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
    private TextView descriptionCard, priceCard, colorCard;
    private ImageView imageCard;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        descriptionCard = itemView.findViewById(R.id.tv_description);
        priceCard = itemView.findViewById(R.id.tv_price);
        colorCard = itemView.findViewById(R.id.tv_color);
        imageCard = itemView.findViewById(R.id.iv_icon);

    }
}

}
