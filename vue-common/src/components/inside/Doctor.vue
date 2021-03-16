<template>
  <div>
      <el-form :inline="true" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="search" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="el-icon-search" @click="handleSearch">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSelect">确定</el-button>
        </el-form-item>
      </el-form>
    <el-table :data="tableData" highlight-current-row border style="width: 100%" @selection-change="handleSelectionChange" ref="table" height="250">
      <el-table-column type="selection" fixed></el-table-column>
      <el-table-column label="医生" fixed>
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          <span>{{ scope.row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="医院">
        <template slot-scope="scope">
          <span>{{ scope.row.hospital }}</span>
        </template>
      </el-table-column>
      <el-table-column label="科室">
        <template slot-scope="scope">
          <span>{{ scope.row.department }}</span>
        </template>
      </el-table-column>
      <el-table-column label="职称">
        <template slot-scope="scope">
          <span>{{ scope.row.professionTitle }}</span>
        </template>
      </el-table-column>
      <el-table-column label="职务">
        <template slot-scope="scope">
          <span>{{ scope.row.profession }}</span>
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
      tableData: [],
      multipleSelection:[],
      search:'',
      nowSearch:'',
      pageSize: 10,
      currentPage: 1,
      total: 0
    }
  },
  methods:{
    handleSelect() {
      this.$emit("parentHandle", this.multipleSelection);
    },
    handleSelectionChange(val){
      this.multipleSelection=val;
    },
    handleSearch(){
      this.nowSearch=this.search;
      this.getRows();
      this.currentPage=1;
      this.handleCurrentChange();
    },
    getRows() {
      let postData = this.qs.stringify({
        name:this.nowSearch
      });
      this.axios({
        method: 'post',
        url:'/doctor/rows',
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
        name:this.nowSearch
      });
      this.axios({
        method: 'post',
        url:'/doctor/page',
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
.pages{
  margin-top: 2%;
  text-align: center;
}
</style>
