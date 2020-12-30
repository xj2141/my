// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'//引入路由配置
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'

Vue.use(elementUI)

import axios from 'axios'
Vue.prototype.axios = axios

import qs from 'qs';
Vue.prototype.qs = qs;

/* 星空背景特效 */
import VueParticles from 'vue-particles'
Vue.use(VueParticles)

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,//使用路由配置
  components: {App},
  template: '<App/>'
})
