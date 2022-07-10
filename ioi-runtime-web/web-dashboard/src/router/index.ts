import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import routes from './routers'

//创建router
const router = createRouter({
  history: createWebHashHistory(''),
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
