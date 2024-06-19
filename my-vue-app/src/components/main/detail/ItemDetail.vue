<template>
    <div class="item-detail">
        <p>{{ item.name }}</p>
        <img :src="'/image/' + item.image" class="item-detail-image">
        <p>{{ item.comment }}</p>
        <p>{{ item.price }}円</p>
    </div>
    <ReviewList :item-id="item.id"/>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import ReviewList from './ReviewList.vue';

const item = ref('')
const route = useRoute();

onMounted(()=>{
    axios.post('/item/detail',
        {
            id: route.params.id
        }
    ).then(response => {
        item.value = response.data
    })
})
</script>
<style scoped>
.item-detail-image{
    width: 400px;
    height: 300px;
}
</style>