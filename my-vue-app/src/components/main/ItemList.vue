<template>
    <div class="container">
        <div class="item-area">
            <h1>アイドル風景写真一覧</h1>
            <div class="item-list">
                <div v-for = "item in itemList" :key = "item.id" class="item-box">
                    <p>{{ item.name }}</p>
                    <img :src="'/image/' + item.image" class="item-image" @click = "itemDetail(item.id)">
                    <p>{{ item.price + '円' }}</p>
                    <select @change="updateCount">
                        <option  v-for="num in 50" :key="num" :value="num">{{num}}</option>
                    </select>
                    <button class="item-btn" @click="itemAdd(item)">カートに入れる</button>
                </div>
            </div>
        </div>
        <CartList v-show="false" :cartList="cartList" :user="user" :totalPrice="totalPrice" 
        @cartUpdate = "cartUpdate" @total-price-delete-update = "totalPriceDeleteUpdate" @cart-list-view = "cartListView"/>
    </div>
    <CartAddModal v-show="modalShow" @close-modal="closeModal"/>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

import CartList from './cart/CartList.vue'
import CartAddModal from './modal/CartAddModal.vue';

const itemList = ref('')
const cartList = ref('')
const totalPrice = ref(0)
const count = ref(1)
const modalShow = ref(false)

const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)

onMounted(() => {
    axios.get('/item')
    .then(response => {
        itemList.value = response.data   
    })
    cartListView()
})

function cartListView(){
    if(user.value !== null){
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
}
function itemDetail(id){
    router.push('/detail/' + id)
}
function itemAdd(item){
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
    .then(response => {
        cartList.value = response.data
        totalPrice.value = totalPrice.value + item.price * count.value
        modalShow.value = true;
    })
}
function cartUpdate(response){
    cartList.value = response
}
function totalPriceDeleteUpdate(price, count){
    totalPrice.value = totalPrice.value - price * count
}
function updateCount(event){
    count.value = event.target.value
}
function closeModal(){
    modalShow.value = false
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
    justify-content: center;
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
    width: 20%;
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
.item-count{
    border: 1px solid black;
    border-radius: 5px;
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
