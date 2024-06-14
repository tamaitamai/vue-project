<template>
    <h1>ホーム画面</h1>
</template>
<script setup>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';

const store = useStore();
const response = computed(() => store.getters.getResponseData);

const count = ref(store.getters.getResponseData)
console.log(count.value)

console.log(response.value);
console.log(response.value.mail)
console.log(response.value.password)


</script>
