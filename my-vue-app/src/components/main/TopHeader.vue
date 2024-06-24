<template>
    <header>
        <div class="login-box">
            <RouterLink to="/">ホーム</RouterLink>
            <RouterLink to="/item">商品一覧</RouterLink>
            <div v-if="!isShow">
                <RouterLink to="/cartConfirm">🛒</RouterLink>
            </div>
        </div>
        <div v-if="isShow" class="login-box">
            <RouterLink to="/insert">新規登録</RouterLink>
            <RouterLink to="/login">ログイン</RouterLink>
        </div>
        <div v-else class="login-box">
            <p>{{ user.name }}</p>
            <button @click="logOut()">ログアウト</button>
        </div>
    </header>
    
</template>

<script setup>
import { computed, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const user = computed(() => store.getters.getUserData);
const isShow = computed(() => user.value === null);
const router = useRouter()

watch(isShow, ()=>{
    console.log('watch: '+isShow.value)
})

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
    background-color: bisque;
    border-radius: 10px;
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.5);
    height: 100px;
}

.home-box{
    margin: 20px;
}

.login-box{
    display: flex;
    flex-direction: column;    
    margin: 10px;
}
</style>