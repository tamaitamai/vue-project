<template>
    <div class="cart-area">
        <h1>カートの中身の確認</h1>
        <div class="cart-list">
            <div v-for="cart in cartList" :key="cart.id" class="cart-box"> 
                <p>{{ cart.name }}</p>
                <img :src="'/image/' + cart.image" class="cart-image" @click = "itemDetail(cart.itemId)">
                <p>{{ cart.price * cart.count }}円</p>
                <p class="item-count">
                    <span class="count-down" @click="countDown(cart)">-</span>
                    <span class="count-view">{{ cart.count }}</span>
                    <span class="count-up" @click="countUp(cart)">+</span>
                </p>
                <button class="cart-btn" @click="deleteCart(cart)">削除</button>
            </div>
        </div>
        <h2>合計金額: {{ totalPrice }}円</h2>
        <button @click="payment">購入完了</button>
    </div>
    
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)
const cartList = ref('')
const totalPrice = ref(0)

onMounted(() => {
    countData()
})
//カート内の商品を削除
function deleteCart(cart){
    axios.post('/cart/delete',
        {
            id: cart.id,
            userId: user.value.id
        }
    )
    .then(response => {                
        cartList.value = response.data
        totalPrice.value -= cart.price * cart.count     
    })  
}
//カート一覧を取得
function countData(){
    axios.post('/cart',
        {
            id: user.value.id
        }
    )
    .then(response => {
        cartList.value = response.data
        totalPrice.value = 0
        cartList.value.forEach(cart => {
            totalPrice.value = totalPrice.value + cart.price * cart.count
        });
    })
}
//カート内の商品数を更新
function countUpdate(cart, add){
    axios.post('/cart/countUpdate',
        {
            id: cart.id,
            count: cart.count + add,
            userId: user.value.id
        }
    )
    .then(response => {
        cartList.value = response.data
        totalPrice.value = 0
        cartList.value.forEach(cart => {
            totalPrice.value = totalPrice.value + cart.price * cart.count
        });
    })
}
function itemDetail(id){
    router.push('/detail/' + id)
}
function countUp(cart){ 
    countUpdate(cart, 1)
}
function countDown(cart){
    if(cart.count > 0){
        countUpdate(cart, -1)
    }
}
function payment(){
    axios.post('/cart/payment',
        {
            userId: user.value.id
        }
    )
    .then(()=>{
        router.push('/buyPage');
    })
}
</script>
<style scoped>
.cart-image{
    width: 80%;
    height: 80%;
}
.cart-image:hover{
    opacity: 0.7;
}
.cart-area, .cart-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.cart-box{
    width: 30%;
    border: 1px solid black;
    border-radius: 5px;
    margin: 10px;
}
.cart-list{
    display: flex;
    flex-wrap: wrap;
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