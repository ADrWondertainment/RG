<template>
  <fieldset style="width: 60%; margin-bottom: 300px; margin-top: 30px">
    <legend>{{ formObj.name }}</legend>
    <el-form label-width="200px" label-position="top" disabled>
      <template v-for="(item, index) in formObj.originContent" :key="index">
        <!-- input -->
        <template v-if="item.type === 'input'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item :label="item.description">
                <el-input
                  v-model="item.value"
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
              <el-form-item :label="item.description">
                <el-input-number v-model="item.value"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- date-selector -->
        <template v-if="item.type === 'date-selector'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item :label="item.description">
                <el-date-picker v-model="item.value"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </template>

        <!-- single-check -->
        <template v-if="item.type === 'single-check'">
          <el-row :gutter="20" justify="space-evenly">
            <el-col :span="24">
              <el-form-item :label="item.description">
                <el-radio-group v-model="item.value">
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
          <el-form-item :label="item.description">
            <el-checkbox-group v-model="item.value">
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
              <el-form-item :label="item.description">
                <el-select v-model="item.value">
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

  <el-affix position="bottom" :offset="20">
    <el-button type="primary" @click="pass">通过审批</el-button>
    <el-button type="primary" @click="refuse">拒绝审批</el-button>
  </el-affix>

  <el-dialog v-model="passDialog" title="审批通过确认" width="500px">
    <el-form>
      <el-row>
        <el-col :span="24">
          <el-form-item label="审批者签名">
            <el-input v-model="passSignName"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8" :offset="16">
          <el-form-item>
            <el-button @click="confirmPass">确认提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>

  <el-dialog v-model="refuseDialog" title="审批拒绝确认">
    <el-form>
      <el-row>
        <el-col :span="24">
          <el-form-item label="审批拒绝原因">
            <el-input
              v-model="refuseReason"
              type="textarea"
              :rows="2"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8" :offset="16">
          <el-form-item>
            <el-button @click="confirmRefuse">确认提交</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-dialog>
</template>


<script>
import axios from "axios";
export default {
  data() {
    return {
      formObj: {},
      formContent: [],
      formResult: {
        content: [],
      },

      passDialog: false,
      refuseDialog: false,
      passSignName: "",
      refuseReason: "",
    };
  },
  mounted() {
    this.formObj = JSON.parse(this.$route.params.json);

    this.formContent = this.formObj.originContent;
    this.formResult = this.formObj.data;

    console.log(this.formContent);
    console.log(this.formContent);
    console.log(this.formResult);
    // var formItem;
    // for (formItem in this.formObj.originContent) {
    //   console.log(formItem);
    //   console.log(123456);
    // if (
    //   this.formObj.originContent[formItem].value.length === 0 ||
    //   this.formObj.originContent[formItem].type === "single-check"
    // ) {
    //   this.formResult.content.push({
    //     id: formItem,
    //     value: [],
    //   });
    // } else {
    // this.formResult.content.push({
    //   id: formItem,
    //   value: [],
    // });
    // if(this.formObj.originContent[formItem].type === "date-selector"){
    //   console.log(this.formObj.originContent[formItem].value)
    //   // this.formObj.originContent[formItem].value = new Date(this.formObj.originContent[formItem].value)
    //   console.log(this.formObj.originContent[formItem].value)
    // }

    // }
    // }

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
    pass() {
      this.passDialog = true;
    },
    refuse() {
      this.refuseDialog = true;
    },
    confirmPass() {
      axios.post("url", {
        name: this.passSignName,
      });
    },
    confirmRefuse() {
      axios.post("url", {
        reason: this.refuseReason,
      });
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
</style>
