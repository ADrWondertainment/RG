<template>
  <HeaderBar />
  <div style="display: flex">
    <SideBar />
    <el-container>
      <div>
        <img alt="Vue logo" class="element-plus-logo" src="./assets/logo.png" />
        <hello-world msg="Hello Vue 3.0 + Element Plus + Vite"></hello-world>
        <multi-check :items="[1,2,3]"></multi-check>
      </div>
    </el-container>
  </div>
</template>

<style>
#app {
  text-align: center;
  /* color: var(--el-text-color-primary); */
  color: cornflowerblue;
}

.element-plus-logo {
  width: 50%;
}
</style>