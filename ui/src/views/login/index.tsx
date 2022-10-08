import { defineComponent } from 'vue'
import styles from './index.module.scss'

const login = defineComponent({
  setup() {},
  render() {
    return <div class={styles.login}>login</div>
  }
})

export default login
