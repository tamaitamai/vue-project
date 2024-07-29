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
        <div v-else class="login-box">
            <div class="top-name" @click="router.push('/userEdit')">{{ user.name }}さんこんにちは！</div>
            <div @click="logOut()" class="top-name">ログアウト</div>
        </div>

    </header>
    
</template>

<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const itemName = ref('');
const genreList = ref(new Map());
const user = computed(() => store.getters.getUserData);
const isShow = computed(() => user.value === null);
const store = useStore();
const router = useRouter()
// ジャンル一覧を取得
axios.get('/item')
.then(response => {
    response.data.forEach(item => {
        genreList.value.set(item.genre,item.genre)
    })
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

<style scoped>
header{
    display: flex;
    justify-content: space-between;
    align-items: center;
    /* background-image: url(../../../public/image/backGround/haikei1.jpg);
    background-size: cover; */
    border: 1px solid black;
    border-radius: 10px;
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.5);
    height: 100px;
}
.menu-box{
    display: flex;
    align-items: center;
}
/* 検索欄 */
.search-box{
    display: flex;
    align-items: center;
    border: 1px solid black;
    border-radius: 5px;
    background-color: orange;
}
.search-input{
    font-size: 20px;
    width: 300px;
    padding: 5px;
    border: none;
    border-right: 1px solid black;
    border-bottom-left-radius: 5px;
    border-top-left-radius: 5px;
}
.search-btn{
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    padding: 0px 5px;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
}
/* アイテム絞り込み */
.top-genre{
    padding: 5px;
    font-size: 20px;
    border-radius: 5px;
    margin: 0px 10px;
    background-color: rgba(180, 176, 176, 0.19);
}
/* アイテム情報 */
.select-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin: 0px 10px;
}
.select-item{
    font-size: 25px;
    text-decoration: none;
    cursor: pointer;
}
/* ユーザー情報 */
.login-box{
    display: flex;
    justify-content: end;
    align-items: center;
    flex-direction: column;
    margin-right: 30px; 
}
.top-name{
    cursor: pointer;
    font-size: 20px;
    color: black;
    text-decoration: none;
    margin: 5px 0px;
}
.top-name:hover{
    color: blue;
}
</style>