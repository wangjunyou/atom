import axios, {AxiosError, AxiosRequestConfig, AxiosResponse} from 'axios'
import _ from 'lodash'
import qs from 'qs'
import router from "@/router";

const requestConfig: AxiosRequestConfig = {
    baseURL:
        import.meta.env.MODE === 'development'
            ? import.meta.env.VITE_APP_DEV_WEB_URL + '/atom/api'
            : import.meta.env.VITE_APP_PROD_WEB_URL + '/atom/api',
    timeout: 15000,
    transformRequest: (params) => {
        if (_.isPlainObject(params)) {
            return qs.stringify(params, {arrayFormat: "repeat"})
        } else {
            return params
        }
    },
    paramsSerializer: (params) => {
        return qs.stringify(params, {arrayFormat: "repeat"})
    }
}

const service = axios.create(requestConfig)

const err = (err: AxiosError): Promise<AxiosError> => {
    if (err.response?.status === 401 || err.response?.status === 405) {
        //缺少session管理功能
        router.push({path: '/login'})
    }
    return Promise.reject(err)
}

/*service.interceptors.request.use((config: AxiosRequestConfig) => {
  config.headers
})*/

service.interceptors.response.use((resp: AxiosResponse) => {
    return resp.data.data
}, err)

export {service as axios}
