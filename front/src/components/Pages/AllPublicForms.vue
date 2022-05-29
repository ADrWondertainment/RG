<template>
    <h1>我发布的表单</h1>
    <div>
        <el-button-group v-model="formKind">
            <el-radio-button label="all">全部表单</el-radio-button>
            <el-radio-button label="query">问卷</el-radio-button>
            <el-radio-button label="approval">审批表</el-radio-button>
        </el-button-group>
    </div>
</template>


<script>
export default{
    data(){
        return{
            formKind:'all',
        }
    }
}
</script>