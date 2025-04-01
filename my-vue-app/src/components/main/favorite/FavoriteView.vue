<template>
    <div class="favorite-main">
        <!-- ジャンル一覧 -->
        <div class="favorite-genre-list">
            <div class="favorite-genre-title">絞り込み</div>
            <div class="favorite-genre-box">
                <div>カテゴリ</div>
                <div v-for="genre in genreList.keys()" :key="genre">
                    <input type="checkbox" :value="genre" v-model="genreSelectList" @change="genreChange">
                    {{ genreList.get(genre) }}
                </div>
                <div class="favorite-genre-btn-box">
                    <button class="favorite-genre-btn" @click="genreClick">絞り込む</button>
                    <button class="favorite-genre-reset-btn" @click="resetClick">選択解除</button>
                </div>
            </div>
        </div>
        <!-- お気に入り一覧 -->
        <div class="favorite-area">
            <div class="favorite-total-count">全件</div>
            <div class="favorite-list">
                <div v-for="favorite in favoriteList" :key="favorite.id" class="favorite-box">
                    <div class="favorite-iamge-box">
                        <img :src="'/image/' + favorite.image" class="favorite-image">
                    </div>
                    <div class="favorite-info-box">
                        <div>{{ favorite.name }}</div>
                        <div>{{ favorite.price }}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';

const favoriteList = ref('');
const genreList = ref(new Map());
const genreSelectList = ref([]);

const store = new useStore();
const user = computed(() => store.getters.getUserData)

onMounted(() => {  
    // お気に入り一覧を確保
    axios.post('/favorite',
        {
            userId: user.value.id,    
        }
    )
    .then(response => {
        favoriteList.value = response.data;
    })

    // ジャンル一覧を確保
    axios.get('/item')
    .then(response => {
        response.data.forEach(item => {
            genreList.value.set(item.genre,item.genre);
        })
    })
})
function resetClick(){
    genreSelectList.value = [];
    axios.post('/favorite',
        {
            userId: user.value.id,    
        }
    )
    .then(response => {
        favoriteList.value = response.data;
    })
}
// 絞り込んだジャンル一覧を表示
function genreClick(){
    axios.post('/favorite/genre',
        {
            userId: user.value.id,
            genreList: genreSelectList.value
        }
    )
    .then(response => {
        favoriteList.value = response.data;
    })
}
</script>
<style src="../../../css/favorite/favorite.css"></style>