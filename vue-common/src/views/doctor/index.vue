<template>
      <div style="margin-top: 80px">
        <el-form :inline="true" class="demo-form-inline" size="mini">
          <el-form-item>
            <el-input class="search_name" v-model="search" placeholder="请输入患者姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="el-icon-search" @click="handleSearch">查询</el-button>
            <el-button type="primary" class="el-icon-circle-plus-outline" @click="dialogAdd=true">添加患者</el-button>
          </el-form-item>
        </el-form>

        <el-table :data="tableData" highlight-current-row border style="width: 100%" ref="table">
          <el-table-column label="姓名">
            <template slot-scope="scope">
              <a @click="click(scope.row.username)" style="color:blue;cursor:pointer">{{ scope.row.name }}</a>
            </template>
          </el-table-column>
          <el-table-column label="性别">
            <template slot-scope="scope">
              <span>{{ scope.row.sex }}</span>
            </template>
          </el-table-column>
          <el-table-column label="年龄">
            <template slot-scope="scope">
              <span>{{ scope.row.age }}</span>
            </template>
          </el-table-column>
          <el-table-column label="联系电话">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="200px">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pages">
          <el-pagination
            :current-page.sync="currentPage"
            layout="total,sizes,prev,pager,next,jumper"
            :page-sizes="[10, 30, 50, 100]"
            :page-size.sync="pageSize"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange">
          </el-pagination>
        </div>

        <el-form :model="patientForm" :rules="rules" ref="patientForm" label-width="100px" class="demo-form" size="mini">
          <el-dialog :append-to-body='true' :visible.sync="dialogAdd" width="380px" :before-close="handleClose">
            <el-form-item label="患者账号" prop="username">
              <el-input style="width: 200px" placeholder="请输入" v-model="patientForm.username"></el-input>
            </el-form-item>
            <span slot="footer" class="dialog-footer">
          <el-button @click="cancel" size="mini">取消</el-button>
          <el-button @click="add" type="primary" size="mini">确定</el-button>
        </span>
          </el-dialog>
        </el-form>

        <el-form label-width="100px" class="demo-form" size="mini">
          <el-dialog :append-to-body='true' :visible.sync="dialogLook" width="80%"
                     :before-close="handleCloseLook" center>
            <Analyze v-if="renderComponentLook" :username="patientForm.username"></Analyze>
          </el-dialog>
        </el-form>
      </div>
</template>

<script>
import Analyze from '@/components/inside/Analyze'

export default {
  name: "index",
  components:{
    Analyze
  },
  data() {
    return {
      renderComponentLook: true,
      dialogLook:false,
      patientForm: {
        username: ''
      },
      tableData: [],
      search: '',
      nowSearch: '',
      pageSize: 10,
      currentPage: 1,
      total: 0,
      dialogAdd: false,
      rules: {
        username: [
          {required: true, message: '必填项', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    forceRerenderLook() {
      this.renderComponentLook = false;
      this.$nextTick(() => {
        this.renderComponentLook = true;
      });
    },
    handleCloseLook() {
      this.dialogLook = false;
    },
    click(val){
      sessionStorage.setItem('click','yes');
      sessionStorage.setItem('search',this.search);
      sessionStorage.setItem('nowSearch',this.nowSearch);
      sessionStorage.setItem('pageSize',this.pageSize);
      sessionStorage.setItem('currentPage',this.currentPage);
      sessionStorage.setItem('total',this.total);
      this.patientForm.username=val;
      this.forceRerenderLook();
      this.dialogLook = true;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.cancel();
          done();
        })
        .catch(_ => {});
    },
    cancel() {
      this.patientForm={
        username: ''
      };
      this.$refs.patientForm.clearValidate();
      this.dialogAdd = false;
    },
    handleDelete(index, row) {
      this.$confirm('删除操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let postData = this.qs.stringify({
          patientId: row.username
        });
        this.axios({
          method: 'post',
          url:'/connection/deleteByPatient',
          data:postData
        }).then(response =>
        {
          this.getRows();
          this.currentPage=1;
          this.handleCurrentChange();
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          console.log(response);
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
    add(){
      this.$refs.patientForm.validate(valid => {
        if (valid) {
          let postData = this.qs.stringify({
            username: this.patientForm.username
          });
          let tempData=this.qs.stringify({
            doctorId:sessionStorage.getItem('username'),
            patientId:this.patientForm.username
          });
          this.axios({
            method: 'post',
            url: '/patient/findByUsername',
            data: postData
          }).then(response => {
            let status = response.data.status;
            if (status === 'success') {
              this.axios({
                method: 'post',
                url: '/connection/findByConnection',
                data: tempData
              }).then(res => {
                let result = res.data.status;
                if (result === 'success') {
                  this.$message.info('此患者已存在');
                } else {
                  this.axios.post('/connection/insert',tempData).then(res=>{
                    this.search='';
                    this.nowSearch='';
                    this.getRows();
                    this.currentPage=1;
                    this.handleCurrentChange();
                    this.$message.success('添加患者成功');
                    this.cancel();
                  }).catch(res=>{});
                }
              }).catch(error => {
                console.log(error);
              });
            } else {
              this.$message.error('此患者账号不存在');
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
    handleSearch(){
      this.nowSearch=this.search;
      this.getRows();
      this.currentPage=1;
      this.handleCurrentChange();
    },
    getRows() {
      let postData = this.qs.stringify({
        name:this.nowSearch,
        doctorId:sessionStorage.getItem('username')
      });
      this.axios({
        method: 'post',
        url:'/patient/rows',
        data:postData
      }).then(response =>
      {
        this.total = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
    },
    handleSizeChange(){
      this.currentPage=1;
      this.handleCurrentChange();
    },
    handleCurrentChange() {
      // console.log(`当前页: ${this.currentPage}`);
      let postData = this.qs.stringify({
        page: this.currentPage,
        pageSize:this.pageSize,
        name:this.nowSearch,
        doctorId:sessionStorage.getItem('username')
      });
      this.axios({
        method: 'post',
        url:'/patient/page',
        data:postData
      }).then(response =>
      {
        this.tableData = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
    }
  },
  created(){
    let click=sessionStorage.getItem('click');
    if(click=='yes'){
      sessionStorage.setItem('click','no');
      this.search=sessionStorage.getItem('search');
      this.nowSearch=sessionStorage.getItem('nowSearch');
      this.total=parseInt(sessionStorage.getItem('total'));
      this.currentPage=parseInt(sessionStorage.getItem('currentPage'));
      this.pageSize=parseInt(sessionStorage.getItem('pageSize'));
      this.handleCurrentChange();
    }else{
      this.nowSearch='';
      this.getRows();
      this.currentPage=1;
      this.handleCurrentChange();
    }
  }
}
</script>

<style scoped>
.search_name{
  width:210px;
}
.pages{
  margin-top: 2%;
  text-align: center;
}
</style>
