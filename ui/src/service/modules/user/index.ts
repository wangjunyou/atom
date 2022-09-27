import { axios } from '@/service/service'


export function getUser(data: any): any {
    return axios({
        url: '/getuser',
        method: 'post',
        data
    })
}

export function getLogin(data: any): any {
    return axios({
        url: '/login',
        method: 'post',
        data
    })
}

export function getUser2(data: any): any {
    return axios({
        url: '/getuser2',
        method: 'post',
        data
    })
}
