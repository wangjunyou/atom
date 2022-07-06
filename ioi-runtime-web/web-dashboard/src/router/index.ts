import { createRouter, createWebHistory } from 'vue-router'
import routes from './routers'

//创建router
const router = createRouter({
  history: createWebHistory(''),
  strict: true,
  routes
})

// router.beforeEach(async (to, from, next) => {
//     // to.
// })
//
// router.afterEach(() => {
//
// })

export default router
