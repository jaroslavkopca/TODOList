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
      console.log("FIRST REMOVE");
      this.$emit('removeTodo', this.todo.id);
    },
    async toggleTodo() {
      console.log("FIRST TOGGLE");
      this.$emit('toggleTodo', this.todo);
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
