<template>
    <div class="content-box">
        <div>
        <el-table ref="Table1"
                    :data="list"
                    @select="select"
                    @select-all="selectAll"
                    style="width: 100%;"
                    :row-style="{height: '0'}"
                    :cell-style="{padding: '8px'}"
                    :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                    border>
            <el-table-column label="名称" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{scope.row.name}}</template>
            </el-table-column>
            <el-table-column label="总数" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{scope.row.allCount}}</template>
            </el-table-column>
            <el-table-column label="口罩正常佩戴次数" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{scope.row.whileCount}}</template>
            </el-table-column>
            <el-table-column label="口罩未正常佩戴次数" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">{{scope.row.halfWhileCount}}</template>
            </el-table-column>
            <el-table-column label="未佩戴口罩次数" align="center">
            <template slot-scope="scope">{{scope.row.blackCount}}</template>
            </el-table-column>
            <el-table-column label="口罩佩戴率" align="center">
            <template slot-scope="scope">{{scope.row.whileRate}}</template>
            </el-table-column>
        </el-table>
        </div>
    </div>
</template>
  
<script>
    import axios from 'axios'
    export default {
        name: 'AllImg',
        data(){
            return{
                list: [],
                listFinal:[],
            }
        },
        mounted(){
            this.getData();
        },
        methods:{
            getData(){
                console.log('this.$store.state=', JSON.parse(sessionStorage.getItem('state')));
                let sessionId = this.$store.state.user.sessionId;
                console.log("sessionId", sessionId);
                axios.get('http://localhost:8077/getMaskCount',
                {
                    params:{
                        "msg_id": "1036",
                        "sessionId":sessionId
                    }
                }).then((res) =>{
                    this.list = res.data.data;
                    console.log("this.list", this.list);
                    console.log(res.data.data);
                })
            }
        }
    }

</script>
  
<style scoped>
  .content-box{
    position: absolute;
    left: 210px;
    right: 0;
    top: 80px;
    bottom: 0;
    padding: 10px;
    width: 1270px;
    transition: left .3s ease-in-out;
    background-color: #f0f0f0;
    display: flex;
    flex-direction: column;
    height: 89%;
    /* overflow-y: auto; */
  }
</style>
 
 