<template>
  {{ formResult }}
  <el-divider />
  <el-form v-for="(formItem, index) in formObj.content" :key="index">
    <el-form-item v-if="formItem.type == 'input'">
      <span>{{ formItem.value }}</span>
      <el-input v-model="formResult.content[index].value"></el-input>
    </el-form-item>
    <el-form-item v-if="formItem.type == 'multi-check'">
      <el-checkbox-group v-model="formResult.content[index].value">
        <div
          v-for="(checkItem, checkItemIndex) in Object.keys(formItem.value)"
          :key="checkItemIndex"
        >
          <div
            v-if="checkItemIndex == 0"
            style="font-size: 20px; margin-bottom: 15px"
          >
            {{ checkItem }}
          </div>
          <el-checkbox v-else :label="checkItem"></el-checkbox>
        </div>
      </el-checkbox-group>
    </el-form-item>
    <el-form-item v-if="formItem.type == 'sigle-check'">
      <el-radio-group v-model="formResult.content[index].value">
        <div
          v-for="(checkItem, checkItemIndex) in Object.keys(formItem.value)"
          :key="checkItemIndex"
        >
          <div
            v-if="checkItemIndex == 0"
            style="font-size: 20px; margin-bottom: 15px"
          >
            {{ checkItem }}
          </div>
          <el-radio v-else :label="checkItem"></el-radio>
        </div>
      </el-radio-group>
    </el-form-item>
    <el-form-item v-if="formItem.type == 'date-selector'">
      <span>{{ formItem.value }}</span>
      <el-date-picker
        v-model="formResult.content[index].value"
        value-format="YYYY.MM.DD"
      ></el-date-picker>
    </el-form-item>
    <el-form-item v-if="formItem.type == 'pull-selector'">
      <span style="font-size: 20px; margin: 15px; margin-top: 0">{{
        Object.keys(formItem.value)[0]
      }}</span>
      <el-select v-model="formResult.content[index].value">
        <div
          v-for="(checkItem, checkItemIndex) in Object.keys(formItem.value)"
          :key="checkItemIndex"
        >
          <el-option
            v-if="checkItemIndex > 0"
            :label="checkItem"
            :value="checkItemIndex - 1"
          ></el-option>
        </div>
      </el-select>
    </el-form-item>
    <el-form-item v-if="formItem.type == 'num-input'">
      <span>{{ formItem.value }}</span>
      <el-input-number
        v-model="formResult.content[index].value"
      ></el-input-number>
    </el-form-item>

    <el-divider />
  </el-form>
  <el-button @click="SubmitForm">提交问卷</el-button>
</template>

<script>
import axios from 'axios';
import { ElMessage } from "element-plus";
// import { isArray, isString } from "@vue/shared";
export default {
  data() {
    return {
      formResult: {
        content: [],
      },
      test: {
        a: 1,
        b: 2,
      },
      testa: 0,
      formObj: {},
    };
  },
  mounted() {
    // var string;

    this.formObj = JSON.parse(this.$route.params.json);
    var formItem;
    // axios.get('url',{
    //     formId:0,
    // }).then(res => {
    //     this.formObj = JSON.parse(res.data.data);
    // })

    // string = this.$route.params.json;

    // console.log(string);
    // console.log(obj);

    // var item;
    // for (item in this.test) {
    //   console.log(item, this.test[item]);
    //   console.log(this.test[item]);
    // }

    if (this.formObj["default"] != "default") {
      console.log(this.formObj);
      for (formItem in this.formObj.content) {
        console.log(formItem);
        console.log(123456);
        // if (isString(formObj.content[formItem].value)) {
        //   this.formResult.content.push({
        //     id: formItem,
        //     value: "",
        //   });
        // } else {
        if (this.formObj.content[formItem].type == "multi-check") {
          this.formResult.content.push({
            id: formItem,
            value: [],
          });
        } else {
          this.formResult.content.push({
            id: formItem,
            value: "",
          });
        }
        // }
      }
      console.log(this.formResult);
    }

    // this.testa=1
  },
  //   beforeRouteUpdate() {
  //     this.testa = 2;
  //   },
  //   beforeRouteEnter(to, from, next) {
  //     next((vm) => {
  //       // 通过 `vm` 访问组件实例
  //       // vm.testa = 3
  //     });
  //   },

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
  },
};
</script>