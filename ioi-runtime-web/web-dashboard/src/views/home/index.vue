<template>
  <n-layout>
    <n-layout has-sider>
      <n-layout-sider
          bordered
          show-trigger
          collapse-mode="width"
          :collapsed-width="64"
          :width="240"
          :native-scrollbar="false"
          :style="{ height: '100vh'}"
      >
        <div :style="{ textAlign: 'center' }">
          <n-icon size="50px">
            <userOutlined/>
          </n-icon>
        </div>
        <n-menu
            :collapsed-width="64"
            :collapsed-icon-size="22"
            :options="menuOptions"
            @update:value="handleUpdateValue"
        />
      </n-layout-sider>
      <n-layout :style="{ height: '100vh' }">
        <n-layout-header bordered :style="{ height: '12vh' }">
          <n-space align="start" vertical :style="{ height: '12vh' }">
            <n-icon size="30px">
              <menuFoldOutlined/>
            </n-icon>
          </n-space>

        </n-layout-header>
        context
      </n-layout>
    </n-layout>
  </n-layout>

</template>

<script lang="ts" setup>
import {h, Component, onUnmounted} from 'vue'
import {
  NLayout,
  NLayoutHeader,
  NLayoutContent,
  NLayoutFooter,
  NLayoutSider,
  NIcon,
  NMenu,
  MenuOption,
  NSpace
} from 'naive-ui'
import {BookOutlined, UserOutlined, TeamOutlined, MenuFoldOutlined, MenuUnfoldOutlined} from '@vicons/antd'

function renderIcon(icon: Component) {
  return () => h(NIcon, null, {default: () => h(icon)})
}

const menuOptions = [
  {
    label: '且听风吟',
    key: 'hear-the-wind-sing',
    icon: renderIcon(BookOutlined)
  },
  {
    label: '1973年的弹珠玩具',
    key: 'pinball-1973',
    icon: renderIcon(BookOutlined),
    disabled: true,
    children: [
      {
        label: '鼠',
        key: 'rat'
      }
    ]
  },
  {
    label: '寻羊冒险记',
    key: 'a-wild-sheep-chase',
    disabled: true,
    icon: renderIcon(BookOutlined)
  },
  {
    label: '舞，舞，舞',
    key: 'dance-dance-dance',
    icon: renderIcon(BookOutlined),
    children: [
      {
        type: 'group',
        label: '人物',
        key: 'people',
        children: [
          {
            label: '叙事者',
            key: 'narrator',
            icon: renderIcon(UserOutlined)
          },
          {
            label: '羊男',
            key: 'sheep-man',
            icon: renderIcon(UserOutlined)
          }
        ]
      },
      {
        label: '饮品',
        key: 'beverage',
        icon: renderIcon(TeamOutlined),
        children: [
          {
            label: '威士忌',
            key: 'whisky'
          }
        ]
      },
      {
        label: '食物',
        key: 'food',
        children: [
          {
            label: '三明治',
            key: 'sandwich'
          }
        ]
      },
      {
        label: '过去增多，未来减少',
        key: 'the-past-increases-the-future-recedes'
      }
    ]
  }
]

function handleUpdateValue(key: string, item: MenuOption) {
  console.log(key);
}

</script>

<style lang="less" scoped>

</style>