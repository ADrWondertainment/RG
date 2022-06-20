<template>
    <!--1.首先，弹窗页面中要有el-dialog组件即弹窗组件，我们把弹窗中的内容放在el-dialog组件中-->
    <!--2.设置:visible.sync属性，动态绑定一个布尔值，通过这个属性来控制弹窗是否弹出-->
    <!-- <el-dialog :visible.sync="detailVisible" title="编辑部门"  width="35%" model-value="1"> -->
        <!-- visible.sync属性会报错，似乎可以直接使用v-model -->
    <el-dialog v-model="detailVisible" title="编辑部门"  width="35%" model-value="1">
        <div>当前部门名：{{oldname}}</div>
        <div>请输入新的部门名:
        </div>
        <el-input v-model="departName" placeholder="请输入新部门名" />
        <el-button @click="finishedit(this.departName,this.index,this.did)" type="success">确认</el-button>
    </el-dialog>
</template>

<script>
    export default {
        name: "EditDepart",
        data(){
            return{
                detailVisible:false,
                departName:"",
                oldname:"",
                index:0,
                did:0
            }
        },
        methods:{
            //3.定义一个init函数，通过设置detailVisible值为true来让弹窗弹出，这个函数会在父组件的方法中被调用
            init(name,index,did){
                this.detailVisible=true;
                //data是父组件弹窗传递过来的值，我们可以打印看看
                this.departName=name;
                this.oldname=name;
                this.index=index;
                this.did=did;
                console.log("init获取到的did:",this.did);
            },
            finishedit(newname,index,did){
                // axios.post('api/users/udept',{
                //     did:did,
                //     department:newname
                // })
                // .then(()=>{
                //     alert("修改成功");
                // })
                // // .catch(()=>{
                //     alert("修改失败");
                // })
              console.log("传给父组件的did",did);
                this.detailVisible=false;
                this.$emit("childFn",index,newname,did);
            }
        }
    }
</script>