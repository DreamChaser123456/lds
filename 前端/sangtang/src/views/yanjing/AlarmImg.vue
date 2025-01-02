<template>
    <div class="content-box">
      <div>
        <el-table
          ref="Table1"
          :data="list"
          @select="select"
          @select-all="selectAll"
          style="width: 100%;"
          :row-style="tableRowStyle"
          :cell-style="{ padding: '12px 0px','text-align': 'center', 'vertical-align':'middle'}"
          :header-cell-style="{ background: '#67C23A', fontSize:'20px', color: '#fff', fontWeight: 'bold','text-align': 'center', 'vertical-align':'middle' }"
          border
          highlight-current-row
        >
          <el-table-column label="照片" align="center">
            <template slot-scope="scope">
              <ImageWithSession  :key="scope.row.imgPath" :imgPath="scope.row.imgPath" :alt="`照片-${scope.row.imgPath}`" />
            </template>
          </el-table-column>
          <el-table-column label="性别" align="center">
              <template slot-scope="scope">
              <i :class="scope.row.gender==='男'?'el-icon-male':'el-icon-female'" :style="{ color: scope.row.gender === '男' ? '#2196f3' : '#e91e63' }"/>
              {{ scope.row.gender }}
              </template>
          </el-table-column>
          <el-table-column label="姓名" align="center">
            <template slot-scope="scope">
              <el-tag type="success">{{ scope.row.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="相似度" align="center">
            <template slot-scope="scope">
              <el-progress :percentage="scope.row.similarity" />
            </template>
          </el-table-column>
          <el-table-column label="年龄" align="center">
            <template slot-scope="scope">
              {{ scope.row.age }}
            </template>
          </el-table-column>
          <el-table-column label="眼镜" align="center">
            <template slot-scope="scope"> 
                <i :class="scope.row.glass === '无眼镜' ? 'el-icon-close' : 'el-icon-view'" :style="{color:scope.row.glass != '无眼镜' ? '#ff9800' : '#9e9e9e'}" />
              {{ scope.row.glass }}
            </template>
          </el-table-column>
          <el-table-column label="人像库名称" align="center">
            <template slot-scope="scope">
              {{ scope.row.libName }}
            </template>
          </el-table-column>
          <el-table-column label="人像库类型" align="center">
            <template slot-scope="scope">
              {{ scope.row.libType }}
            </template>
          </el-table-column>
          <el-table-column label="抓拍时间" align="center">
            <template slot-scope="scope" >
              {{ dateFormat('YYYY-mm-dd HH:MM:SS', scope.row.date) }}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="spacer"></div>
      <div class="pagination-box">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[8]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        />
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  import ImageWithSession from "@/components/ImageWithSession.vue";
import { color } from "echarts";
  
  export default {
    name: "YanjingAllImg",
    components: {
      ImageWithSession,
    },
    data() {
      return {
        list: [],
        listFinal: [],
        total: 0,
        currentPage: 1,
        pageSize: 8,
      };
    },
    mounted() {
      this.startPolling(); // 开始轮询
      // this.getData();
    },
    beforeDestroy() {
      this.stopPolling(); // 组件销毁时停止轮询
    },
    methods: {
        startPolling() {
          this.pollingInterval = setInterval(() => {
              this.fetchData(); // 调用接口的方法
          }, 1000);
        },
        stopPolling() {
            if (this.pollingInterval) {
                clearInterval(this.pollingInterval); // 清除定时器
            }
        },
        fetchData() {
            const sessionId = this.$store.state.user.sessionId;
            axios
                .get("http://localhost:8077/getAlarm", {
                    params: {
                        msg_id: "1036",
                        sessionId: sessionId,
                    },
                })
                .then((res) => {
                    var dataList = res.data.data;
                    this.list = dataList;
                    this.listFinal = this.list;
                    this.total = this.list.length;
                    this.list = this.list.slice(
                        (this.currentPage - 1) * this.pageSize,
                        this.currentPage * this.pageSize
                    );
                })
                .catch((error) => {
                    console.error("Error fetching data:", error);
                });
        },
      tableRowStyle({ rowIndex }) {
        return {
          background: rowIndex % 2 === 0 ? "#f9f9f9" : "#ffffff",
        };
      },
      dateFormat(fmt, date) {
        date = new Date(date);
        const opt = {
          "Y+": date.getFullYear().toString(),
          "m+": (date.getMonth() + 1).toString(),
          "d+": date.getDate().toString(),
          "H+": date.getHours().toString(),
          "M+": date.getMinutes().toString(),
          "S+": date.getSeconds().toString(),
        };
        for (let k in opt) {
          let ret = new RegExp("(" + k + ")").exec(fmt);
          if (ret) {
            fmt = fmt.replace(
              ret[1],
              ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, "0")
            );
          }
        }
        return fmt;
      },
      getData() {
        axios.get('/static/data2.json')
                .then((res) => {
                    const dataList = res.data;
                    this.list = dataList;
                    this.listFinal = this.list;
                    this.total = this.list.length;
                    this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
                })
                .catch(error => {
                    console.error('Error loading data:', error);
                });
      },
      handleSizeChange(val) {
        this.pageSize = val;
        this.list = this.listFinal.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
        );
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.list = this.listFinal.slice(
          (this.currentPage - 1) * this.pageSize,
          this.currentPage * this.pageSize
        );
      },
    },
  };
  </script>
  
  <style scoped>
  .content-box {
    padding: 20px;
    background-color: rgb(250, 250, 250);
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    height: calc(100vh - 120px);
  }
  .cell{
    padding-left: 0px;
    padding-right:0px;
  }
  .el-table {
    border-radius: 6px;
  }
  .el-table th {
    background-color: #4c84af !important;
    color: #fff;
    font-size: 30px;
    font-weight: bold;
  }
  .el-table tr:nth-child(even) {
    background-color: #f5f7fa;
  }
  
  .el-table tr:hover {
    background-color: #e6f7ff !important;
    transition: background-color 0.3s;
  }
  
  .pagination-box {
    text-align: center;
    margin-top: 20px;
  }
  
  .spacer {
    height: 20px;
  }
  </style>
  