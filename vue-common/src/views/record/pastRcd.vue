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
        <el-button type="primary" class="el-icon-delete" @click="removeBatch()">删除</el-button>
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
  </div>
</template>

<script>
import InTable from '@/components/inside/InTable'

export default {
  components: {
    InTable
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
      multipleSelection:[]
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
