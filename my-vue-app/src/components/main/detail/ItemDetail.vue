<template>
    <div class="item-detail">
        <img :src="'/image/' + item.image" class="item-image">
        <div class="detail-box">
            <div class="item-name">{{ item.name }}</div>
            <div class="average-star">{{ starAverage }}
                <span class="before-star" v-for="star in beforeStar" :key="star">★</span>
                <span class="last-star" v-for="star in lastStar" :key="star" :style="{ '--star-width': starWidth }">★</span>
                <span class="after-star" v-for="star in afterStar" :key="star">★</span>
            </div>
            <div class="item-price">￥{{ item.price }}</div>
            <div>{{ item.comment }}</div>
        </div>
        <div class="price-box">
            <div class="item-cart-price">￥{{ item.price }}</div>
            <div class="item-count">
                <span class="count-down" @click="countDown()">-</span>
                <span class="count-view">{{ count }}</span>
                <span class="count-up" @click="countUp()">+</span>     
            </div>
            <button class="item-btn" @click="itemAdd(item)">カートに入れる</button>
        </div>
    </div>
    <ReviewList :item-id="item.id" @average-review="onAverageReview"/>
    <CartAddModal v-show="modalShow" @close-modal="closeModal"/>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
import axios from 'axios';
import ReviewList from './ReviewList.vue';
import CartAddModal from '../modal/CartAddModal.vue';

const item = ref('')
const starAverage = ref(0)
const beforeStar = ref(0)
const lastStar = ref(0)
const afterStar = ref(0)
const starWidth = ref('100%')
const modalShow = ref(false)

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
function closeModal(){
    modalShow.value = false
}
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
    .then(() => {
        modalShow.value=true
        console.log(modalShow.value)
    })
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
.item-detail{
    display: flex;
    margin-top: 50px;
}
.detail-box{
    width: 50%;
    margin: 20px;
}
.item-name{
    font-size: 30px;
}
.item-price{
    font-size: 35px;
    margin: 10px 0px;
}
.item-image{
    width: 500px;
    height: 300px;
}
.average-star{
    font-size: 20px;
    border-bottom: 1px solid rgba(77, 74, 74, 0.19);
}
/**カートへの追加周り */
.price-box{
    border: 1px solid rgba(77, 74, 74, 0.19);
    padding: 20px;
    width: 15%;
    display: flex;
    align-items: center;
    flex-direction: column;
}
.item-cart-price{
    font-size: 20px;
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
.item-btn{
    width:95%;
    border: 1px solid rgba(77, 74, 74, 0.19);
    background-color: rgb(252 249 4 / 95%);
    margin: 5px 0px;
    font-size: 20px;
    border-radius: 50px;
}
.item-btn:hover{
    opacity: 0.7;
}
/**レビューの平均表示 */
.before-star{
    /* color: rgba(237, 237, 10, 0.889); */
    color: orange;
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
    /* color: rgba(237, 237, 10, 0.889); */
    color: orange;
}
</style>