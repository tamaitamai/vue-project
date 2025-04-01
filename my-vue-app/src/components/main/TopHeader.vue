<template>
    <header>
        <div class="menu-box">
            <RouterLink to="/" class="top-name" style="margin: 0px 10px">ホーム</RouterLink>
            <!-- 検索欄 -->
            <div class="search-box">
                <input type="text" class="search-input" v-model="itemName">
                <span class="search-btn" @click="itemSeach">🔎</span>
            </div>
            <!-- アイテム絞り込み -->
            <select class="top-genre" @change="itemGenre">
                <option value="">---</option>
                <option value="all">すべて</option>
                <option :value="genre" v-for="genre in genreList.keys()" :key="genre">{{ genreList.get(genre) }}</option>
            </select>
            <!-- アイテム情報 -->
            <div class="select-box">
                <RouterLink :to="!isShow ? '/cartConfirm' : '/login'" class="select-item">🛒</RouterLink>
                カート
            </div>
            <div class="select-box">
                <RouterLink :to="!isShow ? '/history' : '/login'" class="select-item">🕐</RouterLink>
                <div>履歴</div>
            </div>
            <div class="select-box">
                <RouterLink :to="!isShow ? '/favorite' : '/login'" class="select-item">☆</RouterLink>
                <div>お気に入り</div>
            </div>
        </div>
        <!-- ユーザー情報 -->
        <div v-if="isShow" class="login-box">
            <RouterLink to="/insert" class="top-name">新規登録</RouterLink>
            <RouterLink to="/login" class="top-name">ログイン</RouterLink>
        </div>
        <div v-else class="login-box" @mouseleave="userInfoShow = false">
            <div class="top-name" @mouseover="userInfoShow = true">{{ user.name }}さんこんにちは！</div>
            <div class="login-info-area" v-show="userInfoShow">
                <div class="login-info-box">
                    <div class="top-name" @click="router.push('/userEdit')">ユーザー情報編集</div>
                    <RouterLink to="/pointList">{{ point }}ポイント</RouterLink>
                </div>
            </div>
            <div @click="logOut()" class="top-name">ログアウト</div>
        </div>

    </header>
    
</template>

<script setup>
import axios from 'axios';
import { computed, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const itemName = ref('');
const genreList = ref(new Map());
const user = computed(() => store.getters.getUserData);
const isShow = computed(() => user.value === null);
const userInfoShow = ref(false);
const point = ref('');

const store = useStore();
const router = useRouter()
// ジャンル一覧を取得
axios.get('/item')
.then(response => {
    response.data.forEach(item => {
        genreList.value.set(item.genre,item.genre)
    })
})
// ユーザーのポイント合計の確保
function getPoint(){
    if(user.value != null){
        axios.post('/point',
            {
                userId: user.value.id
            }
        )
        .then(response=>{
            point.value = response.data
        })
    }
}
getPoint()
watch(user, ()=>{
    getPoint()
})
// アイテムを検索
function itemSeach(){
    axios.post('/item/search',
        {
            name: itemName.value
        }
    )
    .then(response=>{
        itemName.value = ''
        router.push('/item')
        store.dispatch('updateItemListData',response.data);
    })
}
// アイテムを絞り込む
function itemGenre(event){
    if(event.target.value === 'all'){
        axios.get('/item')
        .then(response => {
            router.push('/item')
            store.dispatch('updateItemListData',response.data);
        })
    }else{
        axios.post('/item/genre',
            {
                genre: event.target.value
            }
        )
        .then(response => {
            router.push('/item')
            store.dispatch('updateItemListData',response.data);
        })        
    }
}
// ログアウト
function logOut() {
  store.dispatch('updateUserData', null);
  router.push('/login')
}
</script>
<style src="../../css/main/top.css" scoped></style>