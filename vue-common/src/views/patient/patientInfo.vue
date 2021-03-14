<template>
  <div>
    <div class="title">
      <span>个人信息</span>
    </div>
    <div class="all">
    <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="100px">
      <el-form-item label="账号完善度">
        <el-progress :percentage=completion style="width: 550px;margin-top: 12px"></el-progress>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input name="name" type="text" v-model="infoForm.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input name="sex" type="text" v-model="infoForm.sex" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input name="age" type="text" v-model.number="infoForm.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input name="phone" type="text" v-model="infoForm.phone" placeholder="请输入电话"></el-input>
      </el-form-item>
      <el-form-item label="主治医生">
        <el-input
          style="width:500px"
          placeholder="请选择医生"
          v-model="doctorName"
          :suffix-icon="showTree?'el-icon-arrow-up':'el-icon-arrow-down'"
          @click.native="deptogglePanel($event)"
          readonly="readonly">
        </el-input>
        <div ref="tableList" v-if="renderComponent">
          <el-popover
            placement="bottom"
            width="475"
            trigger="manual"
            v-model="showTree">
            <Doctor @parentHandle="childSelect"></Doctor>
          </el-popover>
        </div>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button type="primary" @click="handleBack">返回</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
</template>

<script>
import Doctor from "@/components/inside/Doctor";
export default{
  name:'info',
  components:{
    Doctor
  },
  data(){
    let validateAge=(rule, value, callback) => {
      if (value !== '') {
        var age = /^[0-9]*$/;
        if(!age.test(value)){
          callback(new Error('年龄只能为数字'));
        }
      }
      callback()
    }
    let validatePhone=(rule, value, callback) => {
        if (value !== '') {
          var phone=/^1[3|4|5|6|7|8][0-9]{9}$/;
          if(!phone.test(value)){
            callback(new Error('请输入有效的手机号码'));
          }
        }
      callback()
    }
    return{
      renderComponent: true,
      infoForm:{
        username:'',
        name:'',
        sex:'',
        age:'',
        phone:''
      },
      completion:0,
      doctorName:'',
      doctorId:'',
      showTree:false,
      rules:{
        age:[
          {validator: validateAge, trigger: 'blur'}
        ],
        phone:[
          {validator: validatePhone, trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    forceRerender() {
      // 从 DOM 中删除 my-component 组件
      this.renderComponent = false;
      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponent = true;
      });
    },
    // 点击input 阻止冒泡 控制table显示隐藏
    deptogglePanel (event) {
      event || (event = window.event)
      event.stopPropagation ? event.stopPropagation() : (event.cancelBubble = true)
      this.showTree ? this.tableHide() : this.tableShow()
    },
    tableShow() {
      this.showTree = true
      this.forceRerender();
      document.addEventListener('click', this.tableHideList, false)
    },
    tableHide() {
      this.showTree = false
      document.addEventListener('click', this.tableHideList, false)
    },
    tableHideList(e) {
      if (this.$refs.tableList&& !this.$refs.tableList.contains(e.target)) {
        this.tableHide()
      }
    },
    childSelect(data){
      let names='';
      data.forEach((va, index)=>{
        names+=va.name;
        if(index!=data.length-1){
          names+='，';
        }
      });
      this.doctorName=names;
      let ids=data.map(item=>item.username).join();
      this.doctorId=ids;
      this.showTree=false;
    },
    progress(){
      let num=0;
      if(this.infoForm.name!=''){
        num+=20;
      }
      if(this.infoForm.sex!=''){
        num+=20;
      }
      if(this.infoForm.age!=''){
        num+=20;
      }
      if(this.infoForm.phone!=''){
        num+=20;
      }
      if(this.doctorName!=''){
        num+=20;
      }
      this.completion=num;
    },
    handleSubmit(){
      this.$refs.infoForm.validate(valid=>{
        if(valid){
            let postData=this.qs.stringify({
              username:this.infoForm.username,
              name:this.infoForm.name,
              sex:this.infoForm.sex,
              age:this.infoForm.age,
              phone:this.infoForm.phone
            });
            this.axios({
              method:'post',
              url:'/patient/updateO',
              data:postData
            }).then(response=>{
              let status=response.data.status;
              if(status=="success"){
                this.axios.post("/connection/update", {patientId:sessionStorage.getItem('username'),ids:this.doctorId}).then(()=>{
                  this.$message.success('个人信息修改成功');
                  this.progress();
                }).catch(()=>{});
              }else{
                this.$message.error('个人信息修改失败');
              }
            }).catch(error => {
              console.log(error);
            });
        }else{
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    handleBack(){
      this.$router.push({
        path:"/"
      });
    }
  },
  created(){
    this.infoForm.username=sessionStorage.getItem('username');
    let postData=this.qs.stringify({
      username:this.infoForm.username
    });
    this.axios({
      method:'post',
      url:'/patient/getInfo',
      data:postData
    }).then(response=>{
      let status=response.data.status;
      if(status=="success"){
        this.infoForm.name=response.data.name;
        this.infoForm.sex=response.data.sex;
        this.infoForm.age=response.data.age;
        this.infoForm.phone=response.data.phone;
        this.progress();
      }else{
        this.$message.error('获取用户信息失败');
      }
    }).catch(error => {
      console.log(error);
    });
    let tempData=this.qs.stringify({
      patientId:sessionStorage.getItem('username')
    })
    this.axios.post('/connection/findByPatient', tempData).then(response=>{
      let data=response.data;
      if(data.length==0){
        this.doctorName='';
      }else{
        let ids=data.map(item=>item.doctorId).join();
        this.axios.post('/doctor/getAllName', {ids:ids}).then(response=>{
          this.doctorName=response.data;
        }).catch(()=>{});
      }
    }).catch(()=>{});
  }
}
</script>

<style scoped>
.title {
  margin-top: 4%;
  text-align: center;
  font-size: 28px;
  color: rgba(20, 20, 20, 1);
}
.all{
  width:600px;
  margin-top: 4%;
  margin-left: calc(calc(100vw - 950px) / 2); /** 动态居中 */
  font-size: 20px;
}
</style>
