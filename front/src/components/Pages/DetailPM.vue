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
                        title="添加人员"
                        type="primary"
                        @click="ToCreateForm"
                        plain
                >添加人员</el-button
                >
            </el-col>
        </el-row>
        <el-table :data="formInfo" style="width: 100%">
            <el-table-column prop="uid" label="编号" width="180" align="center" />
            <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
            />
            <el-table-column
                    prop="depart"
                    label="所在部门"
                    width="180"
                    align="center"
            />
            <el-table-column
                    prop="telephone"
                    label="联系方式"
                    width="180"
                    align="center"
            />
            <el-table-column
                    prop="right"
                    label="权限"
                    width="180"
                    align="center"
            />
            <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button
                            size="small"
                            @click="startManageRight(scope.$index,scope.row.id)"
                            title="管理权限"
                            type="success"
                            plain
                            round
                    >管理权限</el-button
                    >
                    <el-button
                            size="small"
                            @click="ToChangeForm(scope.$index, scope.row.id)"
                            title="移动人员"
                            type="success"
                            plain
                            round
                    >移动人员</el-button
                    >
                    <el-button
                            size="small"
                            type="danger"
                            @click="ToDeleteTemplate(scope.$index, scope.row.id)"
                            title="删除人员"
                            plain
                            round
                    >删除人员</el-button
                    >
                    <el-select :style="showManageDepart" v-model="value" placeholder="--请选择权限--">
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                        <el-button>确认</el-button>
                    </el-select>
                </template>
            </el-table-column>
        </el-table>
        <el-divider style="margin-top: 0"></el-divider>
    </el-card>
</template>

<script>
    import axios from "axios";

    export default {
        name:'DetailPM',
        data(){
            return{
                id:99,
                formInfo:null,
                staff:`[
                    {
                        "id":1,
                        "uid":"419210??",
                        "name":"林志康",
                        "telephone":110,
                        "depart":"臭写代码的",
                        "right":9
                    },
                    {
                        "id":1,
                        "uid":41921011,
                        "name":"杜沛霖",
                        "telephone":1881007821,
                        "depart":"臭写代码的",
                        "right":1
                    }

                ]`,
                options:[
                    {
                        value:"fin",
                        label:"1"
                    },
                    {
                        value:'hr',
                        label:"2"
                    },
                    {
                        value:'hr',
                        label:"3"
                    },{
                        value:'hr',
                        label:"4"
                    },{
                        value:'hr',
                        label:"5"
                    },
                ],
                showManageDepart:{
                    "display":"none"
                }
            }
        },
        methods:{
          getId(){
              let routeid=this.$route.params.id;
              this.id=routeid;
          },
          // getStaffbyId(id){
          //     this.formInfo=JSON.parse()
          // },
            startManageRight(index,id){
                this.showManageDepart.display='block';
                console.log(this.staff);
            },
            finishManageRight(index,id){

            }
        },
        created(){
            this.getId();
        },
        mounted(){
            // axios
            //     .get("/PersonnelManagement")
            //     .then(response=>{console.log(response.id);})
            //     .catch(err=>{console.log(err);});
            console.log(this.$route.params.id);
            this.formInfo=JSON.parse(this.staff);
        }
    }
</script>
