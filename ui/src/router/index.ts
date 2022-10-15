import {
    createRouter,
    createWebHistory,
    NavigationGuardNext,
    RouteLocationNormalized
} from 'vue-router'

import routes from './routes'

import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const router = createRouter({
    history: createWebHistory(
        import.meta.env.MODE === 'production' ? 'atom/ui' : '/'
    ),
    routes
})
/* weidenglu tiaozhuan */
router.beforeEach(
    async (
        to: RouteLocationNormalized,
        from: RouteLocationNormalized,
        next: NavigationGuardNext
    ) => {
        NProgress.start()
        if (to.name === undefined)
            next({name: 'login'})
        else
            next()
    }
)
router.afterEach(() => {
    NProgress.done()
})

export default router
