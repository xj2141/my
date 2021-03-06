<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column :label="'日期：'+headData.recordDate">
      <el-table-column
        :label="'起床时间：'+headData.wakeTime+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+'入睡时间：'+headData.sleepTime">
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
      </el-table-column>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "InTable",
  props:{
    tableData:{
      type:Array,
      default:()=>{}
    },
    headData:{
      type:Object,
      default:()=>{}
    }
  },
  methods: {
    indexMethod(index) {
      return index + 1;
    }
  }
}
</script>

<style scoped>

</style>
