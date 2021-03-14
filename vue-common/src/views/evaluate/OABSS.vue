<template>
  <div>
    <div class="title1">膀胱过度活动症评分(OABSS)</div>
    <div class="title2">
      <span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;膀胱过度活动症(OAB)是一种以尿急症状为特征的综合征，常伴有尿频和夜尿症状，可伴或不伴有急迫性尿失禁(UUI)；尿动力学上可表现为逼尿肌过度活动(DO)，也可为其他形式的尿道-膀胱功能障碍。
      </span>
    </div>
    <div class="all">
      <el-divider></el-divider>
      <el-form ref="question" :model="question" :rules="rules" label-position="top" :show-message="false">
        <el-form-item label="1.从早晨起床到晚上入睡的时间内，小便的次数是多少？" prop="one">
          <label v-for="(item, index) in firsts" :key="index">
            <input type="radio" v-model="question.one" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.从晚上入睡到早晨起床的时间内，小便的次数是多少？" prop="two">
          <label v-for="(item, index) in seconds" :key="index">
            <input type="radio" v-model="question.two" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.是否有突然想要小便、同时难以忍受的现象发生？" prop="three">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.three" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="4.是否有突然想要小便、同时无法忍受并出现尿失禁的现象？" prop="four">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.four" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog title="评测结果" :visible.sync="dialogVisible" width="30%">
      <span>得分<br/></span>
      <span>{{ evaluateForm.score }}<br/></span>
      <span>结论<br/></span>
      <span>{{ evaluateForm.conclusion }}<br/></span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      question: {
        one: '',
        two: '',
        three: '',
        four: ''
      },
      firsts: [
        {
          value: 0,
          label: '≤7'
        },
        {
          value: 1,
          label: '8～14'
        },
        {
          value: 2,
          label: '≥15'
        }
      ],
      seconds: [
        {
          value: 0,
          label: '0'
        },
        {
          value: 1,
          label: '1'
        },
        {
          value: 2,
          label: '2'
        },
        {
          value: 3,
          label: '≥3'
        }
      ],
      thirds: [
        {
          value: 0,
          label: '无'
        },
        {
          value: 1,
          label: '每周＜1'
        },
        {
          value: 2,
          label: '每周＞1'
        },
        {
          value: 3,
          label: '每日＝1'
        },
        {
          value: 4,
          label: '每日2～4'
        },
        {
          value: 5,
          label: '每日≥5'
        }
      ],
      rules:{
        one:[
          {required: true, trigger: 'blur'}
        ],
        two:[
          {required: true, trigger: 'blur'}
        ],
        three:[
          {required: true, trigger: 'blur'}
        ],
        four:[
          {required: true, trigger: 'blur'}
        ]
      },
      dialogVisible: false,
      evaluateForm: {
        evaluateDate: '',
        evaluateName: '',
        score: '',
        conclusion: ''
      },
    }
  },
  methods: {
    sum() {
      let sum = 0;
      for (let key in this.question) {
        if(this.question[key]!='')
          sum += parseInt(this.question[key]);
      }
      console.log(sum);
      return sum;
    },
    getDate() {
      let nowDate = new Date();

      let date = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        date: nowDate.getDate(),
      }
      let systemDate = date.year + '-';
      if(date.month<10){
        systemDate+='0' + date.month + '-';
      }else{
        systemDate+=date.month + '-';
      }
      if(date.date<10){
        systemDate+='0' + date.date;
      }else{
        systemDate+=date.date;
      }
      return systemDate;
    },
    clear() {
      for (var key in this.question) {
        this.question[key] = '';
      }
    },
    scrollToTop (node) {
      const ChildHasError = Array.from(node.querySelectorAll('.is-error'))
      if (!ChildHasError.length) throw new Error('有错误，但是找不到错误位置')
      // 找到第一个错误位置
      const FirstErrorNode = ChildHasError[0]
      FirstErrorNode.scrollIntoView({
        behavior: "smooth"
      })
    },
    handleSubmit() {
      this.$refs.question.validate(valid => {
        if (valid) {
          let date=this.getDate();
          this.evaluateForm.evaluateDate=date;
          this.evaluateForm.evaluateName='膀胱过度活动症评分(OABSS)';
          let result = this.sum();
          this.evaluateForm.score=result;
          let message = '';
          if(this.question.three>=2){
            if(result<=5){
              message='轻度OAB';
            }else if(result>=6&&result<=11){
              message='中度OAB';
            }else if(result>=12){
              message='重度OAB'
            }
          }else{
            message='不能确诊OAB';
          }
          this.evaluateForm.conclusion=message;
          this.dialogVisible=true;
          this.clear();

          let postData = this.qs.stringify({
            username:sessionStorage.getItem('username'),
            evaluateDate: this.evaluateForm.evaluateDate,
            evaluateName: this.evaluateForm.evaluateName,
            score: this.evaluateForm.score,
            conclusion: this.evaluateForm.conclusion
          });
          this.axios({
            method: 'post',
            url: '/evaluate/insert',
            data: postData
          }).then(response => {
            console.log(response);
          }).catch(error => {
            console.log(error);
          });
        } else {
          this.$nextTick(() => {
            this.scrollToTop(this.$refs['question'].$el)
          });
          this.$message({
            type:'error',
            message: '星号是必填项！',
            center: true,
            offset:35
          });
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.title1 {
  text-align: center;
  margin-top: 1%;
  margin-right: 16px;
  font-size: 28px;
  color: rgba(20, 20, 20, 1);
}
.title2 {
  width:600px;
  margin-top: 2%;
  margin-left: calc(calc(100vw - 860px) / 2); /** 动态居中 */
  font-size: 16px;
  color: rgba(20, 20, 20, 1);
}
.all{
  width:600px;
  margin-left: calc(calc(100vw - 860px) / 2); /** 动态居中 */
}
</style>
