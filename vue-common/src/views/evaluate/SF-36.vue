<template>
  <div>
    <div class="title1">生命质量量表(SF-36)</div>
    <div class="title2">
      <span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SF-36量表，又叫健康调查简表，是美国医学局研究组(Medical Outcomes Study，MOS)开发的一个普适性测定量表。该工作开始于80年代初期，形成了不同条目不同语言背景的多种版本。1990~1992年，含有36个条目的健康调查问卷简化版SF-36的不同语种版本相继问世，其中用得较多的是英国发展版、美国标准版和中文版，均包含躯体功能、躯体角色(role-physicaI)、肌体疼痛、总的健康状况、活力(vitality)、社会功能、情绪角色(role-emotional)和心理卫生等8个领域。
      </span>
    </div>
    <div class="all">
      <el-divider></el-divider>
      <el-form ref="question" :model="question" :rules="rules" label-position="top" :show-message="false">
        <el-form-item label="题目1.总体来讲，您的健康状况是？" prop="q1">
          <label v-for="(item, index) in firsts" :key="index">
            <input type="radio" v-model="question.q1" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目2.跟1年以前比您觉得自己的健康状况是？" prop="q2">
          <label v-for="(item, index) in seconds" :key="index">
            <input type="radio" v-model="question.q2" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目3.以下这些问题都和日常活动有关。请您想一想，您的健康状况是否限制了这些活动？如果有限制，程度如何？">
        </el-form-item>
        <el-form-item label="1.重体力活动（如跑步举重、参加剧烈运动等）" prop="q3">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q3" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.适度的活动（如移动一张桌子、扫地、打太极拳、做简单体操等）" prop="q4">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q4" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.手提日用品（如买菜、购物等）" prop="q5">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q5" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="4.上几层楼梯" prop="q6">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q6" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="5.上一层楼梯" prop="q7">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q7" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="6.弯腰、屈膝、下蹲" prop="q8">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q8" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="7.步行1500米以上的路程" prop="q9">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q9" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="8.步行1000米的路程" prop="q10">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q10" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="9.步行100米的路程" prop="q11">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q11" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="10.自己洗澡、穿衣" prop="q12">
          <label v-for="(item, index) in thirds" :key="index">
            <input type="radio" v-model="question.q12" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目4.在过去4个星期里，您的工作和日常活动有无因为身体健康的原因而出现以下这些问题？">
        </el-form-item>
        <el-form-item label="1.减少了工作或其他活动时间" prop="q13">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q13" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.本来想要做的事情只完成一部分" prop="q14">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q14" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.想要干的工作或活动种类受到限制" prop="q15">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q15" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="4.完成工作或其他活动困难增多（比如需要额外的努力）" prop="q16">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q16" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目5.在过去4个星期里，您的工作和日常活动有无因为情绪的原因（如压抑或忧虑）而出现以下这些问题？">
        </el-form-item>
        <el-form-item label="1.减少了工作或其他活动时间" prop="q17">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q17" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.本来想要做的事情只完成一部分" prop="q18">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q18" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.做事情不如平时仔细" prop="q19">
          <label v-for="(item, index) in fourths" :key="index">
            <input type="radio" v-model="question.q19" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目6.在过去4个星期里，您的健康或情绪不好在多大程度上影响了您与家人、朋友、邻居或集体的正常社会交往？" prop="q20">
          <label v-for="(item, index) in sixths" :key="index">
            <input type="radio" v-model="question.q20" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目7.在过去4个星期里，您有身体疼痛吗？" prop="q21">
          <label v-for="(item, index) in sevenths" :key="index">
            <input type="radio" v-model="question.q21" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目8.在过去4个星期里，您的身体疼痛影响了您的工作和家务吗？" prop="q22">
          <label v-for="(item, index) in eighths" :key="index">
            <input type="radio" v-model="question.q22" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目9.以下这些问题是关于过去1个月里您自己的感觉，对每一条问题所说的事情，您的情况是什么样的？">
        </el-form-item>
        <el-form-item label="1.您觉得生活充实" prop="q23">
          <label v-for="(item, index) in fninths" :key="index">
            <input type="radio" v-model="question.q23" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.您是一个敏感的人" prop="q24">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q24" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.您的情绪非常不好，什么事都不能使您高兴起来" prop="q25">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q25" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="4.您的心里很平静" prop="q26">
          <label v-for="(item, index) in fninths" :key="index">
            <input type="radio" v-model="question.q26" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="5.您做事精力充沛" prop="q27">
          <label v-for="(item, index) in fninths" :key="index">
            <input type="radio" v-model="question.q27" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="6.您的情绪低落" prop="q28">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q28" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="7.您觉得筋疲力尽" prop="q29">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q29" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="8.您是个快乐的人" prop="q30">
          <label v-for="(item, index) in fninths" :key="index">
            <input type="radio" v-model="question.q30" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="9.您感觉厌烦" prop="q31">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q31" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目10.不健康影响了您的社会活动（如走亲访友）？" prop="q32">
          <label v-for="(item, index) in sninths" :key="index">
            <input type="radio" v-model="question.q32" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="题目11.请看下列每一条问题，哪一种答案是最符合您的情况？">
        </el-form-item>
        <el-form-item label="1.我好像比别人容易生病" prop="q33">
          <label v-for="(item, index) in selevenths" :key="index">
            <input type="radio" v-model="question.q33" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="2.我跟周围人一样健康" prop="q34">
          <label v-for="(item, index) in felevenths" :key="index">
            <input type="radio" v-model="question.q34" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="3.我认为我的健康状况在变坏" prop="q35">
          <label v-for="(item, index) in selevenths" :key="index">
            <input type="radio" v-model="question.q35" :value="item.value"/>{{ item.label }}<br/>
          </label>
        </el-form-item>
        <el-form-item label="4.我的健康状况非常好" prop="q36">
          <label v-for="(item, index) in felevenths" :key="index">
            <input type="radio" v-model="question.q36" :value="item.value"/>{{ item.label }}<br/>
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
        q1: '',
        q2: '',
        q3: '',
        q4: '',
        q5: '',
        q6: '',
        q7: '',
        q8: '',
        q9: '',
        q10: '',
        q11: '',
        q12: '',
        q13: '',
        q14: '',
        q15: '',
        q16: '',
        q17: '',
        q18: '',
        q19: '',
        q20: '',
        q21: '',
        q22: '',
        q23: '',
        q24: '',
        q25: '',
        q26: '',
        q27: '',
        q28: '',
        q29: '',
        q30: '',
        q31: '',
        q32: '',
        q33: '',
        q34: '',
        q35: '',
        q36: ''
      },
      firsts: [
        {
          value: 5,
          label: '非常好'
        },
        {
          value: 4.4,
          label: '很好'
        },
        {
          value: 3.4,
          label: '好'
        },
        {
          value: 2,
          label: '一般'
        },
        {
          value: 1,
          label: '差'
        }
      ],
      seconds: [
        {
          value: 5,
          label: '比1年前好多了'
        },
        {
          value: 4,
          label: '比1年前好一些'
        },
        {
          value: 3,
          label: '跟1年前差不多'
        },
        {
          value: 2,
          label: '比1年前差一些'
        },
        {
          value: 1,
          label: '比1年前差多了'
        }
      ],
      thirds: [
        {
          value: 1,
          label: '限制很大'
        },
        {
          value: 2,
          label: '有些限制'
        },
        {
          value: 3,
          label: '毫无限制'
        }
      ],
      fourths: [
        {
          value: 1,
          label: '是'
        },
        {
          value: 2,
          label: '不是'
        }
      ],
      sixths: [
        {
          value: 6,
          label: '完全没有影响'
        },
        {
          value: 5,
          label: '有一点影响'
        },
        {
          value: 4,
          label: '中等影响'
        },
        {
          value: 3,
          label: '影响很大'
        },
        {
          value: 2,
          label: '影响非常大'
        }
      ],
      sevenths: [
        {
          value: 6,
          label: '完全没有疼痛'
        },
        {
          value: 5.4,
          label: '有一点疼痛'
        },
        {
          value: 4.2,
          label: '中等疼痛'
        },
        {
          value: 3.1,
          label: '有中度疼痛'
        },
        {
          value: 2.2,
          label: '严重疼痛'
        },
        {
          value: 1,
          label: '很严重疼痛'
        }
      ],
      eighths: [
        {
          value: 5,
          label: '完全没有影响'
        },
        {
          value: 4,
          label: '有一点影响'
        },
        {
          value: 3,
          label: '中等影响'
        },
        {
          value: 2,
          label: '影响很大'
        },
        {
          value: 1,
          label: '影响非常大'
        }
      ],
      fninths: [
        {
          value: 6,
          label: '所有的时间'
        },
        {
          value: 5,
          label: '大部分时间'
        },
        {
          value: 4,
          label: '比较多时间'
        },
        {
          value: 3,
          label: '一部分时间'
        },
        {
          value: 2,
          label: '小部分时间'
        },
        {
          value: 1,
          label: '没有这种感觉'
        }
      ],
      sninths: [
        {
          value: 1,
          label: '所有的时间'
        },
        {
          value: 2,
          label: '大部分时间'
        },
        {
          value: 3,
          label: '比较多时间'
        },
        {
          value: 4,
          label: '一部分时间'
        },
        {
          value: 5,
          label: '小部分时间'
        },
        {
          value: 6,
          label: '没有这种感觉'
        }
      ],
      felevenths: [
        {
          value: 5,
          label: '绝对正确'
        },
        {
          value: 4,
          label: '大部分正确'
        },
        {
          value: 3,
          label: '不能肯定'
        },
        {
          value: 2,
          label: '大部分错误'
        },
        {
          value: 1,
          label: '绝对错误'
        }
      ],
      selevenths: [
        {
          value: 1,
          label: '绝对正确'
        },
        {
          value: 2,
          label: '大部分正确'
        },
        {
          value: 3,
          label: '不能肯定'
        },
        {
          value: 4,
          label: '大部分错误'
        },
        {
          value: 5,
          label: '绝对错误'
        }
      ],
      rules: {
        q1: [
          {required: true, trigger: 'blur'}
        ],
        q2: [
          {required: true, trigger: 'blur'}
        ],
        q3: [
          {required: true, trigger: 'blur'}
        ],
        q4: [
          {required: true, trigger: 'blur'}
        ],
        q5: [
          {required: true, trigger: 'blur'}
        ],
        q6: [
          {required: true, trigger: 'blur'}
        ],
        q7: [
          {required: true, trigger: 'blur'}
        ],
        q8: [
          {required: true, trigger: 'blur'}
        ],
        q9: [
          {required: true, trigger: 'blur'}
        ],
        q10: [
          {required: true, trigger: 'blur'}
        ],
        q11: [
          {required: true, trigger: 'blur'}
        ],
        q12: [
          {required: true, trigger: 'blur'}
        ],
        q13: [
          {required: true, trigger: 'blur'}
        ],
        q14: [
          {required: true, trigger: 'blur'}
        ],
        q15: [
          {required: true, trigger: 'blur'}
        ],
        q16: [
          {required: true, trigger: 'blur'}
        ],
        q17: [
          {required: true, trigger: 'blur'}
        ],
        q18: [
          {required: true, trigger: 'blur'}
        ],
        q19: [
          {required: true, trigger: 'blur'}
        ],
        q20: [
          {required: true, trigger: 'blur'}
        ],
        q21: [
          {required: true, trigger: 'blur'}
        ],
        q22: [
          {required: true, trigger: 'blur'}
        ],
        q23: [
          {required: true, trigger: 'blur'}
        ],
        q24: [
          {required: true, trigger: 'blur'}
        ],
        q25: [
          {required: true, trigger: 'blur'}
        ],
        q26: [
          {required: true, trigger: 'blur'}
        ],
        q27: [
          {required: true, trigger: 'blur'}
        ],
        q28: [
          {required: true, trigger: 'blur'}
        ],
        q29: [
          {required: true, trigger: 'blur'}
        ],
        q30: [
          {required: true, trigger: 'blur'}
        ],
        q31: [
          {required: true, trigger: 'blur'}
        ],
        q32: [
          {required: true, trigger: 'blur'}
        ],
        q33: [
          {required: true, trigger: 'blur'}
        ],
        q34: [
          {required: true, trigger: 'blur'}
        ],
        q35: [
          {required: true, trigger: 'blur'}
        ],
        q36: [
          {required: true, trigger: 'blur'}
        ]
      },
      dialogVisible: false,
      evaluateForm: {
        evaluateDate: '',
        evaluateName: '',
        score: '',
        conclusion: ''
      }
    }
  },
  // watch: {
  //   question: {  // 监听对象值的改变
  //     handler(curVal,oldVal){
  //       let re=Number(curVal.q1).toFixed(1);
  //       console.log(this.getTwo(re/3));
  //     },
  //     deep: true,
  //   },
  // },
  methods: {
    getTwo(val) {
      val = Math.round(val * 100) / 100
      var xsd = val.toString().split('.')
      if (xsd.length === 1) {
        val = val.toString() + '.00'
        return val
      }
      if (xsd.length > 1) {
        if (xsd[1].length < 2) {
          val = val.toString() + '0'
        }
        return val
      }
    },
    sum() {
      let pf = 0;
      let rp = 0;
      let bp = 0;
      let gh = 0;
      let vt = 0;
      let sf = 0;
      let re = 0;
      let mh = 0;
      let ht = 0;
      var values=Object.values(this.question);
      let newValues = values.some( (item,i) =>{
        let key=i+1;
        if (key >= 3 && key <= 12) {
          pf += Number(item);
        } else if (key >= 13 && key <= 16) {
          rp += Number(item);
        } else if (key == 21 || key == 22) {
          bp += Number(item);
        } else if (key == 1 || (key >= 33 && key <= 36)) {
          gh += Number(item);
        } else if (key == 23 || key == 27 || key == 29 || key == 31) {
          vt += Number(item);
        } else if (key == 20 || key == 32) {
          sf += Number(item);
        } else if (key >= 17 && key <= 19) {
          re += Number(item);
        } else if (key == 28 || key == 30 || (key >= 24 && key <= 26)) {
          mh += Number(item);
        } else if (key == 2) {
          ht += Number(item);
        }
      });
      if (this.question.q21 == 6 && this.question.q22 == 5) bp += 1;
      console.log(pf+" "+rp+" "+bp+" "+gh+" "+vt+" "+sf+" "+re+" "+mh+" "+ht+" ");
      pf = (pf - 10) / 20;
      rp = (rp - 4) / 4;
      bp = (bp - 2) / 10;
      gh = (gh - 5) / 20;
      vt = (vt - 4) / 20;
      sf = (sf - 3) / 9;
      re = (re - 3) / 3;
      mh = (mh - 5) / 25;
      ht = (ht - 1) / 4;
      let result = '';
      result += "①生理机能PF=" + this.getTwo(pf);
      result += "②生理职能RP=" + this.getTwo(rp);
      result += "③躯体疼痛BP=" + this.getTwo(bp);
      result += "④一般健康状况GH=" + this.getTwo(gh);
      result += "⑤精力VT=" + this.getTwo(vt);
      result += "⑥社会功能SF=" + this.getTwo(sf);
      result += "⑦情感职能RE=" + this.getTwo(re);
      result += "⑧精神健康MH=" + this.getTwo(mh);
      result += "⑨健康变化HT=" + this.getTwo(ht);
      return result;
    },
    getDate() {
      let nowDate = new Date();

      let date = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        date: nowDate.getDate(),
      }
      let systemDate = date.year + '-';
      if (date.month < 10) {
        systemDate += 0 + date.month + '-';
      } else {
        systemDate += date.month + '-';
      }
      if (date.date < 10) {
        systemDate += 0 + date.date;
      } else {
        systemDate += date.date;
      }
      return systemDate;
    },
    clear() {
      for (var key in this.question) {
        this.question[key] = '';
      }
    },
    scrollToTop(node) {
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
          let date = this.getDate();
          this.evaluateForm.evaluateDate = date;
          this.evaluateForm.evaluateName = '生命质量量表(SF-36)';
          let result = this.sum();
          this.evaluateForm.score = result;

          let message = '九项评价指标满分均为1，得分越高，提示健康状况越好';
          this.evaluateForm.conclusion = message;
          this.dialogVisible = true;
          this.clear();

          let postData = this.qs.stringify({
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
            type: 'error',
            message: '星号是必填项！',
            center: true,
            offset: 35
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
  margin-top: 3%;
  margin-left: 39px;
  font-size: 28px;
  color: rgba(20, 20, 20, 1);
}

.title2 {
  width: 750px;
  margin-top: 2%;
  margin-left: calc(calc(100vw - 750px) / 2);
  font-size: 16px;
  color: rgba(20, 20, 20, 1);
}

.all {
  width: 750px;
  margin-left: calc(calc(100vw - 750px) / 2); /** 动态居中 */
}
</style>
