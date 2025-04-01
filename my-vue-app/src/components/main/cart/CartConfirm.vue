<template>
    <div class="cart-main">
        <div class="cart-area">
            <!-- カート一覧 -->
            <div class="cart-list">
                <h1>ショッピングカート</h1>
                <div v-if="cartList.length === 0">カートに商品がありません</div>
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
                            <div class="cart-delete-btn" @click="deleteCart(cart)">削除</div>
                            <div style="margin: 0px 10px">|</div>
                            <div class="cart-after-btn" @click="afterClick(cart,false)">あとで買う</div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- あとで買う一覧 -->
            <h1>あとで買う一覧</h1>
            <div v-if="cartAfterList.length === 0">あとで買うに選択した商品がありません</div>
            <div class="cart-after-list">
                <div v-for="cart in cartAfterList" :key="cart.id" class="cart-after-box">
                    <img :src="'/image/' + cart.image" class="cart-image" @click = "itemDetail(cart.itemId)">
                    <RouterLink :to="'/detail/'+cart.itemId">{{ cart.name }}</RouterLink>
                    <div class="cart-after-price">￥{{ cart.price }}</div>
                    <div class="cart-return-btn-box">
                        <div class="cart-return-btn" @click="afterClick(cart,true)">もとに戻す</div>
                    </div>
                    <div class="cart-after-delete-btn" @click="deleteCart(cart)">削除</div>
                </div>
            </div>
        </div>
        <!-- 価格情報 -->
        <div class="cart-price-box">
            <div class="cart-total-price">小計({{ totalCount }}個の商品)</div>
            <div class="cart-total-price">：￥{{ totalPrice }}</div>
            <div>{{ totalPrice/100 }}ポイント獲得予定</div>
            <div>使用可能ポイント：{{ point }}</div>
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
const cartAfterList = ref('')
const totalPrice = ref(0)
const totalCount = ref(0)
const point = ref(0)

// ユーザーのポイント合計の確保
axios.post('/point',
    {
        userId: user.value.id
    }
)
.then(response=>{
    point.value = response.data
})

onMounted(() => {
    countData()
})
// カート一覧を確保
function cartListGet(response){
    cartList.value = response.filter(cart => cart.afterFlg === 0);
    cartAfterList.value = response.filter(cart => cart.afterFlg === 1);
}
// カート内の商品を削除
function deleteCart(cart){
    axios.post('/cart/delete',
        {
            id: cart.id,
            userId: user.value.id
        }
    )
    .then(response => {                
        cartListGet(response.data);
        if(cart.afterFlg === 0){
            totalPrice.value -= cart.price * cart.count
            totalCount.value -= cart.count
        }
    })  
}
// カート内商品の数を更新
function countData(){
    axios.post('/cart',
        {
            id: user.value.id
        }
    )
    .then(response => {
        cartListGet(response.data)
        totalUpdate(response.data)
    })
}
//合計金額と合計購入数を更新
function totalUpdate(cartList){
    totalPrice.value = 0
    totalCount.value = 0
    cartList.forEach(cart => {
        if(cart.afterFlg === 0){
            totalPrice.value = totalPrice.value + cart.price * cart.count
            totalCount.value = totalCount.value + cart.count
        }
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
// あとで買うに追加、カートに戻す
function afterClick(cart,exists){
    axios.post('/cart/after',{
        id: cart.id,
        userId: user.value.id,
        afterExists: exists
    })
    .then(response=>{
        cartListGet(response.data)
        totalUpdate(response.data)
    })
}
</script>
<style src="../../../css/cart/confirm.css" scoped></style>