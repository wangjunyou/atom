import {defineComponent, ref} from 'vue'
import styles from './index.module.scss'
import {getUser} from '@/service/modules/user'
import {UserInfo} from "@/service/modules/user/types";

const login = defineComponent({
    name: 'login',
    setup() {
        const name = ref(' == hello world')
        const data: UserInfo = getUser({id: 1})
        console.log(data)
        return {name, data}
    },
    render() {
        return (
            <div class={styles.login}>
                {/*hello world {this.name}*/}
                userInfo: {this.data}
            </div>

        )
    }
})

export default login
