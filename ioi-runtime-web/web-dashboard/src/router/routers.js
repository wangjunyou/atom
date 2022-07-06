const modules = import.meta.globEager('./modules/**/*.ts');
const routeModuleList = [];
Object.keys(modules).forEach((key) => {
    const mod = modules[key].default || {};
    const modList = Array.isArray(mod) ? [...mod] : [mod];
    routeModuleList.push(...modList);
});
const rootRoute = {
    name: 'root',
    path: '/',
    component: () => import('@/views/root.vue')
};
const routes = [...routeModuleList, rootRoute];
export default routes;
//# sourceMappingURL=routers.js.map