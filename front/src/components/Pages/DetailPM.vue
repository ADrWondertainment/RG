<template>
    <el-card>
        <el-row>
            <el-col :span="4"></el-col>
            <el-col :span="16">
                <h1 title="">人员管理</h1>
                <el-col :span="4" style="text-align: left" ><el-button type="success" @click="back"><el-icon><Back /></el-icon>返回</el-button></el-col>
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
                <el-button
                        style="padding: 20px"
                        title="编辑角色"
                        type="primary"
                        @click="ToCreateForm"
                        plain
                >编辑角色</el-button
                >
            </el-col>
        </el-row>
        <el-table :data="formInfo" style="width: 100%">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="uid" label="编号" width="180" align="center" />
            <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
            />
            <el-table-column
                    prop="pos"
                    label="角色"
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
                            :title="this.manageButton[scope.$index]"
                            type="success"
                            plain
                            round
                    >{{this.manageButton[scope.$index]}}</el-button
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
<!--                    {{select[scope.$index]}}-->
                    <el-select :style="showManageDepart[scope.$index]"
                               v-model="value"
                               @change="finishManageRight(scope.$index,scope.row.id,value)"
                               :placeholder=scope.row.right
                               clearable="false"


                    >
<!--                        <el-button @click="finishManageRight(scope.$index,scope.row.id,scope.$index.label)">确认</el-button>-->
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"

                        />
                    </el-select>
<!--                    <el-button :style="showManageDepart[scope.$index]" @click="finishManageRight(scope.$index,scope.row.id,scope.el-select)">确认</el-button>-->
                </template>
            </el-table-column>
        </el-table>
        <el-divider style="margin-top: 0" ></el-divider>
    </el-card>
</template>

<script>
    import axios from "axios";
    import {ref} from "@vue/reactivity";
    import {ElMessage, ElMessageBox} from "element-plus";

    export default {
        name:'DetailPM',
        data(){
            return{
                id:99,
                value:ref(''),
                formInfo:null,
                staffnum:0,
                staff:[
                    {
                        "id":1,
                        "uid":"419210??",
                        "pos":"林志康的爹",
                        "name":"杜沛霖",
                        "telephone":110,
                        "depart":"2",
                        "right":9
                    },
                    {
                        "id":2,
                        "uid":41921011,
                        "pos":"林志康的爹",
                        "name":"杜沛霖",
                        "telephone":1881007821,
                        "depart":"1",
                        "right":1
                    },
                    {
                        "id":3,
                        "uid":419212011,
                        "pos":"杜沛霖的儿",
                        "name":"林志康",
                        "telephone":1881007821,
                        "depart":"2",
                        "right":1
                    },
                    {
                        "id":4,
                        "uid":4192111,
                        "pos":"林志康的爹",
                        "name":"陶沙",
                        "telephone":1881007821,
                        "depart":"1",
                        "right":1
                    },
                    {
                        "id":5,
                        "uid":4192101,
                        "pos":"林志康的爹",
                        "name":"庞小博",
                        "telephone":1881007821,
                        "depart":"1",
                        "right":1
                    },
                    {
                        "id":6,
                        "uid":419121011,
                        "pos":"林志康的爹",
                        "name":"ABC",
                        "telephone":1881007821,
                        "depart":"2",
                        "right":1
                    },
                ],
                // select:[],
                options:[
                    {
                        value:"1",
                        label:"1"
                    },
                    {
                        value:'2',
                        label:"2"
                    },
                    {
                        value:'3',
                        label:"3"
                    },{
                        value:'4',
                        label:"4"
                    },{
                        value:'5',
                        label:"5"
                    },
                ],
                showManageDepart:[],
                manageButton:[],
                // showManageDepartMid:[]
            }
        },
        methods:{
            ToDeleteTemplate(index, id) {
                ElMessageBox.confirm("你确定要删除此人员吗", "注意！", {
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
          getId(){
              let routeid=this.$route.params.id;
              this.id=routeid;
              console.log(this.id);
          },
          // getStaffbyId(id){
            //     axios.post("","{
            //      id=id
            //     }").then(res=>{
            //          console.log(res);
            //          this.formInfo=JSON.parse(res);
            //
            //     })
          //     this.formInfo=JSON.parse()
          // },
            startManageRight(index,id){
                var i;
                for(i=0;i<this.staffnum;i++){
                    if(this.showManageDepart[i].display=="block"){
                        if(index==i)
                            continue
                        this.showManageDepart[i].display="none";
                        this.manageButton[i]="管理权限";
                    }
                }
                if(this.showManageDepart[index].display=="block") {
                    this.showManageDepart[index].display = "none";
                    this.manageButton[index]="管理权限";
                }
                else {
                    this.showManageDepart[index].display = 'block';
                    this.manageButton[index] = "取消";
                    // console.log(this.staff);
                }
            },
            finishManageRight(index,id,value){
                this.showManageDepart[index].display="none";
                this.manageButton[index]="管理权限";
                console.log(index);
                // console.log(this.showManageDepart[0][index]);
                // console.log(this.showManageDepart);
                // console.log(index,id,value);
                this.staff[index].right=value;
                // this.getStaffbyId();
            },
            clearSelectStyle(index){
                this.showManageDepart[index].display="none";
            },
            back(){
                this.$router.push('/PersonnelManagement');
            },
            beforeenter(){
                this.staffnum=this.formInfo.length;
                // const element={"display":"none"};
                var i;
                for(i=0;i<this.staffnum;i++){
                    this.showManageDepart.push({"display":"none"});
                    this.manageButton.push("管理权限");
                    // this.select.push({
                    //     "<el-select :style=\"showManageDepart[scope.$index]\"v-model=\"value\"\"item.value\":label=\"item.label\":value=\"item.value\" /> </el-select>'"
                    // })
                }
                // this.showManageDepart=stylelist;
                // console.log(this.showManageDepart);
                // console.log(this.showManageDepart[0]);
                // this.showManageDepart=this.showManageDepartMid;
            }
        },
        created(){
            this.getId();
            this.formInfo=this.staff;
            this.beforeenter();
        },
        mounted(){
            // axios
            //     .get("/PersonnelManagement")
            //     .then(response=>{console.log(response.id);})
            //     .catch(err=>{console.log(err);});
            console.log(this.$route.params.id);
            // this.formInfo=JSON.parse(this.staff);
            this.formInfo=this.staff;

        }

    }
</script>
