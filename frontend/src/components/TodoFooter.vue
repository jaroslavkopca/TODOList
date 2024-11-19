<template>
  <div class="todo-footer">
    <div class="counter">
      <span>{{ remaining }} left</span>
      <span> | </span>
      <span>{{ completed }} completed</span>
    </div>
    <div class="filters">
      <button @click="changeFilter('all')">All</button>
      <button @click="changeFilter('completed')">Completed</button>
      <button @click="changeFilter('notcompleted')">Not Completed</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TodoFooter',
  props: {
    remaining: Number, // Count of remaining tasks
    completed: Number, // Count of completed tasks
    filter: String,    // Current filter
  },
  methods: {
    async changeFilter(newFilter) {
      this.$emit('changeFilter', newFilter);
    }
  }
};

</script>

<style scoped>
.todo-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.filters button {
  margin: 0 5px;
  background: none;
  border: none;
  cursor: pointer;
}

.filters button:hover {
  color: blue;
}
</style>
