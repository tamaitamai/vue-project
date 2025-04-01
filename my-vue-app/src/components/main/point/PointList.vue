<template>
    <h1>ポイント履歴</h1>
    <div class="point-list">
        <div v-for="point in pointList" :key="point.id" class="point-box">
            <div>{{ formatDate(point.createDate) }}</div>
            <div class="point-use-box">
                <div v-if="point.point<0" class="use">利用</div>
                <div v-else class="get">獲得</div>
                <div>{{ point.point }}</div>
            </div>
        </div>
    </div>
</template>
<script setup>
import axios from 'axios';
import { computed, ref } from 'vue';
import { useStore } from 'vuex';

const store = new useStore();

const pointList = ref('');
const user = computed(()=>store.getters.getUserData);

axios.post('/point/list',
    {
        userId: user.value.id
    }
)
.then(response => {
    pointList.value = response.data
})

function formatDate(date) {
  const d = new Date(date);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  return `${year}年${month}月${day}日`;
}
</script>
<style src="../../../css/point/list.css"></style>