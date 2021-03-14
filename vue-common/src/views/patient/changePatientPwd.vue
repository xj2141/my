<template>
  <div>
    <div class="title">
      <span>修改密码</span>
    </div>
    <div class="all">
    <el-form ref="pwdForm" :model="pwdForm" :rules="rules" label-width="100px" label-position="top">
      <el-form-item label="旧密码" prop="oldPwd">
        <el-input name="oldPwd" type="password" v-model="pwdForm.oldPwd" show-password placeholder="请输入旧密码"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPwd">
        <el-input name="newPwd" type="password" v-model="pwdForm.newPwd" show-password placeholder="请输入新密码"></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" prop="snewPwd">
        <el-input name="snewPwd" type="password" v-model="pwdForm.snewPwd" show-password placeholder="请确认新密码"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button type="primary" @click="handleBack">返回</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
</template>

<script>
export default{
  name:"changePwd",
  data(){
    let validateNewPassword=(rule, value, callback) => {
      if (value == this.pwdForm.oldPwd) {
        callback(new Error('新密码不能与旧密码相同'))
      } else {
        callback()
      }
    }
    let validateSNewPassword=(rule, value, callback) => {
      if (value !== this.pwdForm.newPwd) {
        callback(new Error('确认新密码与新密码不一致'))
      } else {
        callback()
      }
    }
    return{
      pwdForm:{
        username:'',
        oldPwd:'',
        newPwd:'',
        snewPwd:''
      },
      rules:{
        oldPwd:[
          {required:true, message:'旧密码不能为空', trigger:'blur'}
        ],
        newPwd:[
          {required:true, message:'新密码不能为空', transform: value => value, trigger:'blur'},
          {
            type: 'string',
            message: '请输入不包含空格的字符',
            trigger: 'blur',
            transform(value) {
              if (value && value.indexOf(' ') === -1) {
                return value
              } else {
                return false
              }
            }
          },
          {
            trigger: 'blur',
            validator: (rule, value, callback) => {
              var passwordreg = /^(?=.*[0-9])(?=.*[a-zA-Z]).{6,15}$/;
              if (!passwordreg.test(value)) {
                callback(new Error('密码长度在6-15位之间，至少包含字母和数字'))
              }else{
                callback()
              }
            }
          },
          {validator: validateNewPassword, trigger: 'blur'}
        ],
        snewPwd:[
          {required:true, message:'确认新密码不能为空', trigger:'blur'},
          {validator:validateSNewPassword, trigger: 'blur'}
        ]
      }
    }
  },
  mounted: function () {
    this.pwdForm.username=sessionStorage.getItem('username');
  },
  methods:{
    handleSubmit(){
      this.$refs.pwdForm.validate(valid=>{
        if(valid){
          let postData=this.qs.stringify({
            username:this.pwdForm.username,
            password:this.pwdForm.oldPwd
          });
          this.axios({
            method:'post',
            url:'/patient/findByUser',
            data:postData
          }).then(response=>{
            let status=response.data.status;
            if(status=="success"){
                let pData=this.qs.stringify({
                  username:this.pwdForm.username,
                  password:this.pwdForm.newPwd
                });
                this.axios({
                  method:'post',
                  url:'/patient/updateP',
                  data:pData
                }).then(response=>{
                  let status=response.data.status;
                  if(status=="success"){
                    this.$message.success('密码修改成功');
                    this.$router.push({
                      path:"/"
                    });
                  }else{
                    this.$message.error('密码修改失败');
                  }
                }).catch(error => {
                  console.log(error);
                });
            }else{
              this.$message.error('旧密码输入错误');
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
  width:300px;
  margin-top: 3%;
  margin-left: calc(calc(100vw - 550px) / 2); /** 动态居中 */
  font-size: 20px;
}
</style>
