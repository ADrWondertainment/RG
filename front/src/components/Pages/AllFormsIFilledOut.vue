<template>
    <h1>我填过的表单</h1>

</template>

<script>
export default{
    data(){
        return{
            testStr:`{
    "errorCode": 66666,
    "message": "成功获取调查信息",
    "data": "[
    {
        "id": 22,
        "sid": 19,
        "uid": 10,
        "data": "[{"id": 0, "value": ["答案1"]}, {"id": 1, "value": ["选项1"]}, {"id": 2, "value": ["选项1"]}, {"id": 3, "value": ["2022.05.28"]}, {"id": 4, "value": ["表项1"]}, {"id": 5, "value": ["2"]}]",
        "done": 1,
        "pass": 0
    },//已提交
    {
        "id": 23,
        "sid": 20,
        "uid": 10,
        "data": "[{"id": 0, "value": ["答案2"]}, {"id": 1, "value": ["选项2"]}, {"id": 2, "value": []}, {"id": 3}, {"id": 4, "value": ["表项2"]}, {"id": 5, "value": ["0"]}]",
        "done": 0,
        "pass": 0
    }//暂存
]"
}`
        }
    },
    mounted(){
        console.log(JJJJSON.parse(this.testStr))
    }
}
</script>
