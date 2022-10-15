import {reactive} from "vue";
import {getUsers} from "@/service/modules/user";
import _ from "lodash";
import {format} from "date-fns";

export function getFrom(name: string) {

    const result = reactive({
        ulist: []
    })

    const users = async () => {
        const us = await getUsers({name: name})
        result.ulist = us.map((item: any) => {
            if (!_.isNull(item.createTime)) {
                item.createTime = format(
                    new Date(item.createTime),
                    'yyyy-MM-dd HH:mm:ss'
                )
            }
            if (!_.isNull(item.updateTime)) {
                item.updateTime = format(
                    new Date(item.updateTime),
                    'yyyy-MM-dd HH:mm:ss'
                )
            }
            return {...item}
        })
    }

    return {result, users}
}
