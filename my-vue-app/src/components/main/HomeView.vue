<template>
    <div class="image-list">
        <div v-for="homeImage in homeImages" :key="homeImage.id" class="image-box" v-show="homeImage.flag">
            <p class="image-change" @click="leftClick">〈</p>
            <img :src="'/image/' + homeImage.image" class="home-image">
            <p class="image-change" @click="rightClick">〉</p>
        </div>
        
    </div>
    <ItemList></ItemList>
</template>

<script setup>
import { ref } from 'vue';
import ItemList from './ItemList.vue';
const homeImages =ref([
    {id:1, image: 'anzu1.jpg', flag: true},
    {id:2, image: 'chieri1.jpg', flag: false},
    {id:3, image: 'uzuki1.jpg', flag: false},
])
const viewImage = ref([])
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

<style scoped>
.image-list,.image-box{
    display: flex;
    justify-content: center;
    align-items: center;
}
.image-list{
    margin-top: 50px;
}
.home-image{
    width: 50%;
    margin: 0px 20px;
}
.image-change{
    font-size: 100px;
}
</style>