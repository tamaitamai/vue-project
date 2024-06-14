<template>
    <h1>ホーム画面</h1>
    <p>{{ user }}</p>
</template>

<script setup>
import { computed } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const user = computed(() => store.getters.getUserData);

console.log(user.value)
</script>
