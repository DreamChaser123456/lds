<template>
  <div>
    <el-container style="min-height: 100vh;">
      <!-- 标题栏 -->
      <el-header class="header">
        <div class="header-title">商汤智能识别系统</div>
        <div class="header-right">
          <div class="time-wrapper">
            <i class="el-icon-time clock-icon"></i>
            <span class="time-text"><CurrentTime /></span>
          </div>
          <el-button
            type="primary"
            class="logout-button"
            @click="logout"
          >
            <i class="el-icon-switch-button logout-icon"></i>
            退出系统
          </el-button>
        </div>
      </el-header>
      <!-- 主体内容 -->
      <el-container>
        <el-aside width="250px" class="sidebar">
          <el-menu
            :default-openeds="['1', '2', '3']"
            background-color="#fff"
            text-color="#333"
            active-text-color="#409eff"
            router
            class="menu"
          >
            <el-submenu index="2">
              <template slot="title">
                <h3>
                  <i
                    class="el-icon-s-tools"
                    :style="{ color: 'rgba(21, 131, 237, 1)' }"
                  ></i>
                  眼镜管理
                </h3>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/yanjing/allImg">
                  <i
                    class="el-icon-camera-solid"
                    :style="{ color: 'rgba(200, 75, 214)' }"
                  ></i>
                  眼镜抓拍
                </el-menu-item>
                <el-menu-item index="/yanjing/alarmImg">
                  <i
                    class="el-icon-warning"
                    :style="{ color: 'rgba(255, 77, 11, 1)' }"
                  ></i>
                  眼镜告警
                </el-menu-item>
                <el-menu-item index="/yanjing/tongji">
                  <i
                    class="el-icon-loading"
                    :style="{ color: 'rgba(0,0,0)' }"
                  ></i>
                  眼镜统计
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CurrentTime from "@/components/CurrentTime.vue";
export default {
  name: "Main",
  components: {
    CurrentTime,
  },
  mounted(){
    console.log("loginid:",this.$store.state.login.loginId);
  },
  methods: {
    logout() {
      this.$store.state.login.loginId=0;
      localStorage.removeItem('token'); // 清除登录状态
      this.$router.replace('/'); // 跳转到登录页面并替换历史记录
    },
  },
};
</script>



<style scoped lang="scss">
/* Header部分 */
.el-header {
  background-color: #409eff; /* 主色调 */
  color: #fff;
  display: flex;
  justify-content: space-between; /* 两端对齐 */
  align-items: center; /* 垂直居中 */
  height: 100px; /* 高度 */
  padding: 0 20px; /* 内边距 */
  box-shadow: 5px 2px 8px rgba(0, 0, 0, 0.1);
}

/* 标题部分 */
.header-title {
  margin: 0 auto;
  margin-left:850px;
  font-size: 30px;
  font-weight: bold;
}

/* 时间和退出按钮部分 */
.header-right {
  display: flex;
  align-items: center;
  gap: 20px; /* 间距 */
}

/* 时间部分 */
.time-wrapper {
  display: flex;
  align-items: center;
  gap: 8px; /* 图标和文字之间的间距 */
  background: linear-gradient(90deg, rgba(0, 136, 255, 1), rgba(200, 75, 214, 1)); /* 渐变背景 */
  padding: 8px 15px;
  border-radius: 20px;
  color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* 退出按钮样式 */
.logout-button {
  background: linear-gradient(90deg, rgba(255, 77, 11, 1), rgba(21, 131, 237, 1));
  border: none;
  color: #fff;
  padding: 8px 20px;
  font-size: 16px;
  font-weight: bold;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 10px;
}
/* 菜单项悬浮效果 */
.el-menu-item:hover, .el-submenu__title:hover {
  background-color: rgb(80, 158, 232) !important; /* 浅蓝背景 */
  transform: translateX(5px) !important; /* 悬浮时右移 */
  color:white !important;
}
.el-menu-item.is-active {
  background-color: #409eff !important;
  color: white !important;
}
.logout-button:hover {
  background: linear-gradient(90deg, rgba(21, 131, 237, 1), rgba(255, 77, 11, 1));
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.logout-icon {
  font-size: 20px;
}
</style>

