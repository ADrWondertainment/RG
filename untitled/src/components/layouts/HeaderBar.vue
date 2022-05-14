<template>
  <el-menu default-active="1" class="el-menu-demo" mode="horizontal">
    <el-menu-item index="1">
      <template #title>
        <el-icon><avatar /></el-icon>
        <span>用户信息</span>
      </template>
    </el-menu-item>
    <el-menu-item index="2">
      <template #title>
        <el-icon><files /></el-icon>
        <span>已发布问卷</span>
      </template>
    </el-menu-item>
    <el-menu-item index="3">
      <template #title>
        <el-icon><tickets /></el-icon>
        <span>自定义问卷</span>
      </template>
    </el-menu-item>
  </el-menu>
</template>

<script>

</script>