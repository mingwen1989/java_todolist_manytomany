# Java To-do List w/ Many-to-Many Relationship

#### By _Ming Wen_

## Description

A basic to-do list written in Java and Spark that saves to a PostgreSQL database and utilizes a many-to-many relationship between tables in the database. Allows the user to create a new task and display a list of tasks, with persistent data.

Features:

      • Allows users to create categories and tasks and list them out.
      • Allows users to assign tasks to categories and vice versa.
      • Allows users to see all tasks assigned to a particular category and vice versa.
      • Allows users to update category names and task descriptions.
      • Allows users to delete categories and tasks. 
      • Lets users mark tasks as done without deleting them, so that they can view all of their completed tasks later. 
      • Includes a feature to let users enter a due date for a task.
      • Sorts tasks by their due date.
      
## Setup and Installation Requirements

  Clone the repo.  
  Run a instance of the gradle web server with 'gradle run'.  
  Navigate to localhost:4567

  Additionally, tests can be run with 'gradle test'.
  
### License

Available for use under the MIT license.
Copyright (c) 2016 **_Ming Wen_**

  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
