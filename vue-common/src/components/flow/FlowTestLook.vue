<template>
  <div style="width: 893px;margin-left: 28px">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
      </div>
      <div>
        <el-form :inline="true">
          <el-form-item :label="'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'姓名:'">
            <el-input v-model="infoForm.name" disabled size="mini" style="width: 180px;"></el-input>
          </el-form-item>
          <el-form-item :label="'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'性别:'">
            <el-input v-model="infoForm.sex" disabled size="mini" style="width: 180px;"></el-input>
          </el-form-item>
          <el-form-item :label="'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'年龄:'">
            <el-input v-model="infoForm.age" disabled size="mini" style="width: 180px;"></el-input>
          </el-form-item>
        </el-form>
        <el-form :inline="true">
          <el-form-item :label="'\xa0\xa0'+'检测时间:'">
            <el-input v-model="testForm.testTime" disabled size="mini" style="width: 180px;"></el-input>
          </el-form-item>
          <el-form-item :label="'\xa0\xa0'+'检测地点:'">
            <el-input v-model="testForm.testPlace" disabled size="mini" style="width: 180px;"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <br/>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>尿流率测定</span>
      </div>
      <div>
        <br/>
        <div class="title"><u>No1.曲线</u></div>
        <div :id="nowId" :style="{width: '800px', height: '400px', marginLeft:'25px'}"></div>
        <div class="title"><u>No2.参数</u></div>
        <br/>
        <div style="margin-left: 120px;">
          <span>尿量(VV)：{{ testForm.vv }}ml</span>
          <span style="margin-left: 120px">尿流时间(FT)：{{ testForm.ft }}s</span>
          <span style="margin-left: 120px">达峰时间(TQmax)：{{ testForm.tqmax }}s</span>
          <br/>
          <span>最大尿流率(Qmax)：{{ testForm.qmax }}ml/s</span>
          <span style="margin-left: 60px">平均尿流率(Qave)：{{ testForm.qave }}ml/s</span>
        </div>
        <br/>
        <div class="title"><u>No3.结论</u></div>
        <br/>
        <div style="text-align: center">{{ testForm.conclusion }}</div>
      </div>
    </el-card>
  </div>
</template>

<script>
// 引入基本模板
let echarts = require('echarts/lib/echarts');
// 引入折线图等组件
require("echarts/lib/chart/line");
// 引入提示框和title组件，图例
require("echarts/lib/component/tooltip");
require("echarts/lib/component/grid");
require("echarts/lib/component/legend");

export default {
  name: "FlowTestLook",
  props:{
    username:{
      type:String,
      default:''
    },
    nowId:{
      type:String,
      default:'0'
    },
    nowTestForm:{
      type:Object,
      default:()=>{}
    },
    nowFlowData:{
      type:Array,
      default:()=>{}
    }
  },
  data() {
    return {
      id:'',
      infoForm: {
        name: '',
        sex: '',
        age: ''
      },
      testForm: {
        testDate: '',
        testTime: '',
        testPlace: '',
        vv: '',
        qmax: '',
        qave:'',
        ft: '',
        tqmax: '',
        flowBeginId: '',
        flowEndId: '',
        conclusion: ''
      },
      flowData:[],
      option: {
        title: {text: '尿流率曲线'},
        backgroundColor: '#FBFBFB',
        xAxis: {
          data: [],
          name: '时间(s)',
          axisTick: {
            alignWithLabel: true,
            inside: true
          }
        },
        yAxis: {
          name: '尿流率(ml/s)',
          axisLine: {
            show: true
          },
          axisTick: {
            show: true,
            alignWithLabel: true,
            inside: true
          }
        },
        series: [{
          name: 'rate',
          type: 'line',
          symbol: "none",
          silent:true,
          data: [],
          smooth: true,
          itemStyle: {
            color: 'hotpink'
          }
        }]
      }
    }
  },
  mounted() {
    let username = this.username;
    let postData = this.qs.stringify({
      username: username
    });
    this.axios({
      method: 'post',
      url: '/patient/getInfo',
      data: postData
    }).then(response => {
      this.infoForm.name = response.data.name;
      this.infoForm.sex = response.data.sex;
      this.infoForm.age = response.data.age;
    }).catch(error => {
      console.log(error);
    });
    this.id=this.nowId;
    this.testForm=this.nowTestForm;
    this.flowData=this.nowFlowData;
    this.drawLineChart();
  },
  methods: {
    //绘制曲线图
    drawLineChart() {
      let params = {
        params: this.flowData
      };
      this.axios({
        method: 'post',
        url: '/file/smooth',
        data: params
      }).then(response => {
        this.drawData=response.data;
        // 基于准备好的dom，初始化echarts实例
        let myChart = echarts.init(document.getElementById(this.id));
        // 绘制基本图表
        myChart.setOption(this.option);
        //显示加载动画
        myChart.showLoading();
        //获取数据
        let data = this.flowData;
        //将json对象的所有time数据组成一个数组
        let time = [];
        for (let i = 0; i < data.length; i++) {
          time.push(data[i].time);
        }
        //将json对象中的所有rate数据组成一个数组
        let rate = [];
        for (let i = 0; i < data.length; i++) {
          rate.push(data[i].rate);
        }
        console.log(rate);
        setTimeout(() => {  //为了让加载动画效果明显,这里加入了setTimeout,实现300ms延时
          myChart.hideLoading(); //隐藏加载动画
          myChart.setOption({
            xAxis: {
              data: time
            },
            series: [{
              data: rate
            }]
          });
          myChart = null;
        }, 100);
      }).catch(error => {
        console.log(error);
      });
    }
  }
}
</script>

<style scoped>
.title {
  text-align: center;
  color: rgb(90, 41, 41);
}
</style>
