<template>
  <el-header height="50px">
    <span class="top-text"> {{ userName }}，您好 </span>
    <!-- <el-badge :value="200" :max="99" class="item">
      <el-button type="info" :icon="Message" circle />
    </el-badge> -->
    <el-button
      type="danger"
      style="
        width: 0px;
        padding-left: 35px;
        padding-right: 35px;
        margin-left: 10px;
      "
      >退出登录</el-button
    >
  </el-header>
  <hr style="margin-bottom: 0" />
</template>

<script>
</script>

<style scoped>
.el-header {
  text-align: right;
  /* display: inline-block; */
  align-items: right;
  /* background: rgb(141, 200, 203); */
  /* vertical-align: middle; */
  margin-top: 15px;
}
.top-text {
  /* background: rgb(141, 200, 203); */
  color: #42b8dd;
  /* font-style: normal; */
  font-size: 20px;
  margin-top: 3px;
}
</style>


<script>
import { Message } from "@element-plus/icons-vue";
export default {
  data() {
    return {
      userName: "test",
      Message,
    };
  },
  mounted() {
    this.userName = sessionStorage["userName"];
  },
};
</script>
