import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'
import _ from 'lodash'

const requestConfig: AxiosRequestConfig = {
  baseURL:
    import.meta.env.MODE === 'development'
      ? '/atom/api'
      : import.meta.env.VITE_APP_PROD_WEB_URL + '/atom/api',
  timeout: 15000,
  transformRequest: (params) => {
    if (_.isPlainObject(params)) {
      // return JSON.stringify()
    } else {
      return params
    }
  }
}

const service = axios.create(requestConfig)

service.interceptors.request.use((config: AxiosRequestConfig) => {
  config.headers
})

service.interceptors.response.use((resp: AxiosResponse) => {
  resp.data
})

export { service as axios }
