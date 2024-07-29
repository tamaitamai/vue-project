<template>
    <div class="history-area">
        <h1>注文履歴</h1>
        <div class="history-list">
            <div v-for="history in historyList" :key="history.id" class="history-box">
                <div class="history-top">
                    <div class="top-left">
                        <div>
                            <div>注文日</div>
                            <div>{{ history.paymentDate }}</div>
                        </div>
                        <div>
                            <div style="margin-left: 20px;">
                                合計:
                                <div>{{ history.price * history.count }}円</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="history-bottom">
                    <div class="bottom-left">
                        <RouterLink :to="'/detail/' + history.itemId" class="history-name"><img :src="'/image/' + history.image" class="history-image"></RouterLink>
                        <RouterLink :to="'/detail/' + history.itemId" class="history-name">{{ history.name }}</RouterLink>
                    </div>
                    <div class="bottom-right">
                        <button class="review-btn" @click="reviewUrl(history.itemId)">商品レビューを書く</button>
                        <!-- <button class="review-btn" @click="reviewUrl(history.itemId)" v-else>商品レビューを書く</button> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const store = new useStore()
const router = new useRouter()

const historyList = ref('')
const user = computed(() => store.getters.getUserData)

onMounted(()=>{
    axios.post('/history',
        {
            userId: user.value.id
        }
    )
    .then(response=>{
        historyList.value = response.data
    })
})
// function reviewExists(itemId){
//     const exists = ref(false)
//     axios.post('/review',
//         {
//             itemId: itemId,
//             userId: user.value.id
//         }
//     )
//     .then(response => {
//         const reviewList = ref(response.data)
//         for(let i=0; i<reviewList.value.length;i++){
//             if(reviewList.value[0].userId == user.value.id){
//                 exists.value = true;
//                 break;
//             }
//         }
//         return exists;
//     })
// }
function reviewUrl(itemId){
    axios.post('/review',
        {
            itemId: itemId,
            userId: user.value.id
        }
    )
    .then(response => {
        const reviewList = ref(response.data)
        const editFlg = ref(false)
        reviewList.value.forEach(review => {
            if(review.userId === user.value.id){
                editFlg.value = true
            }
        });
        router.push('/reviewInput/' + itemId + "/" + editFlg.value)
    })
}
</script>
<style src="../../../css/history/history.css"></style>