<template>
  <el-dialog v-model="addFlowNodeDialog" width="30%">
    <el-table
      :data="flowData"
      highlight-current-row
      @current-change="handleCurrentChange"
    >
      <el-table-column prop="id" label="编号" />
      <el-table-column prop="label" label="名称" />
      <el-table-column
        prop="members"
        label="成员概览"
        :formatter="checkPrincipal"
      />
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="success" @click="confirmAddFlowNode" plain
          >确认</el-button
        >
        <el-button type="warning" @click="addFlowNodeDialog = false" plain
          >取消</el-button
        >
      </span>
    </template>
  </el-dialog>

  <fieldset style="width: 90%">
    <legend>表单信息</legend>
    <el-form label-width="200px">
      <el-row justify="space-around">
        <el-col :span="12">
          <el-form-item label="请输入模板名称：">
            <el-input
              v-model="form.formName"
              placeholder="请输入模板名称"
              class="input_width"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请选择模板类型：">
            <el-select placeholder="模板类型" v-model="form.formType">
              <el-option key="统计表" label="统计表" value="0" />
              <el-option key="审批表" label="审批表" value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- <el-row justify="space-around">
        <el-col :span="12">
          <el-form-item label="请选择模板起始时间：">
            <el-date-picker
              v-model="form.formStartTime"
              type="datetime"
              placeholder="开始日期与时间"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请选择模板结束时间：">
            <el-date-picker
              v-model="form.formEndTime"
              type="datetime"
              placeholder="结束日期与时间"
            />
          </el-form-item>
        </el-col>
      </el-row> -->
      <el-row>
        <el-col :span="24">
          <el-form-item label="请输入模板描述">
            <el-input
              v-model="form.formDescription"
              placeholder="请输入模板描述"
              type="textarea"
              :rows="2"
              style="margin-right: 20%"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </fieldset>

  <!-- 定义审批结构 -->
  <fieldset style="width: 90%" v-show="form.formType === '1'">
    <legend>定义审批流程</legend>
    <el-row :gutter="20">
      <el-button style="margin-left: 60%" @click="addFlowNode"
        >添加流程节点</el-button
      >
      <el-button style="margin-left: 5%" @click="deleteFlowNode"
        >删除流程节点</el-button
      >
    </el-row>
    <el-row>
      <el-timeline style="margin-left: 30%">
        <el-timeline-item
          v-for="(item, index) in flowNodesList"
          :key="index"
          :timestamp="'责任人：' + item.principal + '等'"
        >
          {{ index + 1 + "  " + item.label }}
        </el-timeline-item>
      </el-timeline>
    </el-row>
  </fieldset>

  <!-- 定义表单内容 -->
  <fieldset style="width: 90%; margin-bottom: 700px">
    <legend>表单内容</legend>
    <el-form label-width="200px" label-position="right">
      <!-- <el-form-item label="请选择表单结束时间0：">
        <el-date-picker
          v-model="form.formEndTime"
          type="datetime"
          placeholder="结束日期与时间"
        />
      </el-form-item> 
      <el-row justify="center">
            <el-col :span="20"
              ><el-input
                v-model="item.value"
                placeholder="输入框描述"
                type="textarea"
                :rows="2"
              ></el-input>
              </el-col
            >
            <el-col :span="4"><el-button>删除表项</el-button></el-col>
          </el-row>
      
      -->
      <template v-for="(item, index) in this.form.formContent" :key="index">
        <template v-if="item.type === 'input'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：输入框">
                <el-input
                  v-model="item.description"
                  placeholder="对输入框的描述"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>
        </template>

        <template v-else-if="item.type === 'date-selector'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：日期选择器">
                <el-input
                  v-model="item.description"
                  placeholder="对日期选择的描述"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>
        </template>

        <template v-else-if="item.type === 'num-input'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：数字输入框">
                <el-input
                  v-model="item.description"
                  placeholder="对输入数字的描述"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>
        </template>

        <template v-else-if="item.type === 'multi-check'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：多选栏">
                <el-input
                  v-model="item.description"
                  placeholder="对多选内容的描述："
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>

          <el-form-item label="各个选项的内容：">
            <el-button @click="AddChoice(item)" type="success"
              >添加选项</el-button
            >
            <el-button @click="DeleteChoice(item)" type="warning"
              >减少选项</el-button
            >
            <template
              v-for="(choices, choiceIndex) in item.value"
              :key="choiceIndex"
              style="wideth: 100%"
            >
              <el-input
                :placeholder="'请输入选项' + (choiceIndex + 1) + '的内容'"
                class="local-form"
                v-model="item.value[choiceIndex]"
              ></el-input>
            </template>
          </el-form-item>
        </template>

        <template v-else-if="item.type === 'single-check'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：单选栏">
                <el-input
                  v-model="item.description"
                  placeholder="对多选内容的描述："
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>

          <el-form-item label="各个选项的内容：">
            <el-button @click="AddChoice(item)" type="success"
              >添加选项</el-button
            >
            <el-button @click="DeleteChoice(item)" type="warning"
              >减少选项</el-button
            >
            <template
              v-for="(choices, choiceIndex) in item.value"
              :key="choiceIndex"
              style="wideth: 100%"
            >
              <el-input
                :placeholder="'请输入选项' + (choiceIndex + 1) + '的内容'"
                class="local-form"
                v-model="item.value[choiceIndex]"
              ></el-input>
            </template>
          </el-form-item>
        </template>

        <template v-else-if="item.type === 'pull-selector'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="16">
              <el-form-item label="表单项目类型：下拉选择器">
                <el-input
                  v-model="item.description"
                  placeholder="对下拉选择内容的描述"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4"
              >是否必填项
              <el-switch
                v-model="item.isRequired"
                inline-prompt
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="Y"
                inactive-text="N"
            /></el-col>
            <el-col :span="4">
              <el-button
                style="width: 10%"
                @click="DeleteFormItem(index)"
                type="danger"
                >删除表项</el-button
              >
            </el-col>
          </el-row>

          <el-form-item label="各个选项的内容：">
            <el-button @click="AddChoice(item)" type="success"
              >添加选项</el-button
            >
            <el-button @click="DeleteChoice(item)" type="warning"
              >减少选项</el-button
            >
            <template
              v-for="(choices, choiceIndex) in item.value"
              :key="choiceIndex"
              style="wideth: 100%"
            >
              <el-input
                :placeholder="'请输入选项' + (choiceIndex + 1) + '的内容'"
                class="local-form"
                v-model="item.value[choiceIndex]"
              ></el-input>
            </template>
          </el-form-item>
        </template>

        <el-divider />
      </template>
      <!-- <el-form-item label="请选择表单结束时间1：">
        <el-input v-model="form.formName" placeholder="为什么这里没有label0"></el-input>
      </el-form-item>
      <el-form-item label="请选择表单结束时间2：">
        <el-input v-model="form.formName" placeholder="输入框描述"></el-input>
      </el-form-item>
      <el-form-item label="请输入表单名称：">
        <el-input v-model="form.formName" placeholder="为什么这里没有label2"></el-input>
      </el-form-item> -->
    </el-form>
  </fieldset>

  <el-affix position="bottom" :offset="10">
    <el-button @click="CreateInput" style="padding: 10px" plain type="primary"
      >创建输入框</el-button
    >
    <el-button
      @click="CreateSingleCheck"
      style="padding: 10px"
      plain
      type="primary"
      >创建单选</el-button
    >
    <el-button
      @click="CreateMultiCheck"
      style="padding: 10px"
      plain
      type="primary"
      >创建多选</el-button
    >
    <el-button
      @click="CreateDateSelector"
      style="padding: 10px"
      plain
      type="primary"
      >创建日期选择</el-button
    >
    <el-button
      @click="CreatePullSelector"
      style="padding: 10px"
      plain
      type="primary"
      >创建下拉列表</el-button
    >
    <el-button
      @click="CreateNumberInput"
      style="padding: 10px"
      plain
      type="primary"
      >创建数字输入框</el-button
    >
    <el-button @click="test" style="padding: 10px" plain type="primary"
      >检查、生成json</el-button
    >
    <el-button @click="ToFillForm" style="padding: 10px" plain type="primary"
      >填写表单(测试)</el-button
    >
    <el-button @click="uploadForm" style="padding: 10px" plain type="primary"
      >上传</el-button
    >
    <!--    这里呢函数括号不能删，可能是放了数组里必须加括号，嫩爷试来-->
  </el-affix>
