<template>
  <div>
    <div class="one">
    <el-form :inline="true" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-select v-model="search" class="search_name" placeholder="请选择量表名称">
          <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-search" @click="handleSearch">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-icon-delete" @click="removeBatch()">批量删除</el-button>
      </el-form-item>
    </el-form>
    </div>

    <el-table :data="tableData" highlight-current-row border style="width: 100%" @selection-change="handleSelectionChange" ref="table">
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="日期" sortable>
        <template slot-scope="scope">
          <span>{{ scope.row.evaluateDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="量表名称">
        <template slot-scope="scope">
          <span>{{ scope.row.evaluateName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="得分">
        <template slot-scope="scope">
          <span>{{ scope.row.score }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结论">
        <template slot-scope="scope">
          <span>{{ scope.row.conclusion }}</span>
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
  </div>
</template>

<script>
export default{
  data(){
    return{
      items:[
        {
          value:'',
          label:''
        },
        {
          value:'国际前列腺症状评分表(IPSS)',
          label:'国际前列腺症状评分表(IPSS)'
        },
        {
          value:'膀胱过度活动症评分(OABSS)',
          label:'膀胱过度活动症评分(OABSS)'
        },
        {
          value:'生命质量量表(SF-36)',
          label:'生命质量量表(SF-36)'
        }
      ],
      tableData: [],
      search:'',
      nowSearch:'',
      multipleSelection:[],
      pageSize: 10,
      currentPage: 1,
      total: 0
    }
  },
  methods:{
    handleSearch(){
      this.nowSearch=this.search;
      this.getRows();
      this.currentPage=1;
      this.handleCurrentChange();
    },
    handleSelectionChange(val){
      this.multipleSelection=val;
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
          let ids = this.multipleSelection.map(item => item.evaluateId).join();
          this.axios.post("/evaluate/remove",{ids:ids}).
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
    getRows() {
      let postData = this.qs.stringify({
        evaluateName:this.nowSearch,
        username:sessionStorage.getItem('username')
      });
      this.axios({
        method: 'post',
        url:'/evaluate/rows',
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
        evaluateName:this.nowSearch,
        username:sessionStorage.getItem('username')
      });
      this.axios({
        method: 'post',
        url:'/evaluate/page',
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
    this.nowSearch='';
    this.getRows();
    this.currentPage=1;
    this.handleCurrentChange();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.one{
  margin-top: 1%;
}
.search_name{
  width:210px;
}
.pages{
  margin-top: 2%;
  text-align: center;
}
</style>
