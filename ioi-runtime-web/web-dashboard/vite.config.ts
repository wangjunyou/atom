import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

const path = require('path')

export default defineConfig({
    base: process.env.NODE_ENV === 'development' ? process.env.VITE_APP_DEV_WEB_URL : process.env.VITE_APP_PROD_WEB_URL,
    plugins: [vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    },
    build: {
        outDir: path.resolve(__dirname, 'web')
    }
})
