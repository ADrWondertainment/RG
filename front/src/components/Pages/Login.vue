<template>
  <el-container class="total">
    <div class="front">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span style="margin-left: 30%; width: 40%">用户登录</span>
          </div>
        </template>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="6"><div style="width: 48px">用户名</div></el-col>
          <el-col :span="18">
            <el-input v-model="logInfo.userName" clearable>
              <template #suffix>
                <el-icon class="el-input__icon"><avatar /></el-icon>
              </template>
            </el-input>
          </el-col>
        </el-row>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="6"><div style="width: 48px">密码</div></el-col>
          <el-col :span="18">
            <el-input v-model="logInfo.password" type="password" clearable>
              <template #suffix>
                <el-icon class="el-input__icon"><key /></el-icon>
              </template>
            </el-input>
          </el-col>
        </el-row>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="12"
            ><el-button type="success" @click="login">登录</el-button></el-col
          >
          <el-col :span="12"
            ><el-button type="primary" @click="ToSignIn"
              >注册</el-button
            ></el-col
          >
        </el-row>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="24"
            ><el-button type="info" @click="ToSignInC"
              >企业用户注册</el-button
            ></el-col
          >
        </el-row>
      </el-card>
    </div>
  </el-container>
</template>

<script>
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      logInfo: {
        userName: "",
        password: "",
      },
      userInfo: null,
    };
  },
  methods: {
    login() {
      // 后端调试时解除此段注释(林志康是傻逼)
      sessionStorage.clear();
      axios
        .post("api/users/login", {
          email: this.logInfo.userName,
          pass: this.logInfo.password,
        })
        .then((res) => {
          console.log(res.data);
          this.userInfo = JSON.parse(res.data.data);
          sessionStorage["level"] = this.userInfo.level || null;
          console.log(this.userInfo);
          if (res.data.errorCode == 1 || res.data.errorCode == 10050) {
            sessionStorage["isLogin"] = true;
            sessionStorage["userName"] = this.logInfo.userName;
            sessionStorage["userType"] = this.userInfo.typeId || null;
            if (this.userInfo.typeId > 0) {
              sessionStorage["userCompany"] = this.userInfo.company;
              if (this.userInfo.did > 0) {
                sessionStorage["userDid"] = this.userInfo.did;
                sessionStorage["userDepartment"] = this.userInfo.department;
              }
            }

            // console.log(sessionStorage["userInfo"]);
            // sessionStorage["userType"]=true;
            if (sessionStorage.getItem("targetPage") === null) {
              console.log(111);
              this.$router.push({
                name: "UserInfo",
              });
            } else {
              console.log(222);
              this.$router.push({
                path: sessionStorage["targetPage"],
              });
            }
          } else {
            ElMessage("登陆失败，请检查密码正确性");
          }
        });

      // 前端调试时解除此段注释
      // sessionStorage["isLogin"] = true;
      // sessionStorage["userName"] = "admin";
      // if (sessionStorage.getItem("targetPage") === null) {
      //   // 要用.getItem，否则报错，可能是sessionSrorage直接使用[]索引时没有出错处理
      //   this.$router.push({
      //     name: "UserInfo",
      //   });
      // } else {
      //   this.$router.push({
      //     path: sessionStorage["targetPage"],
      //   });
      // }
    },
    ToSignIn() {
      this.$router.push("/signIn");
    },
    ToSignInC() {
      this.$router.push("/signInC");
    },
  },
};
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.box-card {
  width: 300px;
  margin-left: 60%;
  margin-top: 15%;
  width: 25%;
}

.front {
  z-index: 1;
  width: 100%;
  height: 100%;
  position: absolute;
  /* background-image: "src/assets/background.png"; */
}

.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>