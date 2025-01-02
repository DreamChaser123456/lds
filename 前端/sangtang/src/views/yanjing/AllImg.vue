<template>
    <div class="content-box">
        <div>
            <el-table ref="Table1"
                    :data="list"
                    style="width: 100%;"
                    :row-style="tableRowStyle"
                    :cell-style="{padding: '12px','text-align': 'center', 'vertical-align':'middle'}"
                    :header-cell-style="{ background: '#67C23A', fontSize:'20px', color: '#fff', fontWeight: 'bold' ,'text-align': 'center', 'vertical-align':'middle'}"
                    :row-class-name="tableRowClassName"
                    border>
                <el-table-column label="照片" align="center">
                    <template slot-scope="scope">
                        <!-- 使用自定义组件加载图片 -->
                        <ImageWithSession 
                            :key="scope.row.imgPath"
                            :imgPath="scope.row.imgPath" 
                            :alt="`照片-${scope.row.imgPath}`"
                        />
                    </template>
                </el-table-column>
                <el-table-column label="性别" align="center">
                    <template slot-scope="scope">
                    <i :class="scope.row.gender==='男'?'el-icon-male':'el-icon-female'" :style="{ color: scope.row.gender === '男' ? '#2196f3' : '#e91e63' }"/>
                    {{ scope.row.gender }}
                    </template>
                </el-table-column>
                <el-table-column label="年龄" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ scope.row.age }}</template>
                </el-table-column>
                <el-table-column label="眼镜" align="center">
                    <template slot-scope="scope"> 
                        <i :class="scope.row.glass === '无眼镜' ? 'el-icon-close' : 'el-icon-view'" :style="{color:scope.row.glass != '无眼镜' ? '#ff9800' : '#9e9e9e'}" />
                    {{ scope.row.glass }}
                    </template>
                </el-table-column>
                <el-table-column label="人像库名称" align="supervisionUnit">
                    <template slot-scope="scope">{{ scope.row.libName }}</template>
                </el-table-column>
                <el-table-column label="人像库类型" align="supervisionUnit">
                    <template slot-scope="scope">{{ scope.row.libType }}</template>
                </el-table-column>
                <el-table-column label="抓拍时间" align="supervisionUnit" :show-overflow-tooltip="true">
                    <template slot-scope="scope">{{ dateFormat('YYYY-mm-dd HH:MM:SS', scope.row.date) }}</template>
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
import axios from 'axios';
import ImageWithSession from '@/components/ImageWithSession.vue'; // 路径根据项目结构调整
import { color } from 'echarts';

export default {
    name: 'YanjingAllImg',
    components: {
        ImageWithSession, // 注册组件
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
      this.startPolling(); // 开始轮询并返回真实数据
    //   this.getData();//返回假数据
    },
    beforeDestroy() {
      this.stopPolling(); // 组件销毁时停止轮询
    },
    methods: {
        startPolling() {
          this.pollingInterval = setInterval(() => {
              this.fetchData(); // 调用接口的方法
          }, 3000);
        },
        stopPolling() {
            if (this.pollingInterval) {
                clearInterval(this.pollingInterval); // 清除定时器
            }
        },
        fetchData(){
            const sessionId = this.$store.state.user.sessionId;
            axios.get('http://localhost:8077/getAll', {
                params: {
                    "msg_id": "1037",
                    "sessionId": sessionId,
                },
            }).then((res) => {
                const dataList = res.data.data;
                this.list = dataList.map(item => {
                    item.imgPath = item.imgPath.replace(/"/g, ''); // 去除双引号
                    return item;
                });
                this.listFinal = this.list;
                this.total = this.list.length;
                this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
            });
        },
        tableRowStyle({ rowIndex }) {
            return {
            background: rowIndex % 2 === 0 ? "#f9f9f9" : "#ffffff",
            };
        },
        dateFormat(fmt, date) {
            let ret = '';
            date = new Date(date);
            const opt = {
                'Y+': date.getFullYear().toString(),
                'm+': (date.getMonth() + 1).toString(),
                'd+': date.getDate().toString(),
                'H+': date.getHours().toString(),
                'M+': date.getMinutes().toString(),
                'S+': date.getSeconds().toString(),
            };
            for (let k in opt) {
                ret = new RegExp('(' + k + ')').exec(fmt);
                if (ret) {
                    fmt = fmt.replace(
                        ret[1],
                        ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
                    );
                }
            }
            return fmt;
        },
        getData() {
            axios.get('/static/data.json')
                .then((res) => {
                    const dataList = res.data;
                    this.list=dataList;
                    this.listFinal = this.list;
                    this.total = this.list.length;
                    this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
                })
                .catch(error => {
                    console.error('Error loading data:', error);
                });
            // const sessionId = this.$store.state.user.sessionId;
            // axios.get('http://localhost:8077/getAll', {
            //     params: {
            //         "msg_id": "1037",
            //         "sessionId": sessionId,
            //     },
            // }).then((res) => {
            //     const dataList = res.data.data;
            //     this.list = dataList.map(item => {
            //         item.imgPath = item.imgPath.replace(/"/g, ''); // 去除双引号
            //         return item;
            //     });
            //     this.listFinal = this.list;
            //     this.total = this.list.length;
            //     this.list = this.list.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
            // });
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
        },
        exportJson() {
            const jsonData = JSON.stringify(this.list, null, 2); // 格式化为 JSON 字符串
            const blob = new Blob([jsonData], { type: 'application/json' }); // 创建 Blob 对象
            const link = document.createElement('a'); // 创建下载链接
            link.href = URL.createObjectURL(blob); // 创建 URL 对象
            link.download = 'data.json'; // 设置下载文件名
            link.click(); // 触发下载
        },

    },
};
</script>
<style>
    .content-box {
        padding: 20px;
        background-color: rgb(250, 250, 250);
        border-radius: 8px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        display: flex;
        flex-direction: column;
        height: calc(100vh - 120px);
    }
    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
    background: #f0f9eb;
    }

</style>
