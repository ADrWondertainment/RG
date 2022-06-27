<template>
  <h1 title="123">审批结构管理</h1>
  <el-card style="width: 70%; margin-left: 15%; margin-bottom: 70%">
    <el-table :data="data" default-expand-all>
      <el-table-column label="审批组编号">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            round
            size="small"
            type="primary"
            style="margin-left: 30px"
            @click="addPrincipal(scope.$index)"
            plain
          >
            添加组成员
          </el-button>
          <el-button
            round
            size="small"
            type="danger"
            @click="remove(scope.$index)"
            style="margin-left: 30px"
            plain
          >
            删除审批组
          </el-button>
        </template>
      </el-table-column>
      <el-table-column type="expand" label="组中成员" width="180">
        <template #default="props">
          <!-- {{ props.row.member }} -->

          <el-table
            :data="props.row.member"
            style="width: 90%; margin-left: 10%"
          >
            <el-table-column prop="id" label="成员工号"></el-table-column>
            <el-table-column prop="email" label="成员名称"></el-table-column>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="deleteGroupMember(props.row.member, scope.$index)"
                  >删除成员</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <!-- {{member}} -->
        </template>
      </el-table-column>
    </el-table>

    <el-affix
      position="bottom"
      :offset="20"
      style="margin-left: 65%; padding-top: 20px"
      ><el-button type="primary" plain @click="addNewGroupLog = true"
        >添加审批组</el-button
      ><el-button type="success" plain @click="submit"
        >提交</el-button
      ></el-affix
    >
  </el-card>

  <el-dialog v-model="addNewGroupLog" title="添加组织结构" width="50%">
    <el-row :gutter="20">
      <el-col :span="6">请输入新建组的名称</el-col>
      <el-col :span="18"><el-input v-model="newGroupName" /></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          type="warning"
          @click="
            addNewGroupLog = false;
            tempVar = null;
          "
          plain
          >取消</el-button
        >
        <el-button type="success" @click="addApproveGroup" plain
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="setPrincipalLog" title="添加小组负责人" width="50%">
    <el-row :gutter="20">
      <el-col :span="24">请选择添加的成员</el-col>
    </el-row>
    <el-row>
      <el-table :data="availableMembers" @current-change="handleCurrentChange">
        <el-table-column property="typeId" label="成员工号"></el-table-column>
        <el-table-column property="email" label="成员姓名"></el-table-column>
        <el-table-column
          property="department"
          label="成员部门"
        ></el-table-column>
        <el-table-column property="role" label="成员职位"></el-table-column>
      </el-table>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          type="warning"
          @click="
            setPrincipalLog = false;
            tempVar = null;
          "
          plain
          >取消</el-button
        >
        <el-button
          type="success"
          @click="
            setPrincipalLog = false;
            confirmAddPrincipal();
          "
          plain
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script >
import { ElMessage, ElMessageBox } from "element-plus";
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      addNewGroupLog: false,
      setPrincipalLog: false,

      newGroupName: "",
      principal: "",

      defaultProps: {
        children: "children",
        label: "label",
      },

      tempVar: null,

      data: [
        {
          label: "业务部",
          id: 1,
          member: [
            { id: 1, email: "张三" },
            { id: 2, email: "李四" },
          ],
        },
        {
          label: "市场分析",
          id: 2,
          member: [],
        },
        {
          label: "信息搜集",
          id: 3,
          member: [],
        },
      ],

      availableMembers: [
        {
          id: 2,
          uid: 11,
          cid: 2,
          level: 1,
          groupId: 2,
          email: "cuser1",
          role: "经理",
          dept: "事业部",
        },
        {
          id: 3,
          uid: 12,
          cid: 2,
          level: 1,
          groupId: 2,
          email: "cuser2",
          role: "经理",
          dept: "事业部",
        },
        {
          id: 4,
          uid: 13,
          cid: 2,
          level: 1,
          groupId: 3,
          email: "cuser3",
          role: "经理",
          dept: "事业部",
        },
        {
          id: 5,
          uid: 14,
          cid: 2,
          level: 1,
          groupId: 3,
          email: "cuser4",
          role: "经理",
          dept: "事业部",
        },
        {
          id: 6,
          uid: 15,
          cid: 2,
          level: 1,
          groupId: 0,
          email: "cuser5",
          role: "经理",
          dept: "事业部",
        },
      ],

      allMembers: [],
    };
  },
  methods: {
    handleNodeClick(data) {
      console.log(data);
    },
    remove(groupIndex) {
      this.data.splice(groupIndex, 1);
    },

    // append(data) {
    //   this.tempVar = data;
    //   this.addNewGroupLog = true;
    // },
    // confirmAppend(data) {
    //   const childNode = { label: this.lowerGroupName, children: [] };
    //   if (!data.children) {
    //     data.children = [];
    //   }
    //   data.children.push(childNode);
    //   this.addNewGroupLog = false;
    //   this.lowerGroupName = "";
    //   this.tempVar = null;
    //   console.log(this.data);
    // },
    addApproveGroup() {
      this.data.push({
        label: this.newGroupName,
        id: null,
        member: [],
      });
      this.newGroupName = "";
      this.addNewGroupLog = false;
    },
    addPrincipal(data) {
      console.log(data);
      this.deleteRedundent();
      this.tempVar = data;
      this.setPrincipalLog = true;
    },
    handleCurrentChange(rowObj) {
      this.principal = rowObj;
      console.log(rowObj);
    },
    confirmAddPrincipal() {
      // data.principal = this.principalName;
      console.log(this.tempVar);
      console.log(this.principal);
      this.data[this.tempVar].member.push({
        id: this.principal.typeId,
        email: this.principal.email,
      });
      // this.tempVar.push({ id: this.principal.id, email: this.principal.email });
      // this.principalName = "";
      // this.tempVar.push
    },
    deleteGroupMember(array, index) {
      console.log(array);
      console.log(index);
      array.splice(index, 1);
    },

    // 页面提交
    submit() {
      console.log;
      let submitData = [];
      let tempMember = [];
      for (let item in this.data) {
        tempMember = [];
        submitData.push({
          id: this.data[item].id || null,
          member: [],
        });
        if (this.data[item].member.length > 0) {
          for (let index in this.data[item].member) {
            submitData[item].member.push(this.data[item].member[index].id);
          }
        }
      }
      console.log(submitData);
      axios
        .post("/api/approves/set", {
          submitData,
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.errorCode === 66666) {
            ElMessage.success("提交成功");
            this.getOrg();
          }
        });
    },
    getOrg() {
      this.data = [];
      axios.post("/api/approves/groups", {}).then((res) => {
        this.data = res.data.data;
        // console.log(res.data);
        // this.getMembers();
      });
    },
    getMembers() {
      axios.post("/api/users/showbylevel", {}).then((res) => {
        for (let item in res.data.data) {
          this.allMembers.push(res.data.data[item]);
        }
        // console.log(res.data);
        // console.log(this.availableMembers);
      });
    },
    deleteRedundent() {
      console.log(this.data);
      console.log(this.availableMembers);
      this.availableMembers = this.allMembers;
      // 上帝也不愿看懂这行代码
      for (let inner in this.data) {
        for (let outer in this.availableMembers) {
          for (let innerinner in this.data[inner].member) {
            console.log(
              this.data[inner].member[innerinner].id,
              this.availableMembers[outer].typeId
            );
            if (
              this.data[inner].member[innerinner].id ===
              this.availableMembers[outer].typeId
            ) {
              this.availableMembers.splice(outer, 1);
            }
          }
        }
      }
    },
  },

  mounted() {
    this.getOrg();
    this.getMembers();
    // this.deleteRedundent();
  },
};
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
  height: 0px;
}
</style>