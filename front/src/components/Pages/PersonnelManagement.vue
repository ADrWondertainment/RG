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
                            @click="EnterDepartment(scope.$index, scope.row.id,scope.row.name)"
                            title="进入部门"
                            type="success"
                            plain
                            round
                    >进入部门</el-button
                    >
                    <el-button
                            size="small"
                            @click="EditDepart(scope.$index,scope.row.id)"
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
            >
                <template :style="showManagePosText[scope.$index]" #default="scope">
                    <el-input  v-model="midPosition[scope.$index].role"
                               :placeholder="midPosition[scope.$index].role"
                    />
                    <el-button @click="finishManagePosition(scope.$index,scope.row.id,midPosition[scope.$index].role)">确认</el-button>
                </template>
            </el-table-column>
            <el-table-column
                    prop="level"
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
                            @click="startManagePosition(scope.$index,scope.row.id)"
                            :title="this.positionButton[scope.$index]"
                            type="success"
                            plain
                            round
                    >{{this.positionButton[scope.$index]}}</el-button
                    >
                    <el-button
                            size="small"
                            @click="startMoveStaff(scope.row.id,this.did)"
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
<!--        移动人员-->
        <el-dialog v-model="showMoveStaff" title="移动人员" width="35%">
                <div>当前所在部门：{{oldDepart}}</div>
                <div>请输入待加入的部门名称:
                </div>
                <el-input v-model="newDepart.newname" :placeholder="newDepart.newname" />
                <el-button @click="finishMoveStaff(this.newDepart.id,this.newDepart.newname)" type="success">确认</el-button>
        </el-dialog>
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
                oldDepart:"未分配",
                newDepart:{
                    id:0,
                    newname:''
                },
                departs: [],
                showManageDepart: [],
                showMoveStaff:false,
                showManagePosText:[],
                midPosition:[],
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
            },
            getDeparts() {
              this.formInfo.splice(0,this.formInfo.length);
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
                                this.getDeparts();
                                this.getId();
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
            EnterDepartment(index, id,name) {
              // console.log(id);
                this.$router.push({name: "DetailPM", params: {did: id, fid: 0,thisDepart:name}});
            },
            EditDepart(index, did) {
                this.editDepartVisible = true;
                this.$nextTick(() => {
                    //这里的dialog与上面dialog-component组件里面的ref属性值是一致的
                    //init调用的是dialog-component组件里面的init方法
                    //data是传递给弹窗页面的值
                    // console.log(this.formInfo[index].name);
                  console.log("init前的did:",did);
                    this.$refs.dialog.init(this.formInfo[index].name, index, did);
                })
            },
            finishEdit(index, name, did) {

                // console.log("成了");
                console.log(index, name);
                console.log("did==",did);
                axios.post('api/users/udept', {
                    did: did,
                    dept: name
                }).then(() => {
                    alert("编辑成功");
                  this.editDepartVisible = false;
                  this.getDeparts();
                })
                    .catch(_ => {
                        alert("编辑失败");
                    })
                // this.formInfo[index].name=name

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
            startMoveStaff(id,oldname){
                this.showMoveStaff=true;
                this.oldDepart=oldname;
                this.newDepart.newname=oldname;
                this.newDepart.id=id;
            },
            finishMoveStaff(id,depart){
                axios.post('api/users/sdept',{
                    uid:id,
                    name:depart
                })
                .then(_=>{
                    this.showMoveStaff=false;
                    alert("移动成功");
                    this.getId();
                    this.getDeparts();
                })

            },
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
                this.manageButton[index]="管理权限";
                // console.log(index);
                // console.log(this.showManageDepart[0][index]);
                // console.log(this.showManageDepart);
                // console.log(index,id,value);
                // this.staff[index].level=value;
                axios.post("api/users/ulevel",{
                    level:value,
                    uid:id,
                }).then(_=>{
                    alert("编辑成功");
                    this.getId();
                }).catch(()=>{
                    alert("改变失败");
                })

                this.showManageDepart[index].display="none";
                // this.getStaffbyId();
            },
            startManagePosition(index,id){
                var i;
                for(i=0;i<this.staffnum;i++){
                    if(this.showManagePosText[i].display=="block"){
                        if(index==i)
                            continue
                        this.show
                        this.positionButton[i]="管理角色";
                    }
                }
                if(this.showManagePosText[index].display=="block") {
                    this.showManagePosText[index].display = "none";
                    this.positionButton[index]="管理角色";
                }
                else {
                    this.showManagePosText[index].display = 'block';
                    this.positionButton[index] = "取消";
                    // console.log(this.staff);
                }
            },
            finishManagePosition(index,id,value){
                this.positionButton[index]="管理角色";
                console.log(index);
                // console.log(this.showManageDepart[0][index]);
                // console.log(this.showManageDepart);
                // console.log(index,id,value);
                // this.staff[index].pos=value;
                axios.post('api/users/role',{
                    role:value,
                    uid:id
                }).then(_=>{
                    alert("编辑职位成功");
                    this.getId();
                }).catch(()=>{
                    alert("编译失败");
                })
                this.showManagePosText[index].display="none";
            },
        }
    }
</script>

<style scoped>

</style>