<template>
<div class="order-area">
    <div class="order-box">
        <!-- 送り先の表示 -->
        <div class="address-box">
            <div class="address-top-box">
                <div class="user-name">お届け先：{{ user.name }}さん</div>
                <div class="user-address">{{ user.address }}</div>
            </div>
            <div class="address-bottom-box">
                <div @click="otamesiClick">変更</div>
            </div>
        </div>

        <!-- カート一覧 -->
        <div class="cart-list">
            <div v-for="cart in cartList" :key="cart.id">
                <div class="cart-box" v-if="cart.afterFlg == 0">
                    <div>到着予定：{{ orderDateMap.has(cart.id) ? orderDateMap.get(cart.id) : formatNextDate }}</div>
                    <div class="cart-info-area">
                        <!-- カートの情報 -->
                        <div class="cart-info-box">
                            <div class="cart-info-left-box">
                                <img :src="'image/'+cart.image" class="cart-image">
                                <div>数量：{{ cart.count }}</div>
                            </div>
                            <div class="cart-info-right-box">
                                <div>{{ cart.name }}</div>
                                <div>￥{{ cart.price }}</div>
                            </div>
                        </div>
                        <!-- 送付日の選択 -->
                        <div class="order-date-box">
                            <div>お急ぎ便：</div>
                            <div @click.stop="orderDateMap.set(cart.id,formatNextDate)">
                                <input type="radio" :id="'next-date'+cart.id" :name="'orderDate'+cart.id" :value="formatNextDate" checked>
                                <label :for="'next-date'+cart.id">{{ formatNextDate }}</label>
                            </div>
                            <br>
                            <div @click.stop="selectDateOpen(cart)">
                                <input type="radio" :id="'select-date'+cart.id" :name="'orderDate'+cart.id">
                                <label :for="'select-date'+cart.id">時間指定</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 金額の表示 -->
    <div class="order-price-box">
        <div class="order-total-price">小計({{ totalCount }}個の商品)：</div>
        <div class="order-total-price">￥{{ totalPrice }}</div>
        <div class="order-price-btn-box">
            <button class="order-price-btn" @click="payment">購入確定</button>
        </div>
    </div>
</div>

<!-- 日付選択の表示 -->
<div class="select-date-area" v-show="selectDateShow">
    <div class="select-date-box">
        <h2>日付選択</h2>
        <input type="date" v-model="orderDateValue">
        <button @click="selectDateConfirm">確定</button>
    </div>
</div>
</template>
<script setup>
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const router = useRouter()
const store = useStore()

const user = computed(() => store.getters.getUserData)
const cartList = ref('')
const totalPrice = ref(0)
const totalCount = ref(0)
const selectDateShow = ref(false)
const orderCartId = ref(0)
const orderDateMap = ref(new Map())

// 明日の日付の表示
const today = ref(new Date())
const nextDay = ref(new Date(today.value))
nextDay.value.setDate(today.value.getDate() + 1)
const year = ref(nextDay.value.getFullYear());
const month = ref((nextDay.value.getMonth() + 1).toString().padStart(2, '0'));
const day = ref(nextDay.value.getDate().toString().padStart(2,'0'));
const formatNextDate = ref(year.value+'年'+month.value+'月'+day.value+'日')
const nextDateValue = ref(year.value+'-'+month.value+'-'+day.value)
const orderDateValue = ref('')
orderDateValue.value = nextDateValue.value

// カート一覧の取得
onMounted(()=>{
    axios.post('/cart',
        {
            id: user.value.id
        }
    )
    .then(response => {
        cartList.value = response.data
        cartList.value.forEach(cart => {
            totalPrice.value = totalPrice.value + cart.price * cart.count
            totalCount.value = totalCount.value + cart.count
        });
    })
})
// 商品を購入
function payment(){
    axios.post('/cart/payment',
        {
            userId: user.value.id,
            orderDateMap: Object.fromEntries(orderDateMap.value),
            address: user.value.address
        }
    )
    .then(()=>{
        router.push('/buyPage');
    })
}
// 到着日選択画面を開く
function selectDateOpen(cart){
    orderCartId.value = cart.id
    selectDateShow.value = true
}
// 到着日を選択
function selectDateConfirm(){
    selectDateShow.value = false
    const orderDates = orderDateValue.value.split('-')
    const selectOrderDate = orderDates[0] + '年' + orderDates[1] + '月' + orderDates[2] + '日'
    orderDateMap.value.set(orderCartId.value,selectOrderDate)
}
</script>
<style src="../../../css/order/order.css"></style>