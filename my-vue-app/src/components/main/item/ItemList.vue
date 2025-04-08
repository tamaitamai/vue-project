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

import CartList from '../cart/CartList.vue'
import CartAddModal from '../modal/CartAddModal.vue';
import StarView from '../star/StarView.vue'

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
    console.log('item状態: '+process.env.VUE_APP_API_BASE_URL)
    axios.get(process.env.VUE_APP_API_BASE_URL+'/item')
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
<style src="../../../css/item/list.css" scoped></style>