<template>
    <div class="image-list">
        <div v-for="homeImage in homeImages" :key="homeImage.id" class="image-box" v-show="homeImage.flag">
            <p class="image-change" @click="leftClick">〈</p>
            <img :src="'/image/' + homeImage.image" class="home-image">
            <p class="image-change" @click="rightClick">〉</p>
        </div>
    </div>
    <ItemRank></ItemRank>
</template>

<script setup>
import { ref } from 'vue';
import ItemRank from './item/itemRank.vue';
const homeImages =ref([
    {id:1, image: 'anzu1.jpg', flag: true},
    {id:2, image: 'chieri1.jpg', flag: false},
    {id:3, image: 'uzuki1.jpg', flag: false},
])
const viewImage = ref([])
// ホーム写真を左にスライド
function leftClick(){
    viewImage.value = homeImages.value.filter(h => {
        return h.flag === true
    })    
    homeImages.value.forEach(h => {
        if(h.id === viewImage.value[0].id){            
            h.flag = false
        }
        if(viewImage.value[0].id === 1){
            homeImages.value[homeImages.value.length - 1].flag = true
            return false
        }
        if(h.id === viewImage.value[0].id - 1){
            h.flag = true
        }
    });
}

// ホーム写真を右にスライド
function rightClick(){
    viewImage.value = homeImages.value.filter(h => {
        return h.flag === true
    })    
    homeImages.value.forEach(h => {
        if(h.id === viewImage.value[0].id){            
            h.flag = false
        }
        if(viewImage.value[0].id === homeImages.value.length){
            homeImages.value[0].flag = true
            return false
        }
        if(h.id === viewImage.value[0].id + 1){
            h.flag = true
        }
    });
}
</script>

<style src="../../css/main/home.css" scoped></style>