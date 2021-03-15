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
    //管理员首页
    path:'/admin',
    name:'首页',
    meta: {
      requireAuth: true,
      requireRole:'admin'
    },
    component:()=>import('@/views/user/adminHome')
  },
  {
    path:'/',
    name:'patientHome',
    meta: {
      requireAuth: true
    },
    redirect:'index',
    component:()=>import('@/views/user/patientHome'),
    children:[
      {
        //首页
        path:'index',
        name:'首页',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/patient/index')
      },
      {
        //量表历史记录
        path:'evaluate',
        name:'历史记录',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/evaluate/evaluate')
      },
      {
        //量表
        path:'IPSS',
        name:'IPSS',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/evaluate/IPSS')
      },
      {
        //量表
        path:'OABSS',
        name:'OABSS',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/evaluate/OABSS')
      },
      {
        //量表
        path:'SF-36',
        name:'SF-36',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/evaluate/SF-36')
      },
      {
        //个人信息
        path:'/userInfo',
        name:'个人信息',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/patient/userInfo')
      },
      {
        //修改密码
        path:'/changePwd',
        name:'修改密码',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/patient/changePwd')
      },
      {
        //历史日志
        path:'/pastRcd',
        name:'历史日志',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/record/pastRcd')
      },
      {
        //每日日志
        path:'/todayRcd',
        name:'记录日志',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/record/todayRcd')
      },
      {
        //历史检测
        path:'/pastTest',
        name:'历史检测信息',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/flowTest/pastTest')
      },
      {
        //每日检测
        path:'/todayTest',
        name:'记录检测信息',
        meta: {
          requireAuth: true,
          requireRole:'patient'
        },
        component:()=>import('@/views/flowTest/todayTest')
      }
    ]
  },
  {
    //医生系统
    path:'/home',
    name:'首页',
    meta: {
      requireAuth: true
    },
    redirect:'/home/index',
    component:()=>import('@/views/user/doctorHome'),
    children:[
      {
        //医生首页
        path:'/home/index',
        name:'首页',
        meta: {
          requireAuth: true,
          requireRole:'doctor'
        },
        component:()=>import('@/views/doctor/index')
      },
      {
        //个人信息
        path:'/home/userInfo',
        name:'个人信息',
        meta: {
          requireAuth: true,
          requireRole:'doctor'
        },
        component:()=>import('@/views/doctor/userInfo')
      },
      {
        //修改密码
        path:'/home/changePwd',
        name:'修改密码',
        meta: {
          requireAuth: true,
          requireRole:'doctor'
        },
        component:()=>import('@/views/doctor/changePwd')
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
      if(sessionStorage.getItem("role")==to.meta.requireRole){
        next()
      }else{
        next({
          path: from.path
        })
      }
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
