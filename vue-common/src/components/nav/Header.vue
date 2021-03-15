<template>
  <div class="header">
    <span style="position: absolute;left:15px;top:9px;color: white">尿流监测系统</span>
    <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link" style="color: white">
    {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="a">个人信息</el-dropdown-item>
        <el-dropdown-item command="b">修改密码</el-dropdown-item>
        <el-dropdown-item command="c">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data(){
    return{
      username:''
    }
  },
  methods: {
    handleCommand(command) {
        if(command==='a'){
          this.$router.push('/userInfo')
        }else if(command==='b'){
          this.$router.push('/changePwd')
        }else if(command==='c'){
          this.$store.state.openTab = [];
          this.$store.state.activeIndex = '/index';
          sessionStorage.setItem("token", 'false');
          this.$router.push('/login')
        }
    }
  },
  mounted() {
    this.username=sessionStorage.getItem('username');
  }
}
</script>

<style scoped>
.header {
  margin-top: 10px;
  text-align: right;
}
</style>
