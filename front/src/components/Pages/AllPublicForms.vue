<template>
  <h1>我的表单</h1>
  {{ formKind }}
  <div>
    <el-radio-group v-model="formKind" @change="selectChange">
      <el-radio-button label="all">全部表单</el-radio-button>
      <el-radio-button label="query">统计表</el-radio-button>
      <el-radio-button label="approval">审批表</el-radio-button>
    </el-radio-group>
  </div>
  <el-card>
    <el-table :data="returnList">
      <el-table-column prop="id" label="表单id" />
      <el-table-column
        prop="type"
        label="表单类型"
        :formatter="judgeFormKind"
      />
      <el-table-column label="是否正在发布">
        <template #default="scope">
          <el-switch
            v-model="scope.row.isPublic"
            inline-prompt
            active-text="是"
            inactive-text="否"
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
            :loading="formStatus[scope.$index]"
            @change="beforeSwitch(scope.$index, scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            size="small"
            plain
            type="primary"
            @click="showFormURL(scope.row.url)"
            >表单链接</el-button
          >
          <el-button
            size="small"
            plain
            type="success"
            @click="viewFormResult(scope.row.id)"
            >查看填写结果</el-button
          >
          <el-button
            size="small"
            plain
            type="info"
            @click="viewFormTemplate(scope.row.tid)"
            >查看表单模板</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>


<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";
export default {
  data() {
    return {
      formKind: "all",
      returnList: null,
      formStatus:[],
      testData: `{
    "errorCode": 66666,
    "message": "查询成功",
    "data": [
    {
        "id": 19,
        "uid": 10,
        "tid": 23,
        "did": 0,
        "name": "统计表单1",
        "description": "统计表单1",
        "start": 1653748023000,
        "end": 1653926400000,
        "url": "http://localhost:9090/#/justFillForm/19",
        "location": "0aa040be4bd24ac8a73598c6d87102bd",
        "type": 0,
        "length": 6,
        "isPublic":true
    },
    {
        "id": 20,
        "uid": 10,
        "tid": 23,
        "did": 0,
        "name": "统计表单2",
        "description": "统计表单2",
        "start": 1653667200000,
        "end": 1654272000000,
        "url": "http://localhost:9090/#/justFillForm/20",
        "location": "7bce362e45ba456782ecc6d59ac26bb3",
        "type": 0,
        "length": 6,
        "isPublic":true
    },
    {
        "id": 21,
        "uid": 10,
        "tid": 24,
        "did": 0,
        "name": "审批表单1",
        "description": "审批表单1",
        "start": 1653667200000,
        "end": 1653926400000,
        "url": "http://localhost:9090/#/justFillForm/21",
        "location": "4cc0eb5e66ba496aa4371845dfec2905",
        "type": 1,
        "length": 3,
        "isPublic":true
    },
    {
        "id": 22,
        "uid": 10,
        "tid": 24,
        "did": 0,
        "name": "审批表单2",
        "description": "审批表单2",
        "start": 1653748234000,
        "end": 1654272000000,
        "url": "http://localhost:9090/#/justFillForm/22",
        "location": "e73919a023d64f0287476ec67ca000e8",
        "type": 1,
        "length": 3,
        "isPublic":true
    }
]
}`,
    };
  },
  created() {
    console.log(1111111);
    var returnData;
    // axios
    //   .post("/api/sheets/all", {
    //     pageNum: 1,
    //     size: 10,
    //   })
    //   .then((res) => {
    //     returnData = JSON.parse(res.data);
    //     if (returnData.errorCode === 66666) {
    //       console.log(returnData);
    //       this.returnList = JSON.parse(returnData.data);
    //       console.log(this.returnList);
    //     }
    //   });

    returnData = JSON.parse(this.testData);
    this.returnList = returnData.data;
    for(let item in this.returnList){
      this.formStatus.push(false)
    }
    // console.log(this.returnList);
  },
  methods: {
    beforeSwitch(index, row){
      // 实际上这个是处理switch（change）事件，没有before
      console.log(row)
      this.formStatus[index] = true
      // axios.post('url',{

      // }).then(res => {
      //   this.formStatus[index] = false
      // })
      setTimeout(_ => {
        this.formStatus[index] = false
      },1000)
    },
    selectChange(value) {
      console.log(value);
      var returnData;
      axios
        .post("/api/sheets/" + value, {
          pageNum: 1,
          size: 10,
        })
        .then((res) => {
          returnData = JSON.parse(res.data);
          if (returnData.errorCode === 66666) {
            console.log(returnData);
            this.returnList = JSON.parse(returnData.data);
          }
        });
      // returnData = JSON.parse(this.testData);
      // this.returnList = returnData.data;
      // console.log(this.returnList);
    },
    judgeFormKind(row, column, cellValue, index) {
      // console.log(cellValue)
      if (cellValue === 0) {
        return "统计表";
      } else {
        return "审批表";
      }
    },
    showFormURL(url) {
      ElMessageBox.alert("表单的URL：" + url, "表单链接", {
        confirmButtonText: "确认",
      }).then(() => {
        navigator.clipboard.writeText(url);
        ElMessage({
          type: "success",
          message: "地址已复制到剪贴板",
        });
      });
    },
    viewFormTemplate(tid) {},
    viewFormResult(id) {
      this.$router.push({
        name: "ResultOfOneForm",
        params: {
          id: id,
        },
      });
    },
  },
  mounted() {
    // var returnData;
    // axios.post('/api/sheets/all',{
    //     'pageNum':1,
    //     'size':10
    // }).then(res =>{
    //     returnData = JSON.parse(res.data);
    //     if(returnData.errorCode === 66666){
    //         this.returnList = JSON.parse(returnData.data)
    //     }
    // })
  },
};
</script>