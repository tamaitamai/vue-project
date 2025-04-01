<template>
    <main>
        <div class="user-box">
            <h1>ログイン</h1>
            <div>
                <p>メール:</p>
                <input type="text" v-model="mail">
                <p>パスワード:</p>
                <input type="password" v-model="password">
            </div>
            <button class="user-btn" @click="insertUser()">ログイン</button>
        </div>
    </main>
</template>
<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';


const router = useRouter()
const mail = ref('')
const password = ref('')
const store = useStore()

function insertUser(){
    axios.post('/user/login',
        {
            mail: mail.value,
            password: password.value
        }
    ).then(response => {
        mail.value = ''
        password.value = ''        
        store.dispatch('updateUserData', response.data);
        
        router.push({path: '/item'})
    })
}
</script>
<style src="../../css/user/user.css" scoped></style>