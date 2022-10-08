import { expect, test } from 'vitest'
import utils from '@/utils'
import qs from 'qs'
import { getUser, getUser2 } from '@/service/modules/user'
import { UserInfo } from '@/service/modules/user/types'

test('ceshi', () => {
  const modules = import.meta.glob('/src/views/**/**.tsx')
  let m = utils.mapping(modules)
  console.log(m)
})

interface JsonInfo {
  id: number
  name: string
  births: Date
}
test('test1', () => {
  const jsonInfo: JsonInfo = {
    id: 123456,
    name: 'zhangsan',
    births: new Date()
  }

  let ji = JSON.stringify(jsonInfo)
  console.log(ji)

  let jio: JsonInfo = JSON.parse(ji)
  console.log(jio)
})

test('test2', () => {
  const jsonInfo: JsonInfo = {
    id: 123456,
    name: 'zhangsan',
    births: new Date()
  }
  jsonInfo && (jsonInfo.id = 6543421)
  console.log(jsonInfo)
  let jsonInfo2 = 123
  jsonInfo2 && (jsonInfo2 = 12345678)
  console.log(jsonInfo2)
  let data = qs.stringify(jsonInfo, { arrayFormat: 'repeat' })
  console.log(data)
})

test('getUser', () => {
  const data = getUser({ id: 1 })
  console.log(data)
})
