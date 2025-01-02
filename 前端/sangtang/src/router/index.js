import Vue from 'vue'
import Router from 'vue-router'
// 下面是新的 element-ui 的
import { Message } from 'element-ui';
import Main from '@/views/Main'
import Login from '@/views/Login'
import AllImg from '@/views/user/AllImg'
import AlarmImg from '@/views/user/AlarmImg'
import Tongji from '@/views/user/Tongji'

import CarAllImg from '@/views/car/AllImg'
import CarCount from '@/views/car/Tongji'

import yanjingAllImg from '@/views/yanjing/AllImg'
import yanjingAlarmImg from '@/views/yanjing/AlarmImg'
import yanjingTongji from '@/views/yanjing/Tongji'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/main',
      component: Main,
      children:[
        {path: '/yanjing/allImg', component: yanjingAllImg,meta: { requiresAuth: true }},
        {path: '/yanjing/alarmImg', component: yanjingAlarmImg,meta: { requiresAuth: true }},
        {path: '/yanjing/tongji', component: yanjingTongji,meta: { requiresAuth: true }}
      ],
      meta: { requiresAuth: true }
    },
    {
      path: '/',
      component: Login
    },
  ]
})
//全局路由守卫
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!localStorage.getItem('token'); // 使用 token 判断登录状态
  console.log("isLoggedIn",isLoggedIn);
  if (to.matched.some((record) => record.meta.requiresAuth) && !isLoggedIn) {
    // 如果路由需要登录权限且未登录
    Message.error("请登录");
    next('/'); // 跳转到登录页面
  } else {
    next(); // 继续访问
  }
});
export default router; // 确保默认导出了 router

