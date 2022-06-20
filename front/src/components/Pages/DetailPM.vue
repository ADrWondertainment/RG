
<!--最后把职位选择改成文本框，也可以：保留选择框，也可以在table的column上改成输入框，高级用户有权限修改职位命名，所有该职位同步修改-->

<template>
    <el-card>
<!--        表头-->
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
                        @click="insertStaff"
                        plain
                >添加人员</el-button
                >
                <el-button
                        style="padding: 20px"
                        title="编辑角色"
                        type="primary"
                        @click="EditPosition"
                        plain
                >编辑角色</el-button
                >
            </el-col>
        </el-row>
<!--        主体表格-->
        <el-table :data="formInfo" style="width: 100%" height="440">
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
            <el-table-column
                    prop="department"
                    label="所在部门"
                    width="180"
                    align="center"
            />

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
<!--                    管理权限选择框-->
                    <el-select :style="showManageDepart[scope.$index]"
                               v-model="value"
                               @change="finishManageRight(scope.$index,scope.row.id,value)"
                               :placeholder=scope.row.right
                               :clearable="false"
                    >
<!--                        <el-button @click="finishManageRight(scope.$index,scope.row.id,scope.$index.label)">确认</el-button>-->
                        <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                    </el-select>
<!--<el-button :style="showManageDepart[scope.$index]" @click="finishManageRight(scope.$index,scope.row.id,scope.el-select)">确认</el-button>-->
<!--                    编辑角色选择框-->
                    <el-select :style="showManagePosition[scope.$index]"
                               v-model="posvalue"
                               @change="finishManagePosition(scope.$index,scope.row.id,posvalue)"
                               :placeholder=scope.row.pos
                               :clearable="false"
                    >
                        <el-option
                                v-for="item in PosOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        />
                    </el-select>
                </template>
            </el-table-column>
        </el-table>
<!--        点击编辑角色后的弹窗-->
        <el-dialog v-model="showEdit" title="编辑角色" >
            <el-table :data="midPosition" style="width: 100%" >
                    <el-table-column type="selection" width="55" />
                <el-table-column
                        prop="value"
                        label="名称"
                        width="180"
                        align="center"
                >
                    <!--输入框-->
                    <template #default="scope">
                    <el-input  v-model="midPosition[scope.$index].value"
                               :placeholder="midPosition[scope.$index].value"
                               :disabled="midPosition[scope.$index].isntEditable"
                    />
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template #default="scope">
                        <el-button
                                size="small"
                                type="danger"
                                @click="ToDeleteTemplate(scope.$index, scope.row.id)"
                                title="删除角色"
                                plain
                                round
                        >删除角色</el-button
                        >
                    </template>
                </el-table-column>
            </el-table>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="cancelEditPos">取消</el-button>
                <el-button type="primary" @click="confirmEditPos">确认</el-button
                >
              </span>
            </template>
        </el-dialog>
<!--        添加人员弹框-->
        <el-dialog v-model="showInsertStaff" title="添加人员">
            <el-table
                    ref="InsertStaff"
                    height="400px"
                    :data="staff"
                    style="width: 100% "
                    @selection-change="InsertStaffSelectionChangeHandle"
            >
                <el-table-column type="selection" width="55" />
                <el-table-column
                        prop="email"
                        label="姓名"
                        width="180"
                        align="center"
                >
                </el-table-column>
