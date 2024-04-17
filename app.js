// app.js
async function getTasks() {
    const response = await fetch('/tasks');
    const data = await response.json();
    const taskList = document.getElementById('task-list');
    taskList.innerHTML = '';
    data.forEach(task => {
        const li = document.createElement('li');
        li.textContent = task;
        taskList.appendChild(li);
    });
}

async function addTask() {
    const newTaskInput = document.getElementById('new-task');
    const newTask = newTaskInput.value.trim();
    if (newTask !== '') {
        await fetch('/tasks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTask)
        });
        newTaskInput.value = '';
        getTasks();
    }
}

document.addEventListener('DOMContentLoaded', () => {
    getTasks();
});
