<template>
  <fieldset style="width: 90%; margin-bottom: 300px">
    <!-- <legend>{{ formDescriptionObj.formName }}</legend> -->
    <el-form
      label-width="auto"
      label-position=""
      :model="formResult"
      :disabled="ifDone"
    >
      <template v-for="(item, index) in formObj" :key="index">
        <!-- input -->
        <template v-if="item.type === 'input'">
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
                <el-input
                  v-model="formResult.content[index].value[0]"
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
                  v-model="formResult.content[index].value[0]"
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
                  v-model="formResult.content[index].value[0]"
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
                <el-radio-group v-model="formResult.content[index].value[0]">
                  <el-radio
                    v-for="(checkItem, checkItemIndex) in item.value"
                    :key="checkItemIndex"
                    :label="checkItem"
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
                <el-select v-model="formResult.content[index].value[0]">
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
      </template>
    </el-form>
  </fieldset>

  <el-affix position="bottom" :offset="20" style="margin-left: 50%">
    <el-button type="primary" @click="SubmitForm">提交表单</el-button>
  </el-affix>
</template>


<script>
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";

export default {
  data() {
    return {
      formObj: {},
      formDescriptionObj: {},
      gettenData: {},
      formResult: {
        content: [],
      },
      showResult: false,
      returnToBack: {
        done: 0,
        pass: 1,
      },
      firstTime: false,
      ifDone: false,
    };
  },
  mounted() {
    // console.log(this.$route.params.FormId);
    // console.log(this.$route.params);
    if (this.$route.params.ShowResult) {
      console.log("查看内容");
      console.log(this.$route.params.ShowResult);
      this.ifDone = true;
      axios
        .get("/api/answers/one/" + this.$route.params.FormId, {})
        .then((res) => {
          this.showResult = true;
          console.log(res.data);
          this.formObj = res.data.data.originContent;
          this.formResult.content = JSON.parse(res.data.data.data).data;
        });
    } else {
      console.log("填写");
      axios
        .get("/api/answers/one/" + this.$route.params.FormId, {})
        .then((res) => {
          console.log(res.data.data);
          if (res.data.errorCode == 66666) {
            ElMessage.success("获取成功");
            this.gettenData = JSON.parse(res.data.data.data).data;
            this.formDescriptionObj = res.data.data;
            console.log(this.gettenData);
            this.formObj = this.formDescriptionObj.originContent;
            console.log(this.formObj);
            var formItem;
            // console.log(JSON.parse(this.gettenData.unfinished))
            // console.log(this.gettenData.unfinished);
            if (this.formDescriptionObj.done === 1) {
              this.firstTime = false;
              this.formResult.content = this.gettenData;
            } else {
              this.firstTime = true;
              console.log("formObj", this.formObj);
              for (formItem in this.formObj) {
                // console.log(formItem);
                // console.log(123456);
                if (
                  this.formObj[formItem].type === "single-check" ||
                  this.formObj[formItem].type === "pull-selector" ||
                  this.formObj[formItem].type === "input" ||
                  this.formObj[formItem].type === "date-selector" ||
                  this.formObj[formItem].type === "num-input"
                ) {
                  this.formResult.content.push({
                    id: formItem,
                    value: [""],
                  });
                } else {
                  this.formResult.content.push({
                    id: formItem,
                    value: [],
                  });
                }
              }
              console.log(this.formResult.content);
            }
          } else if (res.data.errorCode === 20220) {
            ElMessage.info("您已完成过此表单的填写，请勿重复填写");
          } else {
            ElMessage.error("访问出错");
          }
        });
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
      if (this.firstTime) {
        axios
          .post("/api/answers/submit", {
            sheetId: this.$route.params.FormId,
            answers: {
              data: this.formResult.content,
              done: this.returnToBack.done,
              pass: this.returnToBack.pass,
              flow: null,
            },
          })
          .then((res) => {
            if (res.data.errorCode == 66666) {
              ElMessage.success("上传成功");
              this.$router.push("/userInfo");
            }
          });
      } else {
        setTimeout(() => {
          axios
            .post("/api/answers/submit", {
              sheetId: this.$route.params.FormId,
              answers: {
                data: this.formResult.content,
                done: this.returnToBack.done,
                pass: this.returnToBack.pass,
                flow: null,
              },
            })
            .then((res) => {
              if (res.data.errorCode == 66666) {
                ElMessage.success("上传成功");
                this.$router.push("/userInfo");
              }
            });
        }, 1000);
      }
      // ElMessage.success("上传成功");
    },
    InstantSubmitForm(rule, value, callback) {
      console.log(JSON.stringify(this.formResult.content));
      if (this.firstTime) {
        axios.post("/api/answers/save", {
          sheetId: this.$route.params.FormId,
          answers: {
            data: this.formResult.content,
            done: this.returnToBack.done,
            pass: this.returnToBack.pass,
            flow: null,
          },
        });
        // .then((res) => {
        //   if (res.data.errorCode == 66666) {
        //     ElMessage.success("上传成功");
        //   }
        // });
      } else {
        axios.post("/api/answers/save", {
          sheetId: this.$route.params.FormId,
          answers: {
            data: this.formResult.content,
            done: this.returnToBack.done,
            pass: this.returnToBack.pass,
            flow: null,
          },
        });
        // .then((res) => {
        //   if (res.data.errorCode == 66666) {
        //     ElMessage.success("上传成功");
        //   }
        // });
        // ElMessage.success("上传成功");
      }
    },
  },
};
</script>