<template>
    <div class="container">
        <div class="item-area">
            <h1>商品一覧</h1>
            <div class="item-list">
                <div v-for = "item in itemList" :key = "item.id" class="item-box">
                    <p>{{ item.name }}</p>
                    <img :src="'/image/' + item.image" class="item-image" @click = "itemDetail(item.id)">
                    <p>{{ item.price + '円' }}</p>
                </div>
            </div>
        </div>
        <CartList :itemList="itemList"/>
    </div>
</template>
<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import CartList from './CartList.vue'

const itemList = ref('')
const router = useRouter()

onMounted(() => {
    axios.get('/item')
    .then(response => {
        itemList.value = response.data   
    })
})

function itemDetail(id){
    console.log(id)
    router.push('/detail/' + id)
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
    margin-bottom: 20px;
}
.item-image:hover{
    opacity: 0.7;
}
</style>
