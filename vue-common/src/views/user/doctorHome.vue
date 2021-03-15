<template>
  <el-container>
    <el-header height="40px" style="text-align: right">
      <span style="position: absolute;left:15px;top:9px;color: white">管理员系统</span>
      <span style="color: white">账号：{{username}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <el-button type="text" @click="loginOut">退出登录</el-button>
    </el-header>
    <el-main>
      <div class="nav">
      <el-menu
        :default-active="this.$route.path"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        router
        background-color="#fff"
        text-color="#333"
        active-text-color="#0084ff"
        style="height: 50px">
        <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name" style="height: 50px">
          <template slot="title">
            <span> {{ item.navItem }}</span>
          </template>
        </el-menu-item>
      </el-menu>
      </div>
        <router-view/>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "doctorHome",
  data() {
    return {
      username:'',
      navList:[
        {name:'/home/index', navItem:'首页'},
        {name:'/home/userInfo',navItem:'个人信息'},
        {name:'/home/changePwd',navItem:'修改密码'},
      ]
    }
  },
  methods: {
    loginOut(){
        sessionStorage.setItem("token", 'false');
        this.$router.push('/login')
    }
  },
  created() {
    this.username=sessionStorage.getItem('username');
  }
}
</script>

<style scoped>
.el-header {
  background-color: #333;
  color: #333;
  position: fixed;
  width: 100%;
  left: 0;
  top: 0;
}
.nav{
  position: fixed;
  width: 100%;
  left: 0;
  top:40px;
}
</style>
