<template>
  <div>
    <el-menu
      class="slider"
      background-color="#f3f3f3"
      text-color="#333"
      :default-active="$route.path"
      router
      @open="handleOpen"
      @close="handleClose">
      <NavMenu :navMenus="menuData"></NavMenu>
    </el-menu>
<!--    <router-view></router-view>-->
  </div>
</template>

<script>
import NavMenu from "./NavMenu.vue";
export default {
  components: {
    NavMenu: NavMenu
  },
  data() {
    return {
      menuData: [
        {
          //一级
          entity: {
            id: '0',
            icon: "el-icon-message",
            name: "首页",
            value:"/index"
          }
        },
        {
          //一级
          entity: {
            id: '1',
            icon: "el-icon-menu",
            name: "尿流检测"
          },
          //二级
          childs: [
            {
              entity: {
                id: '1-1',
                icon: "el-icon-phone-outline\r\n",
                name: "历史检测信息",
                value: "/pastTest"
              }
            },
            {
              entity: {
                id: '1-2',
                icon: "el-icon-phone-outline\r\n",
                name: "记录检测信息",
                value: "/todayTest"
              }
            }
          ]
        },
        {
          //一级
          entity: {
            id: '2',
            icon: "el-icon-menu",
            name: "排尿日志"
          },
          //二级
          childs: [
            {
              entity: {
                id: '2-1',
                icon: "el-icon-phone-outline\r\n",
                name: "历史日志",
                value: "/pastRcd"
              }
            },
            {
              entity: {
                id: '2-2',
                icon: "el-icon-phone-outline\r\n",
                name: "记录日志",
                value: "/todayRcd"
              }
            }
          ]
        },
        {
          //一级
          entity: {
            id: '3',
            icon: "el-icon-news",
            name: "量表"
          },
          //二级
          childs: [
            {
              entity: {
                id: '3-1',
                icon: "el-icon-phone-outline\r\n",
                name: "历史记录",
                value: "/evaluate"
              }
            },
            {
              entity: {
                id: '3-2',
                icon: "el-icon-phone-outline\r\n",
                name: "评测",
                value: ""
              },
              //三级
              childs: [
                {
                  entity: {
                    id: '3-2-1',
                    icon: "el-icon-service\r\n",
                    name: "IPSS",
                    value: "/IPSS"
                  }
                },
                {
                  entity: {
                    id: '3-2-2',
                    icon: "el-icon-service\r\n",
                    name: "OABSS",
                    value: "/OABSS"
                  }
                },
                {
                  entity: {
                    id: '3-2-3',
                    icon: "el-icon-service\r\n",
                    name: "SF-36",
                    value: "/SF-36"
                  }
                }
              ]
            }
          ]
        }
      ]
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style scoped>
.slider{
  height: 100vh;
}
</style>
