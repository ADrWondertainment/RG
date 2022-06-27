<template>
  <el-menu default-active="/userInfo" class="el-menu-vertical-demo" router>
    <el-menu-item index="/userInfo">
      <el-icon><avatar /></el-icon>
      <template #title>用户信息</template>
    </el-menu-item>
    <el-menu-item index="/manageFormTemplates">
      <el-icon><files /></el-icon>
      <template #title>我的模板</template>
    </el-menu-item>
    <el-menu-item index="/allPublicForms">
      <el-icon><edit /></el-icon>
      <template #title>我的表单</template>
    </el-menu-item>
    <el-menu-item index="/allFormsIFilledOut">
      <el-icon><edit /></el-icon>
      <template #title>我填过的表单</template>
    </el-menu-item>
    <el-menu-item index="/allApproves" v-if="userType==='企业'">
      <el-icon><edit /></el-icon>
      <template #title>填写表单</template>
    </el-menu-item>
    <el-menu-item index="/formApprove" v-if="userType==='企业' && userLevel <= 2">
      <el-icon><edit /></el-icon>
      <template #title>查看待审批表</template>
    </el-menu-item>
    <!-- <el-menu-item index="/createForm">
      <el-icon><tickets /></el-icon>
      <template #title>自定义模板</template>
    </el-menu-item> -->
    <el-menu-item index="/PersonnelManagement" v-if="userType==='企业'">
      <el-icon><tickets /></el-icon>
      <template #title>人员管理</template>
    </el-menu-item>
    <el-menu-item index="/organizationStructure" v-if="userType==='企业' && userLevel <= 1">
      <el-icon><tickets /></el-icon>
      <template #title>组织管理</template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      userType: "",
      userLevel: 5,
    };
  },
  mounted() {
    this.userType = sessionStorage.getItem("userType");
    console.log(this.userType);
    if (this.userType != null) {
      this.userType = "企业";
      this.userLevel = sessionStorage.getItem("level");
    } else {
      this.userType = "普通";
    };
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