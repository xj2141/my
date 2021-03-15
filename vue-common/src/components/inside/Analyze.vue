<template>
  <div>
  <el-tabs type="border-card" v-model="activeName">
    <el-tab-pane label="尿流检测信息" name="first">
      <PastTest :username="username"></PastTest>
    </el-tab-pane>
    <el-tab-pane label="排尿日志" name="second">
      <PastRcd :username="username"></PastRcd>
    </el-tab-pane>
  </el-tabs>
  </div>
</template>

<script>
import PastTest from '@/components/analyze/PastTest'
import PastRcd from '@/components/analyze/PastRcd'

export default {
  name: "Analyze",
  props: {
    username: {
      type: String,
      default: ''
    }
  },
  components: {
    PastTest,
    PastRcd
  },
  data() {
    return {
      activeName: 'first'
    }
  }
}
</script>

<style scoped>

</style>
