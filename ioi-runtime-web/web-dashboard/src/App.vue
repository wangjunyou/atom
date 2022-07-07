<template>
  <n-config-provider>
    <n-message-provider>
      <router-view/>
    </n-message-provider>
  </n-config-provider>
</template>

<script lang="ts" setup>
import {NConfigProvider, NMessageProvider} from 'naive-ui'
import {RouterView} from 'vue-router'
</script>
