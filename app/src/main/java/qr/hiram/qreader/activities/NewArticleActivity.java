package qr.hiram.qreader.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import qr.hiram.qreader.R;
import qr.hiram.qreader.models.Article;

public class NewArticleActivity extends AppCompatActivity {
    TextInputEditText inputDescription, inputColor, inputPrice;
    TextView tvId;
    Button btAccept, btCancel;
    private DatabaseReference QRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);
        QRS = FirebaseDatabase.getInstance().getReference("Qrs");
        inputDescription = findViewById(R.id.input_description);
        inputPrice = findViewById(R.id.input_price);
        inputColor = findViewById(R.id.input_color);
        tvId = findViewById(R.id.tv_id);
        btAccept = findViewById(R.id.button_accept_article);
        btCancel = findViewById(R.id.button_cancel_article);
        Bundle extras = getIntent().getExtras();
        tvId.setText(extras.getString("ID"));

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerArticle();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void registerArticle(){
        String description = inputDescription.getText().toString();
        String color = inputColor.getText().toString();
        String image = "";
        float price = Float.valueOf(inputPrice.getText().toString());

        if(!TextUtils.isEmpty(description) || !TextUtils.isEmpty(color) || price!= 0 ){
            String id = tvId.getText().toString();
            Article articles = new Article(id, description, image, color, price);
            QRS.child("Articles").child(id).setValue(articles);
            Toast.makeText(this, "Articulo registrado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        }
    }
}
