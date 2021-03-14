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
          style="width: 160px"
          @change="updateDate">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" class="el-icon-circle-plus-outline" @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" highlight-current-row border style="width: 100%" ref="table">
      <el-table-column
        label="次数"
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
      <el-table-column label="检测结果" width="300px">
        <template slot-scope="scope">
          <span>{{ scope.row.conclusion }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index,scope.row)">查看</el-button>
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index,scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-form style="text-align: center;margin-top: 1%">
      <el-form-item>
        <el-button type="primary" size="mini" @click="submit">提交</el-button>
        <el-button type="primary" size="mini" @click="refresh">刷新</el-button>
      </el-form-item>
    </el-form>

    <el-form label-width="100px" class="demo-form" size="mini">
      <el-dialog title="添加记录" :append-to-body='true' :visible.sync="dialogAdd" width="1000px"
                 :before-close="handleCloseAdd" center>
        <FlowTestAdd v-if="renderComponentAdd" ref="flowTestAdd" :nowTestForm="testForm"
                     :nowFlowData="flowForm" @parentHandle="cancelA"></FlowTestAdd>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelAdd" size="mini">取消</el-button>
          <el-button @click="add" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>

    <el-form label-width="100px" class="demo-form" size="mini">
      <el-dialog title="修改记录" :append-to-body='true' :visible.sync="dialogUpdate" width="1000px"
                 :before-close="handleCloseUpdate" center>
        <FlowTestUpdate v-if="renderComponentUpdate" ref="flowTestUpdate" :nowTestForm="testForm"
                        :nowFlowData="flowForm" @parentHandle="cancelU"></FlowTestUpdate>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelUpdate" size="mini">取消</el-button>
          <el-button @click="update" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import FlowTestAdd from '@/components/flow/FlowTestAdd'
import FlowTestUpdate from '@/components/flow/FlowTestUpdate'

export default {
  components: {
    FlowTestAdd,
    FlowTestUpdate
  },
  data() {
    return {
      renderComponentAdd: true,
      renderComponentUpdate: true,
      testForm: {
        testId:'',
        username:'',
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
      flowForm: [],
      tableData: [],
      flowData: [],
      dialogAdd: false,
      dialogUpdate: false,
      pickerOption: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6
        }
      }
    }
  },
  methods: {
    forceRerenderAdd() {
      // 从 DOM 中删除 my-component 组件
      this.renderComponentAdd = false;
      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponentAdd = true;
      });
    },
    forceRerenderUpdate() {
      // 从 DOM 中删除 my-component 组件
      this.renderComponentUpdate = false;
      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponentUpdate = true;
      });
    },
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
      this.$set(this.testForm, "testDate", defaultDate);
    },
    get() {
      let postData=this.qs.stringify({
        username:sessionStorage.getItem('username')
      });
      this.axios.post('/tempFlowTest/get',postData).then(response => {
        this.tableData = response.data.pre;
        this.flowData = response.data.suf;
      }).catch(error => {
        console.log(error);
      });
    },
    updateDate(){
      let length=this.tableData.length;
      if(length!=0&&this.testForm.testDate!=null){
        let postData=this.qs.stringify({
          username:sessionStorage.getItem('username'),
          testDate:this.testForm.testDate
        });
        this.axios.post('/tempFlowTest/updateDate',postData).then(response=>{}).catch(error =>{});
      }
    },
    handleAdd() {
      let testDate=this.testForm.testDate;
      this.testForm = {
        testId:'',
        username:sessionStorage.getItem('username'),
        testDate: testDate,
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
      this.flowForm = [];
      this.forceRerenderAdd();
      this.dialogAdd = true;
    },
    handleEdit(index, row) {
      this.testForm = Object.assign({}, row); //这句是关键！！！
      this.flowForm = this.flowData[index];
      this.forceRerenderUpdate();
      this.dialogUpdate = true;
    },
    handleDelete(index, row) {
      this.forceRerenderUpdate();
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let postData = this.qs.stringify(row);
        this.axios({
          method: 'post',
          url: '/tempFlowTest/delete',
          data: postData
        }).then(response => {
          this.get();
          this.$message({
            type: 'success',
            message: '删除成功!'
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
    },
    handleCloseAdd(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cancelAdd();
          done();
        })
        .catch(_ => {
        });
    },
    handleCloseUpdate(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cancelUpdate();
          done();
        })
        .catch(_ => {
        });
    },
    cancelAdd() {
      this.$refs.flowTestAdd.cancel();
      this.dialogAdd = false;
    },
    cancelUpdate() {
      this.$refs.flowTestUpdate.cancel();
      this.dialogUpdate = false;
    },
    add() {
      this.$refs.flowTestAdd.add();
    },
    cancelA(){
      this.$message({
        type: 'success',
        message: '添加成功'
      });
      this.cancelAdd();
      this.get();
    },
    update() {
      this.$refs.flowTestUpdate.update();
    },
    cancelU(){
      this.$message({
        type: 'success',
        message: '修改成功'
      });
      this.cancelUpdate();
      this.get();
    },
    clear() {
      let postData=this.qs.stringify({
        username:sessionStorage.getItem('username')
      });
      this.axios.post('/tempFlowTest/remove',postData).then(res => {
      }).catch(err => {
      });
      this.getNowTime();
      this.tableData = [];
      this.flowData = [];
    },
    refresh() {
      this.clear();
      this.$message({
        type: 'success',
        message: '刷新成功'
      });
    },
    submit(){
      let username=sessionStorage.getItem('username');
        let result=this.tableData;
        if(result.length==0){
          this.$message({
            type:'error',
            message:'表中无记录，提交失败'
          });
        }else {
          if(this.testForm.testDate==null){
            this.$message.error("日期为必填项");
          }
          else{
            let postData=this.qs.stringify({
              testDate:this.testForm.testDate,
              username:username
            });
            this.axios({
              method: 'post',
              url: '/flowTest/findByDate',
              data: postData
            }).then(response => {
              let status = response.data.status;
              if (status == "success") {
                let tempData=this.qs.stringify({
                  username:username
                });
                this.axios.post('/flowTest/insert',tempData).then(res=>{
                  this.$message.success('提交成功');
                  this.clear();
                }).catch(err=>{});
              } else {
                this.$message.error('此日尿流检测数据已存在，请先删除旧数据');
              }
            }).catch(error => {
              console.log(error);
            });
          }
        }
    }
  },
  created() {
    let postData=this.qs.stringify({
      username:sessionStorage.getItem('username')
    });
    this.axios.post('/tempFlowTest/get',postData).then(response => {
      this.tableData = response.data.pre;
      this.flowData = response.data.suf;
      let data=this.tableData;
      if (data.length == 0) {
        this.getNowTime();
      } else {
        this.testForm.testDate = data[0].testDate;
      }
    }).catch(error => {
      console.log(error);
    });
  }
}
</script>

