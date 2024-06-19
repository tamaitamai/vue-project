<template>
    <div class="review-area">
        <div class="review-box">
            <h1>レビュー入力</h1>
            <label for="name">ユーザー名：</label>
            <input type="text" id="name" class="review-name" v-model="name">
            <label for="comment">コメント：</label>
            <textarea id="comment" class="review-comment" rows="10" cols="30" v-model="comment"></textarea>
            <div class="review-good">
                <span v-for="star in stars" :key="star.num" @click="reviewGood(star.num)" :class="{ star: star.check }">
                    {{ star.text }}
                </span>
            </div>
            <button class="review-btn" @click="reviewInput()">投稿</button>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

const totalGood = ref(0)
const name = ref('')
const comment = ref('')
const stars = ref([
    { num:1, text: '☆' ,check: false},
    { num:2, text: '☆' ,check: false},
    { num:3, text: '☆' ,check: false},
    { num:4, text: '☆' ,check: false},
    { num:5, text: '☆' ,check: false},
])
const route = useRoute()
const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)
//評価ランクを表示
function reviewGood(num){
    totalGood.value = 0
    stars.value.forEach(star => {
        if(star.num <= num){
            star.check = true
            star.text = '★'
            totalGood.value ++
        }else{
            star.check = false
            star.text = '☆'
        }
    });
}
//入力したレビューの情報を送信
function reviewInput(){
    axios.post('/review/add',
        {
            good: totalGood.value,
            itemId: route.params.itemId,
            userId: user.value.id,
            name: name.value,
            comment: comment.value
        }
    )
    .then(() => {
        router.push('/detail/' + route.params.itemId)
    })
}
</script>
<style scoped>
.review-area{
    display: flex;
    justify-content: center;
}
.review-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.8);
    width: 30%;
    border-radius: 10px;
    margin-top: 50px;
}
.review-name{
    margin-bottom: 20px;
    border-radius: 5px;
}
.review-good{
    margin-top: 20px;
    font-size: 20px;
}
.star{
    color: yellow;
}
.review-btn{
    margin: 10px 0px;
}
</style>