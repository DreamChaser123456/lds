<template>
    <div class="content-box">
        <div>
            <!-- 添加统计图 -->
            <el-card class="chart-card">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <div id="pieChart" style="width: 100%; height: 300px;"></div>
                    </el-col>
                    <el-col :span="12">
                        <div id="barChart" style="width: 100%; height: 300px;"></div>
                    </el-col>
                </el-row>
            </el-card>
            <el-divider></el-divider>
            <!-- 表格部分 -->
            <el-table
                ref="Table1"
                :data="list"
                style="width: 100%;"
                :row-style="tableRowStyle"
                :cell-style="{ padding: '8px' ,'text-align': 'center', 'vertical-align':'middle'}"
                :header-cell-style="{ background: '#eef1f6', color: '#606266' ,'text-align': 'center', 'vertical-align':'middle', border:'1px solid white'}"
                border
            >
                <el-table-column label="名称" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="总数" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ scope.row.allCount }}</template>
                </el-table-column>
                <el-table-column label="佩戴透明眼镜" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ scope.row.transparentCount }}</template>
                </el-table-column>
                <el-table-column label="佩戴普通眼镜" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ scope.row.ordinaryCount }}</template>
                </el-table-column>
                <el-table-column label="佩戴太阳镜" align="center">
                    <template slot-scope="scope">{{ scope.row.sunCount }}</template>
                </el-table-column>
                <el-table-column label="未佩戴" align="center">
                    <template slot-scope="scope">{{ scope.row.noneCount }}</template>
                </el-table-column>
                <el-table-column label="眼镜佩戴率" align="center">
                    <template slot-scope="scope">{{ scope.row.glassRate }}</template>
                </el-table-column>
            </el-table>
            <div style="height: 20px;"></div>
            <div class="block" style="text-align: center;">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[6]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';

export default {
    name: 'YanjingAllImg',
    data() {
        return {
            list: [],
            listFinal: [],
            total: 0,
            currentPage: 1,
            pageSize: 6,
        };
    },
    mounted() {
      this.startPolling(); // 开始轮询
    //   this.getData();
    },
    beforeDestroy() {
      this.stopPolling(); // 组件销毁时停止轮询
    },
    methods: {
        startPolling() {
          this.pollingInterval = setInterval(() => {
              this.fetchData(); // 调用接口的方法
          }, 2000);
        },
        stopPolling() {
            if (this.pollingInterval) {
                clearInterval(this.pollingInterval); // 清除定时器
            }
        },
        fetchData(){
            const sessionId = this.$store.state.user.sessionId;
            axios
                .get('http://localhost:8077/getGlassCount', {
                    params: {
                        msg_id: '1036',
                        sessionId: sessionId,
                    },
                })
                .then((res) => {
                    this.list = res.data.data;
                    this.renderCharts();
                });
        },
        tableRowStyle({ rowIndex }) {
            return {
            background: rowIndex % 2 === 1 ? "#f9f9f9" : "#ffffff",
            };
        },
        getData() {
            axios.get('/static/data1.json')
                .then((res) => {
                    this.list = res.data;
                    this.listFinal = this.list;
                    this.total = this.list.length;
                    this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
                    this.renderCharts();
                })
                .catch(error => {
                    console.error('Error loading data:', error);
                });
            // const sessionId = this.$store.state.user.sessionId;
            // axios
            //     .get('http://localhost:8077/getGlassCount', {
            //         params: {
            //             msg_id: '1036',
            //             sessionId: sessionId,
            //         },
            //     })
            //     .then((res) => {
            //         this.list = res.data.data;
            //         this.renderCharts();
            //     });
        },
        renderCharts() {
            const pieData = this.list
                .filter((item) => item.name !== "总和") // 过滤掉总和
                .map((item) => ({
                    value: item.allCount,
                    name: item.name,
                }));

            const barData = this.list
                .filter((item) => item.name !== "总和") // 过滤掉总和
                .map((item) => ({
                    name: item.name,
                    data: [item.transparentCount, item.ordinaryCount, item.sunCount, item.noneCount],
                }));

            // 渲染饼图
            const pieChart = echarts.init(document.getElementById('pieChart'));
            pieChart.setOption({
                title: {
                    text: '眼镜佩戴分布',
                    left: 'center',
                },
                tooltip: {
                    trigger: 'item',
                },
                legend: {
                    top: 'bottom',
                },
                series: [
                    {
                        name: '佩戴分布',
                        type: 'pie',
                        radius: '50%',
                        data: pieData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)',
                            },
                        },
                    },
                ],
            });

            // 渲染柱状图
            const barChart = echarts.init(document.getElementById('barChart'));
            barChart.setOption({
                title: {
                    text: '眼镜类型统计',
                    left: 'center',
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    data: ['透明眼镜', '普通眼镜', '太阳镜', '未佩戴'],
                    top: 'bottom',
                },
                xAxis: {
                    type: 'category',
                    data: barData.map((item) => item.name), // 使用过滤后的名称
                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        name: '透明眼镜',
                        type: 'bar',
                        data: barData.map((item) => item.data[0]), // 透明眼镜数据
                    },
                    {
                        name: '普通眼镜',
                        type: 'bar',
                        data: barData.map((item) => item.data[1]), // 普通眼镜数据
                    },
                    {
                        name: '太阳镜',
                        type: 'bar',
                        data: barData.map((item) => item.data[2]), // 太阳镜数据
                    },
                    {
                        name: '未佩戴',
                        type: 'bar',
                        data: barData.map((item) => item.data[3]), // 未佩戴数据
                    },
                ],
            });
        },

        handleSizeChange(val) {
            this.pageSize = val;
            this.list = this.listFinal;
            this.total = this.list.length;
            this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.list = this.listFinal;
            this.total = this.list.length;
            this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
        }
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
/* 原代码中饼状图和柱状图的白色背景 
 .chart-card {
    margin-bottom: 20px;
    background-color: #ffffff;
} */
/* 饼状图和柱状图的蓝色渐变背景 */
.chart-card{
  color:black;
  background: linear-gradient(to bottom, #77d7e3, #7cb9ea);
  padding: 20px;
  border-radius: 10px;
}
</style>
