<template>
  <el-card>
    <el-row>
      <el-col :span="4"></el-col>
      <el-col :span="16">
        <h1 title="123">我创建的表单模板</h1>
      </el-col>
      <el-col :span="4">
        <el-button
          style="padding: 20px"
          title="新建空白表单模板"
          type="primary"
          @click="ToCreateForm"
          plain
          >新建模板</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="formInfo" style="width: 100%">
      <el-table-column prop="id" label="模板编号" width="180" align="center" />
      <el-table-column
        prop="name"
        label="模板名称"
        width="180"
        align="center"
      />
      <el-table-column
        prop="description"
        label="模板描述"
        width="180"
        align="center"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            size="small"
            @click="ToPreviewForm(scope.$index, scope.row.id)"
            title="查看根据模板生成的表单"
            type="success"
            plain
            round
            >预览模板</el-button
          >
          <el-button
            size="small"
            @click="ToChangeForm(scope.$index, scope.row.id)"
            title="查看根据模板生成的表单"
            type="success"
            plain
            round
            >编辑模板</el-button
          >
          <el-button
            size="small"
            @click="ToPublishTemplate(scope.$index, scope.row.id)"
            title="根据模板创建并发布一个表单"
            type="primary"
            plain
            round
            >发布模板</el-button
          >
          <el-button
            size="small"
            type="danger"
            @click="ToDeleteTemplate(scope.$index, scope.row.id)"
            title="彻底删除此表单模板"
            plain
            round
            >删除模板</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-divider style="margin-top: 0"></el-divider>
  </el-card>

  <el-dialog v-model="dialog" title="请输入表单的信息">
    <!-- <template #title>
      <h4>请输入表单的信息</h4>
    </template>
    <template #default> -->
    <el-form>
      <el-row>
        <el-col :span="24">
          <el-form-item label="请输入表单名称"
            ><el-input
              v-model="sheetDescription.name"
              placeholder="要发布的表单名称"
              type="textarea"
              :rows="2"
            ></el-input
          ></el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="请输入表单描述"
            ><el-input
              v-model="sheetDescription.description"
              placeholder="对表单的描述"
              type="textarea"
              :rows="4"
            ></el-input
          ></el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="表单起始时间">
            <el-date-picker
              v-model="sheetDescription.start"
              type="datetime"
              placeholder="表单起始时间"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="表单截止时间">
            <el-date-picker
              v-model="sheetDescription.end"
              type="datetime"
              placeholder="表单截止时间"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="是否发布企业表单">
            <el-switch
              v-model="ifCompany"
              inline-prompt
              active-text="是"
              inactive-text="否"
              @change="getDepartments"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="ifCompany">
        <el-col :span="24">
          <el-form-item label="请选择填写表单的部门">
            <el-select>
              <el-option v-for="item in departments" :key="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      
    </el-form>
    <!-- </template> -->
    <template #footer>
      <div style="flex: auto">
        <el-button type="info" @click="cancelClick">取消</el-button>
        <el-button type="primary" @click="confirmClick">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>


