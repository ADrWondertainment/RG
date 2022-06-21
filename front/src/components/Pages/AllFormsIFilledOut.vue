<template>
  <div>
    <h1>我填过的表单</h1>
    <el-table :data="objList">
      <el-table-column prop="id" label="表单编号" />
      <el-table-column prop="name" label="表单名称" />
      <el-table-column label="表单类型" :formatter="formType" />
      <el-table-column label="表单状态" :formatter="formStatus" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button @click="toOneForm(scope.row)"
            >查看、继续填写表单</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      testStr: `{
    "errorCode": 66666,
    "message": "成功获取调查信息",
    "data": [
    {
        "id": 22,
        "sid": 19,
        "uid": 10,
        "type": 0,
        "name": "统计表样例",
        "status": 0,
        "done": 1,
        "pass": 0
    },
    {
        "id": 23,
        "sid": 20,
        "uid": 10,
        "type": 1,
        "name": "审批表样例",
        "status": "等待主管审批",
        "done": 0,
        "pass": 0
    }
]
}`,
      objList: null,
    };
  },
  mounted() {
    axios
      .post("/api/sheets/answer", {
        pageNum: 1,
        size: 10,
      })
      .then((res) => {
        console.log(res.data);
        this.objList = [];
        if (res.data.errorCode === 66666) {
          for (let item in res.data.data) {
            this.objList.push(res.data.data[item]);
          }
        }
      });

    // console.log(JSON.parse(this.testStr));
    // this.objList = JSON.parse(this.testStr).data;
    console.log(this.objList);
  },
  methods: {
    formStatus(row, column, cellValue, index) {
      console.log(row);
      console.log(row.flow != "");
      console.log(row.flow != null);
      if (row.done === 0) {
        return "未完成，已暂存";
      } else {
        if (row.type === 0) {
          return "已完成";
        } else {
          if (
            row.pass === 1 &&
            (row.flow != "" || row.flow != null) &&
            row.flow != "[]"
          ) {
            return "group" + JSON.parse(row.flow)[0] + "审核中";
          } else if (row.pass === 0) {
            return "审批拒绝";
          } else {
            return "审批通过";
          }
        }
      }
    },
    formType(row, column, cellValue, index) {
      if (row.type === 0) {
        return "统计表";
      } else {
        return "审批表";
      }
    },
    toOneForm(row) {
      console.log(row);
      this.$router.push("/justFillForm/" + row.id + "/split/yes");
    },
  },
};
</script>
