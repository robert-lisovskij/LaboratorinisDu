package ds;

import java.io.Serializable;
import java.util.ArrayList;

public class ToDoList implements Serializable {
    private User loggedIn = null;
    private ArrayList<User> users = new ArrayList();
    private ArrayList<Category> categories = new ArrayList();
    private ArrayList<Task> tasks = new ArrayList();

    public Person registerPerson(String login, String password, String name, String surname, String title) {
        if (getUserByLogin(login) == null) {
            Person newPerson = new Person(login, password, name, surname, "-");
            users.add(newPerson);
            return newPerson;
        }
        return null;
    }

    public Company registerCompany(String login, String password, String title, String name, String surname) {
        if (getUserByLogin(login) == null) {
            Company newCompany = new Company(login, password, title, "-", "-");
            users.add(newCompany);
            return newCompany;
        }
        return null;
    }

    public User login(String login, String password) throws Exception {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password) && u.isActive()) {
                loggedIn = u;
                return u;
            }
        }
        throw new ObjectNotExists("Incorrect login data");
    }

    public void logout(int id) {
        loggedIn = null;
    }

    public void editPersonInfo(int id, String name, String surname) {
        User current = getUserById(id);
        if (current != null && current.getClass().equals(Person.class)) {
            Person p = (Person) current;
            p.setName(name);
            p.setSurname(surname);
        }
    }

    public void editCompanyInfo(int id, String title) {
        User current = getUserById(id);
        if (current != null && current.getClass().equals(Company.class)) {
            Company c = (Company) current;
            c.setTitle(title);
        }
    }

    public ArrayList<User> getAllUsers() {
        if (loggedIn != null && loggedIn.isActive()) {
            return users;
        }
        return new ArrayList();
    }

    public ArrayList<User> getAllActiveUsers() {
        if (loggedIn != null && loggedIn.isActive()) {
            ArrayList<User> filtered = new ArrayList();
            for (User u : users) {
                if (u.isActive()) {
                    filtered.add(u);
                }
            }
            return filtered;
        }
        return new ArrayList();
    }

    public boolean disableUser(int id) {
        if (loggedIn != null && loggedIn.isActive()) {
            User forDeletionUser = getUserById(id);
            if (forDeletionUser != null && forDeletionUser.isActive()) {
                forDeletionUser.setActive(false);
                return true;
            }
        }
        return false;
    }

    public User getUserById(int id) {
        if (loggedIn != null && loggedIn.isActive()) {
            for (User u : users) {
                if (u.getId() == id) {
                    return u;
                }
            }
        }
        return null;
    }

    public User getUserByLogin(String login) {
        if (loggedIn != null && loggedIn.isActive()) {
            for (User u : users) {
                if (u.getLogin().equals(login)) {
                    return u;
                }
            }
        }
        return null;
    }

    public Task addTask(String title, String description, int categoryId) {
        if (loggedIn != null) {
            Category parent = getCategoryById(categoryId);
            if (parent != null && title.length() > 0) {
                Task newTask = new Task(title, parent, loggedIn, description);
                tasks.add(newTask);
                return newTask;
            }
        }
        return null;
    }

    public Task addSubTask(String title, String description, int taskId) {
        if (loggedIn != null) {

            Task parent = getTaskById(taskId);
            if (parent != null && title.length() > 0) {
                Task newTask = new Task(title, parent.getCategory(), loggedIn, description);
                parent.addTask(newTask);
                return newTask;
            }
        }
        return null;
    }

    public boolean deleteTask(int id) {
        Task forDeletion = getTaskById(id);
        if (forDeletion != null) {
            tasks.remove(forDeletion);
            forDeletion.getCategory().deleteTask(forDeletion);
        }
        return false;
    }

    public void editTaskInfo(int id, String title) {
        Task current = getTaskById(id);
        if (current != null && current.getClass().equals(Task.class)) {
            Task q = (Task) current;
            q.setTitle(title);
        }
    }

    public Task getTaskById(int id) {
        for (Category p : loggedIn.getCategories()) {
            ArrayList<Task> allTasks = p.getAllTasks();
            for (Task t : allTasks) {
                if (t.getId() == id) {
                    return t;
                }
            }
        }
        return null;
    }

    public Category addCategory(String title) {
        if (loggedIn != null && title.length() > 3) {
            Category newCategory = new Category(title, loggedIn);
            categories.add(newCategory);
            loggedIn.addCategory(newCategory);
            return newCategory;
        }
        return null;
    }

    public void editCategoryInfo(int id, String title) {
        Category current = getCategoryById(id);
        if (current != null && current.getClass().equals(Category.class)) {
            Category q = (Category) current;
            q.setTitle(title);
        }
    }

    public void assignMemberToCategory(int categoryId, int userId) {
        if (loggedIn != null) {
            Category current = getCategoryById(categoryId);
            User toAdd = getUserById(userId);
            if (current != null && toAdd != null) {
                current.addMember(toAdd);
                toAdd.addCategory(current);
            }
        }
    }

    public void deleteCategory(int categoryId) {
        Category current = getCategoryById(categoryId);
        if (current != null) {
            categories.remove(current);
            for (User u : current.getMembers()) {
                u.getCategories().remove(current);
            }
        }
    }

    public ArrayList<Category> getAllUserCategories() {
        if (loggedIn != null) {
            return loggedIn.getCategories();
        }
        return new ArrayList();
    }

    public Category getCategoryById(int id) {
        if (loggedIn != null) {
            for (Category p : loggedIn.getCategories()) {
                if (p.getId() == id) {
                    return p;
                }
            }
        }
        return null;
    }

    public int[] getUserCount() {
        int[] num = new int[2];
        for(User u:users) {
            if(u.getClass().equals(Person.class)) {
                num[0]++;
            } else {
                num[1]++;
            }
        }
        return  num;
    }

    public int[][] getCategoryNumber() {
        int[][] arr = new int[categories.size()][2];
        int id = 0;
        for(Category p: categories) {
            arr[id][0] = p.getId();
            arr[id][1] = p.getAllTasks().size();
            id++;
        }
        return arr;
    }


}
