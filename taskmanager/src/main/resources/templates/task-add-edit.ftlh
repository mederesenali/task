<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Добавить новую задачу</title>
  <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
  <div class="container">
    <h1>${formHeader!'не указано в модели'}</h1>
    <form action="${formAction!'not-set'}" method="post">
      <input type="hidden" name="date" value="${date}">
      <input type="hidden" name="taskId" value="${(task.id)!''}">
      <input placeholder="название" name="name" value="${(task.name)!''}">
      <input placeholder="описание" name="description" value="${(task.description)!''}">
      <select name="taskType">
        <#list taskTypes as tt>
          <option class="${tt}" value="${tt}" <#if task.taskType??>${(task.taskType == tt)?string('selected', '')}</#if>> ${tt}</option>
        </#list>
      </select>
      <button type="submit">${submitText!'не указано в модели'}</button>
    </form>
  </div>
</body>
</html>