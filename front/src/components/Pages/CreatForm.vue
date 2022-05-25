<template>
  <el-divider style="margin-top: 0" />
  <el-row :gutter="20" justify="space-between">
    <el-col :span="6"><span class="header">请输入表单名称</span></el-col>
    <el-col :span="6"
      ><el-input class="local-form" v-model="this.form.formName"></el-input
    ></el-col>
    <el-col :span="6"><span class="header">请选择表单类型</span></el-col>
    <el-col :span="6">
      <el-select v-model="this.form.formType">
        <el-option :value="0" label="问卷">问卷</el-option>
        <el-option :value="1" label="申请表">申请表</el-option>
      </el-select>
    </el-col>
  </el-row>
  <el-divider class="divider" />
  <!-- <router-view /> -->

  <el-form v-for="(item, index) in this.form.formContent" :key="item">
    <el-form-item v-if="item.type === 'input'">
      <span class="header">表单项目类型：输入框</span>
      <el-input
        v-model="item.value"
        type="textarea"
        :rows="2"
        class="local-form"
        placeholder="输入框描述"
      ></el-input>
    </el-form-item>
    <el-form-item v-else-if="item.type === 'date-selector'">
      <span class="header">表单项目类型：日期选择器</span>
      <el-input
        v-model="item.value"
        type="textarea"
        :rows="2"
        class="local-form"
        placeholder="对日期的描述"
      ></el-input>
    </el-form-item>
    <el-form-item v-else-if="item.type === 'multi-check'">
      <span class="header">表单项目类型：多选框</span>
      <el-button @click="AddChoice(index)">添加表项</el-button>
      <el-button @click="DeleteChoice(item, ChoiceIndex)">删除表项</el-button>
      <div
        v-for="(choices, choiceIndex) in item.value"
        :key="choiceIndex"
        style="wideth: 100%"
      >
        <el-input
          v-if="choiceIndex === 0"
          :placeholder="'请输入对于多选的描述'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
        <el-input
          v-else
          :placeholder="'请输入选项' + choiceIndex + '的内容'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
      </div>
    </el-form-item>
    <el-form-item v-else-if="item.type === 'sigle-check'">
      <span class="header">表单项目类型：单选框</span>
      <el-button @click="AddChoice(index)">添加表项</el-button>
      <el-button @click="DeleteChoice(item, ChoiceIndex)">删除表项</el-button>
      <div
        v-for="(choices, choiceIndex) in item.value"
        :key="choiceIndex"
        style="wideth: 100%"
      >
        <el-input
          v-if="choiceIndex === 0"
          :placeholder="'请输入对于单选的描述'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
        <el-input
          v-else
          :placeholder="'请输入选项' + choiceIndex + '的内容'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
      </div>
    </el-form-item>
    <el-form-item v-else-if="item.type === 'pull-selector'">
      <span class="header">表单项目类型：下拉选择</span>
      <el-button @click="AddChoice(index)">添加表项</el-button>
      <el-button @click="DeleteChoice(item, ChoiceIndex)">删除表项</el-button>
      <div
        v-for="(choices, choiceIndex) in item.value"
        :key="choiceIndex"
        style="wideth: 100%"
      >
        <el-input
          v-if="choiceIndex === 0"
          :placeholder="'请输入对于下拉选择的描述'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
        <el-input
          v-else
          :placeholder="'请输入选项' + choiceIndex + '的内容'"
          class="local-form"
          v-model="item.value[choiceIndex]"
        ></el-input>
      </div>
    </el-form-item>
    <el-form-item v-if="item.type === 'num-input'">
      <span class="header">表单项目类型：数字输入框</span>
      <el-input
        v-model="item.value"
        type="textarea"
        :rows="2"
        class="local-form"
        placeholder="数字输入框描述"
      ></el-input>
    </el-form-item>
    <el-divider class="divider" />
  </el-form>
  <el-button @click="CreateInput">创建输入框</el-button>
  <el-button @click="CreateSigleCheck">创建单选</el-button>
  <el-button @click="CreateMultiCheck">创建多选</el-button>
  <el-button @click="CreateDateSelector">创建日期选择</el-button>
  <el-button @click="CreatePullSelector">创建下拉列表</el-button>
  <el-button @click="CreateNumberInput">创建数字输入框</el-button>
  <el-button @click="test">检查、生成json</el-button>
  <el-button @click="ToFillForm">填写表单(测试)</el-button>
  <el-button @click="uploadForm">上传</el-button>
</template>


<script>
import { isArray } from "@vue/shared";
import axios from "axios";
import { ElMessage } from "element-plus";
export default {
  data() {
    return {
      form: {
        userId:1,
        formName: "",
        formType: 0,
        formContent: [],
      },
      finalForm: `{"default": "default"}`,
    };
  },
  beforeRouteUpdate(to, from) {
    console.log(from, to);
  },
  methods: {
    test() {
      if (this.form.formContent.length > 0) {
        var item;
        var outForm = {
          uid:1,
          name: this.form.formName,
          type: this.form.formType,
          contentLength: 0,
          content: [],
        };

        for (item in this.form.formContent) {
          var valueIndex;
          var formDescription = {
            id: item,
            type: this.form.formContent[item].type,
            value: {},
          };
          if (isArray(this.form.formContent[item].value)) {
            for (valueIndex in this.form.formContent[item].value) {
              formDescription.value[
                this.form.formContent[item].value[valueIndex]
              ] = 0;
            }
          } else {
            formDescription.value = this.form.formContent[item].value;
          }
          outForm.content.push(formDescription);
        }
        outForm.contentLength = outForm.content.length;
        this.finalForm = JSON.stringify(outForm, null, "\t");
        console.log(JSON.stringify(outForm, null, "\t"));
        console.log(outForm);
      }
    },
    AddChoice(index) {
      this.form.formContent[index].value.push("");
    },
    DeleteChoice(item, ChoiceIndex) {
      item.value.splice(ChoiceIndex, 1);
    },

    // 创建表单元素的函数
    CreateInput() {
      // this.$router.push(name = 'CreateInput')
      this.form.formContent.push({
        type: "input",
        value: "",
      });
    },
    CreateMultiCheck() {
      this.form.formContent.push({
        type: "multi-check",
        value: [],
      });
    },
    CreateSigleCheck() {
      this.form.formContent.push({
        type: "sigle-check",
        value: [],
      });
    },
    CreateDateSelector() {
      this.form.formContent.push({
        type: "date-selector",
        value: "",
      });
    },
    CreatePullSelector() {
      this.form.formContent.push({
        type: "pull-selector",
        value: [],
      });
    },
    CreateNumberInput() {
      this.form.formContent.push({
        type: "num-input",
        value: "",
      });
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
      axios
        .post("api/templates", {
          json: this.finalForm,
        })
        .then(function (res) {
          if (res.data.ErrorCode == "20011") {
            ElMessage.success("上传成功");
          }
        });
    },
  },
};
</script>

<style scoped>
.local-form {
  margin-left: 20%;
  margin-bottom: 20px;
}

.header {
  margin-left: 10px;
  margin-top: 5px;
}

.divider {
  margin-top: 0px;
  margin-bottom: 0px;
}
</style>