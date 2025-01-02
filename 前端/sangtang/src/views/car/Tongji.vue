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
            <el-table-column label="车牌" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{scope.row.name}}</template>
            </el-table-column>
            <el-table-column label="车牌出现次数" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{scope.row.nameCount}}</template>
            </el-table-column>
            <el-table-column label="白色次数" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{scope.row.whiteColor}}</template>
            </el-table-column>
            <el-table-column label="其他颜色次数" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{scope.row.otherColor}}</template>
            </el-table-column>
            <el-table-column label="白色占比" align="center" :show-overflow-tooltip="true">
                <template slot-scope="scope">{{scope.row.whiteRate}}</template>
            </el-table-column>
        </el-table>
        </div>
    </div>
</template>
  
<script>
    import axios from 'axios'
    export default {
        name: 'YanjingAllImg',
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
                axios.get('http://localhost:8077/getCarCount',
                {
                    params:{
                        "msg_id": "1793",
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
 
 