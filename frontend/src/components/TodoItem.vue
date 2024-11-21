<template>
  <li class="todo-item">
    <input type="checkbox" v-model="todo.completed" @change="toggleTodo" />
    <span :class="{ completed: todo.isCompleted }">{{ todo.title }}</span>
    <button @click="removeTodo">Delete</button>
  </li>
</template>

<script>
export default {
  name: 'TodoItem',
  props: {
    todo: Object
  },
  methods: {
    async removeTodo() {
      try {
        const response = await fetch(`http://localhost:8080/api/deleteTODOEntry/${this.todo.id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          this.$emit('removeTodo', this.todo.id);
        } else {
          console.error('Failed to delete todo:', response.statusText);
        }
      } catch (error) {
        console.error('Error removing todo:', error);
      }
    },
    async toggleTodo() {
      try {
        const response = await fetch('http://localhost:8080/api/updateTODOEntry', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.todo),
        });
        if (response.ok) {
          const updatedTodo = await response.json();
          this.$emit('toggleTodo', updatedTodo);
        } else {
          console.error('Failed to update todo:', response.statusText);
        }
      } catch (error) {
        console.error('Error updating todo:', error);
      }
    }
  }
};
</script>

<style scoped>
.todo-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}
.completed {
  text-decoration: line-through;
  color: grey;
}
button {
  background-color: red;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: darkred;
}
</style>
