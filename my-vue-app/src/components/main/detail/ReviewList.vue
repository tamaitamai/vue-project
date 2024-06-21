<template>
    <h1>レビュー</h1>
    <h2>評価：{{ starAverage }}
        <span class="before-star" v-for="star in beforeStar" :key="star">★</span>
        <span class="last-star" v-for="star in lastStar" :key="star" :style="{ '--star-width': starWidth }">★</span>
        <span class="after-star" v-for="star in afterStar" :key="star">★</span>
    </h2>
    <div v-for="starCount in starCounts" :key="starCount" class="star-box">
        <div class="stars">
            <span v-for="star in starCount.stars" :key="star" class="review-star">
                ★
            </span>
        </div>
        <span class="star-border" :style="{'background-image': starBorderColor + starCount.border + '%, transparent ' + starCount.border + '%)'}"></span>
        <div class="star-count">{{ starCount.count }}人</div>
    </div>
    <div>{{ reviewTotal }}評価</div>

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
const starBorderColor = ref('linear-gradient(to right, rgb(220, 28, 28) ')

const starMap = new Map()
const reviewTotal = ref(0)
const starAverage = ref(0)
const beforeStar = ref(0)
const lastStar = ref(0)
const afterStar = ref(0)
const starCounts = ref([
    { stars: 1, count: 0, border: 0},
    { stars: 2, count: 0, border: 0},
    { stars: 3, count: 0, border: 0},
    { stars: 4, count: 0, border: 0},
    { stars: 5, count: 0, border: 0}
])
const starWidth = ref('100%');

//レビュー一覧をデータベースから取得
function reviewSubmit(itemId, starCountFlag){
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
        if(starCountFlag){
            reviewList.value.forEach(review => {
                if(!starMap.has(review.star)){
                    starMap.set(review.star, 1)
                }else{
                    starMap.set(review.star, starMap.get(review.star) + 1)
                }
            })
            for(const key of starMap.keys()){
                starCounts.value[key-1].count = starMap.get(key)
                reviewTotal.value += starMap.get(key)
                starAverage.value += starMap.get(key) * key
            }
            for(const key of starMap.keys()){
                starCounts.value[key-1].border = starMap.get(key)/reviewTotal.value * 100
            }
            if(reviewTotal.value !== 0){
                starAverage.value = Math.round(starAverage.value/reviewTotal.value * 10)/10
                beforeStar.value = Math.floor(starAverage.value)
                lastStar.value = Math.ceil(starAverage.value) - beforeStar.value
                afterStar.value = 5 - beforeStar.value - lastStar.value                
                
                const starRange = ref(starAverage.value - beforeStar.value)
                if(starRange.value === 0){
                    starWidth.value = '100%'
                }else if(starRange.value < 0.5){
                    starWidth.value = 40 + starRange.value * 10 + '%'
                }else if(starRange.value > 0.5){
                    starWidth.value = 50 + starRange.value * 10 + '%'
                }else if(starRange.value === 0.5){
                    starWidth.value = 50 + '%'
                }
            }
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
        reviewSubmit(itemId.value, false)
    })
}
//レビュー一覧を確保
onMounted(() => {
    reviewSubmit(itemId.value, true)
})
watch(() => props.itemId, (newVal) => {
  itemId.value = newVal;
  reviewSubmit(itemId.value, true)
});
</script>
<style scoped>
.before-star{
    color: rgba(237, 237, 10, 0.889);
}
.after-star{
    color: lightgray;
}
.last-star{
    display: inline-block;
    position: relative;
    color: lightgray;
}
.last-star::before {
    content: '★';
    position: absolute;
    left: 0;
    top: 0;
    width: var(--star-width);
    overflow: hidden;
    color: rgba(237, 237, 10, 0.889);
}
.star-box{
    display: flex;
    align-items: center;
    width: 400px;
}
.stars {
    white-space: nowrap;
    flex-grow: 1;    
}
.star-border{
    border: 1px solid black;
    width: 200px;
    height: 10px;
    border-radius: 5px;
    margin-right: 10px;
    /* background-image: linear-gradient(to right, rgb(220, 28, 28) 50%, transparent 50%); */
}
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