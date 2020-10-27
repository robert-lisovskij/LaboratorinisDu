package ds;

import java.io.Serializable;

public class Company extends User implements Serializable {
    private String title;
    private String name;
    private String surname;

    public Company(String login, String password, String title, String name, String surname) {
        super(login, password, title);
        this.title = title;
        this.name = name;
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Company{" + "title='" + title + '}' + getId();
    }
}
