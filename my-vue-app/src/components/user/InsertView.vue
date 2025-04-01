<template>
    <main>
        <div class="user-box">
            <h1>新規登録</h1>
            <div>
                <p>名前:</p>
                <input type="text" v-model="name">
                <p>メール:</p>
                <input type="text" v-model="mail">
                <p>パスワード:</p>
                <input type="password" v-model="password">
                <p>住所:</p>
                <input type="text" v-model="address">
            </div>
            <button class="user-btn" @click="insertUser()">新規登録</button>
        </div>
    </main>
</template>
<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router'
const router = useRouter()

const name = ref('')
const mail = ref('')
const password = ref('')
const address = ref('')

function insertUser(){
    axios.post('/user/insert',
        {
            name: name.value,
            mail: mail.value,
            password: password.value,
            address: address.value
        }
    ).then(() => {
        name.value = ''
        mail.value = ''
        password.value = ''
        address.value = ''
        router.push('/login')
    })
    
}
</script>
<style src="../../css/user/user.css" scoped></style>
