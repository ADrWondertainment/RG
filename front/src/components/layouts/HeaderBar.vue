<template>
  <el-header height="50px">
    <span class="top-text"> 尊敬的用户，您好 </span>
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
      @click="quitLogin"
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
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";
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
  methods: {
    quitLogin() {
      ElMessageBox.confirm("您确定要退出吗", "请确认", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
      }).then(() => {
        axios.post("/api/users/logout", {}).then(() => {
          ElMessage.warning("已退出登录");
          this.$router.push("/login");
          sessionStorage.clear();
        }).catch(() => {
          ElMessage.success("已取消");
        });
      });
    },
  },
};
</script>
