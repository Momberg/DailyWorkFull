package fiap.com.br.dailyworkfull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fiap.com.br.dailyworkfull.adapter.ListaAndroidAdaper;
import fiap.com.br.dailyworkfull.dao.TarefaDAO;
import fiap.com.br.dailyworkfull.model.Tarefa;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvTarefas;
    private ListaAndroidAdaper adapter;
    private TextView id;
    private int temp_id;
    private TarefaDAO tarefaDAO;
    private SharedPreferences id_tarefa;
    private FloatingActionButton fabDel;
    private FloatingActionButton fabEdit;
    private final String KEY_APP_PREFERENCES = "APP_PREFERENCE";
    private final String KEY_LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tarefaDAO = new TarefaDAO(this.getApplicationContext());
        id_tarefa = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        temp_id = -1;
        rvTarefas = (RecyclerView) findViewById(R.id.rvTarefas);
        fabDel = (FloatingActionButton) findViewById(R.id.fabDel);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);

        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp_id != -1){
                    tarefaDAO.deleteByID(temp_id);
                    carregaTarefas();
                    fabDel.setVisibility(View.INVISIBLE);
                    fabEdit.setVisibility(View.INVISIBLE);
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp_id != -1){
                    id_tarefa.edit().putInt("ID", temp_id).apply();
                    startActivityForResult(new Intent(MainActivity.this,
                                    NovaTarefa.class),
                            NovaTarefa.CODE_EDITA_TAREFA);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        carregaTarefas();
        ItemClickSupport.addTo(rvTarefas).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                id = (TextView) v.findViewById(R.id.id);
                int i = countItemSelected();
                if(!isItemSelected() && i == 0){
                    v.setSelected(true);
                    temp_id = Integer.parseInt(id.getText().toString());
                    fabDel.setVisibility(View.VISIBLE);
                    fabEdit.setVisibility(View.VISIBLE);
                } else if(v.isSelected()){
                    v.setSelected(false);
                    temp_id = -1;
                    fabDel.setVisibility(View.INVISIBLE);
                    fabEdit.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout){
            logoff();
        }

        if (id == R.id.cadastrar){
            id_tarefa.edit().putInt("ID", 0).apply();
            startActivityForResult(new Intent(MainActivity.this,
                            NovaTarefa.class),
                    NovaTarefa.CODE_NOVA_TAREFA);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Cancelado",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == NovaTarefa.CODE_NOVA_TAREFA) {
            fabDel.setVisibility(View.INVISIBLE);
            fabEdit.setVisibility(View.INVISIBLE);
            carregaTarefas();
        } else if (requestCode == NovaTarefa.CODE_EDITA_TAREFA) {
            fabDel.setVisibility(View.INVISIBLE);
            fabEdit.setVisibility(View.INVISIBLE);
            carregaTarefas();
        }
    }

    private void carregaTarefas() {
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        List<Tarefa> tarefas = tarefaDAO.getAll();
        setUpTarefa(tarefas);
    }

    private void setUpTarefa(List<Tarefa> lista) {
        adapter = new ListaAndroidAdaper(this, lista);
        rvTarefas.setLayoutManager(new LinearLayoutManager(this));
        rvTarefas.setAdapter(adapter);
    }

    private boolean isItemSelected(){
        for (int i = 0; i < rvTarefas.getAdapter().getItemCount(); i++) {
            if(rvTarefas.getChildAt(i).isSelected()){
                return true;
            }
        }
        return false;
    }

    private int countItemSelected(){
        int s = 0;
        for (int i = 0; i < rvTarefas.getAdapter().getItemCount(); i++) {
            if(rvTarefas.getChildAt(i).isSelected()){
                s =+ 1;
            }
        }
        return s;
    }

    private void logoff(){
        if(isConectado()){
            SharedPreferences pref = getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(KEY_LOGIN, "");
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
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

}
