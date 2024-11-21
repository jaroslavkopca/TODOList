<template>
  <ul class="todo-list">
    <!-- Loop through TodoEntries and render TodoItem for each -->
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
    TodoItem, // Import and register the TodoItem component
  },
  props: {
    todos: Array, // Array of TodoEntries passed from the parent
    filter: String, // Current filter type ('all', 'completed', 'notcompleted')
  },
  methods: {
    // Emit event to parent to remove a TodoEntry by ID
    async removeTodo(id) {
      this.$emit('removeTodo', id); // Notify parent of the removal
    },
    // Emit event to parent to toggle a TodoEntry's completion status
    async toggleTodo(todo) {
      this.$emit('toggleTodo', todo); // Notify parent of the status change
    }
  }
};
</script>

<style scoped>
/* Styling for the TodoList container */
.todo-list {
  list-style-type: none; /* Remove default list styling */
  padding: 0; /* Remove default padding */
}
</style>
