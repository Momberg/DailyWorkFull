package fiap.com.br.dailyworkfull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {

    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imagem = (ImageView) findViewById(R.id.imagem);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
