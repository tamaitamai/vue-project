<template>
    <main>
        <div class="user-box">
            <h1>ユーザー情報編集</h1>
            <label for="name">名前：</label>
            <input type="text" id="name" v-model="name">
            <br>
            <label for="address">住所：</label>
            <input type="text" id="address" v-model="address">
            <br>
            <label for="mail">メール：</label>
            <input type="text" id="mail" v-model="mail">
            <br>
            <label for="password">パスワード：</label>
            <input type="text" id="password" v-model="password">
            <br>
            <button class="user-btn" @click="userUpdate">ユーザー情報を更新</button>
        </div>
    </main>
</template>
<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore()
const router = useRouter()

const name = ref('')
const address = ref('')
const mail = ref('')
const password = ref('')
const user = computed(() => store.getters.getUserData)
name.value = user.value.name;
address.value = user.value.address;
mail.value = user.value.mail;
password.value = user.value.password;

function userUpdate(){
    axios.post('/user/update',
        {
            id: user.value.id,
            name: name.value,
            address: address.value,
            mail: mail.value,
            password: password.value
        }
    )
    .then(response => {
        store.dispatch('updateUserData',response.data)
        router.push('/')
    })
}
</script>
<style src="../../css/user/user.css" scoped></style>