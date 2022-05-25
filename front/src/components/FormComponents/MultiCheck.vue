<template>
  <el-checkbox-group @change="MutltiChange" v-model="selectedItems">
    <el-checkbox v-for="item in items" :key="item" :label="item"></el-checkbox>
  </el-checkbox-group>
</template>

<script>
export default {
  props: {
    items: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    return {
      selectedItems: [],
    };
  },
  methods: {
    MutltiChange() {
      this.$emit("change", this.selectedItems);
    },
  },
};
</script>
