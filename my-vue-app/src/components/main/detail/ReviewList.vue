<template>
    <h1>レビュー</h1>
    <RouterLink :to="'/reviewInput/' + itemId">レビュー入力</RouterLink>
    <div v-for="review in reviewList" :key = "review.id" class="review-box">
        <p>{{ review.name }}</p>
        <p @click="reviewClick()">{{ review.comment }}</p>
    </div>
</template>
<script setup>
import axios from 'axios';
import { ref, watch } from 'vue';
const props = defineProps(['itemId']);
const itemId = ref(props.itemId)
const reviewList = ref('')

watch(() => props.itemId, (newVal) => {
  itemId.value = newVal;
  axios.post('/review',
        {
            itemId: itemId.value
        }
    )
    .then(response=>{
        reviewList.value = response.data
    })
});

function reviewClick(){
    console.log('reviewClick')
    console.log('item : '+ itemId.value)
}
</script>
<style scoped>
.review-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    margin-top: 20px;
}
</style>