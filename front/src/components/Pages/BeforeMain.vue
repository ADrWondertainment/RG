<template>
  <div class="background">
    <el-image src="src/assets/background.png" fit="scale-down" />
  </div>
  <router-view />
</template>


<style scoped>
.background {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
  overflow-y: hidden;
}
</style>