import { defineComponent } from 'vue'
import { NConfigProvider, NMessageProvider } from 'naive-ui'

const App = defineComponent({
  name: 'App',
  setup() {},
  render() {
    return (
      <NConfigProvider style={{ width: '100%', height: '100vh' }}>
        <NMessageProvider>
          <router-view></router-view>
        </NMessageProvider>
      </NConfigProvider>
    )
  }
})

export default App
