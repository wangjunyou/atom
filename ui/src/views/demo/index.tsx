import { defineComponent, ref } from 'vue'
import styles from './index.module.scss'

const demo = defineComponent({
  name: 'demo',
  setup() {
    const demo = ref('demo')
    return { demo }
  },
  render() {
    return <div class={styles.demo}>router is : {this.demo}</div>
  }
})

export default demo
