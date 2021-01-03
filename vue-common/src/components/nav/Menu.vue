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
            id: 1,
            icon: "el-icon-news",
            name: "量表"
          },
          //二级
          childs: [
            {
              entity: {
                id: 2,
                icon: "el-icon-phone-outline\r\n",
                name: "历史记录",
                value: "/evaluate"
              }
            },
            {
              entity: {
                id: 3,
                icon: "el-icon-phone-outline\r\n",
                name: "评测",
                value: ""
              },
              //三级
              childs: [
                {
                  entity: {
                    id: 14,
                    icon: "el-icon-service\r\n",
                    name: "IPSS",
                    value: "/IPSS"
                  }
                },
                {
                  entity: {
                    id: 15,
                    icon: "el-icon-service\r\n",
                    name: "OABSS",
                    value: "/OABSS"
                  }
                },
                {
                  entity: {
                    id: 16,
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
  }
};
</script>

<style scoped>
.slider{
  height: 100vh;
}
</style>
