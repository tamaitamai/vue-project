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
                        <button class="review-btn" @click="reviewUrl(history.itemId, false)">商品レビューを書く</button>
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
function reviewUrl(itemId, editFlg){
    router.push('/reviewInput/' + itemId + "/" + editFlg)
}
</script>
<style scoped>
.history-area,.history-list{
    display: flex;
    justify-content: center;
    align-items: center;    
    flex-direction: column;
}
.history-list{
    width: 80%;
}
.history-box{
    display: flex;
    flex-direction: column;
    width: 80%;
    border: 1px solid rgba(77, 74, 74, 0.19);
    border-radius: 10px;
    margin-bottom: 20px;
}
.history-top{
    border-bottom: 1px solid rgba(77, 74, 74, 0.19);
    background-color: rgba(180, 176, 176, 0.19);
    padding: 20px;
}
.top-left{
    display: flex;
    float: left;
}
.history-bottom{
    padding: 10px;
}
.bottom-left{
    display: flex;
    float: left;
}
.bottom-right{
    float: right;
    margin: 10px;
}
.history-image{
    width: 180px;
    height: 100px;
}
.history-name{
    font-size: 20px;
    margin-left: 20px;
}
.review-btn{
    padding: 5px 30px;
    font-size: 15px;
    background-color: white;
    border: 1px solid rgba(77, 74, 74, 0.19);
    border-radius: 5px;
    box-shadow: 2px 2px 7px rgba(0, 0, 0, 0.2);
}
.review-btn:hover{
    background-color: rgba(234, 224, 224, 0.2)
}
</style>