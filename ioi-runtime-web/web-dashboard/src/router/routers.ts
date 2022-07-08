import {RouteRecordRaw} from "vue-router";


const modules = import.meta.globEager('./modules/**/*.ts')

const routeModuleList: RouteRecordRaw[] = [];

Object.keys(modules).forEach((key: string) => {
    const mod = modules[key].default || {};
    const modList = Array.isArray(mod) ? [...mod] : [mod];
    routeModuleList.push(...modList);
})

const rootRoute: RouteRecordRaw = {
    name: 'root',
    path: '/',
    redirect: {name: 'login'}
    // component: () => import('@/views/login/index.vue')
    // component: () => import('@/views/home/index.vue')
}
const loginRoute: RouteRecordRaw = {
    name: 'login',
    path: '/login',
    component: () => import('@/views/login/index.vue')
}

const homeRoute: RouteRecordRaw = {
    name: 'home',
    path: '/home',
    component: () => import('@/views/home/index.vue')
}


const routes: RouteRecordRaw[] = [rootRoute, loginRoute, homeRoute]

export default routes