<!--                <el-table-column-->
<!--                        prop="name"-->
<!--                        label="姓名"-->
<!--                        width="180"-->
<!--                        align="center"-->
<!--                />-->
            </el-table>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="cancelEditPos">取消</el-button>
                <el-button type="primary" @click="confirmInsertStaff">确认</el-button>
              </span>
            </template>
        </el-dialog>
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
                did:1,
                value:ref(''),
                posvalue:ref(''),
                formInfo:[],
                staffnum:0,
                showEdit:false,
                showInsertStaff:false,
                selectedStaff:[],
                // staff:[
                //     {
                //         "id":1,
                //         "uid":"419210??",
                //         "pos":"林志康的爹",
                //         "name":"杜沛霖",
                //         "telephone":110,
                //         "depart":"2",
                //         "right":9
                //     },
                //     {
                //         "id":2,
                //         "uid":41921011,
                //         "pos":"林志康的爹",
                //         "name":"杜沛霖",
                //         "telephone":1881007821,
                //         "depart":"1",
                //         "right":1
                //     },
                //     {
                //         "id":3,
                //         "uid":419212011,
                //         "pos":"杜沛霖的儿",
                //         "name":"林志康",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                //     {
                //         "id":4,
                //         "uid":4192111,
                //         "pos":"林志康的爹",
                //         "name":"陶沙",
                //         "telephone":1881007821,
                //         "depart":"1",
                //         "right":1
                //     },
                //     {
                //         "id":5,
                //         "uid":4192101,
                //         "pos":"林志康的爹",
                //         "name":"庞小博",
                //         "telephone":1881007821,
                //         "depart":"1",
                //         "right":1
                //     },
                //     {
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                //     {
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                //     {
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                //     {
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                //     {
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },{
                //         "id":6,
                //         "uid":419121011,
                //         "pos":"林志康的爹",
                //         "name":"ABC",
                //         "telephone":1881007821,
                //         "depart":"2",
                //         "right":1
                //     },
                // ],
                staff:[],
                select:[],
                options:[
                    {
                        value:"1",
                        label:"1"
                    },
                    {
                        value:'2',
                        label:"2"
                    }
                ],
                PosOptions:[
                    {
                        value:"林志康的爹",
                        label:"林志康的爹",
                        isntEditable:true
                    },
                    {
                        value:'杜沛霖的儿',
                        label:"杜沛霖的儿",
                        isntEditable:false
                    },
                    {
                        value:'杜沛霖的孙儿',
                        label:"杜沛霖的孙儿",
                        isntEditable:false
                    },{
                        value:'杜沛霖的好孙儿',
                        label:"杜沛霖的好孙儿",
                        isntEditable:false
                    },{
                        value:"林志康的爷爷",
                        label:"林志康的爷爷",
                        isntEditable:false
                    },
                ],
                showManageDepart:[],
                showManagePosition:[],
                manageButton:[],
                positionButton:[],
                midPosition:[]
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
            getId(){
              this.formInfo=null;
              this.formInfo=[];
              if(this.$router.params.id!=null)
                  this.did=this.$router.params.id;
              let routeid=this.did;
              axios.post('api/users/showuserbydept',{
                  did:routeid
              }).then(res=>{
                console.log(res.data);
                console.log(Object.keys(res.data.data).length);
                for(var i=0;i<Object.keys(res.data.data).length;i++){
                  this.formInfo.push(res.data.data[i]);
                }
                console.log(this.formInfo);
                this.beforeenter();
                  // this.formInfo=res.data.data;
              })
              //     .catch(()=>{
              //     alert("获取部门人员失败")
              // })
              this.did=routeid;
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
                this.manageButton[index]="管理权限";
                // console.log(index);
                // console.log(this.showManageDepart[0][index]);
                // console.log(this.showManageDepart);
                // console.log(index,id,value);
                this.staff[index].right=value;
                axios.put("api/users/",{
                    level:value,
                    uid:id,
                }).then(_=>{
                    alert("编辑成功");
                }).catch(()=>{
                    alert("改变失败");
                })

                this.showManageDepart[index].display="none";
                // this.getStaffbyId();
            },
            startManagePosition(index,id){
                var i;
                for(i=0;i<this.staffnum;i++){
                    if(this.showManagePosition[i].display=="block"){
                        if(index==i)
                            continue
                        this.showManagePosition[i].display="none";
                        this.positionButton[i]="管理角色";
                    }
                }
                if(this.showManagePosition[index].display=="block") {
                    this.showManagePosition[index].display = "none";
                    this.positionButton[index]="管理角色";
                }
                else {
                    this.showManagePosition[index].display = 'block';
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
                }).then(res=>{
                    alert("编辑职位成功");
                }).catch(()=>{
                    alert("编译失败");
                })
                this.showManagePosition[index].display="none";
            },
            clearSelectStyle(index){
                this.showManageDepart[index].display="none";
                this.showManagePosition[index].display='none';
            },
            back(){
                if(this.did==1)
                this.$router.push('/PersonnelManagement');
                else
                    this.did--;
                    this.getId();
            },
            beforeenter(){
                this.staffnum=this.formInfo.length;
                // const element={"display":"none"};
                var i;
                for(i=0;i<this.staffnum;i++){
                    this.showManageDepart.push({"display":"none"});
                    this.manageButton.push("管理权限");
                    this.showManagePosition.push({"display":"none"});
                    this.positionButton.push("管理角色");
                    // this.select.push({
                    //     "<el-select :style=\"showManageDepart[scope.$index]\"v-model=\"value\"\"item.value\":label=\"item.label\":value=\"item.value\" /> </el-select>'"
                    // })
                }
                // this.showManageDepart=stylelist;
                console.log(this.showManageDepart);
                // console.log(this.showManageDepart[0]);
                // this.showManageDepart=this.showManageDepartMid;
            },
            EditPosition(){
                this.showEdit=true;
                let mmid;
                mmid=JSON.stringify(this.PosOptions);
                this.midPosition=JSON.parse(mmid);
                // console.log(this.PosOptions);
            },
            confirmEditPos(){
                let mmid;
                mmid=JSON.stringify(this.midPosition);
                this.PosOptions=JSON.parse(mmid);
                this.showEdit=false;
            },
            cancelEditPos(){
                this.showEdit=false;
            },
            insertStaff(){
                this.showInsertStaff=true;
            },
            cancelInsertStaff(){
                this.showInsertStaff=false;
            },
            InsertStaffSelectionChangeHandle(val){
                this.selectedStaff=val;
            },
            confirmInsertStaff(){
                this.$nextTick(()=>{
                    let deltaStaff;
                    deltaStaff=[];
                    deltaStaff=this.selectedStaff;
                    for(var i=0;i<this.selectedStaff.length;i++){
                        this.staff.push(this.selectedStaff[i]);
                    }
                    // console.log(deltaStaff);
                    // console.log(this.$refs.InsertStaff.selection);
                    // console.log(this.selectedStaff);
                    this.selectedStaff=[];
                    this.getId();
                    this.formInfo=this.staff;
                    this.beforeenter();
                })

                this.showInsertStaff=false;



            }
        },
        created(){
            // this.getId();
          // this.$nextTick(_=>{
          //   this.beforeenter();

          // })
        },
        mounted(){
            // axios
            //     .get("/PersonnelManagement")
            //     .then(response=>{console.log(response.id);})
            //     .catch(err=>{console.log(err);});
            console.log(this.$route.params.id);
            this.getId();
            // this.$nextTick(_=>{
            //   this.beforeenter();

            // })
            // this.formInfo=JSON.parse(this.staff);
            // this.formInfo=this.staff;

        },
    }
</script>
