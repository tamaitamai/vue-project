<template>
    <div class="cart-list">
        <h2>カートの中身</h2>
        <div v-for = "cart in cartList" :key = "cart.id" class="cart-box">
            <p>{{ cart.name }}</p>
            <img :src="'/image/' + cart.image" class="cart-image">
            <p>{{ cart.price + '円' }}</p>
            <button class="cart-btn" @click="deleteCart(cart)">削除</button>
        </div>
        <h2>合計金額: {{ totalPrice }}円</h2>
    </div>
</template>
<script setup>
import axios from 'axios';
const {cartList, user, totalPrice} = defineProps(['cartList', 'user', 'totalPrice'])
const emit = defineEmits(['cartUpdate','totalPriceUpdate']);
function deleteCart(cart){
    axios.post('/cart/delete',
        {
            id: cart.id,
            userId: user.id
        }
    )
    .then(response => {
        emit('cartUpdate', response.data)
        emit('totalPriceUpdate', cart.price)
    })
    
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
    width: 70%;
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
</style>