package ds;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String name = "-";
    private String surname = "-";
    private String title = "-";
    private static int idCounter = 1;
    private boolean active = true;
    private ArrayList<Category> categories = new ArrayList();

    public User(String login, String password, String title) {
        this.login = login;
        this.password = password;
        this.name = "-";
        this.surname = "-";
        this.title = title;
        this.id = idCounter++;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory (Category p) {
        categories.add(p);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getTitle() { return  title; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\'' + '\'' + ", active=" + active + '}';
    }
}
