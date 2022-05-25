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
            <el-table-column
                    prop="description"
                    label="部门描述"
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
                            @click="ToChangeForm(scope.$index, scope.row.id)"
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
    </el-card>

    <el-dialog v-model="dialog" title="请输入表单的信息">
        <!-- <template #title>
          <h4>请输入表单的信息</h4>
        </template>
        <template #default> -->
        <el-form>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="请输入表单名称"
                    ><el-input
                            v-model="sheetDescription.name"
                            placeholder="要发布的表单名称"
                            type="textarea"
                            :rows="2"
                    ></el-input
                    ></el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="请输入表单描述"
                    ><el-input
                            v-model="sheetDescription.description"
                            placeholder="对表单的描述"
                            type="textarea"
                            :rows="4"
                    ></el-input
                    ></el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="24">
                    <el-form-item label="表单起始时间">
                        <el-date-picker
                                v-model="sheetDescription.start"
                                type="datetime"
                                placeholder="表单起始时间"
                        />
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="24">
                    <el-form-item label="表单截止时间">
                        <el-date-picker
                                v-model="sheetDescription.end"
                                type="datetime"
                                placeholder="表单截止时间"
                        />
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="24">
                    <el-form-item label="是否发布企业表单">
                        <el-switch
                                v-model="ifCompany"
                                inline-prompt
                                active-text="是"
                                inactive-text="否"
                                @change="getDepartments"
                        />
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row v-if="ifCompany">
                <el-col :span="24">
                    <el-form-item label="请选择填写表单的部门">
                        <el-select>
                            <el-option v-for="item in departments" :key="item"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>

        </el-form>
        <!-- </template> -->
        <template #footer>
            <div style="flex: auto">
                <el-button type="info" @click="cancelClick">取消</el-button>
                <el-button type="primary" @click="confirmClick">确认</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script>
    import {ElMessage, ElMessageBox} from "element-plus";
    import axios from "axios";

    export default {
        name: "PersonnelManagement",
        data(){
            return{
                formInfo:null,
                departs:`[
                    {
                        "id":1,
                        "name":"财务部",
                        "num":10,
                        "description":"管钱的"
                    },
                    {
                        "id":2,
                        "name":"人力资源部",
                        "num":11,
                        "description":"管人的"
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
            }
        }
    }
</script>

<style scoped>

</style>