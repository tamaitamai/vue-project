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
<style scoped>
.favorite-main{
    display: flex;
    margin-top: 30px;
}
/* ジャンル一覧 */
.favorite-genre-list{
    width: 20%;
    padding: 0px 20px;
}
.favorite-genre-box{
    border: 1px solid rgba(77, 74, 74, 0.19);
    padding: 20px;
}
.favorite-genre-btn-box{
    border-top: 1px solid rgba(77, 74, 74, 0.19);
    display: flex;
    justify-content: center;
    flex-direction: column;
}
.favorite-genre-btn,.favorite-genre-reset-btn{
    margin-top: 20px;
    border-radius: 10px;
    font-size: 20px;
    cursor: pointer;
}
.favorite-genre-btn{
    background-color: blue;
    color: white;
    border: none;
}
.favorite-genre-btn:hover{
    opacity: 0.7;
}
.favorite-genre-reset-btn{
    background-color: white;
    border: none;
}
.favorite-genre-reset-btn:hover{
    background-color: rgba(77, 74, 74, 0.19);
}
/* お気に入り一覧 */
.favorite-area{
    width: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.favorite-list{
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}
.favorite-total-count{
    background-color: rgba(150, 147, 147, 0.19);
    width: 95%;
    padding: 10px;
}
.favorite-box{
    width: 25%;
    margin-top: 10px;
}
.favorite-iamge-box{
    display: flex;
    justify-content: center;
}
.favorite-image{
    width: 90%;
}
.favorite-info-box{
    margin-left: 20px;
}
</style>