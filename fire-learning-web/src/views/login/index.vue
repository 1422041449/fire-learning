<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">登录页面</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="手机号"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin">登录
      </el-button>

      <el-button :loading="loading" type="info" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="add()">注册
      </el-button>
    </el-form>

    <el-dialog :title="editdialog.title" :visible.sync="editdialog.dialogFormVisible" :show-close="true"
               :close-on-click-modal="false" width="800px" id="dialog-input">
      <el-form :model="editdialog.data" label-width="120px" ref="form">
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="editdialog.data.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="editdialog.data.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="editdialog.data.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" :label-width="formLabelWidth">
          <el-input v-model="editdialog.data.realName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像" :label-width="formLabelWidth">
          <el-upload
            action="upload"
            class="avatar-uploader"
            accept=".png, .jpg, .jpeg"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :http-request="uploadAvatar">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editdialog.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="register">注 册</el-button>
        <!--      <el-button type="primary" @click="editdialog.dialogStatus === 'create' ? create() : update()">确 定</el-button>-->
      </div>
    </el-dialog>
  </div>


</template>

<script>

export default {
  name: 'Login',
  data() {
    // const validateUsername = (rule, value, callback) => {
    //   if (!validUsername(value)) {
    //     callback(new Error('Please enter the correct user name'))
    //   } else {
    //     callback()
    //   }
    // }
    // const validatePassword = (rule, value, callback) => {
    //   if (value.length < 6) {
    //     callback(new Error('The password can not be less than 6 digits'))
    //   } else {
    //     callback()
    //   }
    // }
    return {
      imageUrl: '',
      editdialog: {
        dialogFormVisible: false,
        dialogStatus: '',
        title: '详细信息',
        data: {}
      },
      loginForm: {
        username: '',
        password: ''
      },
      // loginRules: {
      //   username: [{ required: true, trigger: 'blur', validator: validateUsername }],
      //   password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      // },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      formLabelWidth: '120px'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('userinfo/login', this.loginForm).then(() => {
            //用户信息存储本地缓存
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('提交错误!!')
          return false
        }
      })
    },
    // 开启模态框
    add(row, state) {
      this.editdialog.dialogFormVisible = true
      this.editdialog.data = Object.assign({}, row)
      this.editdialog.dialogStatus = state
    },
    //注册
    async register() {
      this.$refs['form'].validate(async(valid) => {
        console.log(valid)
        let obj = this.editdialog.data
        obj.avatar = this.imageUrl
        console.log('请求入参:', obj)
        if (valid) {
          await this.$store.dispatch(`userinfo/register`, obj)
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.editdialog.dialogFormVisible = false
        } else {
          return
        }
      })
    },
    //上传头像
    async uploadAvatar(param) {
      const file = param.file
      //通过表单提交，创建formData
      let formData = new FormData()
      formData.append('file', file)
      formData.append('uploadFlag', 1)
      formData.append('content', 'avatar')
      let res = await this.$store.dispatch('upload/upload', formData)
      console.log('通过调取接口拿到的头像地址返回：', res)
      if (res.code == 10000) {
        //赋值返回的图片地址
        this.imageUrl = res.data
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
      } else {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      }
    },
    // async handleAvatarSuccess(res, file) {
    //   console.log('通过钩子拿到的头像地址返回：', res)
    //   if (res.code == 10000) {
    //     //赋值返回的图片地址
    //     this.imageUrl = response.data
    //     this.$message({
    //       type: 'success',
    //       message: '上传成功!'
    //     })
    //   } else {
    //     this.$message({
    //       type: 'error',
    //       message: '上传失败!'
    //     })
    //   }
    // },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;


@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }

  .el-button {
    margin-left: 0px;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}

</style>

<!--注册弹框-->
<style lang="scss">
#dialog-input {
  div.el-form-item {
    background-color: white;
  }

  div.el-input {
    border-radius: 4px;
    border: 1px solid #dcdfe6;
  }

  input.el-input__inner {
    -webkit-appearance: none;
    background-color: #fff;
    background-image: none;
    border-radius: 4px;
    //border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    font-size: inherit;
    height: 45px;
    line-height: 40px;
    outline: none;
    padding: 0 15px;
    transition: border-color .2s cubic-bezier(.645, .045, .355, 1);
    width: 100%;
    caret-color: black;
  }

  button {
    margin-left: 30px;
    margin-right: 20px;
  }
}
</style>

<!--上传照片-->
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
