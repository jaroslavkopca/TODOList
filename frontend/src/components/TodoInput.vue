<template>
  <div class="todo-input">
    <!-- Input field for adding a new TodoEntry -->
    <input
        type="text"
        v-model="newTodo.title"
        @keyup.enter="handleAddTodo"
        placeholder="Add a new todo."
    />
    <!-- Button to trigger adding the new TodoEntry -->
    <button @click="handleAddTodo">Add</button>
  </div>
</template>

<script>
export default {
  name: 'TodoInput',
  data() {
    return {
      newTodo: {
        title: '', // Title of the new TodoEntry
        description: '', // Optional description for future extensions
        isCompleted: false, // Default completion status for new TodoEntry
      }
    };
  },
  methods: {
    /**
     * Handles adding a new TodoEntry.
     * Sends the newTodo data to the backend and emits the new entry to the parent component.
     */
    async handleAddTodo() {
      // Ensure the title is not empty
      if (this.newTodo.title.trim() !== '') {
        try {
          // Send a POST request to create a new TodoEntry
          const response = await fetch('http://localhost:8080/api/createTODOEntry', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.newTodo),
          });

          // If the request is successful, emit the new TodoEntry to the parent
          if (response.ok) {
            const data = await response.json();
            this.$emit('addTodo', data); // Notify the parent to add this entry
          } else {
            console.error('Failed to add TodoEntry:', response.statusText);
          }
        } catch (error) {
          console.error('Error adding TodoEntry:', error);
        }

        // Clear the input field after successfully adding the TodoEntry
        this.newTodo.title = '';
      }
    }
  }
};
</script>

<style scoped>
/* Styling for the TodoInput component */
.todo-input {
  display: flex;
  justify-content: center;
  gap: 10px;
}

/* Input field styling */
input {
  padding: 10px;
  width: 80%;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

/* Button styling */
button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* Button hover effect */
button:hover {
  background-color: #45a049;
}
</style>
