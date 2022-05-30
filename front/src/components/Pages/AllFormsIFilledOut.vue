<template>
    <h1>我填过的表单</h1>
    <el-table :data="objList">
        <el-table-column prop="id" label="表单编号"/>
        <!-- <el-table-column :prop="done"/> -->
    </el-table>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            testStr:`{
    "errorCode": 66666,
    "message": "成功获取调查信息",
    "data": [
    {
        "id": 22,
        "sid": 19,
        "uid": 10,
        "data": [{"id": 0, "value": ["答案1"]}, {"id": 1, "value": ["选项1"]}, {"id": 2, "value": ["选项1"]}, {"id": 3, "value": ["2022.05.28"]}, {"id": 4, "value": ["表项1"]}, {"id": 5, "value": ["2"]}],
        "done": 1,
        "pass": 0
    },
    {
        "id": 23,
        "sid": 20,
        "uid": 10,
        "data": [{"id": 0, "value": ["答案2"]}, {"id": 1, "value": ["选项2"]}, {"id": 2, "value": []}, {"id": 3}, {"id": 4, "value": ["表项2"]}, {"id": 5, "value": ["0"]}],
        "done": 0,
        "pass": 0
    }
]
}`,
        objList:null,
        }
    },
    mounted(){
        // axios.post('/api/sheets/answer',{
        //     "pageNum": 1,
        //     "size": 10
        // }).then(res => {
        //     if(res.errorCode === 66666){
        //         this.objList = JSON.parse(res.data).data
        //     }
        // })
        console.log(JSON.parse(this.testStr))
        this.objList = JSON.parse(this.testStr).data
        console.log(this.objList)
    }
}
</script>
