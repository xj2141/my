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
        <el-form :inline="true" :model="testForm" :rules="rules" ref="testForm">
          <el-form-item label="检测时间:" prop="testTime">
            <el-time-picker value-format="HH:mm:ss" v-model="testForm.testTime" size="mini"
                            style="width: 180px;"></el-time-picker>
          </el-form-item>
          <el-form-item label="检测地点:" prop="testPlace">
            <el-input v-model="testForm.testPlace" placeholder="请输入内容" size="mini" style="width: 180px;"></el-input>
            <span>&nbsp;&nbsp;</span>
          </el-form-item>
          <el-form-item label="排尿量:" prop="vv">
            <el-input v-model.number="testForm.vv" placeholder="请输入内容" size="mini" style="width: 180px;"
                      @change="analyzeAgain" oninput="value=value.replace(/[^\d]/g, '')"></el-input>
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
        <el-upload
          :show-file-list="false"
          :before-upload="beforeUpload"
          :on-success="onSuccess"
          :on-error="onError"
          :disabled="importDisabled"
          action="/file/get">
          <el-button size="small" :disabled="importDisabled" type="success" :icon="importDataIcon">
            {{ importDataText }}
          </el-button>
        </el-upload>
      </div>
      <div v-show="testYes">
        <br/>
        <div class="title"><u>No1.曲线</u></div>
        <div id="myChartAdd" :style="{width: '800px', height: '400px', marginLeft:'25px'}"></div>
        <div class="title"><u>No2.参数</u></div>
        <br/>
        <div style="text-align: center">尿量(VV)：{{ testForm.vv }}ml&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最大尿流率(Qmax)：{{
            testForm.qmax
          }}ml/s&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;尿流时间(FT)：{{
            testForm.ft
          }}s&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;达峰时间(TQmax)：{{
            testForm.tqmax
          }}s
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
  name: "FlowTestAdd",
  props: {
    nowTestForm: {
      type: Object,
      default: () => {
      }
    }
  },
  data() {
    return {
      infoForm: {
        name: '',
        sex: '',
        age: ''
      },
      testForm: {
        testId: '',
        username: '',
        testDate: '',
        testTime: '',
        testPlace: '',
        vv: '',
        qmax: '',
        ft: '',
        tqmax: '',
        flowBeginId: '',
        flowEndId: '',
        conclusion: ''
      },
      flowData: [],
      testYes: false,
      importDataText: '获取数据',
      importDisabled: false,
      importDataIcon: 'el-icon-upload2',
      rules: {
        testTime: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        testPlace: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        vv: [
          {required: true, message: '必填项', trigger: 'blur'}
        ]
      },
      option: {
        title: {text: '尿流率曲线'},
        backgroundColor: '#FBFBFB',
        tooltip: {
          trigger: 'axis'
        },
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
    this.$message.info("获取数据前请先输入排尿量");
    let username = sessionStorage.getItem('username');
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
    this.testForm = this.nowTestForm;
  },
  methods: {
    //绘制曲线图
    drawLineChart() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('myChartAdd'));
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
    },
    analyzeAgain() {
      if (this.flowData.length != 0) {
        if (this.testForm.vv=='') {
          this.$message.warning("请先输入排尿量");
        } else {
          this.analyze();
        }
      }
    },
    analyze() {
      let ids = {
        params: this.flowData
      };
      let flows = JSON.stringify(ids);
      this.axios.post('/file/analyze', {flows: flows, vv: this.testForm.vv}).then(response => {
        let test = response.data;
        this.testForm.qmax = test.qmax;
        this.testForm.ft = test.ft;
        this.testForm.tqmax = test.tqmax;
        this.testForm.conclusion = test.conclusion;
      }).catch(() => {
      });
    },
    onError() {
      this.importDataText = '获取数据';
      this.importDataIcon = 'el-icon-upload2'
      this.importDisabled = false;
      this.$message.error("获取数据失败！");
      this.testYes = false;
    },
    onSuccess(res) {
      this.testYes = true;
      //res是后端返回的值
      this.importDataText = '获取数据';
      this.importDataIcon = 'el-icon-upload2'
      this.importDisabled = false;
      this.$message.success("获取数据成功！");
      console.log(res)
      this.flowData = res;
      this.drawLineChart();
      this.analyze();
    },
    beforeUpload() {
      if (this.testForm.vv == '') {
        this.$message.warning("请先输入排尿量");
        return false;
      } else {
        this.importDataText = '正在获取';
        this.importDisabled = true;
        this.importDataIcon = 'el-icon-loading';
      }
    },
    cancel() {
      this.testForm = {
        testId: '',
        username: '',
        testDate: '',
        testTime: '',
        testPlace: '',
        vv: '',
        qmax: '',
        ft: '',
        tqmax: '',
        flowBeginId: '',
        flowEndId: '',
        conclusion: ''
      };
      this.flowData = [];
      this.$refs.testForm.clearValidate();
    },
    add() {
      this.$refs.testForm.validate(valid => {
        if (valid) {
          console.log(this.flowData.length);
          if (this.flowData.length == 0) {
            this.$message.error("无尿流率测定数据，添加失败");
          } else {
            let params = {
              params: this.flowData
            };
            let tempTest = JSON.stringify(this.testForm);
            let count = this.flowData.length;
            this.axios({
              method: 'post',
              url: '/tempFlowTest/insertFlow',
              data: params
            }).then(response => {
              this.axios({
                method: 'post',
                url: '/tempFlowTest/insertTest',
                data: {tempTest: tempTest, count: count}
              }).then(response => {
                this.$emit("parentHandle");
              }).catch(error => {
              });
            }).catch(error => {
              console.log(error);
            });
          }
        } else {
          this.$message({
            type: 'error',
            message: '必填项不能为空'
          });
          console.log("参数验证不合法！");
        }
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
