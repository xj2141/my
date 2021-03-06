<template>
  <div>
    <el-form :inline="true" style="margin-top: 1%" size="mini">
      <el-form-item label="日期">
        <el-date-picker
          v-model="testForm.testDate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOption"
          style="width: 160px">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" class="el-icon-circle-plus-outline" @click="dialogAdd=true">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" highlight-current-row border style="width: 100%" ref="table">
      <el-table-column
        label="检测次数"
        width="50px"
        type="index"
        :index="indexMethod">
      </el-table-column>
      <el-table-column label="检测时间">
        <template slot-scope="scope">
          <span>{{ scope.row.testTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测地点">
        <template slot-scope="scope">
          <span>{{ scope.row.testPlace }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测结果">
        <template slot-scope="scope">
          <span>{{ scope.row.conclusion }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index,scope.row)">修改</el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-form style="text-align: center;margin-top: 1%">
      <el-form-item>
        <el-button type="primary" size="mini">提交</el-button>
        <el-button type="primary" size="mini" @click="refresh">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-form label-width="100px" class="demo-form" size="mini">
      <el-dialog title="添加记录" :append-to-body='true' :visible.sync="dialogAdd" width="1000px" :before-close="handleCloseAdd" center>
        <FlowTest ref="flowtest"></FlowTest>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAdd" size="mini">取消</el-button>
          <el-button @click="add" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>

    <el-form label-width="100px" class="demo-form" size="mini">
      <el-dialog title="修改记录" :append-to-body='true' :visible.sync="dialogUpdate" width="1000px" :before-close="handleCloseUpdate" center>
        <FlowTest :testForm="testForm" :flowData="flowForm"></FlowTest>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelUpdate" size="mini">取消</el-button>
          <el-button @click="update" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import FlowTest from '@/components/flow/FlowTest'

export default {
  components: {
    FlowTest
  },
  data(){
    return{
      testForm: {
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
      flowForm:[],
      tableData:[],
      flowData:[],
      dialogAdd: false,
      dialogUpdate: false,
      pickerOption: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6
        }
      }
    }
  },
  methods:{
    indexMethod(index) {
      return index + 1;
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
    get(){
      this.axios.post('/tempFlowTest/get').then(response=>{
        this.tableData=response.data.pre;
        this.flowData=response.data.suf;
      }).catch(error =>
      {
        console.log(error);
      });
    },
    handleEdit(index, row) {
      console.log(index)
      this.dialogUpdate = true;
      this.testForm = Object.assign({}, row); //这句是关键！！！
      this.flowForm=this.flowData[index];
    },
    handleDelete(index, row) {
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let postData = this.qs.stringify(row);
        this.axios({
          method: 'post',
          url:'/tempFlowTest/delete',
          data:postData
        }).then(response =>
        {
          this.get();
          this.$message({
            type: 'success',
            message: '删除成功!'
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
      this.dialogAdd = false;
    },
    cancelUpdate(){
      this.dialogUpdate = false;
    },

    add(){

    },
    update(){
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
      this.axios.post('/tempFlowTest/remove').then(res=>{}).catch(err=>{});
      this.testDate=this.getDate();
      this.tableData=[];
    },
    refresh(){
      this.clear();
      this.$message({
        type: 'success',
        message: '刷新成功'
      });
    },
    // submit(){
    //     let result=this.tableData;
    //     if(result.length==0){
    //       this.$message({
    //         type:'error',
    //         message:'表中无记录，提交失败'
    //       });
    //     }else {
    //       let postData=this.qs.stringify({
    //         recordDate:this.tempPreRcdForm.recordDate
    //       });
    //       this.axios({
    //         method: 'post',
    //         url: '/record/findByDate',
    //         data: postData
    //       }).then(response => {
    //         let status = response.data.status;
    //         if (status == "success") {
    //           this.axios.post('/record/insertTempSuf').then(res=>{}).catch(err=>{});
    //           let data=this.qs.stringify({
    //             recordDate:this.tempPreRcdForm.recordDate,
    //             wakeTime:this.tempPreRcdForm.wakeTime,
    //             sleepTime:this.tempPreRcdForm.sleepTime
    //           });
    //           this.axios({
    //             method: 'post',
    //             url: '/record/insertTempPre',
    //             data: data
    //           }).then(response => {
    //             console.log(response);
    //           }).catch(error => {
    //             console.log(error);
    //           });
    //           this.$message.success('提交成功');
    //           this.clear();
    //         } else {
    //           this.$message.error('此日日志已存在，请先删除旧日志');
    //         }
    //       }).catch(error => {
    //         console.log(error);
    //       });
    //     }
    // }
  },
  created(){
    this.get();
    let data=this.tableData;
      if(data.length==0){
        this.testDate=this.getDate();
      }else{
        this.testDate=data[0].testDate;
      }
  }
}
</script>

