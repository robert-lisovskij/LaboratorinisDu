package ds;

import java.io.Serializable;

public class Person extends User implements Serializable {
    private String name;
    private String surname;

    public Person(String login, String password, String name, String surname, String title) {
        super(login, password, title);
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", surname='" + surname + '\'' +  '}' + getId();
    }
}
