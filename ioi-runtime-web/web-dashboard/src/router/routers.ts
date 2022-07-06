import { RouteRecordRaw } from 'vue-router'

const modules = import.meta.globEager('./modules/**/*.ts')

const routeModuleList: RouteRecordRaw[] = []

Object.keys(modules).forEach((key: string) => {
  const mod = modules[key].default || {}
  const modList = Array.isArray(mod) ? [...mod] : [mod]
  routeModuleList.push(...modList)
})

const rootRoute: RouteRecordRaw = {
  name: 'root',
  path: '/',
  component: () => import('@/views/hello.vue')
}

const routes: RouteRecordRaw[] = [...routeModuleList, rootRoute]

export default routes
