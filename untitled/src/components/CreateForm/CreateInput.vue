<template>
  <span>请输入输入框的描述</span>
  <el-input v-model="description"></el-input>
</template>

<script>
export default {
  data() {
    return {
      description: "",
    };
  },
  methods: {
    submit(value) {
      this.$route.push({
        name: "",
        params: {
          description: this.description,
        },
      });
    },
  },
};
</script>

<style scoped>
  
</style>