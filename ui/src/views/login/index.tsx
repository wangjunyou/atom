import {defineComponent, ref, reactive} from 'vue'
import styles from './index.module.scss'
import {getUser2} from '@/service/modules/user'
import {UserInfo} from "@/service/modules/user/types";

const login = defineComponent({
    name: 'login',
    setup() {
        const name = ref(' == hello world')
        // const data = getUser({id: 1})
        // console.log(data)
        var u
        const data = getUser2({id: 1})
        data.then((msg: UserInfo) =>{
            u = msg;
        })
        console.log(u)
        return {name}

    },
    render() {
        return (
            <div class={styles.login}>
                {/*hello world {this.name}*/}
                userInfo: {}
            </div>

        )
    }
})

export default login
