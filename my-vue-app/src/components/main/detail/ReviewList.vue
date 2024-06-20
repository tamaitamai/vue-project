<template>
    <h1>レビュー</h1>
    <div v-if = "reviewExists" class="review-input-btn" @click="reviewUrl(false)">商品レビューを書く ></div>
    <div v-else class="review-input-btn" @click="reviewUrl(true)">レビューを編集</div>
    <div v-for="review in reviewList" :key = "review.id" class="review-box">
        <p>{{ review.name }}さん</p>
        <p>{{ review.comment }}</p>
        <div>
            <span v-for="star in review.star" :key="star" class="review-star">
                ★
            </span>
        </div>
        <p>{{ review.goodTotal }}人が参考になったと言ってます</p>
        <div v-if = "user !== null">
            <p :class="review.reviewGood.userId !== user.id ? {'review-good': true} : {'review-good-check': true}"
            @click="reviewGood(review)">参考になった</p>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
const props = defineProps(['itemId']);
const router = useRouter()
const store = useStore()

const itemId = ref(props.itemId)
const reviewList = ref('')
const user = computed(() => store.getters.getUserData)
const reviewExists = ref(true)

//レビュー一覧をデータベースから取得
function reviewSubmit(itemId){
    const userId = ref('')
    if(user.value == null){
        userId.value = 0
    }else{
        userId.value = user.value.id
    }
    axios.post('/review',
        {
            itemId: itemId,
            userId: userId.value
        }
    )
    .then(response=>{
        reviewList.value = response.data
        if(user.value !== null) {
            reviewList.value.forEach(review => {
                if(review.userId === user.value.id) {
                    reviewExists.value = false
                }
            });
        }
    })
}
//レビュー入力画面へ移動
function reviewUrl(editFlag){
    user.value !== null ? 
        router.push('/reviewInput/' + itemId.value + '/' + editFlag)
        :router.push('/login')
}
//レビューにいいねをつける
function reviewGood(review){ 
    axios.post('/review/goodUpdate',
        {
            id: review.id,
            userId : user.value.id,
            itemId: review.itemId
        }
    )
    .then(() => {
        reviewSubmit(itemId.value)
    })
}
//レビュー一覧を確保
onMounted(() => {
    reviewSubmit(itemId.value)
})
watch(() => props.itemId, (newVal) => {
  itemId.value = newVal;
  reviewSubmit(itemId.value)
});
</script>
<style scoped>
.review-input-btn{
    padding: 10px;
    border: 1px solid black;
    width: 200px;
    text-align: center;
    border-radius: 5px;
}
.review-input-btn:hover, .review-good:hover{
    border: 1px solid red
}
.review-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    margin-top: 20px;
    width: 50%;
}
.review-star{
    color: rgba(237, 237, 10, 0.889);
    font-size: 20px;
}
.review-good{
    border: 1px solid black;
    padding: 5px;
}
.review-good-check{
    border: 1px solid red;
    padding: 5px;
}
</style>