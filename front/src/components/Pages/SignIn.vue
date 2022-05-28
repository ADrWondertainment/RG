<template>
<!--  <span>请输入用户名</span>-->
<!--  <el-input v-model="user.userName"></el-input>-->
<!--  <span>请输入密码</span>-->
<!--  <el-input v-model="user.password"></el-input>-->
<!--  <span>请确认密码</span>-->
<!--  <el-input v-model="user.validPassword"></el-input>-->
<!--  <el-button @click="SignIn">注册</el-button>-->
  <el-container class="total">
    <div class="front">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span style="margin-left: 30%; width: 40%">注册</span>
          </div>
        </template>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="6"><div style="width: 96px">请输入用户名</div></el-col>
<!--          140% 96px-->
          <el-col :span="18">
            <el-input v-model="user.userName" clearable @click="clear">
              <template #suffix>
                <el-icon class="el-input__icon"><avatar /></el-icon>
              </template>
            </el-input>

          </el-col>
        </el-row>
        <div :style=[style,noticeuname] >用户名不能为空！</div>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="6"><div style="width: 80px">请输入密码</div></el-col>
<!--          120% 80px-->
          <el-col :span="18">
            <el-input type="password" v-model="user.password" clearable @click="clear">
              <template #suffix>
                <el-icon class="el-input__icon"><key /></el-icon>
              </template>
            </el-input>

          </el-col>
        </el-row>
        <div :style=[style,noticeupass] >密码不能为空！</div>
        <el-row :gutter="20" justify="space-evenly">
          <el-col :span="6"><div style="width: 80px">请确认密码</div></el-col>
<!--          12-% 80px-->
          <el-col :span="18">
            <el-input type="password" v-model="user.validPassword" clearable @click="clear">
              <template #suffix>
                <el-icon class="el-input__icon"><key /></el-icon>
              </template>
            </el-input>

          </el-col>
        </el-row>
        <div :style=[style,noticeval] >{{message}}</div>
        <el-row :gutter="20" justify="space-evenly" style="padding-top: 10px">
          <el-col :span="12"><el-button type="success" @click="Back">返回</el-button></el-col>
          <el-col :span="12"><el-button type="primary" @click="SignIn">注册</el-button></el-col>
        </el-row>
      </el-card>
    </div>
  </el-container>
</template>


<script>
import axios from "axios";
import { ElMessage } from "element-plus";
// var el=document.querySelector('el-buton');
// window.addEventListener('resize',function(){
//   if(window.innerWidth<=1000){
//     el.style.display='none';
//   }else
//     el.style.display='block';
// })
export default {
  data() {
    return {
      user: {
        userName: "",
        password: "",
        validPassword: "",
      },
      style:{
        'font-size': '6px',
    'color':'red',
    'margin-left': '114px',
    'margin-top': '-16px',
    'width':'108px',
    'text-align':'left',
      },
      noticeuname:{
        'display':'none'
      },
      noticeupass:{
        'display':'none'
      },
      noticeval:{
        'display':'none'
      },
      message:''
    }
  },
  methods: {
    clear(){
      this.noticeuname.display='none';
      this.noticeupass.display='none';
      this.noticeval.display='none';
    },
    initUser(a,b,c){
      this.user.userName=a;
      this.user.password=b;
      this.user.validPassword=c;
    },
    SignIn() {
      // console.log(this.user);
      //   ElMessage.success("注册成功");
      //   this.$router.push("/login");
      if(this.user.userName=='') {
        // alert("请输入用户名");
        this.noticeuname.display='block';
        this.initUser('','','');
      }
      else if(this.user.password=='') {
        // alert("请输入密码");
        this.noticeupass.display='block';
        this.message='确认密码不能为空！';
        this.noticeval.display='block';
        this.initUser(this.user.userName,'','');
      }
      else if(this.user.validPassword=='') {
        // alert("请输入确认密码");
        this.message='确认密码不能为空！';
        this.noticeval.display='block';
        this.initUser(this.user.userName,'','');
      }
      else if (this.user.password == this.user.validPassword) {
        axios
                .post("api/users/register", {
                  email: this.user.userName,
                  pass: this.user.password,
                })
                .then(res => {
                  console.log(res)
                  if (res.data.data == 1) {
                    ElMessage.success("注册成功");
                    this.$router.push("/login");
                  }
                });
      }
      else{
          // alert("两次输入密码需一致");
          this.message='两次输入密码需一致';
          this.noticeval.display='block';
          this.initUser(this.user.userName,'','');
      }

    },
    Back(){
      this.$router.push("login");
    }
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
    width: 27%;
  }

  .total {
    width: 100px;
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

  .el-input {
    margin-left: 12px;
  }
  /*.notice{*/
  /*  font-size: 6px;*/
  /*  color:red;*/
  /*  margin-left: 114px;*/
  /*  margin-top: -18px;*/
  /*  width:96px;*/
  /*  text-align: left;*/
  /*  display: none;*/
  /*}*/
</style>
