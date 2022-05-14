<template>
  <el-menu default-active="1" class="el-menu-vertical-demo">
    <el-menu-item index="1" @click="ToUserInfo">
      <el-icon><avatar /></el-icon>
      <template #title>用户信息 </template>
    </el-menu-item>
    <el-menu-item index="2">
      <el-icon><files /></el-icon>
      <template #title>我创建的问卷</template>
    </el-menu-item>
    <el-menu-item index="3" @click="ToCreateForm">
      <el-icon><edit /></el-icon>
      <template #title>我填写的问卷</template>
    </el-menu-item>
    <el-menu-item index="4" @click="ToCreateForm">
      <el-icon><tickets /></el-icon>
      <template #title>自定义问卷</template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    ToUserInfo() {
      this.$router.push((name = "UserInfo"));
    },
    ToCreateForm() {
      this.$router.push((name = "CreateForm"));
    },
  },
};
</script>