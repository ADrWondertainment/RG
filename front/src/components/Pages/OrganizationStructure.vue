<template>
  <h1 title="123">审批结构管理</h1>
  <el-card style="width: 70%; margin-left: 15%; margin-bottom: 70%">
    <el-table :data="data" default-expand-all>
      <el-table-column label="审批组名称">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <span style="margin-left: 10px">{{ scope.row.label }}</span>
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
          <!-- {{ props.row.members }} -->

          <el-table
            :data="props.row.members"
            style="width: 90%; margin-left: 10%"
          >
            <el-table-column prop="id" label="成员工号"></el-table-column>
            <el-table-column prop="email" label="成员名称"></el-table-column>
            <el-table-column label="操作">
              <template #default="scope">
                <el-button
                  size="small"
                  type="danger"
                  @click="deleteGroupMember(props.row.members, scope.$index)"
                  >删除成员</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <!-- {{members}} -->
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
      <el-table :data="allMembers" @current-change="handleCurrentChange">
        <el-table-column property="id" label="成员工号"></el-table-column>
        <el-table-column property="email" label="成员姓名"></el-table-column>
        <el-table-column property="dept" label="成员部门"></el-table-column>
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
import { ElMessageBox } from "element-plus";
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
          members: [{ id: 1, email: "张三" }],
        },
        {
          label: "市场分析",
          id: 2,
          members: [],
        },
        {
          label: "信息搜集",
          id: 3,
          members: [],
        },
      ],

      allMembers: [
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
        members: [],
      });
      this.newGroupName = "";
      this.addNewGroupLog = false;
    },
    addPrincipal(data) {
      console.log(data);
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
      this.data[this.tempVar].members.push({ id: this.principal.id, email: this.principal.email })
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
      axios.post("/api/approves/set", {

      }).then((res) => {
        console.log(res.data)
      });
    },
    getOrg() {
      this.data = [];
      axios.post("/api/approves/groups", {}).then((res) => {
        console.log(res.data);
      });
    },
    getMembers() {
      this.members = [];
      axios.post("", {}).then((res) => {
        console.log(res.data);
      });
    },
  },

  mounted() {
    this.getOrg()
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