<template>
  <h1>表单结果展示</h1>
  <!-- <div>表单名称：{{ formDescriptionObj }}</div> -->
  <el-card>
    <el-table :data="formResultObj" stripe>
      <el-table-column type="expand">
        <template #default="props">
          <el-card style="margin-left: 10%; width: 80%">
            <el-table
              :data="allValueArray[props.row.id]"
              v-if="
                formResultObj[props.row.id].type === 'input' ||
                formResultObj[props.row.id].type === 'date-selector'
              "
            >
              <el-table-column
                prop="key"
                label="获取到的答案内容"
              ></el-table-column>
            </el-table>
            <el-table :data="allValueArray[props.row.id]" v-else>
              <el-table-column prop="key" label="选项条目"></el-table-column>
              <el-table-column prop="value" label="选择人数"></el-table-column>
            </el-table>
          </el-card>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="项目序号"></el-table-column>
      <el-table-column prop="description" label="项目描述"></el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      ifDone: false,
      formObj: null,
      formResultObj: null,
      formDescriptionObj: null,
      formResultList: [],
      allValueArray: [],
      testObj: {
        id: "0",
        type: "input",
        value: {
          答案1: 0,
          pxb的答案: 0,
        },
        testArray: [
          {
            key: "答案1",
            value: 0,
          },
        ],
        // oneValueArray:[],
        description: "这是输入框",
      },
      testStr: `{
    "errorCode": 66666,
    "message": "问卷结果返回成功",
    "data": {
    "uid": 10,
    "name": "统计表模板1",
    "type": "0",
    "startTime": "2022-05-27T16:00:00.000Z",
    "endTime": "2022-05-30T16:00:00.000Z",
    "description": "统计表模板1",
    "contentLength": 6,
    "originContent": [{"type":"input","isRequired":false,"description":"这是输入框","value":[]},{"type":"single-check","isRequired":false,"description":"这是单选题","value":["选项1","选项2"]},{"type":"multi-check","isRequired":false,"description":"这是多选题","value":["选项1","选项2"]},{"type":"date-selector","isRequired":false,"description":"这是日期选择","value":[]},{"type":"pull-selector","isRequired":false,"description":"这是下拉列表","value":["表项1","表项2"]},{"type":"num-input","isRequired":false,"description":"这是数字输入框","value":[]}],
    "content": [
        {
            "id": "0",
            "type": "input",
            "value": {
                "答案1": 0,
                "pxb的答案": 0
            },
            "description": "这是输入框"
        },
        {
            "id": "1",
            "type": "single-check",
            "value": {
                "选项2": 1,
                "选项1": 1
            },
            "description": "这是单选题"
        },
        {
            "id": "2",
            "type": "multi-check",
            "value": {
                "选项2": 1,
                "选项1": 2
            },
            "description": "这是多选题"
        },
        {
            "id": "3",
            "type": "date-selector",
            "value": {
                "2022.05.11": 0,
                "2022.05.28": 0
            },
            "description": "这是日期选择"
        },
        {
            "id": "4",
            "type": "pull-selector",
            "value": {
                "表项1": 1,
                "表项2": 1
            },
            "description": "这是下拉列表"
        },
        {
            "id": "5",
            "type": "num-input",
            "value": {
                "2": 0,
                "3": 0
            },
            "description": "这是数字输入框"
        }
    ]
}
}`,
    };
  },
  mounted() {
    let id = this.$route.params.id;
    axios.get("/api/answers/result/" + id).then((res) => {
      if (res.data.errorCode === 66666) {
        console.log(res.data.data);
        this.formDescriptionObj = res.data.data;
        this.formObj = this.formDescriptionObj.originContent;
        this.formResultObj = this.formDescriptionObj.content;
        console.log("formDescriptionObj", this.formDescriptionObj);
        console.log("formObj", this.formObj);
        console.log("formResultObj", this.formResultObj);
      }
    });

    // this.formDescriptionObj = JSON.parse(this.testStr);
    // this.formObj = this.formDescriptionObj.data.originContent;
    // this.formResultObj = this.formDescriptionObj.data.content;

    // console.log(this.formDescriptionObj);
    // console.log(this.formObj);
    // console.log(this.formResultObj);
    // console.log(this.allValueArray);
    // this.Obj2Array(this.testObj);
    for (let oneFormResult in this.formResultObj) {
      console.log(oneFormResult);
      this.formResultList.push(this.formResultObj[oneFormResult]);
      this.allValueArray.push(this.Obj2Array(this.formResultObj[oneFormResult]));
    }
    console.log(this.allValueArray);
  },
  methods: {
    Obj2Array(obj) {
      let resultArray = [];
      for (let item of Object.keys(obj.value)) {
        console.log(item);
        resultArray.push({
          key: item,
          value: obj.value[item],
        });
      }
      return resultArray;
    },
  },
};
</script>