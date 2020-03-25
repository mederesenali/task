<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Задачи на ${displayDateLongFormat}</title>
  <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
  <div class="container">
    <a href="/">Назад к календарю</a>
    <h1>Задачи на ${displayDateLongFormat}</h1>
    <div>
    <div class="flex flex-center task-row">
          <div>Тип</div>
          <div class="task-name">Название</div>
          <div class="task-description">Описание</div>
          <div class="task-actions">Действия</div>
        </div>
      <#list tasks as task>
        <div class="flex flex-center ${task?item_parity} task-row">
          <div class="task-type ${task.taskType}"></div>
          <div class="task-name">${task.name}</div>
          <div class="task-description">${task.description}</div>
          <div class="task-actions"><a href="/task/delete/${date}/${task.id}">Удалить</a></div>
        </div>
      </#list>
    </div>
    <a href="/task/add?date=${date}">Добавить задачу</a>
  </div>
</body>
</html>