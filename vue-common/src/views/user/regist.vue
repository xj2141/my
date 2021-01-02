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
          <span>注册</span>
        </div>
        <div>
          <el-form ref="registForm" :model="registForm" status-icon :rules="rules" label-width="0"
                   class="demo-registForm">
            <el-form-item prop="username">
              <el-input name="username" type="text" v-model="registForm.username" autocomplete="on"
                        placeholder="请输入用户名">
                <i slot="prepend" class="el-icon-s-custom"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input name="password" type="password" v-model="registForm.password" show-password
                        placeholder="请输入密码">
                <i slot="prepend" class="el-icon-lock"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="spassword">
              <el-input name="spassword" type="password" v-model="registForm.spassword" show-password
                        placeholder="请确认密码">
                <i slot="prepend" class="el-icon-lock"/>
              </el-input>
            </el-form-item>
            <el-form-item style="text-align: center">
              <el-button type="primary" @click="handleRegist">注册</el-button>
              <el-button type="primary" @click="handleBack">返回</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "regist",
  data() {
    let validateSPassword = (rule, value, callback) => {
      if (value !== this.registForm.password) {
        callback(new Error('确认密码与密码不一致'))
      } else {
        callback()
      }
    }
    return {
      registForm: {
        username: '',
        password: '',
        spassword: ''
      },
      rules: {
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', transform: value => value, trigger: 'blur'},
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
              } else {
                callback()
              }
            }
          }
        ],
        spassword: [
          {required: true, message: '确认密码不能为空', trigger: 'blur'},
          {validator: validateSPassword, trigger: 'blur'}
        ]
      },
      imgSrc: require('../../assets/bg.png')
    }
  },
  methods: {
    handleRegist() {
      this.$refs.registForm.validate(valid => {
        if (valid) {
          let postData = this.qs.stringify({
            username: this.registForm.username,
            password: this.registForm.password
          });
          this.axios({
            method: 'post',
            url: '/user/regist',
            data: postData
          }).then(response => {
            let status = response.data.status;
            if (status == "success") {
              this.$message.success('注册成功');
              this.$router.push({
                path: "/login"
              });
            } else {
              this.$message.error('用户名已存在');
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
    handleBack() {
      this.$router.push({
        path: "/login"
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
  margin-top: calc(calc(100vh - 350px) / 2);  /** 竖直动态居中 */
  margin-left: calc(calc(100vw - 300px) / 2); /** 水平动态居中 */
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
