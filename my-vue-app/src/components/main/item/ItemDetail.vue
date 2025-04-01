<template>
    <div class="item-detail">
        <!-- 商品詳細 -->
        <div class="item-image-box">
            <img :src="'/image/' + item.image" class="item-image" @click="imageModalShow = true">
            <div class="item-favorite" :class="favoriteSelect || favoriteShow ? {'select-favorite': true} : ''"
            @click="favoriteClick">{{ favorite }}</div>
        </div>
        <div class="detail-box">
            <div class="item-name">{{ item.name }}</div>
            <div class="item-star-box">
                <div class="item-star-average">{{ starAverage }}</div>
                <StarView :itemId="item.id" @star-average="starAverageView"/>
            </div>
            <div class="item-price">￥{{ item.price }}</div>
            <div>{{ item.comment }}</div>
        </div>
        <!-- カートへの追加 -->
        <div class="price-box">
            <div class="item-cart-price">￥{{ item.price }}</div>
            <div class="price-add-box">
                <select name="" @change="count = $event.target.value" class="item-count">
                    <option v-for="num in 50" :value="num" :key="num">数量：{{ num }}</option>
                </select>
                <button class="item-btn" @click="itemAdd(item)">カートに入れる</button>
            </div>
        </div>
    </div>
    <ReviewList :item-id="item.id"/>
    <CartAddModal v-show="modalShow" @close-modal="closeModal"/>
    <ItemImageModal v-show="imageModalShow" :item-image="item.image" @close-image="closeImage"/>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';
import axios from 'axios';

import ReviewList from '../review/ReviewList.vue';
import CartAddModal from '../modal/CartAddModal.vue';
import StarView from '../star/StarView.vue';
import ItemImageModal from '../modal/ItemImageModal.vue'

const item = ref('')
const starAverage = ref(0)
const modalShow = ref(false)
const imageModalShow = ref(false)
const favorite = ref('♡');
const favoriteSelect = ref(false);
const favoriteShow = ref(false);

const route = useRoute()
const router = useRouter()
const store = useStore()
const count =ref(1)
const user = computed(() => store.getters.getUserData)

// 商品の詳細を表示
onMounted(()=>{
    axios.post('/item/detail',
        {
            id: route.params.id
        }
    ).then(response => {
        item.value = response.data
        if(user.value != null){
            favoriteExists()
        }
    })
})
function closeModal(){
    modalShow.value = false
}
// 商品をカートに追加
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
    })
}
function starAverageView(average){
    starAverage.value = average;
}
function closeImage(){
    imageModalShow.value = false
}
// すでにお気に入りに登録しているかを確認
function favoriteExists(){
    axios.post('/favorite/exists',
        {
            userId: user.value.id,
            itemId: item.value.id,
        }
    )
    .then(response => {
        favoriteShow.value = response.data;
        if(response.data == true){
            favorite.value = '♥';
        }
    })
}
// お気に入りを追加
function favoriteClick(){
    if(user.value == null){
        router.push('/login');
        return false;
    }
    if(favorite.value === '♡'){
        favoriteSelect.value = true;
        favorite.value = '♥';
    }else{
        favoriteSelect.value = false;
        favoriteShow.value = false;
        favorite.value = '♡';
    }
    axios.post('/favorite/add',
        {
            userId: user.value.id,
            itemId: item.value.id,
            name: item.value.name,
            image: item.value.image,
            price: item.value.price,
            genre: item.value.genre
        }
    )
    .then(() => {
        console.log('success');
    })
}
</script>
<style src="../../../css/item/detail.css"></style>