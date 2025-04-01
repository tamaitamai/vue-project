<template>
    <div class="cart-list">
        <h2>カートの中身</h2>
        <div v-for = "cart in cartList" :key = "cart.id" class="cart-box">
            <p>{{ cart.name }}</p>
            <img :src="'/image/' + cart.image" class="cart-image">
            <p>{{ cart.price * cart.count + '円' }}</p>
            <p class="item-count">
                <span class="count-down" @click="countDown(cart)">-</span>
                <span class="count-view">{{ cart.count }}</span>
                <span class="count-up" @click="countUp(cart)">+</span>
            </p>
            <button class="cart-btn" @click="deleteCart(cart)">削除</button>
        </div>
        <h2>合計金額: {{ totalPrice }}円</h2>
    </div>
</template>
<script setup>
import axios from 'axios';
const {cartList, user, totalPrice} = defineProps(['cartList', 'user', 'totalPrice'])
const emit = defineEmits(['cartUpdate','totalPriceDeleteUpdate', 'cartListView']);
function deleteCart(cart){
    axios.post('/cart/delete',
        {
            id: cart.id,
            userId: user.id
        }
    )
    .then(response => {                
        emit('cartUpdate', response.data)
        emit('totalPriceDeleteUpdate', cart.price, cart.count)
    })  
}
function countUpdate(cart, add){
    axios.post('/cart/countUpdate',
        {
            id: cart.id,
            count: cart.count + add,
            userId: cart.userId
        }
    )
    .then(() => {
        emit('cartListView')
    })
}
function countUp(cart){ 
    countUpdate(cart, 1)
}
function countDown(cart){
    if(cart.count > 0){
        countUpdate(cart, -1)
    }
}
</script>
<style scoped>
.cart-image{
    width: 80%;
    height: 80%;
}
.cart-list{
    display: flex;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    margin-top: 20px;
    width: 100%;
}
.cart-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 5px;
    margin: 10px 10px;
}
.cart-btn{
    margin-bottom: 10px;
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