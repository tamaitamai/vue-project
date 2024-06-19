<template>
    <div class="container">
        <div class="item-area">
            <h1>商品一覧</h1>
            <div class="item-list">
                <div v-for = "item in itemList" :key = "item.id" class="item-box">
                    <p>{{ item.name }}</p>
                    <img :src="'/image/' + item.image" class="item-image" @click = "itemDetail(item.id)">
                    <p>{{ item.price + '円' }}</p>
                    <button class="item-btn" @click="itemAdd(item)">カートに入れる</button>
                </div>
            </div>
        </div>
        <CartList v-show="user !== null" :cartList="cartList" :user="user" :totalPrice="totalPrice" 
        @cartUpdate = "cartUpdate" @total-price-update = "totalPriceUpdate"/>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import CartList from './CartList.vue'
import { useStore } from 'vuex';

const itemList = ref('')
const cartList = ref('')
const totalPrice = ref(0)
const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)

onMounted(() => {
    axios.get('/item')
    .then(response => {
        itemList.value = response.data   
    })
    if(user.value !== null){
        axios.post('/cart',
            {
                id: user.value.id
            }
        )
        .then(response => {
            cartList.value = response.data
            cartList.value.forEach(cart => {
            totalPrice.value = totalPrice.value + cart.price
        });
        })
    }
})

function itemDetail(id){
    router.push('/detail/' + id)
}
function itemAdd(item){
    axios.post('/cart/add',
        {
            userId: user.value.id,
            name: item.name,
            image: item.image,
            price: item.price
        }
    )
    .then(response => {
        cartList.value = response.data
        totalPrice.value = totalPrice.value + item.price
    })
}
function cartUpdate(response){
    cartList.value = response
}
function totalPriceUpdate(price){
    console.log('totalPrice'+price)
    totalPrice.value = totalPrice.value - price
}
</script>
<style scoped>
.container{
    display: flex;
}
.item-area{
    display: flex;
    align-items: center;
    flex-direction: column;
}
.item-list{
    display: flex;
    flex-wrap: wrap;
}
.item-title{
    text-align: center;
}
.item-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 5px;
    width: 27%;
    margin: 20px 15px;
}
.item-image{
    width: 80%;
    height: 80%;
}
.item-image:hover{
    opacity: 0.7;
}
.item-btn{
    margin-bottom: 10px;
}
</style>
