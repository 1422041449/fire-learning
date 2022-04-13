<template>
  <div class="app-container">
    <el-form :model="tableData" label-width="120px" ref="form">
      <el-form-item label="手机号" :label-width="formLabelWidth" >
        <el-input v-model="tableData.phone" autocomplete="off" style="width: 300px" disabled></el-input>
      </el-form-item>
      <el-form-item label="密码" :label-width="formLabelWidth">
        <el-input v-model="tableData.password" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="昵称" :label-width="formLabelWidth">
        <el-input v-model="tableData.name" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="真实姓名" :label-width="formLabelWidth">
        <el-input v-model="tableData.realName" autocomplete="off" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="头像" :label-width="formLabelWidth" >
        <el-upload
          action="upload"
          class="avatar-uploader"
          accept=".png, .jpg, .jpeg"
          :show-file-list="false"
          :before-upload="beforeAvatarUpload"
          :http-request="uploadAvatar">
          <img v-if="imageUrl" :src="imageUrl" class="avatar" style="width: 300px;height: 300px">
          <i v-else class="el-icon-plus avatar-uploader-icon" ></i>
        </el-upload>
      </el-form-item>
      <el-button type="primary" @click="editPersonalInfo()" style="margin-left: 200px">修 改</el-button>
    </el-form>
  </div>
</template>

<script>
  import { getUsername, removeToken } from '../../../utils/auth'

  const allOptions = ['A', 'B', 'C']
  export default {
    /*初始化执行方法*/
    async created() {
      this.username = getUsername()
      this.getData()
    },

    /*方法*/
    methods: {
      //获取用户信息
      async getData() {
        let object = {}
        console.log('查询--入参：{}', object)
        let res = await this.$store.dispatch(
          'userinfo/getInfo',
          object
        )
        console.log('查询返回内容--:' + JSON.stringify(res))
        //处理数据.
        this.tableData = res
        this.imageUrl = this.tableData.avatar
        console.log("tableData数据",this.tableData)
      },

      // 更新
      update() {
        this.$refs['form'].validate(async(valid) => {
          if (valid) {
            console.log('调用修改接口入参--：{}', this.editdialog.data)
            let object = this.editdialog.data
            object.stageNum = this.stageInfo.stageNum
            console.log('调用修改接口入参--：{}', object)
            await this.$store.dispatch(
              `stageLearn/editStageLearn`,
              object
            )
            this.$message({
              type: 'success',
              message: '编辑成功!'
            })
            this.editdialog.dialogFormVisible = false
            this.getData()
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
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isLt2M
      },

      //修改个人资料
      async editPersonalInfo() {
        //确认修改
        await this.$confirm('是否确认修改个人信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        this.$refs['form'].validate(async(valid) => {
          console.log(valid)
          let obj = this.tableData
          obj.avatar = this.imageUrl
          console.log('请求入参:', obj)
          if (valid) {
            await this.$store.dispatch(`userinfo/updateUserInfo`, obj)
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            //返回登录界面
            removeToken() // must remove  token  first
            // resetRouter()
            this.$router.push(`/login`)
          } else {
            return
          }
        })
      },

      /**
       * 错误弹框
       * */
      errorMsg(msg) {
        this.$alert(msg, '错误', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${action}`
            })
          }
        })
      },

    },

    /*数据*/
    data() {
      return {
        tableData: {},
        formLabelWidth: '120px',
        imageUrl: '',
      }
    }

  }
</script>

<style scoped>
  .line {
    text-align: center;
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
