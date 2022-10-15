import {defineComponent, reactive, ref, toRefs} from 'vue'
import styles from './index.module.scss'
import {NButton, NDataTable, NForm, NFormItem, NInput} from "naive-ui";
import {getUsers} from "@/service/modules/user";
import {getFrom} from "@/views/login/user-from";

const login = defineComponent({
    name: 'login',
    setup() {
        const userr = ref<any>()
        const cols = [{title: 'id', key: 'id'},
            {title: 'userName', key: 'userName'},
            {title: 'phone', key: 'phone'},
            {title: 'email', key: 'email'},
            {title: 'createTime', key: 'createTime'},
            {title: 'updateTime', key: 'updateTime'}]
        /*const datas = reactive({tables: []})
        const users = async () => {
            datas.tables =  await getUsers({name: 'zhangsan'})
        }*/
            const {result, users} = getFrom('zhangsan')

        // const datas = [{id: 'zhangsan',userName: 'zhangsan',phone: '12345767',email: '123dff'}]
        return {userr, cols, ...toRefs(result), users}
    },
    render() {
        return (
            <div class={styles.login}>
                <NDataTable
                    columns={this.cols}
                    data={this.ulist}
                />
                <br/>
                {<NButton onClick={this.users}>get</NButton>}
            </div>
        )
    }
})

export default login
