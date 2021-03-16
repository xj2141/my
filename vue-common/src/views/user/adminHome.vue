<template>
  <el-container>
    <el-header height="40px" style="text-align: right">
      <span style="position: absolute;left:15px;top:9px;color: white">管理员系统</span>
      <el-button style="color: white" type="text" @click="loginOut">退出登录</el-button>
    </el-header>
    <el-main>
    <div class="one">
    <el-form :inline="true" class="demo-form-inline" size="mini">
      <el-form-item label="账号">
        <el-input v-model="searchU" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="searchS" class="search_name" placeholder="请选择账号状态">
          <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-search" @click="handleSearch">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="allow">启用</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="forbid">禁用</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-download" @click="handleExport">导出</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-delete" @click="removeBatch()">批量删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-circle-plus-outline" @click="dialogAdd=true">添加账号</el-button>
      </el-form-item>
    </el-form>
    </div>

    <el-table :data="tableData" highlight-current-row border style="width: 100%" @selection-change="handleSelectionChange" ref="table">
      <el-table-column type="selection" fixed></el-table-column>
      <el-table-column label="账号" fixed>
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="密码">
        <template slot-scope="scope">
          <span>{{ scope.row.password }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span>{{ scope.row.state }}</span>
        </template>
      </el-table-column>
    </el-table>

    <div class="pages">
      <el-pagination
        :current-page.sync="currentPage"
        layout="total,sizes,prev,pager,next,jumper"
        :page-sizes="[30, 50, 100, 200]"
        :page-size.sync="pageSize"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <el-form :model="addForm" :rules="rules" ref="addForm" label-width="100px" class="demo-form" size="mini">
      <el-dialog :append-to-body='true' :visible.sync="dialogAdd" width="380px" :before-close="handleClose" center>
        <el-form-item label="账号数量" prop="number">
          <el-input style="width: 200px" placeholder="请输入数值" v-model.number="addForm.number" oninput="value=value.replace(/[^\d]/g, '')"></el-input>
        </el-form-item>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel" size="mini">取消</el-button>
          <el-button @click="add" type="primary" size="mini">确定</el-button>
        </span>
      </el-dialog>
    </el-form>
    </el-main>
  </el-container>
</template>

<script>
export default{
  data(){
    let checkValue = (rule, value, callback) => {
      if (value<1||value>1000) {
        callback(new Error('数值范围在1-1000'))
      } else {
        callback()
      }
    }
    return{
      items:[
        {
          value:'',
          label:''
        },
        {
          value:'已使用',
          label:'已使用'
        },
        {
          value:'已禁用',
          label:'已禁用'
        },
        {
          value:'未使用',
          label:'未使用'
        }
      ],
      tableData: [],
      multipleSelection:[],
      searchU:'',
      nowSearchU:'',
      searchS:'',
      nowSearchS:'',
      pageSize: 30,
      currentPage: 1,
      total: 0,
      dialogAdd: false,
      addForm:{
        number:''
      },
      rules: {
        number: [
          {required: true, message: '输入不能为空', trigger: 'blur'},
          {validator: checkValue, trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    loginOut(){
      sessionStorage.setItem("token", 'false');
      this.$router.push('/login')
    },
    handleSelectionChange(val){
      this.multipleSelection=val;
    },
    handleSearch(){
      this.nowSearchU=this.searchU;
      this.nowSearchS=this.searchS;
      this.getRows();
      this.currentPage=1;
      this.handleCurrentChange();
    },
    getRows() {
      let postData = this.qs.stringify({
        username:this.nowSearchU,
        state:this.nowSearchS
      });
      this.axios({
        method: 'post',
        url:'/admin/rows',
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
        username:this.nowSearchU,
        state:this.nowSearchS
      });
      this.axios({
        method: 'post',
        url:'/admin/page',
        data:postData
      }).then(response =>
      {
        this.tableData = response.data;
      }).catch(error =>
      {
        console.log(error);
      });
    },
    removeBatch(){
      if(this.multipleSelection.length==0){
        this.$message.warning("请先勾选选项");
      }else{
        this.$confirm('批量删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let usernames = this.multipleSelection.map(item => item.username).join();
          let states = this.multipleSelection.map(item => item.state).join();
          this.axios.post("/admin/delete",{usernames:usernames,states:states}).
          then(response =>
          {
            this.getRows();
            this.currentPage=1;
            this.handleCurrentChange();
            this.$message({
              type: 'success',
              message: '批量删除成功!'
            });
          }).catch(error =>
          {
            console.log(error);
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消批量删除'
          });
        });
      }
    },
    allow(){
      if(this.multipleSelection.length==0){
        this.$message.warning("请先勾选选项");
      }else{
        let ids = {
          params: this.multipleSelection
        };
        let users=JSON.stringify(ids);
        let op='启用';
        this.axios.post('/admin/update',{users:users,op:op}).then(response=>{
          this.handleCurrentChange();
          this.$message.success("启用成功");
        }).catch(()=>{});
      }
    },
    forbid(){
      if(this.multipleSelection.length==0){
        this.$message.warning("请先勾选选项");
      }else{
        let ids = {
          params: this.multipleSelection
        };
        let users=JSON.stringify(ids);
        let op='禁用';
        this.axios.post('/admin/update',{users:users,op:op}).then(response=>{
          this.handleCurrentChange();
          this.$message.success("禁用成功");
        }).catch(()=>{});
      }
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
      this.addForm={
        number: ''
      };
      this.$refs.addForm.clearValidate();
      this.dialogAdd = false;
    },
    add(){
      this.$refs.addForm.validate(valid => {
        if (valid) {
          let postData=this.qs.stringify({
            number:this.addForm.number
          });
          this.axios.post('/admin/insert',postData).then(response=>{
            this.getRows();
            this.currentPage=1;
            this.handleCurrentChange();
            this.$message.success('添加成功');
            this.cancel();
          }).catch(()=>{});
        } else {
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    handleExport() {
      if (this.multipleSelection.length == 0) {
        this.$message.warning("请先勾选选项");
      } else {
        let params = {
          params: this.multipleSelection
        };
        this.axios({
          method: 'post',
          url: '/admin/export',
          data: params,
          responseType: 'blob'
        }).then(response => {
          const link = document.createElement('a');
          let blob = new Blob([response.data], {type: "application/vnd.ms-excel"});
          link.style.display = 'none';
          link.href = URL.createObjectURL(blob);
          link.setAttribute('download', '医生账户.xls');
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        }).catch(error => {
          this.$message.success("系统数据错误！");
        });
      }
    }
  },
  created(){
    this.nowSearchU='';
    this.nowSearchS='';
    this.getRows();
    this.currentPage=1;
    this.handleCurrentChange();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  background-color: #333;
  color: #333;
  position: fixed;
  width: 100%;
  left: 0;
  top: 0;
}
.one{
  margin-top: 2%;
}
.pages{
  margin-top: 1%;
  text-align: center;
}
</style>
