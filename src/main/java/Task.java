import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Task {
  private int id;
  private String description;
  private static boolean completed;


  public Task(String description, boolean completed) {
    this.description = description;
    this.completed = completed;
  }

  public String getDescription() {
    return description;
  }


  public boolean getStatus() {
    return completed;
  }

  public int getId() {
    return id;
  }

  public static Task findComplete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where completed=true";
      Task task = con.createQuery(sql)
        .addParameter("completed", completed)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void completeTask(){
  //  completed = true;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET completed = true WHERE id = :id";
      con.createQuery(sql)
      //  .addParameter("completed", completed)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description, completed) VALUES (:description, :completed)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("completed", this.completed)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String newDescription) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", newDescription)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM tasks WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM categories_tasks WHERE task_id = :taskId";
        con.createQuery(joinDeleteQuery)
          .addParameter("taskId", this.getId())
          .executeUpdate();
    }
  }

  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories_tasks (category_id, task_id) VALUES (:category_id, :task_id)";
      con.createQuery(sql)
        .addParameter("category_id", category.getId())
        .addParameter("task_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Category> getCategories() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT category_id FROM categories_tasks WHERE task_id = :task_id";
      List<Integer> categoryIds = con.createQuery(joinQuery)
        .addParameter("task_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Category> categories = new ArrayList<Category>();

      for (Integer categoryId : categoryIds) {
        String taskQuery = "Select * From categories WHERE id = :categoryId";
        Category category = con.createQuery(taskQuery)
          .addParameter("categoryId", categoryId)
          .executeAndFetchFirst(Category.class);
        categories.add(category);
      }
      return categories;
    }
  }
}
