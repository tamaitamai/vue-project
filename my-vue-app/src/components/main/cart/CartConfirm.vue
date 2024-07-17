<template>
    <div class="cart-area">
        <div class="cart-list">
            <h1>ショッピングカート</h1>
            <div v-for="cart in cartList" :key="cart.id" class="cart-box"> 
                <img :src="'/image/' + cart.image" class="cart-image" @click = "itemDetail(cart.itemId)">
                <div class="cart-info">
                    <div class="cart-info-top">
                        <div class="cart-name">{{ cart.name }}</div>
                        <div class="cart-price">￥{{ cart.price }}</div>
                    </div>
                    <div class="cart-info-bottom">
                        <select @change="countSelect($event,cart)" class="cart-count">
                            <option v-for="num in 50" :key="num" :value="num" :selected="num===cart.count">数量：{{ num }}</option>
                        </select>
                        <div class="cart-delete" @click="deleteCart(cart)">削除</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="cart-price-box">
            <div class="cart-total-price">小計({{ totalCount }}個の商品)</div>
            <div class="cart-total-price">：￥{{ totalPrice }}</div>
            <div class="cart-price-btn-box">
                 <button @click="orderInput" class="cart-price-btn">注文詳細確認</button>
            </div>
        </div>
    </div>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)
const cartList = ref('')
const totalPrice = ref(0)
const totalCount = ref(0)

onMounted(() => {
    countData()
})
// カート内の商品を削除
function deleteCart(cart){
    axios.post('/cart/delete',
        {
            id: cart.id,
            userId: user.value.id
        }
    )
    .then(response => {                
        cartList.value = response.data
        totalPrice.value -= cart.price * cart.count
        totalCount.value -= cart.count
    })  
}
// カート一覧を取得
function countData(){
    axios.post('/cart',
        {
            id: user.value.id
        }
    )
    .then(response => {
        cartList.value = response.data
        totalUpdate(response.data)
    })
}
//合計金額と合計購入数を更新
function totalUpdate(cartList){
    totalPrice.value = 0
    totalCount.value = 0
    cartList.forEach(cart => {
        totalPrice.value = totalPrice.value + cart.price * cart.count
        totalCount.value = totalCount.value + cart.count
    });
}
//商品詳細を表示
function itemDetail(id){
    router.push('/detail/' + id)
}
//カート内の商品数を更新
function countSelect(event,cart){
    axios.post('/cart/countUpdate',
        {
            id: cart.id,
            count: event.target.value,
            userId: user.value.id
        }
    )
    .then(response => {
        totalUpdate(response.data)
    })
}
// 注文詳細へ遷移
function orderInput(){
    router.push('/orderInput');
}
</script>
<style scoped>
.cart-area{
    display: flex;
}
.cart-list{
    display: flex;
    flex-direction: column;
    width: 80%
}
.cart-box{
    width: 90%;
    display: flex;
    border-top: 1px solid rgba(77, 74, 74, 0.19);    
}
.cart-image{
    width: 200px;
    height: 120px;
    padding: 10px;
}
.cart-image:hover{
    opacity: 0.7;
}
.cart-info{  
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
}
/* カート情報の上側 */
.cart-info-top{
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}
.cart-name{
    margin-right: 20px;
    font-size: 20px;
}
.cart-price{
    font-size: 20px;
    margin-right: 20px;
}
/* カート情報の下側 */
.cart-info-bottom{
    display: flex;
    align-items: center;
    margin-top: auto;    
}
.cart-count{
    border-radius: 5px;
    padding: 5px;
    background-color: rgba(180, 176, 176, 0.19);
    margin: 0px 20px 10px 0px;
}
.cart-delete:hover{
    color: red;
}

/* 価格情報 */
.cart-price-box{
    border: 1px solid rgba(77, 74, 74, 0.19);
    width: 20%;
    height: 300px;
    margin: 20px;
    padding: 20px;
    display: flex;
    flex-direction: column;
}
.cart-price-btn-box{
    display: flex;
    justify-content: center;
    flex-direction: column;
    margin-top: auto;
}
.cart-total-price{
    font-size: 30px;
}
.cart-price-btn{
    cursor: pointer;
    width: 90%;
    border: 1px solid rgba(77, 74, 74, 0.19);
    background-color: rgb(252 249 4 / 95%);
    font-size: 20px;
    border-radius: 50px;
}
.cart-price-btn:hover{
    opacity: 0.7;
}
</style>