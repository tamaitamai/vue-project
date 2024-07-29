import { createRouter,createWebHistory } from 'vue-router'
import InsertView from '../components/user/InsertView.vue'
import LoginView from '../components/user/LoginView.vue'
import HomeView from '../components/main/HomeView.vue'
import ItemList from '../components/main/ItemList.vue'
import ItemDetail from '@/components/main/detail/ItemDetail.vue'
import CartConfirm from '@/components/main/cart/CartConfirm.vue'
import ReviewInput from '@/components/main/detail/ReviewInput.vue'
import HistoryItem from '@/components/main/history/HistoryItem.vue'
import BuyPage from '@/components/main/cart/BuyPage.vue'
import OrderInput from '@/components/main/order/OrderInput.vue'
import UserEdit from '@/components/user/UserEdit.vue'
import FavoriteView from '@/components/main/favorite/FavoriteView.vue'
const router = new createRouter({
    history: createWebHistory(),
    routes: [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/insert',
        name: 'insert',
        component: InsertView
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },
    {
        path: '/item',
        name: 'item',
        component: ItemList
    },
    {
        path: '/detail/:id',
        name: 'detail',
        component: ItemDetail
    },
    {
        path: '/cartConfirm',
        name: 'cartConfirm',
        component: CartConfirm
    },
    {
        path: '/reviewInput/:itemId/:editFlag',
        name: 'reviewInput',
        component: ReviewInput
    },
    {
        path: '/history',
        name: 'history',
        component: HistoryItem
    },
    {
        path: '/buyPage',
        name: 'buyPage',
        component: BuyPage
    },
    {
        path: '/orderInput',
        name: 'orderInput',
        component: OrderInput
    },
    {
        path: '/userEdit',
        name: 'userEdit',
        component: UserEdit
    },
    {
        path: '/favorite',
        name: 'favorite',
        component: FavoriteView
    },
]
})
export default router