<template>
  <div class="todo-app">
    <!-- Header component for app title or navigation -->
    <Header />

    <!-- Input component for adding new TodoEntry -->
    <TodoInput @addTodo="addTodo" />

    <!-- List component for displaying filtered TodoEntries -->
    <TodoList
        :todos="filteredTodos"
        :filter="filter"
        @changeFilter="changeFilter"
        @removeTodo="removeTodo"
        @toggleTodo="toggleTodo"
    />

    <!-- Footer component for filtering and stats -->
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
      todos: [],  // All TodoEntries from backend
      filteredTodos: [],  // TodoEntries filtered by current filter
      filter: 'all', // Current filter ('all', 'completed', or 'notcompleted')
    };
  },
  computed: {
    // Count of completed TodoEntries
    completedCount() {
      return this.todos.filter(entry => entry.completed).length;
    },
    // Count of remaining (not completed) TodoEntries
    remainingCount() {
      return this.todos.filter(entry => !entry.completed).length;
    }
  },
  methods: {
    // Add a new TodoEntry and refresh the filtered list
    async addTodo(newTodo) {
      this.todos.push(newTodo); // Add new TodoEntry to local list
      this.filterTodos(); // Recalculate filtered TodoEntries
    },

    // Remove a TodoEntry by its ID and refresh the filtered list
    async removeTodo(id) {
      this.todos = this.todos.filter((entry) => entry.id !== id); // Remove from local list
      this.filterTodos(); // Recalculate filtered TodoEntries
    },

    // Toggle completion status of a TodoEntry and refresh the filtered list
    async toggleTodo(todo) {
      const index = this.todos.findIndex((entry) => entry.id === todo.id);
      if (index !== -1) {
        this.todos.splice(index, 1, todo); // Replace with updated TodoEntry
        this.filterTodos(); // Recalculate filtered TodoEntries
      }
    },

    // Change the filter and refresh the filtered list
    async changeFilter(newFilter) {
      this.filter = newFilter; // Update filter value
      this.filterTodos(); // Fetch and update filtered TodoEntries
    },

    // Fetch TodoEntries from the backend based on the current filter
    async filterTodos() {
      let url = 'http://localhost:8080/api/getAllTODOEntries'; // Default: fetch all TodoEntries

      // Adjust URL based on the filter
      if (this.filter === 'completed') {
        url = 'http://localhost:8080/api/status/true'; // Fetch completed TodoEntries
      } else if (this.filter === 'notcompleted') {
        url = 'http://localhost:8080/api/status/false'; // Fetch not completed TodoEntries
      }

      try {
        const response = await fetch(url);
        if (response.ok) {
          const data = await response.json();
          this.filteredTodos = data; // Update filtered TodoEntries
        } else {
          console.error('Failed to fetch TodoEntries:', response.statusText);
        }
      } catch (error) {
        console.error('Error fetching TodoEntries:', error);
      }
    },
  },
  async created() {
    // Fetch all TodoEntries when the component is created
    try {
      const response = await fetch('http://localhost:8080/api/getAllTODOEntries');
      if (response.ok) {
        this.todos = await response.json(); // Initialize local TodoEntries list
        this.filterTodos(); // Initialize filtered TodoEntries
      } else {
        console.error('Failed to fetch TodoEntries:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching TodoEntries:', error);
    }
  },
};
</script>

<style scoped>
/* Center and style the TodoApp */
.todo-app {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}
</style>
