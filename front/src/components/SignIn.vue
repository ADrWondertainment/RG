<template>
  <span>请输入用户名</span>
  <el-input v-model="user.userName"></el-input>
  <span>请输入密码</span>
  <el-input v-model="user.password"></el-input>
  <span>请确认密码</span>
  <el-input v-model="user.validPassword"></el-input>
  <el-button @click="SignIn">注册</el-button>
</template>

<script>
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      user: {
        userName: "",
        password: "",
        validPassword: "",
      },
    };
  },
  methods: {
    SignIn() {
      console.log(this.user);
      //   ElMessage.success("注册成功");
      //   this.$router.push("/login");

      if (this.password == this.validPassword) {
        axios
          .post("api/users/register", {
            email: this.user.userName,
            pass: this.user.password,
          })
          .then(res => {
            if (res.data.data == "1") {
              ElMessage.success("注册成功");
              this.$router.push("/login");
            }
          });
      }
    },
  },
};
</script>