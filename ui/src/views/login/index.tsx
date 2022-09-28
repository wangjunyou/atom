import {defineComponent, ref, reactive, onMounted} from 'vue'
import styles from './index.module.scss'
import {getUser, getUser2} from '@/service/modules/user'
import {UserInfo, UserInfo2} from "@/service/modules/user/types";
import {useAsyncState} from "@vueuse/core";
import {NButton} from "naive-ui";
import user from "@/store/user";

const login = defineComponent({
    name: 'login',
    setup() {
        const name = ref(' == hello world')
        const ddd = ref<any>()
        const user = async () => {
            const user = await getUser2({id: 1})
            ddd.value = user
        }
        const user1 = () => {
            const user = getUser2({id: 1}).then(
                (u: any) =>{
                    ddd.value = u
                }
            )
            console.log(ddd)
        }
        /*onMounted(()=>{
            user1()
        })*/
        return {name, ddd, user, user1}

    },
    render() {
        return (
            <div class={styles.login}>
                helloworld:{this.ddd?.addr}
                <NButton onClick={this.user}>hello</NButton>
            </div>

        )
    }
})

export default login
