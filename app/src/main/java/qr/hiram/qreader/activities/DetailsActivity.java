package qr.hiram.qreader.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import qr.hiram.qreader.R;
import qr.hiram.qreader.models.Article;

public class DetailsActivity extends AppCompatActivity {
    TextView tvDescription;
    Button btCancel, btAccept;
    ArrayList<Article> articlesList;
    SharedPreferences sharedPreferences;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvDescription = findViewById(R.id.tv_description_details);
        btAccept = findViewById(R.id.button_accept_detail);
        btCancel = findViewById(R.id.button_cancel_detail);
        extras = getIntent().getExtras();
        sharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        getCartList();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        tvDescription.setText(extras.getString("DSCR"));

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    public void addToCart(){
        if(articlesList == null){
            articlesList = new ArrayList<>();
        }
        articlesList.add(new Article(extras.getString("ID"), extras.getString("DSCR"),
                "", extras.getString("COLOR"), extras.getFloat("PRICE")));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(articlesList);
        editor.putString("cartlist", json);
        editor.apply();
        for (int i = 0; i<articlesList.size(); i++){
            System.out.println(articlesList.get(i).getId());
        }
        finish();

    }

    public ArrayList<Article> getCartList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartlist", null);
        Type type = new TypeToken<ArrayList<Article>>() {}.getType();
        articlesList = gson.fromJson(json, type);
        if(articlesList == null){
            articlesList = new ArrayList<>();
        }
        return articlesList;
    }

}
