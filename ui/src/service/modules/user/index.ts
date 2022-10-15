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

export function getUsers(data: any): any {
  return axios({
    url: '/queryUserName',
    method: 'post',
    data
  })
}
