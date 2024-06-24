<template>
    <div class="item-detail">
        <p>{{ item.name }}</p>

        <h2>評価：{{ starAverage }}
            <span class="before-star" v-for="star in beforeStar" :key="star">★</span>
            <span class="last-star" v-for="star in lastStar" :key="star" :style="{ '--star-width': starWidth }">★</span>
            <span class="after-star" v-for="star in afterStar" :key="star">★</span>
        </h2>        
        
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
    <ReviewList :item-id="item.id" @average-review="onAverageReview"/>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import ReviewList from './ReviewList.vue';
import { useStore } from 'vuex';

const item = ref('')
const starAverage = ref(0)
const beforeStar = ref(0)
const lastStar = ref(0)
const afterStar = ref(0)
const starWidth = ref('100%')

const route = useRoute()
const router = useRouter()
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
    if(user.value === null){
        router.push('/login')
        return false
    }
    axios.post('/cart/add',
        {
            userId: user.value.id,
            itemId: item.id,
            name: item.name,
            image: item.image,
            price: item.price,
            count: count.value      
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
function onAverageReview(average, before, last, after, width){
    starAverage.value = average
    beforeStar.value = before
    lastStar.value = last
    afterStar.value = after
    starWidth.value = width
}
</script>
<style scoped>
.before-star{
    color: rgba(237, 237, 10, 0.889);
}
.after-star{
    color: lightgray;
}
.last-star{
    display: inline-block;
    position: relative;
    color: lightgray;
}
.last-star::before {
    content: '★';
    position: absolute;
    left: 0;
    top: 0;
    width: var(--star-width);
    overflow: hidden;
    color: rgba(237, 237, 10, 0.889);
}
.item-detail-image{
    width: 500px;
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