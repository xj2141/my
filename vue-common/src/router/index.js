import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const constantRouterMap=[
  {
    //登录
    path:'/',
    name:'login',
    component:()=>import('@/views/user/login')
  },
  {
    //注册
    path:'/regist',
    name:'regist',
    component:()=>import('@/views/user/regist')
  },
  {
    //首页
    path:'/home',
    name:'home',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/home')
  },
  {
    //个人信息
    path:'/userInfo',
    name:'userInfo',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/user/userInfo')
  },
  {
    //修改密码
    path:'/changePwd',
    name:'changePwd',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/user/changePwd')
  },
  {
    //量表历史记录
    path:'/evaluate',
    name:'evaluate',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/evaluate/evaluate')
  },
  {
    //量表
    path:'/IPSS',
    name:'IPSS',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/evaluate/IPSS')
  },
  {
    //量表
    path:'/OABSS',
    name:'OABSS',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/evaluate/OABSS')
  },
  {
    //量表
    path:'/SF-36',
    name:'SF-36',
    meta: {
      requireAuth: true
    },
    component:()=>import('@/views/evaluate/SF-36')
  }
]

const router = new Router({
  // mode: 'history',
  routes: constantRouterMap //指定路由列表
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
    if (sessionStorage.getItem("token") == 'true') { // 判断本地是否存在token
      next()
    } else {
      // 未登录,跳转到登陆页面
      next({
        path: '/'
      })
    }
  } else {
    next();
  }
})

export default router
