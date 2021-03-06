import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const constantRouterMap=[
  {
    //登录
    path:'/login',
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
    path:'/',
    name:'home',
    meta: {
      requireAuth: true
    },
    redirect:'index',
    component:()=>import('@/views/home'),
    children:[
      {
        //首页
        path:'index',
        name:'首页',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/index')
      },
      {
        //量表历史记录
        path:'evaluate',
        name:'历史记录',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/evaluate/evaluate')
      },
      {
        //量表
        path:'IPSS',
        name:'IPSS',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/evaluate/IPSS')
      },
      {
        //量表
        path:'OABSS',
        name:'OABSS',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/evaluate/OABSS')
      },
      {
        //量表
        path:'SF-36',
        name:'SF-36',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/evaluate/SF-36')
      },
      {
        //个人信息
        path:'/userInfo',
        name:'个人信息',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/user/userInfo')
      },
      {
        //修改密码
        path:'/changePwd',
        name:'修改密码',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/user/changePwd')
      },
      {
        //历史日志
        path:'/pastRcd',
        name:'历史日志',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/record/pastRcd')
      },
      {
        //每日日志
        path:'/todayRcd',
        name:'每日日志',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/record/todayRcd')
      },
      {
        //历史检测
        path:'/pastTest',
        name:'历史检测',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/flowTest/pastTest')
      },
      {
        //每日检测
        path:'/todayTest',
        name:'每日检测',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/views/flowTest/todayTest')
      },
      {
        //测试
        path:'/flowTest',
        name:'尿流检测',
        meta: {
          requireAuth: true
        },
        component:()=>import('@/components/flow/FlowTest')
      }
    ]
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
        path: '/login'
      })
    }
  } else {
    next();
  }
})

export default router
