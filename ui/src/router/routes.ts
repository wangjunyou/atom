import { Component } from 'vue'
import { RouteRecordRaw } from 'vue-router'
import utils from '@/utils'
import demo from './modules/demo'

const modules = import.meta.glob('/src/views/**/**.tsx')
const components: { [key: string]: Component } = utils.mapping(modules)
const loginPage: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'home',
    component: components['home'],
    meta: {
      title: 'home'
    }
  },
  {
    path: '/login',
    name: 'login',
    component: components['login'],
    meta: {
      auth: []
    }
  },
  demo
]

const routes: RouteRecordRaw[] = [...loginPage]

export default routes
