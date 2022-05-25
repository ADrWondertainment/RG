<template>
  <el-container class="total">
    <div class="background">
      <el-image src="src/assets/background.png" fit="scale-down" />
    </div>
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
            <el-input v-model="logInfo.password" clearable>
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
      </el-card>
    </div>
  </el-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      logInfo: {
        userName: "",
        password: "",
      },
    };
  },
  methods: {
    login() {
      axios
        .post("api/users/login", {
          email: this.logInfo.userName,
          pass: this.logInfo.password,
        })
        .then(res => {
          console.log(res.data)
          if (res.data.data == "1") {
            this.$router.push({
              name: "UserInfo",
            });
          }
        });
    },
    ToSignIn() {
      this.$router.push("/signIn");
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

.total {
  width: 100px;
}

.background {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
}

.front {
  z-index: 1;
  width: 100%;
  height: 100%;
  position: absolute;
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