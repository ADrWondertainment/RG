<template>
  <h1 title="123">组织结构管理</h1>
  <el-card style="width: 70%; margin-left: 15%">
    <el-tree :data="data" default-expand-all :expand-on-click-node="false">
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <el-tag type="info" effect="plain">{{ node.label }}</el-tag>
          <span>
            <el-button
              round
              size="small"
              type="success"
              @click="append(data)"
              plain
            >
              添加下设组织
            </el-button>
            <el-button
              round
              size="small"
              type="primary"
              style="margin-left: 30px"
              @click="setPrincipalLog = true"
              plain
            >
              设置负责人
            </el-button>
            <el-button
              round
              size="small"
              type="danger"
              @click="remove(node, data)"
              style="margin-left: 30px"
              plain
            >
              删除此组织
            </el-button>
          </span>
        </span>
      </template>
    </el-tree>
  </el-card>

  <el-dialog
    v-model="addLowerGroupLog"
    title="添加组织结构"
    width="50%"
  >
    <el-row :gutter="20">
      <el-col :span="6">请输入下设组织名称</el-col>
      <el-col :span="18"><el-input v-model="lowerGroupName" /></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addLowerGroupLog = false">Cancel</el-button>
        <el-button type="primary" @click="addLowerGroupLog = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>

  <el-dialog
    v-model="setPrincipalLog"
    title="选择组织负责人"
    width="50%"
  >
    <el-row :gutter="20">
      <el-col :span="6">请输入负责人名称</el-col>
      <el-col :span="18"><el-input v-model="principalName" /></el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="setPrincipalLog = false">Cancel</el-button>
        <el-button type="primary" @click="setPrincipalLog = false"
          >Confirm</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script >
import { ElMessageBox } from "element-plus";
export default {
  name: "App",
  data() {
    return {
      addLowerGroupLog: false,
      setPrincipalLog: false,

      lowerGroupName: "",
      principalName: "",

      defaultProps: {
        children: "children",
        label: "label",
      },
      data: [
        {
          label: "Level one 1",
          children: [
            {
              label: "Level two 1-1",
              children: [
                {
                  label: "Level three 1-1-1",
                  children: [],
                },
              ],
            },
          ],
        },
        {
          label: "Level one 2",
          children: [
            {
              label: "Level two 2-1",
              children: [
                {
                  label: "Level three 2-1-1",
                },
              ],
            },
            {
              label: "Level two 2-2",
              children: [
                {
                  label: "Level three 2-2-1",
                },
              ],
            },
          ],
        },
        {
          label: "Level one 3",
          children: [
            {
              label: "Level two 3-1",
              children: [
                {
                  label: "Level three 3-1-1",
                },
              ],
            },
            {
              label: "Level two 3-2",
              children: [
                {
                  label: "Level three 3-2-1",
                },
              ],
            },
          ],
        },
      ],
    };
  },

  methods: {
    handleNodeClick(data) {
      console.log(data);
    },
    remove(node, data) {
      console.log(data);
      const parent = node.parent;
      console.log(parent);
      const children = parent.data.children || parent.data;
      console.dir(children);
      const index = children.findIndex((d) => d.id === data.id);
      children.splice(index, 1);
      // data.value = [...data.value]
      console.log(this.data);
    },
    append(data) {
      this.addLowerGroupLog = true;
      // const childNode = { label: "test", children: [] };
      // if (!data.children) {
      //   data.children = [];
      // }
      // data.children.push(childNode);
    },
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