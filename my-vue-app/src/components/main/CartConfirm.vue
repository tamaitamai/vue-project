<template>
    <div class="cart-area">
        <h1>カートの中身の確認</h1>
        <div class="cart-list">
            <div v-for="cart in cartList" :key="cart.id" class="cart-box"> 
                <p>{{ cart.name }}</p>
                <img :src="'/image/' + cart.image" class="cart-image">
            </div>
        </div>
        <h2>合計金額: {{ totalPrice }}</h2>
    </div>
    
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import { useStore } from 'vuex';

const store = useStore()
const user = computed(() => store.getters.getUserData)
const cartList = ref('')
const totalPrice = ref(0)
onMounted(() => {
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
})
</script>
<style scoped>
.cart-image{
    width: 80%;
    height: 80%;
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
</style>