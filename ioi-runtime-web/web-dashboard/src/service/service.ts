import axios, {AxiosError, AxiosRequestConfig, AxiosResponse} from 'axios'
import qs from 'qs'
import _ from 'lodash'
import router from '@/router'
import {useUserStore} from '@/store/user/user'

const userStore = useUserStore()

const baseRequestConfig: AxiosRequestConfig = {
    baseURL:
        import.meta.env.MODE == 'development'
            ?
            import.meta.env.VITE_APP_DEV_SERVICE_URL
            :
            import.meta.env.VITE_APP_PROD_SERVICE_URL
    ,
    timeout: 15000,
    transformRequest: (params) => {
        if (_.isPlainObject(params)) {
            return qs.stringify(params, {arrayFormat: 'repeat'})
            // return qs.stringify(params)
        } else {
            return params
        }
    },
    paramsSerializer: (params) => {
        return qs.stringify(params, {arrayFormat: 'repeat'})
        // return qs.stringify(params)
    }
}

const service = axios.create(baseRequestConfig)

const err = (err: AxiosError): Promise<AxiosError> => {
    if (err.response?.status === 401 || err.response?.status === 504) {
        userStore.setSessionId('')
        router.push({path: '/login'})
    }
    return Promise.reject(err)
}

/*service.interceptors.request.use((config: AxiosRequestConfig<any>) => {
    config.headers && (config.headers.sessionId = userStore.getSessionId)
    const language = cookies.get('language')
    config.headers = config.headers || {}
    if (language) config.headers.language = language

    return config
}, err)*/


service.interceptors.response.use((res: AxiosResponse) => {

    if (res.data.code === undefined) {
        return res.data
    }

    switch (res.data.code) {
        case 0:
            return res.data.data
        default:
            // handleError(res)
            throw new Error()
    }
}, err)

export {service as axios}
