package fiap.com.br.dailyworkfull.model;

/**
 * Created by gabri on 16/04/2017.
 */

public class Usuario {

    private String user, password;
    private int id;

    public Usuario(String user, String password){
        this.user = user;
        this.password = password;
    }

    public Usuario(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
