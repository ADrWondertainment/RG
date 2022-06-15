<template>
  <el-menu default-active="/userInfo" class="el-menu-vertical-demo" router>
    <el-menu-item index="/userInfo">
      <el-icon><avatar /></el-icon>
      <template #title>用户信息</template>
    </el-menu-item>
    <el-menu-item index="/manageFormTemplates">
      <el-icon><files /></el-icon>
      <template #title>问卷模板管理</template>
    </el-menu-item>
    <el-menu-item index="/allPublicForms">
      <el-icon><edit /></el-icon>
      <template #title>已发布模板</template>
    </el-menu-item>
    <el-menu-item index="/allFormsIFilledOut">
      <el-icon><edit /></el-icon>
      <template #title>我填过的表单</template>
    </el-menu-item>
    <el-menu-item index="/formApprove">
      <el-icon><edit /></el-icon>
      <template #title>查看待审批表</template>
    </el-menu-item>
    <el-menu-item index="/createForm">
      <el-icon><tickets /></el-icon>
      <template #title>自定义模板</template>
    </el-menu-item>
    <el-menu-item index="/PersonnelManagement">
      <el-icon><tickets /></el-icon>
      <template #title>人员管理</template>
    </el-menu-item>
    <el-menu-item index="/organizationStructure">
      <el-icon><tickets /></el-icon>
      <template #title>组织管理</template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    // ToUserInfo() {
    //   // this.$router.push((name = "UserInfo"));
    // },
    // ToCreateForm() {
    //   // this.$router.push((name = "CreateForm"));
    // },
  },
};
</script>