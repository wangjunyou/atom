import { createApp } from 'vue'
import App from './App'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import '@/assets/styles/default.scss'
import * as echarts from 'echarts'
import 'echarts/theme/macarons'
import 'echarts/theme/dark-bold'
import router from '@/router'
import i18n from '@/locales'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

app.config.globalProperties.echarts = echarts

app.use(router)
app.use(pinia)
app.use(i18n)
app.mount('#app')
