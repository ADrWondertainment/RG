<template>

    <el-card>
<!--        表头-->
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
                        @click="addDepart"
                        plain
                >新建部门</el-button
                >
            </el-col>
        </el-row>
<!--        部门名单-->
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
                            @click="EditDepart(scope.$index,scope.row.did)"
                            title="编辑部门"
                            type="success"
                            plain
                            round
                    >编辑部门</el-button
                    >
                    <el-button
                            size="small"
                            type="danger"
                            @click="ToDeleteDepart(scope.$index, scope.row.id)"
                            title="删除部门"
                            plain
                            round
                    >删除部门</el-button
                    >

                </template>
            </el-table-column>
        </el-table>
<!--        人员表单-->
        <el-table :data="staffFormInfo" style="width: 100%" height="440">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="编号" width="180" align="center" />
            <el-table-column
                    prop="email"
                    label="姓名"
                    width="180"
                    align="center"
            />
            <el-table-column
                    prop="role"
                    label="职位"
                    width="180"
                    align="center"
            />
            <el-table-column label="操作" align="center">
                <template #default="scope">
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
                            @click="ToDeleteStaff(scope.$index, scope.row.id)"
                            title="删除人员"
                            plain
                            round
                    >删除人员</el-button
                    >
                </template>
            </el-table-column>
        </el-table>
        <el-divider style="margin-top: 0"></el-divider>
<!--        编辑部门-->
        <edit-depart @childFn="finishEdit" v-if="editDepartVisible" ref="dialog"></edit-depart>
<!--        添加部门-->
        <add-depart  @unshow="closeAddDepart" v-model="AddDepartVisible" ref="add" :before-close="closeAdd" @closed="getDeparts"/>
    </el-card>


</template>

<script>
    import {ElMessage, ElMessageBox} from "element-plus";
    import axios from "axios";
    import EditDepart from "./EditDepart.vue";
    import AddDepart from "./AddDepart.vue";
    import {ref} from "@vue/reactivity";
    export default {
        name: "PersonnelManagement",
        components: {EditDepart, AddDepart},
        data() {
            return {
                did:0,
                staffFormInfo: [],
                formInfo: [],
                staffnum: 0,
                editDepartVisible: false,
                AddDepartVisible: false,
                reserveAdd: false,
                value:ref(''),
                // departs:`[
                //     {
                //         "id":1,
                //         "name":"财务部",
                //         "num":10
                //
                //     },
                //     {
                //         "id":2,
                //         "name":"人力资源部",
                //         "num":11
                //
                //     }
                //
                // ]`,
                departs: [],
                showManageDepart: [],
                manageButton: [],
                showManagePosition: [],
                positionButton: [],
            };
        },
        mounted() {
            this.getDeparts();
            this.getId();
        },
        methods: {
            closeAddDepart() {
                this.AddDepartVisible = false;
                this.getDeparts();
            },
            getDeparts() {
                axios.post('api/users/showdept', {
                    fid: 0
                }).then((res) => {
                    // console.log(res.data);
                    console.log(res.data.data);
                    // let resj;
                    // resj=Object.keys(res.data.data);
                    // let numb;
                    // numb=Object.values(res.data.data);
                    // console.log(resj.type);
                    // console.log(resj[0].type);
                    // // for(var i=0;i<resj.length;i++){
                    // //     resj[i].num=numb[i];
                    // // }
                    // test_company_root
                    // console.log(resj);
                    // this.departs=res.data.data;
                    // console.log(this.departs);
                    // console.log(Object.keys(res.data.data).length);
                    for (var i = 0; i < Object.keys(res.data.data).length; i++) {
                        this.formInfo.push(res.data.data[i]);
                    }
                    console.log(this.formInfo);
                    // console.log(this.departs);
                })
                // .catch(()=>{
                // alert("获取部门失败");
                // })
            },
            ToDeleteDepart(index, id) {
                ElMessageBox.confirm("你确定要删除此部门吗", "注意！", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        axios
                            .post("/api/users/ddept", {
                                did: id,
                                fid: 0
                            })
                            .then(() => {
                                alert("删除成功");
                            }).catch(_ => {
                            alert("删除失败");
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
            EnterDepartment(index, id) {
                this.$router.push({name: "DetailPM", params: {did: id, fid: 0}});
            },
            EditDepart(index, did) {
                this.editDepartVisible = true;
                this.$nextTick(() => {
                    //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
                    //init调用的是dialog-component组件里面的init方法
                    //data是传递给弹窗页面的值
                    // console.log(this.formInfo[index].name);
                    this.$refs.dialog.init(this.formInfo[index].name, index, did);
                })
            },
            finishEdit(index, name, did) {
                this.editDepartVisible = false;
                // console.log("成了");
                console.log(index, name);
                axios.post('api/users/udept', {
                    did: did,
                    department: name
                }).then(() => {
                    alert("编辑成功");
                })
                    .catch(_ => {
                        alert("编辑失败");
                    })
                // this.formInfo[index].name=name
                this.getDeparts();
            },
            addDepart() {
                this.AddDepartVisible = true;
                // this.$refs.add.isshow=true;
            },
            closeAdd(done) {
                ElMessageBox.confirm("你确定要退出吗？", "注意！", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                }).then(_ => {
                    ElMessageBox.confirm("是否保存编辑？", "注意！", {
                        confirmButtonText: "是",
                        cancelButtonText: "否",
                        type: "warning",
                    }).then(_ => {
                        this.reserveAdd = false;
                        this.$refs.add.isReserve(this.reserveAdd);
                        done();
                    }).catch(_ => {
                        this.reserveAdd = true;
                        this.$refs.add.isReserve(this.reserveAdd);
                        // console.log(this.reserveAdd);
                        done();
                    })
                }).catch(_ => {
                })
            },
            // 人员部分
            getId() {
                this.staffFormInfo = null;
                this.staffFormInfo = [];
                let routeid = this.did;
                axios.post('api/users/showuserbydept', {
                    did: routeid
                }).then(res => {
                    console.log(res.data);
                    console.log(Object.keys(res.data.data).length);
                    for (var i = 0; i < Object.keys(res.data.data).length; i++) {
                        this.staffFormInfo.push(res.data.data[i]);
                    }
                    console.log(this.formInfo);
                    this.beforeenter();
                    // this.formInfo=res.data.data;
                })
                //     .catch(()=>{
                //     alert("获取部门人员失败")
                // })
            },
            beforeenter() {
                this.staffnum = this.staffFormInfo.length;
                var i;
                for (i = 0; i < this.staffnum; i++) {
                    this.showManageDepart.push({"display": "none"});
                    this.manageButton.push("管理权限");
                    this.showManagePosition.push({"display": "none"});
                    this.positionButton.push("管理角色");
                }
            },
            ToDeleteStaff(index, id) {
                ElMessageBox.confirm("你确定要删除此人员吗", "注意！", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(() => {
                        axios
                            .post("/api/users/rcuser", {
                                id:id
                            })
                            .then(() => {
                                alert("删除成功");
                                this.getId();
                                // this.formInfo=JSON.parse(this.staff);
                                // this.beforeenter();
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
        }
    }
</script>

<style scoped>

</style>