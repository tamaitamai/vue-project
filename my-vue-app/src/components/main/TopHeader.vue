<template>
    <header>
        <div class="menu-box">
            <RouterLink to="/">
                <div style="margin: 0px 10px;">ホーム</div>
            </RouterLink>
            <div class="search-box">
                <input type="text" class="search-input">
                <p class="search-btn">🔎</p>
            </div>
            <RouterLink to="/item">商品一覧</RouterLink>
            <div class="select-box">
                <RouterLink :to="!isShow ? '/cartConfirm' : '/login'" style="font-size: 25px">🛒</RouterLink>
                カート
            </div>
            <div class="select-box">
                <RouterLink :to="!isShow ? '/history' : '/login'" style="font-size: 25px">🕐</RouterLink>
                <div>履歴</div>
            </div>
            <div class="select-box">
                <div style="font-size: 25px">☆</div>
                <div>お気に入り</div>
            </div>
        </div>

        <div v-if="isShow" class="login-box">
            <RouterLink to="/insert">新規登録</RouterLink>
            <RouterLink to="/login">ログイン</RouterLink>
        </div>
        <div v-else class="login-box">
            <div>{{ user.name }}さんこんにちは！</div>
            <button @click="logOut()" class="log-out-btn">ログアウト</button>
        </div>

    </header>
    
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const user = computed(() => store.getters.getUserData);
const isShow = computed(() => user.value === null);
const router = useRouter()

// watch(isShow, ()=>{
//     console.log('watch: '+isShow.value)
// })

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
.search-box{
    display: flex;
    justify-content: center;
    align-items: center;
}
.search-input{
    height: 30px;
    width: 500px;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
}
.search-btn{
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: rgb(14, 199, 137);
    width: 35px;
    height: 35px;
    border-top-right-radius: 5px;
    border-bottom-right-radius: 5px;
}
.select-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin: 0px 5px;
}
.login-box{
    display: flex;
    justify-content: end;
    align-items: center;
    flex-direction: column;
    margin: 10px;
}
.log-out-btn{
    width: 100px;
    padding: 3px;
    margin-top: 5px;
    border-radius: 5px;
}
.log-out-btn:hover{
    opacity: 0.7;
}
</style>