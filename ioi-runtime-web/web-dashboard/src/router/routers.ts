import {RouteRecordRaw} from "vue-router";


const modules = import.meta.globEager('./modules/**/*.ts')

const routeModuleList: RouteRecordRaw[] = [];

Object.keys(modules).forEach((key: string) => {
  const mod = modules[key].default || {};
  const modList = Array.isArray(mod) ? [...mod] : [mod];
  routeModuleList.push(...modList);
})

const rootRoute: RouteRecordRaw = {
  name: 'login',
  path: '/',
  component: () => import('@/views/login/index.vue')
  // component: () => import('@/views/home/index.vue')
}


const routes: RouteRecordRaw[] = [rootRoute]

export default routes