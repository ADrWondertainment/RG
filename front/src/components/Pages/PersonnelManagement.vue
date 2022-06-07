<template>

    <el-card>
        <el-row>
            <el-col :span="4"></el-col>
            <el-col :span="16">
                <h1 title="123">人员管理</h1>
            </el-col>
            <el-col :span="4">
                <el-button
                        style="padding: 20px"
                        title="新建部门"
                        type="primary"
                        @click="ToCreateForm"
                        plain
                >新建部门</el-button
                >
            </el-col>
        </el-row>
        <el-table :data="formInfo" style="width: 100%">
            <el-table-column prop="name" label="部门名称" width="180" align="center" />
            <el-table-column
                    prop="num"
                    label="部门人数"
                    width="180"
                    align="center"
            />
            <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button
                            size="small"
                            @click="EnterDepartment(scope.$index, scope.row.id)"
                            title="进入部门"
                            type="success"
                            plain
                            round
                    >进入部门</el-button
                    >
                    <el-button
                            size="small"
                            @click="EditDepart(scope.$index)"
                            title="编辑部门"
                            type="success"
                            plain
                            round
                    >编辑部门</el-button
                    >
                    <el-button
                            size="small"
                            type="danger"
                            @click="ToDeleteTemplate(scope.$index, scope.row.id)"
                            title="删除部门"
                            plain
                            round
                    >删除部门</el-button
                    >

                </template>
            </el-table-column>
        </el-table>
        <el-divider style="margin-top: 0"></el-divider>
        <edit-depart @childFn="finishEdit" v-if="editDepartVisible" ref="dialog"></edit-depart>
    </el-card>


</template>

<script>
    import {ElMessage, ElMessageBox} from "element-plus";
    import axios from "axios";
    import EditDepart from "./EditDepart.vue";
    import AddDepart from "./AddDepart.vue";

    export default {
        name: "PersonnelManagement",
        components: {EditDepart,AddDepart},
        data(){
            return{
                formInfo:null,
                editDepartVisible:false,
                departs:`[
                    {
                        "id":1,
                        "name":"财务部",
                        "num":10

                    },
                    {
                        "id":2,
                        "name":"人力资源部",
                        "num":11

                    }

                ]`,

            };
        },

        mounted(){
            this.formInfo=JSON.parse(this.departs);
        },
        methods:{
            ToDeleteTemplate(index, id) {
                ElMessageBox.confirm("你确定要删除此部门吗", "注意！", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        axios
                            .delete("/api/templates/" + id, {})
                            .then((res) => {
                                if (res.data.errorCode === 66666) {
                                    ElMessage({
                                        type: "info",
                                        message: "删除成功",
                                    });
                                    this.getTemplates();
                                }
                            });
                    })
                    .catch(() => {
                        ElMessage({
                            type: "info",
                            message: "取消操作",
                        });
                    });
                console.log(index, id);
            },
            EnterDepartment(index,id){
                this.$router.push({name:"DetailPM",params:{id:id}});
            },
            EditDepart(index){
                this.editDepartVisible=true;
                this.$nextTick(()=>{
                    //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
                    //init调用的是dialog-component组件里面的init方法
                    //data是传递给弹窗页面的值
                    // console.log(this.formInfo[index].name);
                    this.$refs.dialog.init(this.formInfo[index].name,index);

                })
            },
            finishEdit(index,name){
                this.editDepartVisible=false;
                // console.log("成了");
                console.log(index,name);
                this.formInfo[index].name=name;
            },
        }
    }
</script>

<style scoped>

</style>