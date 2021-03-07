<template>
  <div>
    <el-form :inline="true" :model="tempPreRcdForm" :rules="rules" ref="tempPreRcdForm" style="margin-top: 1%" size="mini">
      <el-form-item label="日期" prop="recordDate">
        <el-date-picker
          v-model="tempPreRcdForm.recordDate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOption"
          @change="updatePre">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="起床时间" prop="wakeTime">
        <el-time-picker value-format="HH:mm:ss" v-model="tempPreRcdForm.wakeTime" @change="updatePre"></el-time-picker>
      </el-form-item>
      <el-form-item label="入睡时间" prop="sleepTime">
        <el-time-picker value-format="HH:mm:ss" v-model="tempPreRcdForm.sleepTime" @change="updatePre"></el-time-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" class="el-icon-circle-plus-outline" @click="dialogAdd=true">新增</el-button>
      </el-form-item>
    </el-form>

  <el-table :data="tableData" highlight-current-row border style="width: 100%" ref="table">
    <el-table-column
      label="次数"
      width="50px"
      type="index"
      :index="indexMethod">
    </el-table-column>
    <el-table-column label="排尿时间">
      <template slot-scope="scope">
        <span>{{ scope.row.flowTime }}</span>
      </template>
    </el-table-column>
    <el-table-column label="尿量">
      <template slot-scope="scope">
        <span>{{ scope.row.capacity }}</span>
      </template>
    </el-table-column>
    <el-table-column label="是否尿急">
      <template slot-scope="scope">
        <span>{{ scope.row.flowFastYN }}</span>
      </template>
    </el-table-column>
    <el-table-column label="是否漏尿">
      <template slot-scope="scope">
        <span>{{ scope.row.flowLeakYN }}</span>
      </template>
    </el-table-column>
    <el-table-column label="备注">
      <template slot-scope="scope">
        <span>{{ scope.row.remark }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" fixed="right" width="200px">
      <template slot-scope="scope">
        <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
        <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

    <el-form style="text-align: center;margin-top: 1%">
      <el-form-item>
        <el-button type="primary" size="mini" @click="submit">提交</el-button>
        <el-button type="primary" size="mini" @click="refresh">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-form :model="tempSufRcdAForm" :rules="rules" ref="tempSufRcdAForm" label-width="100px" class="demo-form" size="mini">
      <el-dialog :append-to-body='true' :visible.sync="dialogAdd" width="380px" :before-close="handleCloseAdd">
        <el-input type="hidden" v-model="tempSufRcdAForm.recordId"/>
        <el-form-item label="排尿时间" prop="flowTime">
          <el-time-picker style="width: 200px" value-format="HH:mm:ss" placeholder="请选择时间" v-model="tempSufRcdAForm.flowTime"></el-time-picker>
        </el-form-item>
        <el-form-item label="尿量" prop="capacity">
          <el-input style="width: 200px" placeholder="请输入数值" v-model="tempSufRcdAForm.capacity"></el-input>
        </el-form-item>
        <el-form-item label="是否尿急" prop="flowFastYN">
          <el-select style="width: 200px" v-model="tempSufRcdAForm.flowFastYN" placeholder="请选择">
            <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否漏尿" prop="flowLeakYN">
          <el-select style="width: 200px" v-model="tempSufRcdAForm.flowLeakYN" placeholder="请选择">
            <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input style="width: 200px" v-model="tempSufRcdAForm.remark"></el-input>
        </el-form-item>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAdd" size="mini">取消</el-button>
          <el-button @click="addRecord" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>

    <el-form :model="tempSufRcdUForm" :rules="rules" ref="tempSufRcdUForm" label-width="100px" class="demo-form" size="mini">
      <el-dialog :append-to-body='true' :visible.sync="dialogUpdate" width="380px" :before-close="handleCloseUpdate">
        <el-input type="hidden" v-model="tempSufRcdUForm.recordId"/>
        <el-form-item label="排尿时间" prop="flowTime">
          <el-time-picker style="width: 200px" value-format="HH:mm:ss" placeholder="请选择时间" v-model="tempSufRcdUForm.flowTime"></el-time-picker>
        </el-form-item>
        <el-form-item label="尿量" prop="capacity">
          <el-input style="width: 200px" placeholder="请输入数值" v-model="tempSufRcdUForm.capacity"></el-input>
        </el-form-item>
        <el-form-item label="是否尿急" prop="flowFastYN">
          <el-select style="width: 200px" v-model="tempSufRcdUForm.flowFastYN" placeholder="请选择">
            <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否漏尿" prop="flowLeakYN">
          <el-select style="width: 200px" v-model="tempSufRcdUForm.flowLeakYN" placeholder="请选择">
            <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input style="width: 200px" v-model="tempSufRcdUForm.remark"></el-input>
        </el-form-item>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelUpdate" size="mini">取消</el-button>
          <el-button @click="updateRecord" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
export default {
  data(){
    return{
      tempPreRcdForm:{
        recordDate:'',
        wakeTime:'',
        sleepTime:''
      },
      tempSufRcdAForm:{
        recordId:'',
        flowTime:'',
        capacity:'',
        flowFastYN:'',
        flowLeakYN:'',
        remark:''
      },
      tempSufRcdUForm:{
        recordId:'',
        flowTime:'',
        capacity:'',
        flowFastYN:'',
        flowLeakYN:'',
        remark:''
      },
      tableData:[],
      dialogAdd: false,
      dialogUpdate: false,
      items:[
        {
          value:'是',
          label:'是'
        },
        {
          value:'否',
          label:'否'
        }
      ],
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
        ],
        flowTime: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        capacity: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        flowFastYN: [
          {required: true, message: '必填项', trigger: 'blur'}
        ],
        flowLeakYN: [
          {required: true, message: '必填项', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    indexMethod(index) {
      return index + 1;
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
    updatePre(){
      let postData=this.qs.stringify({
        recordDate:this.tempPreRcdForm.recordDate,
        wakeTime:this.tempPreRcdForm.wakeTime,
        sleepTime:this.tempPreRcdForm.sleepTime
      });
      this.axios.post('/tempRecord/getPre').then(response=>{
        let first=response.data;
        if(first.length==0){
          this.axios({
            method: 'post',
            url:'tempRecord/insertPre',
            data:postData
          }).then(response =>
          {
            console.log(response);
          }).catch(error =>
          {
            console.log(error);
          });
        }else{
          this.axios({
            method: 'post',
            url:'tempRecord/updatePre',
            data:postData
          }).then(response =>
          {
            console.log(response);
          }).catch(error =>
          {
            console.log(error);
          });
        }
      }).catch(error =>
      {
        console.log(error);
      });
    },
    handleEdit(index, row) {
      console.log(index)
      this.dialogUpdate = true;
      this.tempSufRcdUForm = Object.assign({}, row); //这句是关键！！！
    },
    handleDelete(index, row) {
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let postData = this.qs.stringify({
          recordId: row.recordId,
        });
        this.axios({
          method: 'post',
          url:'/tempRecord/deleteSuf',
          data:postData
        }).then(response =>
        {
          this.axios.post('/tempRecord/getSuf').then(response=>{
            this.tableData = response.data;
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(error=>{
            console.log(error);
          });
        }).catch(error =>
        {
          console.log(error);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleCloseAdd(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cancelAdd();
          done();
        })
        .catch(_ => {});
    },
    handleCloseUpdate(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cancelUpdate();
          done();
        })
        .catch(_ => {});
    },
    cancelAdd() {
      this.tempSufRcdAForm={
        recordId:'',
        flowTime:'',
        capacity:'',
        flowFastYN:'',
        flowLeakYN:'',
        remark:''
      };
      this.$refs.tempSufRcdAForm.clearValidate();
      this.dialogAdd = false;
    },
    cancelUpdate(){
      this.tempSufRcdUForm={
        recordId:'',
        flowTime:'',
        capacity:'',
        flowFastYN:'',
        flowLeakYN:'',
        remark:''
      };
      this.$refs.tempSufRcdUForm.clearValidate();
      this.dialogUpdate = false;
    },
    addRecord(){
      this.$refs.tempSufRcdAForm.validate(valid => {
        if (valid) {
          let postData = this.qs.stringify({
            flowTime: this.tempSufRcdAForm.flowTime,
            capacity: this.tempSufRcdAForm.capacity,
            flowFastYN:this.tempSufRcdAForm.flowFastYN,
            flowLeakYN:this.tempSufRcdAForm.flowLeakYN,
            remark:this.tempSufRcdAForm.remark
          });
          this.axios({
            method: 'post',
            url: '/tempRecord/insertSuf',
            data: postData
          }).then(response => {
            this.axios.post('/tempRecord/getSuf').then(response=>{
              this.tableData = response.data;
              this.$message({
                type: 'success',
                message: '新增一条记录'
              });
              this.cancelAdd();
            }).catch(error=>{
              console.log(error);
            });
          }).catch(error => {
            console.log(error);
          });
        } else {
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    updateRecord(){
      this.$refs.tempSufRcdUForm.validate(valid => {
        if (valid) {
          let postData = this.qs.stringify({
            recordId: this.tempSufRcdUForm.recordId,
            flowTime: this.tempSufRcdUForm.flowTime,
            capacity: this.tempSufRcdUForm.capacity,
            flowFastYN:this.tempSufRcdUForm.flowFastYN,
            flowLeakYN:this.tempSufRcdUForm.flowLeakYN,
            remark:this.tempSufRcdUForm.remark
          });
          console.log(postData);
          this.axios({
            method: 'post',
            url: '/tempRecord/updateSuf',
            data: postData
          }).then(response => {
            this.axios.post('/tempRecord/getSuf').then(response=>{
              this.tableData=response.data;
              this.$message({
                type: 'success',
                message: '修改成功'
              });
              this.cancelUpdate();
            }).catch(error=>{
              console.log(error);
            });
          }).catch(error => {
            console.log(error);
          });
        } else {
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    clear(){
      this.axios.post('/tempRecord/removePre').then(res=>{}).catch(err=>{});
      this.axios.post('tempRecord/removeSuf').then(res=>{}).catch(err=>{});
      this.getNowTime();
      this.tempPreRcdForm.wakeTime='';
      this.tempPreRcdForm.sleepTime='';
      this.tableData=[];
    },
    refresh(){
      this.clear();
      this.$message({
        type: 'success',
        message: '刷新成功'
      });
    },
    submit(){
      this.$refs.tempPreRcdForm.validate(valid => {
        if (valid) {
          this.axios.post('/tempRecord/getSuf').then(response=>{
            let result=response.data;
            if(result.length==0){
              this.$message({
                type:'error',
                message:'日志无记录，提交失败'
              });
            }else {
              let postData=this.qs.stringify({
                recordDate:this.tempPreRcdForm.recordDate
              });
              this.axios({
                method: 'post',
                url: '/record/findByDate',
                data: postData
              }).then(response => {
                let status = response.data.status;
                if (status == "success") {
                  this.axios.post('/record/insertTempSuf').then(res=>{}).catch(err=>{});
                  let data=this.qs.stringify({
                    recordDate:this.tempPreRcdForm.recordDate,
                    wakeTime:this.tempPreRcdForm.wakeTime,
                    sleepTime:this.tempPreRcdForm.sleepTime
                  });
                  this.axios({
                    method: 'post',
                    url: '/record/insertTempPre',
                    data: data
                  }).then(response => {
                    console.log(response);
                  }).catch(error => {
                    console.log(error);
                  });
                  this.$message.success('提交成功');
                  this.clear();
                } else {
                  this.$message.error('此日日志已存在，请先删除旧日志');
                }
              }).catch(error => {
                console.log(error);
              });
            }
          })
        } else {
          this.$message({
            type:'error',
            message:'星号为必填项'
          });
          console.log("参数验证不合法！");
          return false;
        }
      });
    }
  },
  created(){
    this.axios.post('/tempRecord/getPre').then(response=>{
      let first=response.data;
      console.log(first.length);
      if(first.length==0){
        this.getNowTime();
      }else{
        this.tempPreRcdForm.recordDate=first[0].recordDate;
        this.tempPreRcdForm.wakeTime=first[0].wakeTime;
        this.tempPreRcdForm.sleepTime=first[0].sleepTime;
      }
    }).catch(error =>
    {
      console.log(error);
    });

    this.axios.post('/tempRecord/getSuf').then(response=>{
      this.tableData=response.data;
    }).catch(error =>
    {
      console.log(error);
    });
  }
}
</script>

