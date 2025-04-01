<template>
    <div class="review-area">
        <!-- レビュー評価の表示 -->
        <div class="review-star-box">
            <h2>カスタマーレビュー</h2>
            <!-- <h2>評価：{{ starAverage }}
                <span class="before-star" v-for="star in beforeStar" :key="star">★</span>
                <span class="last-star" v-for="star in lastStar" :key="star" :style="{ '--star-width': starWidth }">★</span>
                <span class="after-star" v-for="star in afterStar" :key="star">★</span>
            </h2> -->
            <div class="item-star-box">
                <StarView :itemId="itemId" @star-average="starAverageView"/>
                <div class="item-star-average">5つのうち{{ starAverage }}つ</div>
            </div>
            <div v-for="starCount in starCounts" :key="starCount" class="star-box">
                <div class="stars">
                    <span v-for="star in starCount.stars" :key="star" class="review-star">
                        ★
                    </span>
                </div>
                <span class="star-border" :style="{'background-image': starBorderColor + starCount.border + '%, transparent ' + starCount.border + '%)'}"></span>
                <div class="star-count">{{ starCount.count }}人</div>
            </div>
            <div>{{ reviewTotal }}件の評価</div>

            <div v-if = "reviewExists" class="review-input-btn" @click="reviewUrl(false)">商品レビューを書く ></div>
            <div v-else class="review-input-btn" @click="reviewUrl(true)">レビューを編集</div>
        </div>
        <!-- レビュー一覧 -->
        <div class="review-list">
            <div v-for="review in reviewList" :key = "review.id" class="review-box">
                <div>👤{{ review.name }}さん</div>
                <div>
                    <span v-for="star in review.star" :key="star" class="review-star">
                        ★
                    </span>
                </div>
                <div>{{ review.comment }}</div>
                <div style="margin-top: 10px;">{{ review.goodTotal }}人が参考になったと言ってます</div>
                <div v-if = "user !== null">
                    <p :class="review.reviewGood.userId !== user.id ? {'review-good': true} : {'review-good-check': true}"
                    @click="reviewGood(review)">参考になった</p>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

import StarView from '../star/StarView.vue';

// const emit = defineEmits(['averageReview'])
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
// const beforeStar = ref(0)
// const lastStar = ref(0)
// const afterStar = ref(0)
const starCounts = ref([
    { stars: 1, count: 0, border: 0},
    { stars: 2, count: 0, border: 0},
    { stars: 3, count: 0, border: 0},
    { stars: 4, count: 0, border: 0},
    { stars: 5, count: 0, border: 0}
])
// const starWidth = ref('100%');

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
                // starAverage.value += starMap.get(key) * key
            }
            for(const key of starMap.keys()){
                starCounts.value[key-1].border = starMap.get(key)/reviewTotal.value * 100
            }
            // if(reviewTotal.value !== 0){
            //     starAverage.value = Math.round(starAverage.value/reviewTotal.value * 10)/10
            //     beforeStar.value = Math.floor(starAverage.value)
            //     lastStar.value = Math.ceil(starAverage.value) - beforeStar.value
            //     afterStar.value = 5 - beforeStar.value - lastStar.value                
                
            //     const starRange = ref(starAverage.value - beforeStar.value)
            //     if(starRange.value === 0){
            //         starWidth.value = '100%'
            //     }else if(starRange.value < 0.5){
            //         starWidth.value = 40 + starRange.value * 10 + '%'
            //     }else if(starRange.value > 0.5){
            //         starWidth.value = 50 + starRange.value * 10 + '%'
            //     }else if(starRange.value === 0.5){
            //         starWidth.value = 50 + '%'
            //     }
            //     emit('averageReview',starAverage.value, beforeStar.value, lastStar.value, afterStar.value, starWidth.value)
            // }
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
// レビューの平均値
function starAverageView(average){
    starAverage.value = average;
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
<style src="../../../css/review/list.css"></style>