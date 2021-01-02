<template>
  <div style="position: fixed">
  <el-container style="height: 600px; border: 1px solid #eee">
        <!-- 左侧导航栏 -->
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <Menu></Menu>
        </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
          <Header></Header>
        <!-- 此处放置el-tabs代码 -->
        <div class="tab">
          <el-tabs
            v-model="activeIndex"
            type="card"
            closable
            @tab-click='tabClick'
            @tab-remove='tabRemove'>
            <el-tab-pane
              v-for="item of openTab"
              v-if="openTab.length"
              :key="item.name"
              :label="item.name"
              :name="item.route">
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-header>
        <!-- 内容区 -->
        <el-main>
          <div>
            <router-view/>
          </div>
        </el-main>
    </el-container>
  </el-container>
  </div>
</template>

<script>
import Header from '@/components/nav/Header'
import Menu from '@/components/nav/Menu'
export default {
  name: 'home',
  components: {
    Header,
    Menu
  },
  data(){
    return{
    }
  },
  methods:{
    //tab标签点击时，切换相应的路由
    tabClick(tab){
      this.$router.push({path: this.activeIndex});
    },
    //移除tab标签
    tabRemove(target){
      //首页不删
      if(target == '/index'){
        return
      }
      this.$store.commit('delete_tabs', target);
      if (this.activeIndex === target) {
        // 设置当前激活的路由
        if (this.openTab && this.openTab.length >= 1) {
          this.$store.commit('set_active_index', this.openTab[this.openTab.length-1].route);
          this.$router.push({path: this.activeIndex});
        }
      }
    }
  },
  mounted () {
    // 刷新时以当前路由做为tab加入tabs
    // 当前路由不是首页时，添加首页以及另一页到store里，并设置激活状态
    // 当当前路由是首页时，添加首页到store，并设置激活状态
    this.$store.state.openTab = [];
    this.$store.commit('add_tabs', {route: '/index', name: '首页'})
    this.$store.commit('set_active_index', '/index')
    this.$router.push('/index')
  },
  computed: {
    openTab () {
      return this.$store.state.openTab;
    },
    activeIndex:{
      get () {
        return this.$store.state.activeIndex;
      },
      set (val) {
        this.$store.commit('set_active_index', val);
      }
    }
  },
  watch:{
    '$route'(to,from){
      //判断路由是否已经打开
      //已经打开的 ，将其置为active
      //未打开的，将其放入队列里
      let flag = false;
      for(let item of this.openTab){
        if(item.name === to.name){
          this.$store.commit('set_active_index',to.path)
          flag = true;
          break;
        }
      }
      if(!flag){
        this.$store.commit('add_tabs', {route: to.path, name: to.name});
        this.$store.commit('set_active_index', to.path);
      }
    }
  }
}
</script>
<style scoped>
.el-header {
  width: 1147px;
  height:30px;
  background-color: #B3C0D1;
  color: #333;
}
.el-aside {
  color: #333;
}
.tab{
  margin-top: 2%;
}
</style>
