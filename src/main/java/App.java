import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("categories", Category.all());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("template", "templates/tasks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String description = request.queryParams("description");
      boolean completed = false;
      Task newTask = new Task(description, completed);
      newTask.save();
      response.redirect("/tasks");
      return null;
    });

    post("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Category newCategory = new Category(name);
      newCategory.save();
      response.redirect("/categories");
      return null;
    });

    get("/tasks/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("allCategories", Category.all());
      model.put("template", "templates/task.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params("id")));
      model.put("category", category);
      model.put("allTasks", Task.all());
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_tasks", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      Category category = Category.find(categoryId);
      Task task = Task.find(taskId);
      category.addTask(task);
      response.redirect("/categories/" + categoryId);
      return null;
    });

    post("/add_categories", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      Category category = Category.find(categoryId);
      Task task = Task.find(taskId);
      task.addCategory(category);
      response.redirect("/tasks/" + taskId);
      return null;
    });

    get("/tasks/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params(":id")));
      model.put("task", task);
      model.put("template", "templates/task-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks-update", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      String description = request.queryParams("update-description");
      Task updateTask = Task.find(taskId);
      updateTask.update(description);
      response.redirect("/tasks");
      return null;
    });

    get("/categories/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params("id")));
      model.put("category", category);
      model.put("template", "templates/category-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories-update", (request, response) -> {
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      String name = request.queryParams("update-name");
      Category updateCategory = Category.find(categoryId);
      updateCategory.update(name);
      response.redirect("/categories");
      return null;
    });

    get("/tasks/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("template", "templates/task-delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/task-delete", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      Task deleteTask = Task.find(taskId);
      deleteTask.delete();
      response.redirect("/tasks");
      return null;
    });

    get("/categories/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params("id")));
      model.put("category", category);
      model.put("template", "templates/category-delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/category-delete", (request, response) -> {
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      Category deleteCategory = Category.find(categoryId);
      deleteCategory.delete();
      response.redirect("/categories");
      return null;
    });

    get("/tasks/:id/complete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params(":id")));
      model.put("task", task);
      model.put("template", "templates/task-complete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks/:id/complete", (request, response) -> {
      Task task = Task.find(Integer.parseInt(request.params("id")));
      task.completeTask();
      response.redirect("/tasks");
      return null;
    });


  }
}
