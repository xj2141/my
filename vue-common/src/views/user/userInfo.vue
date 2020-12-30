<template>
  <div>
    <div class="title">
      <span>个人信息</span>
    </div>
    <div class="all">
    <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="100px">
      <el-form-item label="账号完善度">
        <el-progress :percentage=completion style="width: 350px;margin-top: 12px"></el-progress>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input name="name" type="text" v-model="infoForm.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="infoForm.sex" placeholder="请选择" style="width: 300px">
          <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input name="age" type="text" v-model.number="infoForm.age" placeholder="请输入年龄"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input name="phone" type="text" v-model="infoForm.phone" placeholder="请输入电话"></el-input>
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
export default{
  name:'info',
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
      items:[
        {
          value:'',
          label:''
        },
        {
          value:'男',
          label:'男'
        },
        {
          value:'女',
          label:'女'
        }
      ],
      infoForm:{
        username:'',
        name:'',
        sex:'',
        age:'',
        phone:''
      },
      completion:0,
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
    progress(){
      let num=0;
      if(this.infoForm.name!=''){
        num+=25;
      }
      if(this.infoForm.sex!=''){
        num+=25;
      }
      if(this.infoForm.age!=''){
        console.log(this.infoForm.age);
        num+=25;
      }
      if(this.infoForm.phone!=''){
        num+=25;
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
              url:'/user/updateO',
              data:postData
            }).then(response=>{
              let status=response.data.status;
              if(status=="success"){
                this.$message.success('个人信息修改成功');
                this.progress();
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
      url:'/user/getInfo',
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
  width:400px;
  margin-top: 4%;
  margin-left: calc(calc(100vw - 500px) / 2); /** 动态居中 */
  font-size: 20px;
}
</style>