</template>


<script>
// import 'element-theme-chalk';
// import { reactive } from "vue";
import { isArray } from "@vue/shared";
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      form: {
        userName: null,
        formName: "",
        formType: "",
        formStartTime: "",
        formEndTime: "",
        formDescription: "",
        formContent: [],
      },
      // approveNodeNums:0,
      flowNodesList: [], // 展示流程给时使用
      flowNodesIdList: [],
      addFlowNodeDialog: false,

      tempVar: null,

      flowData: [
        {
          label: "业务部",
          id: 1,
          members: [{ id: 1, email: "张三" }],
        },
        {
          label: "市场分析",
          id: 2,
          members: [{ id: 4, email: "罗翔" }],
        },
        {
          label: "信息搜集",
          id: 3,
          members: [{ id: 5, email: "张三三" }],
        },
      ],
    };
  },
  mounted() {
    this.form.userName = sessionStorage["userName"];
    if (this.$route.params.json) {
      console.log("yes");
      const formObj = JSON.parse(this.$route.params.json);
      console.log(formObj);
      // this.form.formContent = JSON.parse(formObj.data.originContent);  // 与后端交互使用此语句，因为后端提供的是json字符串
      this.form.formContent = formObj.data.originContent; // 前端测试使用此语句
      this.form.formName = formObj.data.name;
      this.form.formType = formObj.data.type;
      this.form.formDescription = formObj.description;
      console.log(formObj.description);
    } else {
      console.log("no");
    }
  },
  methods: {
    // 定义流程时使用的函数
    handleCurrentChange(rowObj) {
      this.tempVar = rowObj;
    },
    addFlowNode() {
      this.addFlowNodeDialog = true;
    },
    deleteFlowNode() {
      if (this.flowNodesList.length > 0) {
        this.flowNodesList.splice(-1, 1);
        this.flowNodesIdList.splice(-1, 1);
      }
    },
    handleNodeClick(data) {
      this.tempVar = data;
    },
    confirmAddFlowNode() {
      // 此处tempVar是flowData中的Obj
      let item;
      if (this.tempVar.members.length === 0) {
        this.addFlowNodeDialog = false;
        ElMessage.warning("不能选择没有负责人的组");
        return;
      }
      for (item in this.flowNodesIdList) {
        if (this.flowNodesIdList[item] === this.tempVar.id) {
          this.addFlowNodeDialog = false;
          ElMessage.warning("不能在一个流程中加入重复节点");
          return;
        }
      }
      this.flowNodesList.push({
        label: this.tempVar.label,
        principal: this.tempVar.members[0].email,
      });
      this.flowNodesIdList.push(this.tempVar.id);
      this.addFlowNodeDialog = false;
      console.log(this.flowNodesIdList);
      console.log(this.flowNodesList);
    },
    checkPrincipal(row, column) {
      if (row.members.length > 0) {
        return "责任人：" + row.members[0].email + "等";
      } else {
        return "暂无成员";
      }
    },

    test() {
      if (this.form.formContent.length > 0) {
        var item;
        var outForm = {
          uid: 1,
          name: this.form.formName,
          type: this.form.formType,
          startTime: this.form.formStartTime,
          description: this.form.formDescription,
          endTime: this.form.formEndTime,
          contentLength: 0,
          originContent: this.form.formContent,
          content: [],
        };

        for (item in this.form.formContent) {
          var valueIndex;
          var descriptionForStaisics = {
            id: item,
            type: this.form.formContent[item].type,
            value: {},
          };
          // if (isArray(this.form.formContent[item].value)) {
          for (valueIndex in this.form.formContent[item].value) {
            descriptionForStaisics.value[
              this.form.formContent[item].value[valueIndex]
            ] = 0;
          }
          descriptionForStaisics["description"] =
            this.form.formContent[item].description;
          // } else {
          //   descriptionForStaisics.value = this.form.formContent[item].value;
          // }
          outForm.content.push(descriptionForStaisics);
        }
        outForm.contentLength = outForm.content.length;
        this.finalForm = JSON.stringify(outForm, null, "\t");
        // console.log(JSON.stringify(outForm, null, "\t"));
        console.log(this.finalForm);
      }
    },
    AddChoice(item) {
      // console.log(item);
      item.value.push("");
    },
    DeleteChoice(item) {
      item.value.splice(-1, 1);
    },

    // 创建表单元素的函数
    CreateInput() {
      // this.$router.push(name = 'CreateInput')
      // console.log(this.form);
      this.form.formContent.push({
        type: "input",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    CreateMultiCheck() {
      this.form.formContent.push({
        type: "multi-check",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    CreateSingleCheck() {
      this.form.formContent.push({
        type: "single-check",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    CreateDateSelector() {
      this.form.formContent.push({
        type: "date-selector",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    CreatePullSelector() {
      this.form.formContent.push({
        type: "pull-selector",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    CreateNumberInput() {
      this.form.formContent.push({
        type: "num-input",
        isRequired: false,
        description: "",
        value: [],
      });
    },
    DeleteFormItem(index) {
      this.form.formContent.splice(index, 1);
    },

    // 业务逻辑
    ToFillForm() {
      this.$router.push({
        name: "FillForm",
        params: {
          json: this.finalForm,
        },
      });
    },
    uploadForm() {
      this.check();
      if (this.form.formContent.length === 0) {
        ElMessage.error("表单不能为空");
        return;
      } else {
        axios
          .post("api/templates/create", {
            json: this.finalForm,
          })
          .then((res) => {
            console.log(res.data);
            if (res.data.errorCode == 20011) {
              ElMessage.success("上传成功");
              this.$router.push({ name: "ManageFormTemplates" });
            }
          });
      }
    },
    check() {
      console.log("有");
      // if(this.form.startTime!=''&&this.form.formEndTime!='') {
      if (this.form.formStartTime > this.form.formEndTime) {
        alert("起始时间不能晚于结束时间");
        // }
      }
    },
  },
};
</script>

<style scoped>
.main {
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  /* align-items: center;
    margin-left: 30%;
    width: 60%; */
}
.inside {
  /* display: inline-block; */
  display: flex;
  justify-content: center;
  /* align-items: center; */
}
fieldset {
  /* 表单页面居中，宽度50% ,legend颜色设置，legend圆角*/
  border: 2px solid #dcdfe6;
  text-align: left;
  border-radius: 8px;
  margin: 0 auto;
  width: 50%;
}
.button-container {
  background: white;
}
</style>