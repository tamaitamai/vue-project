<template>
    <h1>おすすめ</h1>
    <div class="item-rank-list">
        <div v-for="(item,index) in itemRankList" :key="item.id" class="item-rank-box" @click="itemDetail(item)">
            <div class="item-rank">#{{ index + 1 }}</div>
            <img :src="'/image/'+item.image" class="item-rank-image">
            <div>{{ item.name }}</div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const itemRankList = ref('')
const router = new useRouter();
// 商品ランキングの一覧
axios.post(process.env.VUE_APP_API_BASE_URL+'/item/rank')
.then(response=>{
    console.log('home状態: '+process.env.VUE_APP_API_BASE_URL)
    itemRankList.value = response.data;
})
// 商品詳細
function itemDetail(item){
    router.push('/detail/'+item.id);
}
</script>
<style src="../../../css/item/rank.css"></style>