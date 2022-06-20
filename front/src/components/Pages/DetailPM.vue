
<!--最后把职位选择改成文本框，也可以：保留选择框，也可以在table的column上改成输入框，高级用户有权限修改职位命名，所有该职位同步修改-->

<template>
    <el-card>
<!--        表头-->
        <el-row>
            <el-col :span="4"></el-col>
            <el-col :span="16">
                <h1 title="">{{thisDepartName[this.thisDepartName.length-1]}}部人员管理</h1>
                <el-col :span="4" style="text-align: left" >
                    <el-button type="success" @click="back">
                        <el-icon><Back /></el-icon>返回上一级
                    </el-button>
                </el-col>
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
                        title="新建部门"
                        type="primary"
                        @click="startCreateDepart"
                        plain
                >新建部门</el-button
                >
            </el-col>
        </el-row>
<!--        部门表单-->
        <el-table :data="departFormInfo" style="width: 100%">
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
                            @click="startEditDepart(scope.$index,scope.row.id,scope.row.name)"
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
<!--        人员表格-->
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
                            @click="ToDeleteStaff(scope.$index, scope.row.id)"
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
                               :placeholder=scope.row.level
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
                    height="400px"
                    :data="fatherStaffInfo"
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
<!--        新建部门弹窗-->
        <el-dialog ref="add" title="新建部门" model-value="1" v-model="showInsertDepart" :before-close="closeAdd">
            <el-form :model="depart" label-width="120px">
                <el-form-item label="部门名称">
                    <el-input v-model="depart.name" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="finishCreateDepart">创建</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
