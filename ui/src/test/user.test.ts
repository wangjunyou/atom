import {test} from 'vitest'
import {getUser, getUsers} from "@/service/modules/user";
import {format, parseISO} from 'date-fns'
import _ from 'lodash'

test('getUsers', async () => {
    const dd = await getUsers({name: 'zhangsan'})
    dd.map((item: any) => {
        if(!_.isNull(item.createTime)) {
            item.createTime = format(
                new Date(item.createTime),
                'yyyy-MM-dd HH:mm:ss'
            )
        }
        if(!_.isNull(item.updateTime)) {
            item.updateTime = format(
                new Date(item.updateTime),
                'yyyy-MM-dd HH:mm:ss'
            )
        }
    })
    console.log(dd)
})

test('user', () => {
    console.log('user')
})

test('demo', () => {
    console.log(import.meta.env.MODE);
    console.log(import.meta.env.VITE_APP_PROD_WEB_URL);
})
