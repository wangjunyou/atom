import axios, { AxiosRequestConfig, AxiosResponse } from 'axios'
import _ from 'lodash'
import qs from 'qs'

const requestConfig: AxiosRequestConfig = {
  baseURL:
    import.meta.env.MODE === 'development'
      ? import.meta.env.VITE_APP_DEV_WEB_URL + '/atom/api'
      : import.meta.env.VITE_APP_PROD_WEB_URL + '/atom/api',
  timeout: 15000,
  transformRequest: (params) => {
    if (_.isPlainObject(params)) {
      return qs.stringify(params, { arrayFormat: "repeat"})
    } else {
      return params
    }
  },
  paramsSerializer: (params) => {
    return qs.stringify(params, {arrayFormat: "repeat"})
  }
}

const service = axios.create(requestConfig)

/*service.interceptors.request.use((config: AxiosRequestConfig) => {
  config.headers
})*/

service.interceptors.response.use((resp: AxiosResponse) => {
  return resp.data.data
})

export { service as axios }
