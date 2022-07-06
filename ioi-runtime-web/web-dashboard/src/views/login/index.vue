<template>
  <div>{{ hello }}</div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue'
  const hello = ref('helloworld')
</script>

<style lang="less" scoped></style>
