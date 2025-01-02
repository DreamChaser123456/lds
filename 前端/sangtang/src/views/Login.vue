<template>
  <div class="qq-login-page">
    <div class="qq-login-container">
      <h3 class="login-title">欢迎登录</h3>

      <!-- 头像 -->
      <img src="@/assets/wallhaven-x6k87v.png" alt="头像" class="avatar" />
      
      <!-- 表单 -->
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="0px" class="qq-login-box">
        <div>
          <div style="display: flex;align-items: center !important;height: 30px;">
            <h3 style="font-size: 18px;color: #333;">账号：</h3>
            <el-form-item  prop="username">
              <el-input type="text" placeholder="请输入账号" v-model="form.username" class="qq-login-input" />
            </el-form-item>
          </div>
          <div style="display: flex;align-items: center !important;">
            <h3 style="font-size: 18px;color: #333;">密码：</h3>
            <el-form-item  prop="password">
              <el-input type="password" placeholder="请输入密码" v-model="form.password" class="qq-login-input" />
            </el-form-item>
          </div>
        </div>
        
        <el-form-item>
          <div class="checkbox-container">
            <el-checkbox v-model="form.agreement">
              已阅读并同意
              <a href="#" class="link">商汤智能识别系统服务协议</a>
            </el-checkbox>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            class="qq-login-button"
            @click="submitForm('loginForm')"
            :disabled="!form.agreement"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      form: {
        username: "",
        password: "",
        agreement: false, // 用户协议状态
      },
      rules: {
        username: [{ required: true, message: "账号不可为空", trigger: "blur" }],
        password: [{ required: true, message: "密码不可为空", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      // this.$router.push("/main");
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios 
            .get("http://localhost:8077/login", {
              params: {
                msgId:"257",
                username: this.form.username,
                password: this.form.password,
              },
            })
            .then((res) => {
              console.log(res);
              if (res.data.code !== 200) {
                this.$message.error("用户名或密码错误");
                return;
              }
              let sessionId = res.data.data;
              this.$store.dispatch("asyncUpdateUser", {sessionId:sessionId});
              this.$store.dispatch("asyncUpdateLogin", {loginId:1});
              console.log('this.$store.state=', this.$store.state);
              localStorage.setItem("token", 1);
              this.$router.push("/main");
            });
        } else {
          this.$message.error("请输入账号和密码");
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
/* 背景 */
.qq-login-page {
  background: linear-gradient(to bottom, #f0f9ff, #c1e9ff);
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Arial", sans-serif;
}
.login-title {
  font-size: 30px;
  font-weight: bold;
  color: #333;
  margin-bottom: 30px;
}
.el-form-item__label{
  display: flex;
}
/* 登录框容器 */
.qq-login-container {
  width: 400px;
  background: #ffffff;
  padding: 30px 20px;
  border-radius: 16px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
}

/* 头像 */
.avatar {
  width: 120px;
  height: 120px;
  margin: 0 auto 20px;
  border-radius: 50%;
  display: block;
  object-fit: cover;
}

/* 输入框 */
.qq-login-box {
  margin-top: 30px;
}
.qq-login-input {
  // align-items: center !important;
  
  width:300px;
  border-radius: 25px;
  margin-top:22px;
  height: 40px;
  // padding: 0 15px;
}

/* 协议样式 */
.checkbox-container {
  margin-left:50px;
  font-size: 12px;
  color: #666;
  text-align: left;
  margin-top: -10px;
}

.link {
  color: #409eff;
  text-decoration: none;
  margin-left: 2px;
}

.link:hover {
  text-decoration: underline;
}

/* 登录按钮 */
.qq-login-button {
  width: 80%;
  height: 40px;
  border-radius: 25px;
  background-color: #409eff;
  border: none;
  color: white;
  font-size: 16px;
  font-weight: bold;
  transition: all 0.3s ease;
}

.qq-login-button:disabled {
  background-color: #a6cfff;
  cursor: not-allowed;
}


.footer-link {
  font-size: 12px;
  color: #409eff;
  margin: 0 10px;
  text-decoration: none;
}

.footer-link:hover {
  text-decoration: underline;
}
</style>