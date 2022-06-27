<template>
  <div>
    <el-card>
      <el-row>
        <el-col :span="4"></el-col>
        <el-col :span="16">
          <h1 title="123">待审批的表单</h1>
        </el-col>
      </el-row>
      <el-table :data="formInfo" style="width: 100%">
        <el-table-column
          prop="id"
          label="表单编号"
          width="180"
          align="center"
        />
        <el-table-column
          prop="name"
          label="表单名称"
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
              @click="ToApproveForm(scope.$index, scope.row.id)"
              title="查看审批表的具体内容"
              type="success"
              plain
              round
              >查看申请内容</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-divider style="margin-top: 0"></el-divider>
    </el-card>

    <el-dialog v-model="drawer" title="请输入表单的信息">
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
      </el-form>
      <!-- </template> -->
      <template #footer>
        <div style="flex: auto">
          <el-button type="info" @click="cancelClick">取消</el-button>
          <el-button type="primary" @click="confirmClick">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>


<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
  data() {
    return {
      drawer: false,
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
              "value": "fasdfasdf"
            },
            {
              "type": "date-selector",
              "isRequired": false,
              "description": "235235",
              "value": "2022-05-20"
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
    // console.log(111111);
    axios.post("/api/sheets/pass/show", {}).then((res) => {
      this.formInfo = [];
      if (res.data.errorCode == 66666) {
        console.log(res.data.data);
        for (let item in res.data.data) {
          this.formInfo.push(res.data.data[item]);
        }
        console.log(this.formInfo);
        for (let item in this.formInfo) {
          this.formInfo[item].data = JSON.parse(this.formInfo[item].data);
        }
        console.log(this.formInfo);
      }
    });

    // console.log(this.testStr)
    // console.log(JSON.parse(this.test2))
    // console.log(JSON.parse(this.testStr))

    // testStr符合正确格式
    // this.formInfo = JSON.parse(this.testStr);
    // console.log(this.formInfo)
  },

  methods: {
    ToApproveForm(index, id) {
      // console.log(this.formInfo[index]);
      // console.log(index, id);
      this.$router.push({
        name: "ShowFormApprove",
        params: {
          json: JSON.stringify(this.formInfo[index]),
        },
      });
    },
    ToChangeForm(index, id) {
      // console.log(index, id);
      this.$router.push({
        name: "CreateForm",
        params: {
          json: JSON.stringify(this.formInfo[index]),
        },
      });
    },
    ToPublishTemplate(index, id) {
      // console.log(index, id);
      this.sheetDescription.tid = id;
      this.drawer = true;
    },
    ToDeleteTemplate(index, id) {
      ElMessageBox.confirm("你确定要删除此模板吗", "注意！", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .post("url", {
              deleteId: id,
            })
            .then((res) => {
              if (res.data.errorCode === 66666) {
                ElMessage({
                  type: "info",
                  message: "删除成功",
                });
              }
            });
        })
        .catch(() => {
          ElMessage({
            type: "info",
            message: "取消操作",
          });
        });
      // console.log(index, id);
    },

    cancelClick() {
      this.drawer = false;
    },
    confirmClick() {
      axios
        .post("url", {
          tid: this.sheetDescription.tid,
          did: this.sheetDescription.tid,
          name: this.sheetDescription.name,
          description: this.sheetDescription.description,
          start: this.sheetDescription.start,
          end: this.sheetDescription.end,
        })
        .then((res) => {
          if (res.data.errorCode == 66666) {
            ElMessage.success("发布成功");
          }
        });
      console.log(this.sheetDescription);
      this.drawer = false;
    },
  },
};
</script>