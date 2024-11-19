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
      try {
        const response = await fetch('http://localhost:8080/api/createTODOEntry', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(newTodo),
        });

        if (response.ok) {
          const data = await response.json();
          this.todos.push(data); // Add to local todos array
          this.filterTodos(); // Refetch filtered todos
        } else {
          console.error('Failed to add todo:', response.statusText);
        }
      } catch (error) {
        console.error('Error adding todo:', error);
      }
    },

    // Delete todo and sync with backend
    async removeTodo(id) {
      console.log("THIRD REMOVE");
      try {
        const response = await fetch(`http://localhost:8080/api/deleteTODOEntry/${id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          this.todos = this.todos.filter(todo => todo.id !== id); // Remove from local todos array
          this.filterTodos(); // Refetch filtered todos
        } else {
          console.error('Failed to delete todo:', response.statusText);
        }
      } catch (error) {
        console.error('Error removing todo:', error);
      }
    },

    async toggleTodo(todo){
      console.log("THIRD TOGGLE")
      try {
        const response = await fetch('http://localhost:8080/api/updateTODOEntry', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(todo),
        });

        if (response.ok) {
          this.filterTodos();
        } else {
          console.error('Failed to update todo:', response.statusText);
        }
      } catch (error) {
        console.error('Error updating todo:', error);
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
  // Fetch todos initially when the app is loaded
  created() {
    this.filterTodos();
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
