<template>
    <div class="cart-list">
        <h2>カートの中身</h2>
        <div v-for = "item in itemList" :key = "item.id" class="cart-box">
            <p>{{ item.name }}</p>
            <img :src="'/image/' + item.image" class="cart-image" @click = "itemDetail(item.id)">
            <p>{{ item.price + '円' }}</p>
        </div>
    </div>
</template>
<script setup>
defineProps(['itemList'])
</script>
<style scoped>
.cart-image{
    width: 80%;
    height: 80%;
    margin-bottom: 20px;
}
.cart-list{
    display: flex;
    align-items: center;
    flex-direction: column;
    border: 1px solid black;
    border-radius: 10px;
    margin-top: 20px;
}
.cart-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
</style>