<template>
  <div>
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt=""/>
    </div>
    <div class="middle">
      <vue-particles
        color="#dedede"
        :particleOpacity="0.7"
        :particlesNumber="90"
        shapeType="star"
        :particleSize="4"
        linesColor="#dedede"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push">
      </vue-particles>
    </div>
    <div class="front">
      <el-card class="card">
        <div slot="header" class="title">
          <span>登录</span>
        </div>
        <div>
          <el-form ref="loginForm" :model="loginForm" status-icon :rules="rules" label-width="0" class="demo-loginForm">
            <el-form-item prop="username">
              <el-input name="username" type="text" v-model="loginForm.username" autocomplete="on"
                        placeholder="用户名">
                <i slot="prepend" class="el-icon-s-custom"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input name="password" type="password" v-model="loginForm.password" show-password
                        placeholder="密码">
                <i slot="prepend" class="el-icon-lock"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="vcode">
              <el-row :span="24">
                <el-col :span="14">
                  <el-input name="vcode" type="text" v-model="loginForm.vcode"
                            placeholder="请输入验证码"></el-input>
                </el-col>
                <el-col :span="4">
                  <div @click="createCode">
                    <s-identify :identifyCode="identifyCode"></s-identify>
                  </div>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item style="text-align: center">
              <el-button type="primary" @click="handleLogin">登录</el-button>
              <el-button type="primary" @click="handleRegist">注册</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import SIdentify from '@/components/identify/SIdentify'

export default {
  name: "login",
  components: {
    SIdentify
  },
  data() {
    let validateVcode = (rule, value, callback) => {
      var codeStatus = this.checkCode();
      if (!codeStatus) {
        callback(new Error('验证码错误'));
        this.createCode();
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: '',
        vcode: ''
      },
      identifyCode: '',
      rules: {
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
        vcode: [
          {required: true, message: '验证码不能为空', trigger: 'blur'},
          {validator: validateVcode, trigger: 'blur'}
        ]
      },
      imgSrc: require('../../assets/bg.png')
    }
  },
  mounted() {
    this.createCode();
  },
  methods: {
    createCode() {
      let code = "";
      let codeLength = 4; //验证码的长度
      let random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //随机数
      for (let i = 0; i < codeLength; i++) { //循环操作
        let index = Math.floor(Math.random() * 36); //取得随机数的索引（0~35）
        code += random[index]; //根据索引取得随机数加到code上
      }
      this.identifyCode = code; //把code值赋给验证码
    },
    checkCode() {
      let vcode = this.loginForm.vcode;
      vcode = vcode.toUpperCase();
      let identifyCode = this.identifyCode;
      identifyCode = identifyCode.toUpperCase();
      if (vcode == identifyCode) return true;
      return false;
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          let postData = this.qs.stringify({
            username: this.loginForm.username,
            password: this.loginForm.password
          });
          this.axios({
            method: 'post',
            url: '/user/login',
            data: postData
          }).then(response => {
            let status = response.data.status;
            if (status === 'success') {
              let role=response.data.role;
              sessionStorage.setItem('token', 'true');
              sessionStorage.setItem("role",role);
              sessionStorage.setItem('username', this.loginForm.username);
              if(role=='doctor'){
                this.$router.push({
                  path: '/doctorHome'
                });
              }else{
                this.$router.push({
                  path: '/'
                });
              }
            } else {
              this.$message.error('用户名或密码错误');
              this.createCode();
            }
          }).catch(error => {
            console.log(error);
          });
        } else {
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    handleRegist() {
      this.$router.push({
        path: "/regist"
      });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
  position: fixed;
}

.middle {
  position: fixed;
  top: 0;
  width: 100%;
}

.front {
  z-index: 1;
  position: absolute;
}

.card {
  width: 300px;
  height: 350px;
  margin-top: calc(calc(100vh - 350px) / 2); /** 竖直动态居中 */
  margin-left: calc(calc(100vw - 300px) / 2); /** 动态居中 */
  background-color: rgba(200, 200, 200, 0.3); /** rgba中的a为alpha通道，为不透明参数，0即为完全透明 */
  border-color: rgba(200, 200, 200, 0.3);
  border-radius: 10px;
}

.title {
  text-align: center;
  font-size: 24px;
  color: rgba(20, 20, 20, 1);
}
</style>

