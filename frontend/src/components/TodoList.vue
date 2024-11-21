<template>
  <ul class="todo-list">
    <TodoItem
        v-for="todo in todos"
        :key="todo.id"
        :todo="todo"
        @removeTodo="removeTodo"
        @toggleTodo="toggleTodo"
    />
  </ul>
</template>

<script>
import TodoItem from './TodoItem.vue';

export default {
  name: 'TodoList',
  components: {
    TodoItem,
  },
  props: {
    todos: Array, // List of todos passed from parent
    filter: String, // Current filter from parent
  },
  methods: {
    async removeTodo(id) {
      this.$emit('removeTodo', id); // Emit remove event to parent
    },
    async toggleTodo(todo) {
      this.$emit('toggleTodo', todo);
    }
  }
};
</script>

<style scoped>
.todo-list {
  list-style-type: none;
  padding: 0;
}
</style>
