<template>
  <div>
    <el-menu
      class="slider"
      background-color="#f3f3f3"
      text-color="#333"
      :default-active="$route.path"
      router
    >
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
            id: 0,
            icon: "el-icon-message",
            name: "首页",
            value:"/index"
          }
        },
        {
          //一级
          entity: {
            id: 4,
            icon: "el-icon-news",
            name: "量表"
          },
          //二级
          childs: [
            {
              entity: {
                id: 40,
                icon: "el-icon-phone-outline\r\n",
                name: "历史记录",
                value: "/evaluate"
              }
            },
            {
              entity: {
                id: 41,
                icon: "el-icon-phone-outline\r\n",
                name: "评测",
                value: ""
              },
              //三级
              childs: [
                {
                  entity: {
                    id: 42,
                    icon: "el-icon-service\r\n",
                    name: "IPSS",
                    value: "/IPSS"
                  }
                },
                {
                  entity: {
                    id: 43,
                    icon: "el-icon-service\r\n",
                    name: "OABSS",
                    value: "/OABSS"
                  }
                },
                {
                  entity: {
                    id: 44,
                    icon: "el-icon-service\r\n",
                    name: "SF-36",
                    value: "/SF-36"
                  }
                }
              ]
            }
          ]
        },
        {
          //一级
          entity: {
            id: 7,
            icon: "el-icon-menu",
            name: "排尿日志"
          },
          //二级
          childs: [
            {
              entity: {
                id: 8,
                icon: "el-icon-phone-outline\r\n",
                name: "历史日志",
                value: "/pastRcd"
              }
            },
            {
              entity: {
                id: 9,
                icon: "el-icon-phone-outline\r\n",
                name: "每日日志",
                value: "/todayRcd"
              }
            }
          ]
        }
      ]
    };
  }
};
</script>

<style scoped>
.slider{
  height: 100vh;
}
</style>
