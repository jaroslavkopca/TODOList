<template>
  <div class="todo-app">
    <Header />
    <TodoInput @addTodo="addTodo" />
    <TodoList
        :todos="filteredTodos"
        :filter="filter"
        @changeFilter="changeFilter"
        @removeTodo="removeTodo"
        @toggleTodo="toggleTodo"
    />
    <TodoFooter
        :todos="todos"
        :filter="filter"
        :remaining="remainingCount"
        :completed="completedCount"
        @changeFilter="changeFilter"
    />

  </div>
</template>

<script>
import Header from './components/Header.vue';
import TodoInput from './components/TodoInput.vue';
import TodoList from './components/TodoList.vue';
import TodoFooter from './components/TodoFooter.vue';

export default {
  components: {
    Header,
    TodoInput,
    TodoList,
    TodoFooter
  },
  data() {
    return {
      todos: [],  // Local state for all todos
      filteredTodos: [],  // Local state for filtered todos
      filter: 'all', // Default filter value
    };
  },
  computed: {
    // Count of completed todos
    completedCount() {
      return this.todos.filter(todo => todo.completed).length; // Note: Use 'completed' from backend
    },
    // Count of remaining todos
    remainingCount() {
      return this.todos.filter(todo => !todo.completed).length;
    }
  },
  methods: {
    // Add todo and sync with backend
    async addTodo(newTodo) {
          this.todos.push(newTodo);
          this.filterTodos();
    },

    // Delete todo and sync with backend
    async removeTodo(id) {
      this.todos = this.todos.filter((todo) => todo.id !== id);
      this.filterTodos();
    },

    async toggleTodo(todo){
      const index = this.todos.findIndex((t) => t.id === todo.id);
      if (index !== -1) {
        this.todos.splice(index, 1, todo); // Update the specific todo
        this.filterTodos(); // Recalculate filtered todos
      }
    },

    // Change filter and refetch filtered todos
    async changeFilter(newFilter) {
      this.filter = newFilter;
      this.filterTodos(); // Refetch based on the new filter
    },

    // Fetch filtered todos from backend
    async filterTodos() {
      let url = 'http://localhost:8080/api/getAllTODOEntries';

      if (this.filter === 'completed') {
        url = 'http://localhost:8080/api/status/true';
      } else if (this.filter === 'notcompleted') {
        url = 'http://localhost:8080/api/status/false';
      }

      try {
        const response = await fetch(url);
        if (response.ok) {
          const data = await response.json();
          this.filteredTodos = data; // Set filteredTodos based on the response
        } else {
          console.error('Failed to fetch todos:', response.statusText);
        }
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    },
  },
  async created() {
    try {
      const response = await fetch('http://localhost:8080/api/getAllTODOEntries');
      if (response.ok) {
        this.todos = await response.json(); // Initialize the todos array
        this.filterTodos(); // Initialize filtered todos
      } else {
        console.error('Failed to fetch todos:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  },
};
</script>

<style scoped>
.todo-app {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}
</style>
