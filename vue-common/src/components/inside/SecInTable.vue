<template>
  <div>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column :label="'日期：'+headData.testDate">
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
      <el-table-column label="检测结果">
        <template slot-scope="scope">
          <span>{{ scope.row.conclusion }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleLook(scope.$index,scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table-column>
  </el-table>

    <el-form label-width="100px" class="demo-form" size="mini">
      <el-dialog :append-to-body='true' :visible.sync="dialogLook" width="1000px"
                 :before-close="handleCloseLook" center>
        <FlowTestLook v-if="renderComponentLook" :nowTestForm="testForm" :nowFlowData="flowForm"></FlowTestLook>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import FlowTestLook from '@/components/flow/FlowTestLook'

export default {
  name: "SecInTable",
  components: {
    FlowTestLook
  },
  props: {
    tableData: {
      type: Array,
      default: () => {
      }
    },
    flowData: {
      type: Array,
      default: () => {
      }
    },
    headData: {
      type: Object,
      default: () => {
      }
    }
  },
  data(){
    return{
      renderComponentLook: true,
      testForm: {
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
      dialogLook: false
    }
  },
  methods: {
    indexMethod(index) {
      return index + 1;
    },
    forceRerenderLook() {
      this.renderComponentLook = false;
      this.$nextTick(() => {
        this.renderComponentLook = true;
      });
    },
    handleLook(index, row) {
      this.testForm = Object.assign({}, row); //这句是关键！！！
      this.flowForm = this.flowData[index];
      this.forceRerenderLook();
      this.dialogLook = true;
    },
    handleCloseLook() {
      this.dialogLook = false;
    }
  }
}
</script>

<style scoped>

</style>
