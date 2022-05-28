<template>
  {{ formResult.content }}
  <h1>表单作答界面</h1>

  <fieldset style="width: 60%; margin-bottom: 300px; margin-top: 30px">
    <legend>{{ formDescriptionObj.name }}</legend>
    <el-form label-width="auto" style="" :model="formResult">
      <template v-for="(item, index) in formObj" :key="index">
        <!-- input -->
        <template v-if="item.type === 'input'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item
                :label="item.description"
                style="text-align: center"
                :rules="[
                  {
                    required: item.isRequired,
                    message: '此项为必填项！',
                    trigger: 'blur',
                  },
                  { validator: InstantSubmitForm, trigger: 'blur' },
                ]"
                :prop="'content[' + index + '].value'"
              >
                <el-input
                  v-model="formResult.content[index].value"
                  placeholder="请输入答案"
                  type="textarea"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- num-input -->
        <template v-if="item.type === 'num-input'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item
                :label="item.description"
                :rules="[
                  {
                    required: item.isRequired,
                    message: '此项为必填项！',
                    trigger: 'blur',
                  },
                  { validator: InstantSubmitForm, trigger: 'blur' },
                ]"
                :prop="'content[' + index + '].value'"
              >
                <el-input-number
                  v-model="formResult.content[index].value"
                ></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- date-selector -->
        <template v-if="item.type === 'date-selector'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item
                :label="item.description"
                :rules="[
                  {
                    required: item.isRequired,
                    message: '此项为必填项！',
                    trigger: 'blur',
                  },
                  { validator: InstantSubmitForm, trigger: 'blur' },
                ]"
                :prop="'content[' + index + '].value'"
              >
                <el-date-picker
                  v-model="formResult.content[index].value"
                  value-format="YYYY.MM.DD"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- single-check -->
        <template v-if="item.type === 'single-check'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item
                :label="item.description"
                style="text-align: center"
                :rules="[
                  {
                    required: item.isRequired,
                    message: '此项为必填项！',
                    trigger: 'blur',
                  },
                  { validator: InstantSubmitForm, trigger: 'blur' },
                ]"
                :prop="'content[' + index + '].value'"
              >
                <el-radio-group v-model="formResult.content[index].value">
                  <el-radio
                    v-for="(checkItem, checkItemIndex) in item.value"
                    :key="checkItemIndex"
                    :label="checkItem"
                    style="text-align: center"
                  ></el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- multi-check -->
        <template v-if="item.type == 'multi-check'">
          <el-form-item
            :label="item.description"
            :rules="[
              {
                required: item.isRequired,
                message: '此项为必填项！',
                trigger: 'blur',
              },
              { validator: InstantSubmitForm, trigger: 'blur' },
            ]"
            :prop="'content[' + index + '].value'"
          >
            <el-checkbox-group v-model="formResult.content[index].value">
              <el-checkbox
                v-for="(checkItem, checkItemIndex) in item.value"
                :key="checkItemIndex"
                :label="checkItem"
              >
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </template>

        <!-- pull-selector -->
        <template v-if="item.type === 'pull-selector'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item
                :label="item.description"
                :rules="[
                  {
                    required: item.isRequired,
                    message: '此项为必填项！',
                    trigger: 'blur',
                  },
                  { validator: InstantSubmitForm, trigger: 'blur' },
                ]"
                :prop="'content[' + index + '].value'"
              >
                <el-select v-model="formResult.content[index].value">
                  <el-option
                    v-for="(checkItem, checkItemIndex) in item.value"
                    :key="checkItemIndex"
                    :label="checkItem"
                    :value="checkItem"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </template>
        <el-divider style="margin-top: 0" />
      </template>
    </el-form>
  </fieldset>

  <el-affix position="bottom" :offset="20" style="margin-left: 50%">
    <el-button type="primary" @click="SubmitForm">提交表单</el-button>
  </el-affix>
</template>


<script>
import { ElMessage, ElMessageBox } from "element-plus";
export default {
  data() {
    return {
      formObj: {},
      formDescriptionObj: [],
      formResult: {
        content: [],
      },
    };
  },
  mounted() {
    this.formDescriptionObj = JSON.parse(this.$route.params.json);
    this.formObj = JSON.parse(this.formDescriptionObj.originContent);
    // this.formObj = this.formDescriptionObj.originContent;
    // console.log(this.formObj);
    var formItem;
    for (formItem in this.formObj) {
      // console.log(formItem);
      // console.log(123456);
      // if (
      //   this.formObj.originContent[formItem].value.length === 0 ||
      //   this.formObj.originContent[formItem].type === "single-check"
      // ) {
      //   this.formResult.content.push({
      //     id: formItem,
      //     value: [],
      //   });
      // } else {
      this.formResult.content.push({
        id: formItem,
        value: [],
      });
      // }
    }
    // if (isString(formObj.content[formItem].value)) {
    //   this.formResult.content.push({
    //     id: formItem,
    //     value: "",
    //   });
    // } else {

    //   if (this.formObj.content[formItem].type == "multi-check") {
    //     this.formResult.content.push({
    //       id: formItem,
    //       value: [],
    //     });
    //   } else {
    //     this.formResult.content.push({
    //       id: formItem,
    //       value: "",
    //     });
    //   }
    //   // }
    // }
    // console.log(this.formResult);
  },
  methods: {
    SubmitForm() {
      // axios
      //   .post("url", {
      //     json: JSON.stringify(this.formResult, null, '\t'),
      //   })
      //   .then(function (res) {
      //     if (res == true) {
      //       ElMessage.success("上传成功");
      //     }
      //   });
      ElMessage.success("上传成功");
    },
    InstantSubmitForm() {
      ElMessage.success("上传成功");
    },
  },
};
</script>

<style scoped>
fieldset {
  /* 表单页面居中，宽度50% ,legend颜色设置，legend圆角*/
  border: 2px solid #dcdfe6;
  text-align: left;
  border-radius: 8px;
  margin: 0 auto;
  width: 50%;
}

/* .el-form-item__label {
  text-align: left;
  float: none; 
  word-break: break-word; 
} */
</style>