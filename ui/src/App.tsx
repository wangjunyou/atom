import { defineComponent } from 'vue'
import Login from '@/views/login'

const App = defineComponent({
  name: 'App',
  setup() {},
  render() {
    // return <div>IOI WEB</div>
    return <router-view />
  }
})

export default App