<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
  data() {
    return {
      dialog: false,
      sheetDescription: {
        tid: 0,
        did: 0,
        name: "",
        description: "",
        start: "",
        end: "",
      },
      testStr2: `{
  "id": 6,
	"uid": 1,
	"name": "122434",
	"type": "0",
	"startTime": "",
	"description": "123",
	"endTime": "",
	"contentLength": 2,
	"originContent": [
		{
			"type": "input",
			"isRequired": false,
			"description": "25431",
			"value": []
		},
		{
			"type": "date-selector",
			"isRequired": false,
			"description": "235235",
			"value": []
		}
	],
	"content": [
		{
			"id": "0",
			"type": "input",
			"value": {},
			"description": "25431"
		},
		{
			"id": "1",
			"type": "date-selector",
			"value": {},
			"description": "235235"
		}
	]
}`,
      testStr: `[
    {
        "id": 6,
        "uid": 7,
        "description": "No Description",
        "name": "111",
        "time": 1652457600000,
        "data": {
            "uid": 1,
            "name": "111",
            "type": 1,
            "content": [
                {
                    "id": "0",
                    "type": "input"
                },
                {
                    "id": "1",
                    "type": "sigle-check",
                    "value": {
                        "56565": 0
                    }
                }
            ],
            "originContent": [
            {
              "type": "input",
              "isRequired": false,
              "description": "25431",
              "value": []
            },
            {
              "type": "date-selector",
              "isRequired": false,
              "description": "235235",
              "value": []
            }
          ],
            "contentLength": 2
        },
        "length": 2
    },
    {
        "id": 7,
        "uid": 7,
        "description": "No Description",
        "name": "uuu",
        "time": 1652457600000,
        "data": {
            "uid": 1,
            "name": "uuu",
            "type": 0,
            "content": [
                {
                    "id": "0",
                    "type": "input"
                }
            ],
            "contentLength": 1
        },
        "length": 1
    }
]`,
      formInfo: null,
    };
  },

  mounted() {
        axios.post("api/templates/get", {
          pageNum:1,
          size:10
        })
        .then(res => {
          console.log(res)
          if (res.data.errorCode == 66666) {
            console.log(res.data)
            this.testStr = res.data.data
            console.log(this.testStr)
            this.formInfo = JSON.parse(this.testStr);
          }
        });

    // this.formInfo = JSON.parse(this.testStr);
    // console.log(this.testStr)
    // console.log(JSON.parse(this.test2))
    // console.log(JSON.parse(this.testStr))
    // console.log(this.formInfo);
  },

  methods: {
    getTemplates(){
      axios.post("api/templates/get", {
        pageNum:1,
        size:10
      })
          .then(res => {
            console.log(res)
            if (res.data.errorCode == 66666) {
              console.log(res.data)
              this.testStr = res.data.data
              console.log(this.testStr)
              this.formInfo = JSON.parse(this.testStr);
            }
          });
    },
    ToCreateForm() {
      this.$router.push("/createForm");
    },
    ToPreviewForm(index, id) {
      console.log(this.formInfo[index]);
      console.log(index, id);
      this.$router.push({
        name: "FillForm",
        params: {
          json: JSON.stringify(this.formInfo[index].data),
        },
      });
    },
    ToChangeForm(index, id) {
      console.log(index, id);
      this.$router.push({
        name: "CreateForm",
        params: {
          json: JSON.stringify(this.formInfo[index]),
        },
      });
    },
    ToPublishTemplate(index, id) {
      console.log(index, id);
      this.sheetDescription.tid = id;
      this.dialog = true;
    },
    ToDeleteTemplate(index, id) {
      ElMessageBox.confirm("你确定要删除此模板吗", "注意！", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .delete("/api/templates/" + id, {})
            .then((res) => {
              if (res.data.errorCode === 66666) {
                ElMessage({
                  type: "info",
                  message: "删除成功",
                });
                this.getTemplates();
              }
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

    cancelClick() {
      this.dialog = false;
    },
    confirmClick() {
      let resObj = null;
      axios
        .post("/api/sheets", {
          tid: this.sheetDescription.tid,
          did: this.sheetDescription.did,
          name: this.sheetDescription.name,
          description: this.sheetDescription.description,
          start: this.sheetDescription.start,
          end: this.sheetDescription.end,
        })
        .then((res) => {
          console.log(res)
          if (res.data.errorCode == 66666) {
            console.log(res.data)
            resObj = JSON.parse(res.data.data)
            // ElMessageBox.alert("填写表单的url是:" + "http://localhost:9090/#/justFillForm/" + res.data.data, "Title", {
              ElMessageBox.alert("填写表单的url是:" + "http://localhost:9090/#/justFillForm/" + resObj.insertId, "确认", {
              confirmButtonText: "OK",
              callback: () => {
                ElMessage({
                  type: "success",
                  message: `发布成功`,
                });
              },
            });
          };
          // axios.post("/api/url",{
          //   url:"http://localhost:9090/#/justFillForm/" + resObj.insertId,
          //   id:res.data.data.insertId
          // }).then(res => {
          //   console.log("yes")
          // })
        });
      console.log(this.sheetDescription);
      this.dialog = false;
    },
  },
};
</script>