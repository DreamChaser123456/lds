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
          <el-table-column label="照片" align="center">
          <template slot-scope="scope">
              <el-image style="width: 35px; height: 35px;" 
              src="http://192.168.1.101/ws/record/face/channel2/2024/03/21/v214_1710996169_1376_0.jpg">
              </el-image>
          </template>
          </el-table-column>
          <el-table-column label="车牌" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{scope.row.name}} </template>
          </el-table-column>
        </el-table-column>
          <el-table-column label="车辆颜色" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{scope.row.carColor}} </template>
          </el-table-column>
        </el-table-column>
          <el-table-column label="车辆类别" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{scope.row.carType}} </template>
          </el-table-column>
          <el-table-column label="车辆方向" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{scope.row.carDirection}}</template>
          </el-table-column>
          <el-table-column label="抓拍时间" align="supervisionUnit" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{dateFormat('YYYY-mm-dd HH:MM',scope.row.date)}}</template>
          </el-table-column>
      </el-table>
      </div>
      <div style="height: 20px;"></div>
      <div class="block" style="text-align: center;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[8]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
      </div>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
      name: 'CarAllImg',
      data(){
          return{
              list: [],
              listFinal:[],
              total: 0, 
              currentPage: 1, //当前页数 ，默认为1
              pageSize: 8, // 每页显示数量
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
              axios.get('http://localhost:8077/getCar',
              {
                  params:{
                      "msg_id": "1793",
                      "sessionId":sessionId
                  }
              }).then((res) =>{
                var dataList = res.data.data;
                    this.list = dataList;
                    console.log(this.list);
                    console.log(res.data.data);
                    this.listFinal = this.list;
                    this.total = this.list.length;
                    this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
              })
          },
          handleSizeChange(val){ 
              this.pageSize = val;
              this.list = this.listFinal;
              this.total = this.list.length;
              this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);      
          },
          handleCurrentChange(val){
              this.currentPage = val;
              this.list = this.listFinal;
              this.total = this.list.length;
              this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
          },
          dateFormat(fmt, date) {
            let ret="";
            date=new Date(date);
            const opt = {
                'Y+': date.getFullYear().toString(), // 年
                'm+': (date.getMonth() + 1).toString(), // 月
                'd+': date.getDate().toString(), // 日
                'H+': date.getHours().toString(), // 时
                'M+': date.getMinutes().toString(), // 分
                'S+': date.getSeconds().toString() // 秒
                // 有其他格式化字符需求可以继续添加，必须转化成字符串
            }
            for (let k in opt) {
                ret = new RegExp('(' + k + ')').exec(fmt)
                if (ret) {
                fmt = fmt.replace(
                    ret[1],
                    ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
                )
                }
            }
            return fmt
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

