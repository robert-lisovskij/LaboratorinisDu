package ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {
    private int id;
    private int taskIdCounter = 1;
    private String title;
    private String description;
    private Date created;
    private Date completed;
    private User createdBy;
    private User completedBy;
    private Category category;
    private Task parentTask;
    private ArrayList<Task> subTasks = new ArrayList();


    public Task(String title, Category category, User createdBy, String description) {
        this.title = title;
        this.createdBy = createdBy;
        this.category = category;
        this.id = taskIdCounter;
        this.description = description;
        category.addTaskToCategory(this);
        created = new Date();
        taskIdCounter++;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> all = new ArrayList();
        for (Task t : subTasks) {
            all.addAll(t.getAllTasks());
        }
        return all;
    }

    public void addTask(Task t) {
        subTasks.add(t);
    }

    public void deleteTask(Task t){
        int before = subTasks.size();
        subTasks.remove(t);
        int after = subTasks.size();

        for (Task sub: subTasks)
            sub.deleteTask(t);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public Date getCompleted() {
        return completed;
    }

    public User getCompletedBy() {
        return completedBy;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Task> getSubTasks() {
        return subTasks;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +  "id=" + id + ", title='" + title + '\'' + ", created=" + created + '}';
    }
}
