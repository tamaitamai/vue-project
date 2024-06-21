<template>
    <div class="review-area">
        <div class="review-box">
            <h1 v-if="$route.params.editFlag === 'false'">レビュー入力</h1>
            <h1 v-else>レビュー編集</h1>
            <label for="name">ニックネーム：</label>
            <input type="text" id="name" class="review-name" v-model="name">
            <label for="comment">コメント：</label>
            <textarea id="comment" class="review-comment" rows="10" cols="30" v-model="comment"></textarea>
            <div class="review-star">
                <span v-for="star in stars" :key="star.num" @click="reviewStar(star.num)" :class="{ star: star.check }">
                    {{ star.text }}
                </span>
            </div>
            <button class="review-btn" @click="reviewInput()" v-if="$route.params.editFlag === 'false'">投稿</button>
            <button class="review-btn" @click="reviewInput()" v-else>編集</button>
        </div>
        <RouterLink :to="'/detail/' + $route.params.itemId" style="margin-top: 50px">商品詳細に戻る</RouterLink>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

const totalStar = ref(0)
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

onMounted(() => {
    if(route.params.editFlag === 'true') {
        axios.post('/review/select',
            {
                itemId: route.params.itemId,
                userId: user.value.id
            }
        )
        .then(response => {
            name.value = response.data.name
            comment.value = response.data.comment
            reviewStar(response.data.star)
        })
    }
})

//評価ランクを表示
function reviewStar(num){
    totalStar.value = 0
    stars.value.forEach(star => {
        if(star.num <= num){
            star.check = true
            star.text = '★'
            totalStar.value ++
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
            star: totalStar.value,
            itemId: route.params.itemId,
            userId: user.value.id,
            name: name.value,
            comment: comment.value,
            editFlag: route.params.editFlag
        }
    )
    .then(() => {
        router.push('/detail/' + route.params.itemId)
    })
}
</script>
<style scoped>
.review-area, .review-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.review-box{
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.8);
    width: 30%;
    border-radius: 10px;
    margin-top: 50px;
}
.review-name{
    margin-bottom: 20px;
    border-radius: 5px;
}
.review-star{
    margin-top: 20px;
    font-size: 20px;
}
.star{
    color: rgba(237, 237, 10, 0.889);;
}
.review-btn{
    margin: 10px 0px;
}
</style>