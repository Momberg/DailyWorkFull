package fiap.com.br.dailyworkfull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_DEFAULT = "android";
    private final String SENHA_DEFAULT = "123";

    private final String KEY_APP_PREFERENCES = "APP_PREFERENCE";
    private final String KEY_LOGIN = "login";

    private TextInputLayout tilLogin;
    private TextInputLayout tilSenha;
    private CheckBox cbManterConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilLogin = (TextInputLayout) findViewById(R.id.tilLogin);
        tilSenha = (TextInputLayout) findViewById(R.id.tilSenha);
        cbManterConectado = (CheckBox) findViewById(R.id.cbManterConectado);

        if(isConectado()){
            iniciarApp();
        }

    }

    public void logar(View v){
        if(isLoginValido()){
            if(cbManterConectado.isChecked()){
                manterConectado();
            }
            iniciarApp();
        } else {
            Toast.makeText(this, "Usuário ou senha inválido", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isLoginValido(){
        String login = tilLogin.getEditText().getText().toString();
        String senha = tilSenha.getEditText().getText().toString();
        if(login.equals(LOGIN_DEFAULT) && senha.equals(SENHA_DEFAULT)){
            return true;
        } else {
            return false;
        }
    }

    private void manterConectado(){
        String login = tilLogin.getEditText().getText().toString();
        SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    private boolean isConectado(){
        SharedPreferences shared = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
        String login = shared.getString(KEY_LOGIN, "");
        if(login.equals("")){
            return false;
        } else {
            return true;
        }
    }

    private void iniciarApp(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
