<template>
    <el-container>
      <el-header height="40px">
        <Header></Header>
      </el-header>
      <el-container>
        <!-- 左侧导航栏 -->
        <el-aside width="200px">
          <Menu></Menu>
        </el-aside>
          <!-- 内容区 -->
          <el-main>
            <!-- 此处放置el-tabs代码 -->
            <div class="tab">
              <el-tabs
                v-model="activeIndex"
                type="border-card"
                style="height: 38px"
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
              <canvas id="mycanvas" width="1200" height="0"></canvas>
            </div>
            <div>
              <router-view/>
            </div>
          </el-main>
        </el-container>
      </el-container>
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
    },
    doDraw(){
      //  获取canvas
      let canvas = document.getElementById("mycanvas")
      let context = canvas.getContext('2d')
      context.moveTo(0,0)
      context.lineTo(1200,0)
      context.strokeStyle = "#09c3f8"
      context.lineWidth = 0;
      context.stroke()
    }
  },
  mounted () {
    this.doDraw();
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
  },
  destroyed() {
    window.removeEventListener('scroll',this.getScrollPosition,false);
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
.el-aside {
  color: #333;
  position: fixed;
  height: 100%;
  left: 0;
  top: 40px;
}
.el-main{
  position: absolute;
  left: 200px;
  right: 0;
  top: 80px;
  bottom: 0;
}
.tab{
  position: fixed;
  left: 200px;
  top: 40px;
}
</style>
