<template>
  <div style="margin-top: 80px">
    <div class="title">
      <span>个人信息</span>
    </div>
    <div class="all">
      <el-form ref="infoForm" :model="infoForm" :rules="rules" label-width="100px" size="mini">
        <el-form-item label="账号完善度">
          <el-progress :percentage=completion style="width: 350px;margin-top: 12px"></el-progress>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input name="name" type="text" v-model="infoForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select style="width: 300px" v-model="infoForm.sex" placeholder="请选择">
            <el-option v-for="item in items" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input name="age" type="text" v-model.number="infoForm.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input name="phone" type="text" v-model="infoForm.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="医院" prop="phone">
          <el-input name="hospital" type="text" v-model="infoForm.hospital" placeholder="请输入医院名称"></el-input>
        </el-form-item>
        <el-form-item label="科室" prop="phone">
          <el-input name="department" type="text" v-model="infoForm.department" placeholder="请输入科室名称"></el-input>
        </el-form-item>
        <el-form-item label="职称" prop="phone">
          <el-input name="professionTitle" type="text" v-model="infoForm.professionTitle" placeholder="请输入职称"></el-input>
        </el-form-item>
        <el-form-item label="职务" prop="phone">
          <el-input name="profession" type="text" v-model="infoForm.profession" placeholder="请输入职务"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" @click="handleSubmit">保存</el-button>
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
      infoForm:{
        username:'',
        name:'',
        sex:'',
        age:'',
        phone:'',
        hospital:'',
        department:'',
        professionTitle:'',
        profession:''
      },
      completion:0,
      items:[
        {
          value:'男',
          label:'男'
        },
        {
          value:'女',
          label:'女'
        }
      ],
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
        num+=12.5;
      }
      if(this.infoForm.sex!=''){
        num+=12.5;
      }
      if(this.infoForm.age!=''){
        num+=12.5;
      }
      if(this.infoForm.phone!=''){
        num+=12.5;
      }
      if(this.infoForm.hospital!=''){
        num+=12.5;
      }
      if(this.infoForm.department!=''){
        num+=12.5;
      }
      if(this.infoForm.professionTitle!=''){
        num+=12.5;
      }
      if(this.infoForm.profession!=''){
        num+=12.5;
      }
      this.completion=num;
    },
    handleSubmit(){
      this.$refs.infoForm.validate(valid=>{
        if(valid){
          let postData=this.qs.stringify(this.infoForm);
          this.axios({
            method:'post',
            url:'/doctor/updateO',
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
    }
  },
  created(){
    this.infoForm.username=sessionStorage.getItem('username');
    let postData=this.qs.stringify({
      username:this.infoForm.username
    });
    this.axios({
      method:'post',
      url:'/doctor/getInfo',
      data:postData
    }).then(response=>{
      let status=response.data.status;
      if(status=="success"){
        this.infoForm.name=response.data.name;
        this.infoForm.sex=response.data.sex;
        this.infoForm.age=response.data.age;
        this.infoForm.phone=response.data.phone;
        this.infoForm.hospital=response.data.hospital;
        this.infoForm.department=response.data.department;
        this.infoForm.professionTitle=response.data.professionTitle;
        this.infoForm.profession=response.data.profession;
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
  margin-left: calc(calc(100vw - 530px) / 2); /** 动态居中 */
  font-size: 20px;
}
</style>
