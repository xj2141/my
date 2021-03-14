<template>
  <div>
    <div class="title1">国际前列腺症状评分表(IPSS)</div>
    <div class="title2">
      <span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;国际前列腺症状评分表(IPSS)是WHO推荐评估前列腺增生(BPH)症状的标准方法，IPSS包括储尿期症状及排尿期症状两部分，能较全面反应BPH患者排尿情况及尿频尿急严重程度。
      </span>
      <el-divider></el-divider>
    </div>
    <div class="title3">在最近一个月内，您是否有以下症状?</div>
    <div class="all">
    <el-form ref="question" :model="question" :rules="rules" label-position="top" :show-message="false">
      <el-form-item label="1.是否经常有尿不尽的感觉？" prop="one">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.one" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="2.两次排尿间隔是否经常小于两小时？" prop="two">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.two" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="3.是否经常有间断性排尿？" prop="three">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.three" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="4.是否经常有憋尿困难？" prop="four">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.four" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="5.是否经常有尿线变细现象？" prop="five">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.five" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="6.是否经常需要用力及使劲才能开始排尿？" prop="six">
        <label v-for="(item, index) in firsts" :key="index">
          <input type="radio" v-model="question.six" :value="item.value"/>{{ item.label }}<br/>
        </label>
      </el-form-item>
      <el-form-item label="7.从入睡到早起一般需要起来排尿几次？" prop="seven">
        <label v-for="(item, index) in seconds" :key="index">
          <input type="radio" v-model="question.seven" :value="item.value"/>{{ item.label }}<br/>
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
        four: '',
        five: '',
        six: '',
        seven: ''
      },
      firsts: [
        {
          value: 0,
          label: '没有'
        },
        {
          value: 1,
          label: '在五次中少于一次'
        },
        {
          value: 2,
          label: '在五次中少于半数'
        },
        {
          value: 3,
          label: '在五次中大约半数'
        },
        {
          value: 4,
          label: '在五次中多于半数'
        },
        {
          value: 5,
          label: '在五次中几乎每次'
        }
      ],
      seconds: [
        {
          value: 0,
          label: '没有'
        },
        {
          value: 1,
          label: '一次'
        },
        {
          value: 2,
          label: '二次'
        },
        {
          value: 3,
          label: '三次'
        },
        {
          value: 4,
          label: '四次'
        },
        {
          value: 5,
          label: '五次以上'
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
        ],
        five:[
          {required: true, trigger: 'blur'}
        ],
        six:[
          {required: true, trigger: 'blur'}
        ],
        seven:[
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
          this.evaluateForm.evaluateName='国际前列腺症状评分表(IPSS)';
          let result = this.sum();
          this.evaluateForm.score=result;
          let message = '';
          if (result >= 0 && result <= 7) {
            message = '轻度症状，密切观察';
          } else if (result >= 8 && result <= 19) {
            message = '中度症状，需要治疗';
          } else if (result >= 20 && result <= 35) {
            message = '重度症状，需要积极治疗';
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
.title3 {
  width:600px;
  margin-left: calc(calc(100vw - 860px) / 2); /** 动态居中 */
  font-size: 15px;
  color: rgba(20, 20, 20, 1);
}
.all{
  width:600px;
  margin-top: 1%;
  margin-left: calc(calc(100vw - 860px) / 2); /** 动态居中 */
}
</style>
