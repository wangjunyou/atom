<template>
  <div class="login">
    <n-card
    >
      <div :style="{ textAlign: 'center',fontSize: '50px'}">
        <n-icon size="50px">
          <teamOutlined/>
        </n-icon>
        ioi
      </div>
      <n-form>
        <n-form-item path="username" label="username">
          <n-input placeholder="username"></n-input>
        </n-form-item>
        <n-form-item path="password" label="password">
          <n-input placeholder="password"></n-input>
        </n-form-item>
      </n-form>
      <n-button type="info" :style="{ width: '100%' }" @click="loginInfo">登陆</n-button>
    </n-card>
  </div>

</template>

<script lang="ts" setup>
import {NCard, NIcon, NForm, NFormItem, NInput, NButton} from 'naive-ui'
import { TeamOutlined} from '@vicons/antd'
import { login } from '@/service/modules/login'
import { LoginReq } from '@/service/modules/login/types'
import router from "../../router";

function loginInfo() {
  let loginReq: LoginReq = {
    userName: 'zhangsan',
    userPassword: 'admin'
  }
  let result = login(loginReq)
  console.log(result)
  router.push({ name: 'home'})
  // let req: AxiosResponse = login(loginReq)
  // console.log(req.data)
}

</script>

<style lang="less" scoped>
.login {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #0098e1;
}

.n-card {
  max-width: 400px;
}

</style>