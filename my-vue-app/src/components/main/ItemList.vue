<template>
    <div class="container">
        <div class="item-area">
            <!-- <h1>アイドル風景写真一覧</h1> -->
            <div class="item-list">
                <div v-for = "item in itemList" :key = "item.id" class="item-box" @click = "itemDetail(item.id)">
                    <div class="item-image-area">
                        <img :src="'/image/' + item.image" class="item-image">
                    </div>
                    <div class="item-info-area">
                        <div style="font-size: 20px; margin-top: 10px;">{{ item.name }}</div>
                        <div>{{ item.comment }}</div>
                        <div class="item-star-box">
                            <StarView :itemId="item.id" @review-total-count="reviewTotalCountView"/>
                            <div class="item-star-average" v-if="item.totalReviewCount != 0">{{ item.totalReviewCount }}</div>
                        </div>
                        <div style="font-size: 30px;">{{ '￥'+item.price }}</div>
                        <select @change.stop="countUpdate" class="item-count" @click.stop>
                            <option  v-for="num in 50" :key="num" :value="num">{{num}}</option>
                        </select>
                    </div>
                    <div class="item-btn-area" @click.stop>
                        <button class="item-btn" @click.stop="itemAdd(item)">カートに追加</button>
                    </div>
                </div>
            </div>
        </div>
        <CartList v-show="false" :cartList="cartList" :user="user" :totalPrice="totalPrice" 
        @cartUpdate = "cartUpdate" @total-price-delete-update = "totalPriceDeleteUpdate" @cart-list-view = "cartListView"/>
    </div>
    <CartAddModal v-show="modalShow" @close-modal="closeModal"/>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

import CartList from './cart/CartList.vue'
import CartAddModal from './modal/CartAddModal.vue';
import StarView from './star/StarView.vue';

const itemList = ref('')
const cartList = ref('')
const totalPrice = ref(0)
const count = ref(1)
const reviewTotalCount = ref(0)
const modalShow = ref(false)

const router = useRouter()
const store = useStore()
const user = computed(() => store.getters.getUserData)
const searchItemList = computed(() => store.getters.getItemListData)

onMounted(() => {
    axios.get('/item')
    .then(response => {
        if(searchItemList.value === null){
            itemList.value = response.data
        }else{
            itemList.value = searchItemList.value
        }
    })
    cartListView()
})
//商品を検索したときに一覧の更新
watch(searchItemList, ()=>{
    itemList.value = searchItemList.value
})

function cartListView(){
    if(user.value !== null){
        axios.post('/cart',
            {
                id: user.value.id
            }
        )
        .then(response => {
            cartList.value = response.data
            totalPrice.value = 0
            cartList.value.forEach(cart => {
                totalPrice.value = totalPrice.value + cart.price * cart.count
            });
        })
    }
}
//商品詳細へ移動
function itemDetail(id){
    router.push('/detail/' + id)
}
//商品をカートに追加
function itemAdd(item){
    axios.post('/cart/add',
        {
            userId: user.value.id,
            itemId: item.id,
            name: item.name,
            image: item.image,
            price: item.price,   
            count: count.value         
        }
    )
    .then(response => {
        cartList.value = response.data
        totalPrice.value = totalPrice.value + item.price * count.value
        modalShow.value = true;
    })
}
function cartUpdate(response){
    cartList.value = response
}
function totalPriceDeleteUpdate(price, count){
    totalPrice.value = totalPrice.value - price * count
}
// 商品の購入数の更新
function countUpdate(event){
    count.value = event.target.value
}
// モーダルを閉じる
function closeModal(){
    modalShow.value = false
}
// レビューした合計人数
function reviewTotalCountView(totalCount){
    reviewTotalCount.value = totalCount
}
</script>
<style scoped>
.container{
    display: flex;
}
.item-area{
    display: flex;
    align-items: center;
    flex-direction: column;
}
.item-list{
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}
.item-title{
    text-align: center;
}
.item-box{
    display: flex;
    flex-direction: column;
    border: 1px solid rgba(77, 74, 74, 0.19);
    border-radius: 5px;
    width: 20%;
    margin: 20px 15px;
    cursor: pointer;
}
/* 商品画像と情報 */
.item-image-area{
    width: 100%;
    display: flex;
    justify-content: center;
    padding: 20px 0px;
    background-color: rgba(180, 176, 176, 0.19);
}
.item-image{
    width: 90%;
    height: 100%;
}
.item-info-area{
    margin-left: 20px;
}
.item-star-box{
    display: flex;
    align-items: center;
}
.item-star-average{
    margin-left: 5px;
}
.item-count{
    font-size: 20px;
    margin: 10px 0px;
    padding: 2px;
    cursor: pointer;
}
.item-count:hover{
    opacity: 0.5;
}
/* 商品追加ボタン */
.item-btn-area{
    display: flex;
    justify-content: center;
    width: 100%;
    cursor: default;
    margin-top: auto;
}
.item-btn{
    cursor: pointer;
    width: 70%;
    border: 1px solid rgba(77, 74, 74, 0.19);
    background-color: rgb(252 249 4 / 95%);
    margin: 5px 0px;
    font-size: 20px;
    border-radius: 50px;
    margin-bottom: 20px;
}
.item-btn:hover{
    opacity: 0.7;
}
</style>
