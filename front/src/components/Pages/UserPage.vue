<template>
  <el-card>
    <el-row>
      <el-col :span="4"> 用户名： </el-col>
      <el-col :span="4">
        {{ userName }}
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4"> 用户类型： </el-col>
      <el-col :span="4">
        {{ userTypeStr }}
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4"> 用户部门： </el-col>
      <el-col :span="4">
        {{ userDepartment }}
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-button @click="changePassword">修改用户密码</el-button>
      </el-col>
      <el-col :span="8">
        <el-button @click="showJoinCompany">申请加入企业</el-button>
      </el-col>
    </el-row>
  </el-card>

  <el-dialog v-model="changePasswordDialog" width="30%">
    <el-row>
      <el-col :span="8">请输入当前密码</el-col>
      <el-col :span="16"><el-input v-model="password.now" /></el-col>
    </el-row>
    <el-row>
      <el-col :span="8">请输入新密码</el-col>
      <el-col :span="16"><el-input v-model="password.new" /></el-col>
    </el-row>
    <el-row>
      <el-col :span="8">请确认新密码</el-col>
      <el-col :span="16"><el-input v-model="password.validNew" /></el-col>
    </el-row>
    <el-row
      ><el-col :span="8" :offset="16"
        ><el-button @click="changePasswordSubmit">提交</el-button></el-col
      ></el-row
    >
  </el-dialog>

  <el-dialog v-model="joinCompanyDialog" width="30%">
    <el-row>
      <el-col :span="8">请输入企业邀请码</el-col>
      <el-col :span="16"><el-input v-model="companyId" /></el-col>
    </el-row>
    <el-row style="margin-top: 5px"
      ><el-col :span="8" :offset="16"
        ><el-button @click="submitCompanyId">提交</el-button></el-col
      ></el-row
    >
  </el-dialog>
</template>


<script>
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      userName: sessionStorage["userName"],
      userType: 0,
      userTypeStr: "",
      userDepartment: "",
      companyId: "",
      joinCompanyDialog: false,
      changePasswordDialog: false,
      password: {
        now: "",
        new: "",
        validNew: "",
      },
    };
  },

  mounted() {
    if (this.userType === 0) {
      this.userTypeStr = "普通用户";
    } else {
      this.userTypeStr = "企业用户";
    }
  },

  methods: {
    changePassword() {
      this.changePasswordDialog = true;
    },
    showJoinCompany() {
      this.joinCompanyDialog = true;
    },
    submitCompanyId() {
      if (this.companyId != "") {
        axios
          .post("/api/users/joincompany", {
            invite: this.companyId,
          })
          .then((res) => {
            console.log(res);
            if (res.data.errorCode === 10010) {
              this.joinCompanyDialog = false;
            }
          });
      } else {
        ElMessage.error("企业邀请码不可为空");
      }
    },
    changePasswordSubmit() {
      if (this.password.new === this.password.validNew) {
        axios
          .post("/api/users/changepass", {
            oldpass: this.password.now,
            newpass: this.password.new,
          })
          .then((res) => {
            console.log(res.data);
            if (res.data.errorCode === 10070) {
              this.changePasswordDialog = false;
              ElMessage.success("修改密码成功！");
            } else if (res.data.errorCode === 10060) {
              ElMessage.error("您输入了错误的当前密码");
              this.password = {
                now: "",
                new: "",
                validNew: "",
              };
            } else {
              ElMessage.warning("修改失败，请联系管理员");
            }
          });
      } else {
        this.password.new = "";
        this.password.validNew = "";
        ElMessage.error("两次输入的密码应该相同");
      }
    },
  },
};
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
</style>