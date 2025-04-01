<template>
<div class="modal-area" @click="close">
    <img :src="'/image/' + itemImage" class="modal-image">
</div>
</template>
<script setup>
import { ref, watch } from 'vue';
const props = defineProps(['itemImage'])
const emit = defineEmits(['closeImage'])
const itemImage = ref(props.itemImage)

function close(){
    emit('closeImage')
}
watch(() => props.itemImage,(newVal)=>{
    itemImage.value = newVal;
})
</script>
<style scoped>
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
.modal-image{
    width: 65%;
    height: 80%;
}
</style>