<template>
  <li class="todo-item">
    <!-- Checkbox to toggle completion status -->
    <input type="checkbox" v-model="todo.completed" @change="toggleTodo" />

    <!-- Display TodoEntry title or an input field for editing -->
    <span
        v-if="!isEditing"
        :class="{ completed: todo.completed }"
        @dblclick="enableEditing"
    >
      {{ todo.title }}
      <!--    against XSS      -->
    </span>
    <input
        v-else
        type="text"
        v-model="editedTitle"
        @blur="updateTitle"
        @keyup.enter="updateTitle"
        @keyup.esc="cancelEditing"
        class="edit-input"
    />

    <!-- Button to remove the TodoEntry -->
    <button @click="removeTodo">Delete</button>
  </li>
</template>

<script>
export default {
  name: 'TodoItem',
  props: {
    todo: Object // Single TodoEntry passed as a prop
  },
  data() {
    return {
      isEditing: false, // Tracks whether the TodoEntry is in editing mode
      editedTitle: this.todo.title // Temporary title for editing
    };
  },
  methods: {
    // Remove the TodoEntry by sending a DELETE request to the backend
    async removeTodo() {
      try {
        const response = await fetch(`http://localhost:8080/api/deleteTODOEntry/${this.todo.id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          this.$emit('removeTodo', this.todo.id); // Notify parent to update state
        } else {
          console.error('Failed to delete TodoEntry:', response.statusText);
        }
      } catch (error) {
        console.error('Error removing TodoEntry:', error);
      }
    },
    // Toggle the completion status of the TodoEntry and sync with backend
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
          this.$emit('toggleTodo', updatedTodo); // Notify parent with updated TodoEntry
        } else {
          console.error('Failed to update TodoEntry:', response.statusText);
        }
      } catch (error) {
        console.error('Error updating TodoEntry:', error);
      }
    },
    // Enable editing mode for the TodoEntry
    enableEditing() {
      this.isEditing = true;
    },
    // Update the TodoEntry's title on the server
    async updateTitle() {
      if (this.editedTitle.trim() === '') {
        // Prevent updating to an empty title
        this.editedTitle = this.todo.title;
        this.isEditing = false;
        return;
      }
      try {
        const updatedTodo = { ...this.todo, title: this.editedTitle };
        const response = await fetch('http://localhost:8080/api/updateTODOEntry', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(updatedTodo),
        });
        if (response.ok) {
          const result = await response.json();
          this.$emit('toggleTodo', result); // Notify parent with updated TodoEntry
        } else {
          console.error('Failed to update TodoEntry title:', response.statusText);
        }
      } catch (error) {
        console.error('Error updating TodoEntry title:', error);
      } finally {
        this.isEditing = false; // Exit editing mode
      }
    },
    // Cancel editing mode and restore the original title
    cancelEditing() {
      this.editedTitle = this.todo.title;
      this.isEditing = false;
    }
  }
};
</script>

<style scoped>
/* Styling for each TodoEntry item */
.todo-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

/* Styling for completed TodoEntry items */
.completed {
  text-decoration: line-through;
  color: grey;
}

/* Styling for the delete button */
button {
  background-color: red;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: darkred;
}

/* Styling for the editing input field */
.edit-input {
  flex: 1;
  margin-left: 5px;
  font-size: 16px;
}
</style>
