package ds;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private int id;
    private static int categoryIdCounter = 1;
    private String title;
    private ArrayList<User> members = new ArrayList();
    private ArrayList<Task> tasks = new ArrayList();

    public Category(String title, User creator) {
        this.title = title;
        members.add(creator);
        this.id = categoryIdCounter;
        categoryIdCounter++;
    }

    public void addMember(User u) {
        members.add(u);
    }

    public void addTaskToCategory(Task t){
        tasks.add(t);
    }

    public void deleteTask(Task t) {
        int before = tasks.size();
        tasks.remove(t);
        int after = tasks.size();

        for (Task sub : tasks) {
            sub.deleteTask(t);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> all = new ArrayList();
        all.addAll(this.tasks);
        for (Task t : tasks) {
            all.addAll(t.getAllTasks());
        }
        return all;
    }

    @Override
    public String toString() {
        return "Category{" +  "id=" + id +  ", title='" + title + '\'' +  '}';
    }
}
