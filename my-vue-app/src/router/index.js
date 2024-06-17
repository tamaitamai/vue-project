import { createRouter,createWebHistory } from 'vue-router'
import InsertView from '../components/user/InsertView.vue'
import LoginView from '../components/user/LoginView.vue'
import HomeView from '../components/main/HomeView.vue'
import ItemList from '../components/main/ItemList.vue'
import ItemDetail from '@/components/main/detail/ItemDetail.vue'
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
    }
]
})
export default router