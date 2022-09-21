import {
    createRouter,
    createWebHistory,
    NavigationGuardNext,
    RouteLocationNormalized
} from 'vue-router'

import routes from './routes'

// import NProgress from 'nprogress'
// import 'nprogress/nprogress.css'

const router = createRouter({
    history: createWebHistory(
        import.meta.env.MODE === 'production' ? 'atom/ui' : '/'
    ),
    routes
})

/*router.beforeEach(
    async (
        to: RouteLocationNormalized,
        from: RouteLocationNormalized,
        next: NavigationGuardNext
    ) => {
        // NProgress.start()


    }
)*/

export default router
