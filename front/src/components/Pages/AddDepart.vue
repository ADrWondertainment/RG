<template>
    <el-dialog title="新建部门" model-value="1" v-model="isshow">
        <el-form :model="depart" label-width="120px">
            <el-form-item label="部门名称">
                <el-input v-model="depart.name" />
            </el-form-item>
<!--            <el-form-item label="是否添加成员">-->
<!--                <el-switch v-model="ifAddstaff" />-->
<!--            </el-form-item>-->
<!--            <el-form v-if="ifAddstaff">-->
<!--                <el-form-item>-->
<!--                    <div>hhh</div>-->
<!--                </el-form-item>-->

<!--            </el-form>-->
<!--            <el-form-item label="Activity zone">-->
<!--                <el-select v-model="depart" placeholder="please select your zone">-->
<!--                    <el-option label="Zone one" value="shanghai" />-->
<!--                    <el-option label="Zone two" value="beijing" />-->
<!--                </el-select>-->
<!--            </el-form-item>-->
<!--            <el-form-item label="Activity form">-->
<!--                <el-input v-model="depart" type="textarea" />-->
<!--            </el-form-item>-->
            <el-form-item>
                <el-button type="primary" @click="createDepart">创建</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
    export default {
        name: "AddDepart",
        data(){
            return{
                isshow:false,
                depart:{
                    name:"",
                },
                ifAddstaff:false,
                staff:[
                    {
                        "id":1,
                        "uid":"419210??",
                        "pos":"杜沛霖的儿",
                        "name":"林志康",
                        "telephone":110,
                        "depart":"0",
                        "right":9
                    },
                    {
                        "id":2,
                        "uid":41921011,
                        "pos":"林志康的爹",
                        "name":"杜沛霖",
                        "telephone":1881007821,
                        "depart":"0",
                        "right":1
                    },
                    {
                        "id":3,
                        "uid":419212011,
                        "pos":"林志康的爹",
                        "name":"杜沛霖",
                        "telephone":1881007821,
                        "depart":"0",
                        "right":1
                    },
                    {
                        "id":4,
                        "uid":4192111,
                        "pos":"林志康的爹",
                        "name":"陶沙",
                        "telephone":1881007821,
                        "depart":"0",
                        "right":1
                    },
                    {
                        "id":5,
                        "uid":4192101,
                        "pos":"林志康的爹",
                        "name":"庞小博",
                        "telephone":1881007821,
                        "depart":"0",
                        "right":1
                    },
                    {
                        "id":6,
                        "uid":419121011,
                        "pos":"林志康的爹",
                        "name":"ABC",
                        "telephone":1881007821,
                        "depart":"0",
                        "right":1
                    },
                ]
            }
        },
        methods:{
            isReserve(bool){
                if(bool){
                    this.depart={
                        name:"",
                    };
                    this.ifAddstaff=false;
                }
                else
                    return
            },
            createDepart(){
                axios.put("api/users/cdept",{
                    name:this.depart.name,
                    fid:0
                }).then(_=>{
                    alert("新建成功");
                }).catch(_=>{
                    alert("创建失败");
                });
                this.isshow=false;
            }
        }
    }
</script>

<style scoped>

</style>