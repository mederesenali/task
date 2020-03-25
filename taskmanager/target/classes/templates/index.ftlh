<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Планировщик задач</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
  <div class="container">
    <h1>Планировщик задач на ${month}</h1>
    <div class="calendar">
      <div class="header flex">
        <div class="header-col">
            <p>Понедельник</p>
        </div>
        <div class="header-col">
            <p>Вторник</p>
        </div>
        <div class="header-col">
            <p>Среда</p>
        </div>
        <div class="header-col">
            <p>Четверг</p>
        </div>
        <div class="header-col">
            <p>Пятница</p>
        </div>
        <div class="header-col">
            <p>Суббота</p>
        </div>
        <div class="header-col">
            <p>Воскресенье</p>
        </div>
      </div>
      <div class="days flex flex-wrap">
        <#list days as day, tasks>
          <a class="day <#if day?is_first>shift${day.dayOfWeek.value!'0'}</#if> <#if day?counter == today>today</#if>" href="/tasks/?date=${day}">
              <p class="day-title">${day.dayOfMonth?c}</p>
              <ul class="flex flex-wrap">
                <#list tasks as task>
                  <li class="task ${task.taskType}"></li>
                </#list>
              </ul>
          </a>
        </#list>
      </div>
    </div>
  </div>
</body>
</html>