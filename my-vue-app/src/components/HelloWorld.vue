<template>
  <div>
    <h1>{{ message }}</h1>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';

const message = ref('hello')
onMounted(() => {
  axios.get('/hello')
  .then(response => {
    message.value = response.data
  })
})
</script>

<style scoped>
/* スタイルはお好みで */
</style>