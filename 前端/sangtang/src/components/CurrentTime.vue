<template>
    <div>{{ currentTime }}</div>
  </template>
  
  <script>
  export default {
    name: "CurrentTime",
    data() {
      return {
        currentTime: this.getCurrentTime(),
      };
    },
    methods: {
      getCurrentTime() {
        const now = new Date();
        return now.toLocaleTimeString("zh-CN", { hour12: false }); // 24小时制格式
      },
      updateTime() {
        this.currentTime = this.getCurrentTime();
      },
    },
    mounted() {
      this.timer = setInterval(this.updateTime, 1000); // 每秒更新
    },
    beforeDestroy() {
      clearInterval(this.timer); // 组件销毁时清除定时器
    },
  };
  </script>
  
  <style scoped>
  /* 时间组件的样式 */
  div {
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    text-align: right;
  }
  </style>
  