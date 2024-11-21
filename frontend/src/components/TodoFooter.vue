<template>
  <div class="todo-footer">
    <!-- Display task counters -->
    <div class="counter">
      <span>{{ remaining }} left</span> <!-- Remaining tasks -->
      <span> | </span>
      <span>{{ completed }} completed</span> <!-- Completed tasks -->
    </div>

    <!-- Filter buttons to change the view -->
    <div class="filters">
      <button
          @click="changeFilter('all')"
          :class="{ 'active': filter === 'all' }">All</button> <!-- Show all tasks -->
      <button
          @click="changeFilter('completed')"
          :class="{ 'active': filter === 'completed' }">Completed</button> <!-- Show completed tasks -->
      <button
          @click="changeFilter('notcompleted')"
          :class="{ 'active': filter === 'notcompleted' }">Not Completed</button> <!-- Show tasks not completed -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'TodoFooter',
  props: {
    remaining: Number, // Number of remaining tasks (passed from parent)
    completed: Number, // Number of completed tasks (passed from parent)
    filter: String,    // Current filter applied (passed from parent)
  },
  methods: {
    // Emits a new filter selection to the parent component.
    async changeFilter(newFilter) {
      this.$emit('changeFilter', newFilter); // Notify parent about the filter change
    }
  }
};
</script>

<style scoped>
/* Container for the footer section */
.todo-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

/* Styling for filter buttons */
.filters button {
  margin: 0 5px;
  background: none; /* Transparent background */
  border: none; /* No border */
  cursor: pointer; /* Pointer cursor on hover */
}

/* Hover effect for buttons */
.filters button:hover {
  color: blue;
}

/* Highlight the active filter button */
.filters button.active {
  font-weight: bold; /* Make the active filter bold */
  color: blue; /* Change the color to blue for the active filter */
}
</style>
