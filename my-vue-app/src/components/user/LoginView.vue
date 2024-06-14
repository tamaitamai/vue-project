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

const router = useRouter()
const mail = ref('')
const password = ref('')

function insertUser(){
    axios.post('/user/login',
        {
            mail: mail.value,
            password: password.value
        }
    ).then(response => {
        mail.value = ''
        password.value = ''        
        console.log(response)
        router.push({path: '/'})
    })
}
</script>

<style scoped>
main{
    display: flex;
    justify-content: center;
}
.user-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    width: 30%;
    margin-top: 50px;
}
.user-btn{
    margin: 20px 0px;
    border-radius: 5px;
    padding: 5px;
}
.user-btn:hover{
    opacity: 0.7;
}
</style>

