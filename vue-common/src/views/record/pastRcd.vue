<template>
  <div>
    <div class="one">
    <el-form :inline="true" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-date-picker
          v-model="date"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          style="width: 300px">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-search" @click="handleSearch">查询</el-button>
        <el-button type="primary" icon="el-icon-download" @click="handleExport">导出</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-circle-plus-outline" @click="addDialog">添加日志</el-button>
        <el-button type="primary" class="el-icon-delete" @click="removeBatch()">删除日志</el-button>
      </el-form-item>
    </el-form>
    </div>

    <el-table :data="tableData" highlight-current-row border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection"></el-table-column>
      <el-table-column :label="'姓名：'+infoForm.name+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'性别：'+infoForm.sex+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'年龄：'+infoForm.age">
        <template slot-scope="scope">
          <InTable :tableData="scope.row" :headData="headData[scope.$index]"></InTable>
        </template>
      </el-table-column>
    </el-table>

    <div style="text-align: center;">
    <el-form label-width="100%" class="demo-form" size="mini">
      <el-dialog title="添加日志" :append-to-body='true' :visible.sync="dialogAdd" width="840px" :before-close="handleCloseAdd" center>
        <el-form :inline="true" :model="tempPreRcdForm" :rules="rules" ref="tempPreRcdForm" size="mini">
          <el-form-item label="日期" prop="recordDate">
            <el-date-picker
              v-model="tempPreRcdForm.recordDate"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOption"
              style="width: 160px">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="起床时间" prop="wakeTime">
            <el-time-picker value-format="HH:mm:ss" v-model="tempPreRcdForm.wakeTime" placeholder="选择时间" style="width: 160px"></el-time-picker>
          </el-form-item>
          <el-form-item label="入睡时间" prop="sleepTime">
            <el-time-picker value-format="HH:mm:ss" v-model="tempPreRcdForm.sleepTime" placeholder="选择时间" style="width: 160px"></el-time-picker>
          </el-form-item>
        </el-form>
        <InForm></InForm>
        <span slot="footer" class="dialog-footer">
          <el-button @click="clear" size="mini">取消</el-button>
          <el-button @click="submit" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
  </div>
</template>

<script>
import InTable from '@/components/inside/InTable'
import InForm from '@/components/inside/InForm'

export default {
  components: {
    InTable,
    InForm
  },
  data(){
    return{
      infoForm:{
        name:'',
        sex:'',
        age:''
      },
      tableData:[],
      headData:[],
      date:'',
      sFirstDate:'',
      sLastDate:'',
      multipleSelection:[],
      dialogAdd: false,
      tempPreRcdForm:{
        recordDate:'',
        wakeTime:'',
        sleepTime:''
      },
      pickerOption: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6
        }
      },
      rules:{
        recordDate: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        wakeTime: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        sleepTime: [
          {required: true, message: '必填项', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getAll() {
      let postData = this.qs.stringify({
        firstDate: this.sFirstDate,
        lastDate: this.sLastDate
      });
      this.axios({
        method: 'post',
        url: '/record/getAll',
        data: postData
      }).then(response => {
        this.headData = response.data.pre;
        this.tableData = response.data.suf;
      }).catch(error => {
        console.log(error);
      });
    },
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month + 1;
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date}`;
      this.$set(this.tempPreRcdForm, "recordDate", defaultDate);
    },
    addDialog() {
      this.dialogAdd = true;
      this.getNowTime();
    },
    clear() {
      this.tempPreRcdForm = {
        recordDate: '',
        wakeTime: '',
        sleepTime: ''
      };
      this.$refs.tempPreRcdForm.clearValidate();
      this.axios.post('secTempRecord/remove').then(res => {
      }).catch(err => {
      });
      this.dialogAdd = false;
    },
    handleCloseAdd(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.clear();
          done();
        })
        .catch(_ => {
        });
    },
    submit() {
      this.$refs.tempPreRcdForm.validate(valid => {
        if (valid) {
          this.axios.post('/secTempRecord/get').then(response => {
            let result = response.data;
            if (result.length == 0) {
              this.$message({
                type: 'error',
                message: '日志无记录，添加失败'
              });
            } else {
              let postData = this.qs.stringify({
                recordDate: this.tempPreRcdForm.recordDate
              });
              this.axios({
                method: 'post',
                url: '/record/findByDate',
                data: postData
              }).then(response => {
                let status = response.data.status;
                if (status == "success") {
                  this.axios.post('/record/insertSuf').then(res => {
                  }).catch(err => {
                  });
                  let data = this.qs.stringify({
                    recordDate: this.tempPreRcdForm.recordDate,
                    wakeTime: this.tempPreRcdForm.wakeTime,
                    sleepTime: this.tempPreRcdForm.sleepTime
                  });
                  this.axios({
                    method: 'post',
                    url: '/record/insertPre',
                    data: data
                  }).then(response => {

                  }).catch(error => {

                  });
                  this.getAll();
                  this.$message.success('添加日志成功');
                  this.clear();
                } else {
                  this.$message.error('此日志已存在，请先删除旧日志');
                }
              }).catch(error => {
                console.log(error);
              });
            }
          }).catch(error => {
            console.log(error);
          });
        } else {
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    //获取复选框选中的日期
    getDates() {
      let dates = [];
      let val = this.multipleSelection;
      val.forEach((va, index) => {
        this.tableData.forEach((v, i) => {
          if (va[0].recordId == v[0].recordId) {
            let now = this.headData[i];
            dates.push(now.recordDate);
          }
        })
      });
      dates.sort();
      let result = dates.map(item => item).join();
      return result;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.getDates();
    },
    removeBatch() {
      console.log(this.multipleSelection);
      if (this.multipleSelection.length == 0) {
        this.$message.warning("请先勾选选项");
      } else {
        this.$confirm('删除日志, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let dates = this.getDates();
          this.axios.post("/record/deleteAll", {dates: dates}).then(response => {
            this.getAll();
            this.$message({
              type: 'success',
              message: '删除日志成功!'
            });
          }).catch(error => {
            console.log(error);
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    handleSearch() {
      let search = this.date;
      if(search.length==0){
        this.sFirstDate='0000-00-00';
        this.sLastDate='9999-99-99';
      }else{
        this.sFirstDate = search[0];
        this.sLastDate = search[1];
      }
      this.getAll();
    },
    handleExport() {
      if (this.multipleSelection.length == 0) {
        this.$message.warning("请先勾选选项");
      } else {
        let dates = this.getDates();
        let username = sessionStorage.getItem('username');
        console.log(dates);
        this.axios({
          method: 'post',
          url: '/record/exportAll',
          data: {dates: dates, username: username},
          responseType: 'blob'
        }).then(response => {
          const link = document.createElement('a');
          let blob = new Blob([response.data], {type: "application/vnd.ms-excel"});
          link.style.display = 'none';
          link.href = URL.createObjectURL(blob);
          link.setAttribute('download', '排尿日志.xls');
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        }).catch(error => {
          this.$message.success("系统数据错误！");
        });
      }
    }
  },
  created() {
    let username=sessionStorage.getItem('username');
    let postData=this.qs.stringify({
      username:username
    });
    this.axios({
      method:'post',
      url:'/user/getInfo',
      data:postData
    }).then(response=>{
        this.infoForm.name=response.data.name;
        this.infoForm.sex=response.data.sex;
        this.infoForm.age=response.data.age;
    }).catch(error => {
      console.log(error);
    });
    this.sFirstDate='0000-00-00';
    this.sLastDate='9999-99-99';
    this.getAll();
  }
}
</script>

<style scoped>
.one{
  margin-top: 1%;
}
</style>
