import {LoginReq} from './types'
import {axios} from '@/service/service'


export function login(data: LoginReq): any {
    return axios({
        url: '/login',
        method: 'post',
        data
    })
}