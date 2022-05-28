<template>
  <el-header><HeaderBar /></el-header>
  <el-container>
    <el-aside width="150px">
      <side-bar />
    </el-aside>
    <el-main style="overflow-y:hidden;left:150px;">
      <el-scrollbar>
      <router-view />
      </el-scrollbar>
    </el-main>
  </el-container>
</template>

<style>
</style>

<script>
export default {};
</script>

<style scoped>
.el-header {
  position: relative;
  width: 100%;
}
.el-aside {
  display: block;
  position: absolute;
  left: 0;
  top: 75px;
  bottom: 0;
}
.el-main {
  position: absolute;
  margin-top: 20px;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}
</style>