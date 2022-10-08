import { defineComponent, ref } from 'vue'
import styles from './index.module.scss'
import { getUser, getUsers } from '@/service/modules/user'
import { NButton } from 'naive-ui'

const login_demo = defineComponent({
  name: 'login',
  setup() {
    const name = ref(' == hello world')
    const user = ref<any>()

    const getUser1 = () => {
      getUser({ id: 1 }).then((u: any) => {
        user.value = u
      })
      console.log(user)
    }

    const getUser2 = async () => {
      user.value = await getUser({ id: 1 })
    }

    const users = ref<any>()

    const getUser3 = async () => {
      users.value = await getUsers({ name: 'zhangsan' })
      // console.log(users)
    }

    return { name, user, users, getUser1, getUser2, getUser3 }
  },
  render() {
    return (
      <div class={styles.login}>
        {this.users?.map((item: any, i: any) => {
          return (
            <div>
              {item.addr} = {i}
            </div>
          )
        })}
        <br />
        <NButton onClick={this.getUser3}>get</NButton>
      </div>
    )
  }
})

export default login_demo
