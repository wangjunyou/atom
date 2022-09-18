import { createApp } from 'vue'
import App from './App'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import * as echarts from 'echarts'
// import i18n from "@/locales"

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

app.config.globalProperties.echarts = echarts

app.use(pinia)
// views.use(i18n)
app.mount('#views')
