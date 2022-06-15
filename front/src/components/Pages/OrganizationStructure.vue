<template>
  <h1 title="123">组织结构管理</h1>
  <el-tree
    :data="data"
    :props="defaultProps"
    @node-click="handleNodeClick"
    default-expand-all
  />
  <el-card style="width: 70%; margin-left: 15%">
    <el-tree
      :data="data"
      node-key="id"
      default-expand-all
      :expand-on-click-node="false"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ node.label }}</span>
          <span>
            <el-button round size="small" type="success" @click="append(data)">
              Append
            </el-button>
            <el-button
              round
              size="small"
              type="danger"
              @click="remove(node, data)"
              style="margin-left: 30px"
            >
              Delete
            </el-button>
          </span>
        </span>
      </template>
    </el-tree>
  </el-card>
</template>

<script >
export default {
  name: "App",
  data() {
    return {
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
      console.log(data)
      const parent = node.parent;
      console.log(parent);
      const children = parent.data.children || parent.data;
      console.dir(children);
      const index = children.findIndex((d) => d.id === data.id);
      children.splice(index, 1);
      // data.value = [...data.value]
      console.log(this.data)
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
}
</style>