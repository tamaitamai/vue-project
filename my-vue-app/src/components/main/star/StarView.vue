<template>
    <div class="review-star">
        <span class="before-star" v-for="star in beforeStar" :key="star">★</span>
        <span class="last-star" v-for="star in lastStar" :key="star" :style="{ '--star-width': starWidth }">★</span>
        <span class="after-star" v-for="star in afterStar" :key="star">★</span>
    </div>
</template>
<script setup>
import store from '@/store';
import axios from 'axios';
import { computed, onMounted, ref, watch } from 'vue';

const props = defineProps(['itemId'])
const emit = defineEmits(['starAverage','reviewTotalCount'])

const itemId = ref(props.itemId)
const starMap = new Map()
const reviewTotalCount = ref(0)
const reviewTotal = ref(0)
const starAverage = ref(0)
const beforeStar = ref(0)
const lastStar = ref(0)
const afterStar = ref(0)
const starWidth = ref('100%')
const starCounts = ref([
    { stars: 1, count: 0, border: 0},
    { stars: 2, count: 0, border: 0},
    { stars: 3, count: 0, border: 0},
    { stars: 4, count: 0, border: 0},
    { stars: 5, count: 0, border: 0}
])
const user = computed(() => store.getters.getUserData)

function starAverageView(){
    const userId = ref('')
    if(user.value == null){
        userId.value = 0
    }else{
        userId.value = user.value.id
    }
    axios.post('/review',
        {
            itemId: itemId.value,
            userId: userId.value
        }
    )
    .then(response=>{
        response.data.forEach(review => {
            if(!starMap.has(review.star)){
                starMap.set(review.star, 1)
            }else{
                starMap.set(review.star, starMap.get(review.star) + 1)
            }
            reviewTotalCount.value++;
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
        emit('starAverage',starAverage.value)
        emit('reviewTotalCount',reviewTotalCount.value)
    })
}

onMounted(()=>{
    starAverageView()
})

watch(() => props.itemId, (newVal) => {
  itemId.value = newVal;
  starAverageView()
});


</script>
<style scoped>
/* レビュー平均の☆の表示 */
.review-star{
    font-size: 20px;
}
.before-star{
    /* color: rgba(237, 237, 10, 0.889); */
    color: orange;
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
    /* color: rgba(237, 237, 10, 0.889); */
    color: orange;
}
</style>