<template>
    <div class="item-detail">
        <p>{{ item.name }}</p>
        <img :src="'/image/' + item.image" class="item-detail-image">
        <p>{{ item.comment }}</p>
        <p class="item-count">
            <span class="count-down" @click="countDown()">-</span>
            <span class="count-view">{{ count }}</span>
            <span class="count-up" @click="countUp()">+</span>
        </p>
        <p>{{ item.price }}円</p>
        <button class="item-btn" @click="itemAdd(item)">カートに入れる</button>
    </div>
    <ReviewList :item-id="item.id"/>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import ReviewList from './ReviewList.vue';
import { useStore } from 'vuex';

const item = ref('')
const route = useRoute()
const store = useStore()
const count =ref(1)
const user = computed(() => store.getters.getUserData)

onMounted(()=>{
    axios.post('/item/detail',
        {
            id: route.params.id
        }
    ).then(response => {
        item.value = response.data
    })
})
function itemAdd(item){
    axios.post('/cart/add',
        {
            userId: user.value.id,
            name: item.name,
            image: item.image,
            price: item.price,            
        }
    )
}
function countUp(){
    count.value++
}
function countDown(){
    if(count.value > 0){
        count.value--        
    }
}
</script>
<style scoped>
.item-detail-image{
    width: 400px;
    height: 300px;
}
.item-count{
    border: 1px solid black;
    border-radius: 5px;
    width: 90px;
}
.count-view{
    background-color: gainsboro;
    padding: 0px 10px;
    border: 1px solid black;
}
.count-down,.count-up{
    margin: 10px;
    font-size: 20x;
    cursor: pointer;
}
</style>