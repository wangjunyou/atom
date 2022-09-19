import { defineComponent, ref } from 'vue'
import styles from './index.module.scss'

const login = defineComponent({
  name: 'login',
  setup() {
    const name = ref(' == hello world')
    return { name }
  },
  render() {
    return <div class={styles.login}> hello world {this.name}</div>
  }
})

export default login
