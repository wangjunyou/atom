import { axios } from '@/service/service'


export function getUser(data: any): any {
    return axios({
        url: '/getuser',
        method: 'post',
        data
    })
}