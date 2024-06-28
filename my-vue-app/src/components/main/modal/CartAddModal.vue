<template>
    <div class="modal-area">
        <div class="modal-box">
            <h1>商品をカートに入れました</h1>
            <button @click="closeModal">閉じる</button>
        </div>
    </div>
</template>
<script setup>
const emit = defineEmits(['closeModal']);
function closeModal(){
    emit('closeModal');
}
</script>
<style>
.modal-area,.modal-box{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.modal-area{
    position: fixed;
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    background-color: rgba(0, 0, 0, 0.8);
}
.modal-box{
    background-color: white;
    border-radius: 10px;
    width: 30%;
    height: 50%;
}
</style>
