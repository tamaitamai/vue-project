import { createRouter,createWebHistory } from 'vue-router'
import InsertView from '../components/user/InsertView.vue'
import LoginView from '../components/user/LoginView.vue'
import HelloView from '../components/HelloWorld.vue'
const router = new createRouter({
    history: createWebHistory(),
    routes: [
    {
        path: '/',
        name: 'hello',
        component: HelloView
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
    }
]
})
export default router