<!--      编辑部门弹框  -->
      <el-dialog v-model="showEditDepart" title="编辑部门"  width="35%" >
        <div>当前部门名：{{oldname}}</div>
        <div>请输入新的部门名:
        </div>
        <el-input v-model="depart.name" placeholder="请输入新部门名" clearable/>
        <el-button @click="finishEditDepart(this.depart.name,this.depart.did)" type="success">确认</el-button>
      </el-dialog>
        <!--        移动人员-->
        <el-dialog v-model="showMoveStaff" title="移动人员" width="35%">
            <div>当前所在部门：{{oldDepart}}</div>
            <div>请输入待加入的部门名称:
            </div>
            <el-input v-model="newDepart" placeholder="请输入待加入的部门名称" />
            <el-button @click="finishMoveStaff(this.newDepart.id,this.newDepart.newname)" type="success">确认</el-button>
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
                fid:[],
                did:1,
                thisDepartName:[],
                value:ref(''),
                posvalue:ref(''),
                formInfo:[],
                depart:{
                    did:0,
                    name:''
                },
                departFormInfo:[],
                staffnum:0,
                showEdit:false,
                showEditDepart:false,
                showInsertStaff:false,
                showInsertDepart:false,
                showMoveStaff:false,
                oldDepart:'',
                newDepart:{
                    id:0,
                    newname:''
                },
                selectedStaff:[],
                oldname:'',
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
                fatherStaffInfo:[],
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
                midPosition:[],
                // showManageDepartMid:[]
            }
        },
        methods:{
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
            getId(){
              this.formInfo.splice(0,this.formInfo.length);
              this.departFormInfo.splice(0,this.departFormInfo.length);
              console.log(this.$route.params.did);
              if(this.$route.params.did!=null) {
                  this.did = this.$route.params.did;
                  this.fid.push(this.$route.params.fid);
                  this.thisDepartName.push(this.$route.params.thisDepart);
                  console.log("getId后的fid:",this.fid);
                  this.$route.params.did=null;
                  this.$route.params.fid=null;
              }
              let routeid=this.did;
              axios.post('api/users/showuserbydept',{
                  did:routeid
              }).then(res=>{
                console.log(res.data);
                // console.log(Object.keys(res.data.data).length);
                for(var i=0;i<Object.keys(res.data.data).length;i++){
                  this.formInfo.push(res.data.data[i]);
                }
                console.log(this.formInfo);
                  axios.post('api/users/showdept',{
                      fid:routeid
                  }).then((res)=>{
                      // console.log(res.data);
                      console.log(res.data.data);
                      for(var i=0;i<Object.keys(res.data.data).length;i++){
                          this.departFormInfo.push(res.data.data[i]);
                      }
                      console.log(this.departFormInfo);
                      // console.log(this.departs);
                  })
                this.beforeenter();
                  // this.formInfo=res.data.data;
              })
              //     .catch(()=>{
              //     alert("获取部门人员失败")
              // })
              // console.log("did:",this.did,"fid:",this.fid[this.fid.length-1]);
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
              console.log("fid[-1]:",this.fid[this.fid.length-1]);
                if(this.fid[this.fid.length-1]==0)
                this.$router.push('/PersonnelManagement');
                else {
                    this.did = parseInt(this.fid[this.fid.length-1]);
                    console.log("did:",this.did);
                    this.fid.splice(-1,1);
                    this.thisDepartName.splice(-1,1);
                    this.getId();
                }
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
                // console.log(this.showManageDepart);
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
                    for(var i=0;i<this.selectedStaff.length;i++){
                        axios.post("api/users/",{
                            id:this.selectedStaff.id,
                            name:this.thisDepartName[this.thisDepartName.length-1]
                        })
                        .then(_=>{
                            this.showInsertStaff=false;
                            alert("添加成功");
                            this.selectedStaff.splice(0,this.selectedStaff.length-1);
                            this.getId();
                        })
                    }
                    // let deltaStaff;
                    // deltaStaff=[];
                    // deltaStaff=this.selectedStaff;
                    // for(var i=0;i<deltaStaff.length;i++){
                    //     this.staff.push(deltaStaff[i]);
                    // }
                    // console.log(deltaStaff);
                    // console.log(this.$refs.InsertStaff.selection);
                    // console.log(this.selectedStaff);
                })
            },
            startMoveStaff(id,oldname){
                this.showMoveStaff=true;
                this.oldDepart=oldname;
                this.newDepart.newname=oldname;
                this.newDepart.id=id;
            },
            finishMoveStaff(id,depart){
                axios.post('api/users/',{
                    id:id,
                    dept:depart
                })
                    .then(_=>{
                        this.showMoveStaff=false;
                        alert("移动成功");
                        this.getId();
                    })

            }
            // 部门部分
            EnterDepartment(index, id) {
                this.fid.push(this.did);
                this.did=id;
                this.getId();
            },
            startEditDepart(index,did,name){
              this.showEditDepart=true;
              this.oldname=name;
              this.depart.name=name;
              this.depart.did=did;
            },
            finishEditDepart(name, did) {
              axios.post('api/users/udept', {
                did: did,
                dept: name
              }).then(() => {
                alert("编辑成功");
                this.showEditDepart = false;
                this.getId();
              })
                  .catch(_ => {
                    alert("编辑失败");
                  })

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
            startCreateDepart(){
                this.showInsertDepart=true;
            },
            finishCreateDepart(){
                axios.put("api/users/cdept",{
                    name:this.depart.name,
                    fid:this.did
                }).then(()=>{
                    alert("新建成功");
                    this.showInsertDepart=false;
                    this.getId();
                })
                //     .catch(_=>{
                //     alert("创建失败");
                // });
            },
            isReserve(bool){
                if(bool){
                    this.depart={
                        name:"",
                    };
                }
                else
                    return
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
                        this.isReserve(this.reserveAdd);
                        done();
                    }).catch(_ => {
                        this.reserveAdd = true;
                        this.isReserve(this.reserveAdd);
                        // console.log(this.reserveAdd);
                        done();
                    })
                }).catch(_ => {
                })
            },
        },
        mounted(){
            // console.log(this.$route.params.id);
            this.getId();

        },
    }
</script>
