<template>
  <div class="todo-input">
    <input
        type="text"
        v-model="newTodo.title"
        @keyup.enter="handleAddTodo"
        placeholder="Add a new todo."
    />
    <button @click="handleAddTodo">Add</button>
  </div>
</template>

<script>
export default {
  name: 'TodoInput',
  data() {
    return {
      newTodo: {
        title: '',
        description: '',
        isCompleted: false,
      }
    };
  },
  methods: {
    async handleAddTodo() {
      if (this.newTodo.title.trim() !== '') {
        try {
          const response = await fetch('http://localhost:8080/api/createTODOEntry', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(this.newTodo),
          });

          if (response.ok) {
            const data = await response.json();
            this.$emit('addTodo', data);
          } else {
            console.error('Failed to add todo:', response.statusText);
          }
        } catch (error) {
          console.error('Error adding todo:', error);
        }


        this.newTodo.title = ''; // Clear input after adding
      }
    }
  }
};
</script>

<style scoped>
.todo-input {
  display: flex;
  justify-content: center;
  gap: 10px;
}

input {
  padding: 10px;
  width: 80%;
  font-size: 16px;
}

button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: #4caf50;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
