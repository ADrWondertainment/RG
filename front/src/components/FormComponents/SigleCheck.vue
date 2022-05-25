<template>
  <el-radio-group v-model="selected">
    <el-radio
      v-for="(item, index) in items"
      v-bind:key="index"
      :label="index"
      @select="SigleChange"
      >{{ item }}</el-radio
    >
  </el-radio-group>
</template>

<script>
export default {
  data() {
    return {
      selected: null,
    };
  },
  props: {
    items: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  methods: {
    SigleChange() {
      this.$emit("change", this.selected);
    },
  },
};
</script>