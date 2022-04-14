<template>
  <div class="app-container">
    <div class="table-box">
      <el-table
        :data="tableData"
        style="width: 100%"
        stripe
        border>
        <el-table-column
          type="index"
          width="50"
          label="序号"
          align="center"
        >
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          label="题目"
          align="left">
          <template slot-scope="{ row }">
            <span style="font-weight: bold">{{ row.exercisesTitle }}</span><br>
            <template v-if="row.exercisesType == 1">
              <el-radio-group v-model="row.userRadioAnswer">
                <el-radio v-for="option in row.optionsList" :label="option.optionNum" :key="option.optionNum">
                  {{option.optionNum}}.{{option.optionContent}}
                </el-radio>
              </el-radio-group>
            </template>
            <template v-if="row.exercisesType == 2">
              <el-checkbox-group v-model="row.userMultiAnswer">
                <el-checkbox v-for="option in row.optionsList" :label="option.optionNum" :key="option.optionNum">
                  {{option.optionNum}}.{{option.optionContent}}
                </el-checkbox>
              </el-checkbox-group>
            </template>
          </template>
        </el-table-column>
        <el-table-column
          label="正确答案"
          width="200"
          align="center">
          <template slot-scope="{ row }">
            <span v-if="row.ifAnswer == 1 && row.ifAnswerRight == 2" style="color: red">{{ row.rightAnswer }}</span>
            <span v-else-if="row.ifAnswer == 1 && row.ifAnswerRight == 1">{{ row.rightAnswer }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-footer style="margin-top: 30px">
      <div class="filter-container" align="center">
        <el-button type="primary"
                   @click="commitTest(tableData)"
        >交卷
        </el-button>
      </div>
    </el-footer>
    <!--模态框-->
    <el-dialog
      :title="editdialog.title"
      :visible.sync="editdialog.dialogFormVisible"
      :show-close="true"
      :close-on-click-modal="false"
    >
      <el-form
        ref="form"
        :model="editdialog.data"
        label-width="100px"
      >
        <el-form-item label="题目编号">
          <el-input v-model="editdialog.data.exercisesNum" disabled placeholder="新增自动生成"/>
        </el-form-item>
        <el-form-item label="题目">
          <el-input v-model="editdialog.data.exercisesTitle"/>
        </el-form-item>
        <el-form-item label="题目类型">
          <template>
            <el-radio v-model="editdialog.data.exercisesType" label="1">单选</el-radio>
            <el-radio v-model="editdialog.data.exercisesType" label="2">多选</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="选项A">
          <el-input v-model="editdialog.data.anasA"/>
        </el-form-item>
        <el-form-item label="选项B">
          <el-input v-model="editdialog.data.anasB"/>
        </el-form-item>
        <el-form-item label="选项C">
          <el-input v-model="editdialog.data.anasC"/>
        </el-form-item>
        <el-form-item label="正确答案">
          <template>
            <el-checkbox-group
              v-model="rightList">
              <el-checkbox v-for="option in options" :label="option" :key="option">{{option}}</el-checkbox>
            </el-checkbox-group>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editdialog.dialogFormVisible = false">取消
        </el-button>
        <el-button
          type="primary"
          @click="editdialog.dialogStatus === 'create' ? create() : update()"
        >确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getUsername } from '../../../utils/auth'

  const allOptions = ['A', 'B', 'C']
  export default {
    /*初始化执行方法*/
    async created() {
      let stageInfo = this.$route.query.stageInfo
      console.log('路由携带数据：', stageInfo)
      this.stageInfo = JSON.parse(stageInfo)
      this.username = getUsername()
      this.getData()
    },

    /*方法*/
    methods: {
      //获取用户信息
      async getData() {
        let object = {}
        let keys = Object.keys(this.listQuery)
        keys.forEach(v => {
          if (this.listQuery[v]) {
            object[v] = this.listQuery[v]
          }
        })
        object.username = this.username
        object.stageNum = this.stageInfo.stageNum
        let res = await this.$store.dispatch(
          'userStageTest/listTestCurrentTest',
          object
        )
        console.log('查询返回内容--:' + JSON.stringify(res))
        //处理数据.
        let data = res.data
        for (let i in data) {
          if (!data[i].userMultiAnswer) {
            data[i].userMultiAnswer = []
          }
        }
        this.tableData = data
      },

      //新增修改控制模态框开启
      async add(row, state) {
        console.log('编辑行数据--：{}', row)
        if (this.$refs.form) {
          this.$refs.form.resetFields()
        }
        let obj = Object.assign({}, row)
        let rightList = []
        for (let i in obj.optionsList) {
          let option = obj.optionsList[i]
          if (option.optionNum == 'A') {
            obj.anasA = option.optionContent
            if (option.ifRight == 1) {
              rightList.push(option.optionNum)
            }
          } else if (option.optionNum == 'B') {
            obj.anasB = option.optionContent
            if (option.ifRight == 1) {
              rightList.push(option.optionNum)
            }
          } else if (option.optionNum == 'C') {
            obj.anasC = option.optionContent
            if (option.ifRight == 1) {
              rightList.push(option.optionNum)
            }
          }
        }
        this.rightList = rightList
        console.log('编辑组装后数据---：{}', obj)

        this.editdialog.data = obj
        this.editdialog.dialogStatus = state
        this.editdialog.dialogFormVisible = true

      },

      //删除
      async del(row, index) {
        console.log(this)
        await this.$confirm('是否确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        console.log(row)
        let res = await this.$store.dispatch(
          `exercise/deleteExercisesInfo`,
          row.exercisesNum
        )
        this.tableData.splice(index, 1)
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
        this.getData()
      },

      // 更新
      update() {
        this.$refs['form'].validate(async(valid) => {
          if (valid) {
            console.log('调用修改接口入参--：{}', this.editdialog.data)
            let object = this.editdialog.data
            for (let i in object.optionsList) {
              let option = object.optionsList[i]
              if (option.optionNum == 'A') {
                option.optionContent = this.editdialog.data.anasA
                if (this.contains(this.rightList, 'A')) {
                  option.ifRight = 1
                } else {
                  option.ifRight = 2
                }
              } else if (option.optionNum == 'B') {
                option.optionContent = this.editdialog.data.anasB
                if (this.contains(this.rightList, 'B')) {
                  option.ifRight = 1
                } else {
                  option.ifRight = 2
                }
              } else if (option.optionNum == 'C') {
                option.optionContent = this.editdialog.data.anasC
                if (this.contains(this.rightList, 'C')) {
                  option.ifRight = 1
                } else {
                  option.ifRight = 2
                }
              }
            }
            if (this.rightList.length == 0) {
              this.errorMsg('正确答案不能为空!')
              return
            }
            //校验单选选修
            if (object.exercisesType == '1' && this.rightList.length > 1) {
              this.errorMsg('单选只可选择一个答案!')
              return
            }
            console.log('调用修改接口入参--：{}', object)
            await this.$store.dispatch(
              `exercise/editExercisesInfo`,
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
      //新增
      create() {
        this.$refs['form'].validate(async(valid) => {
          if (valid) {
            console.log('编辑框内容：' + JSON.stringify(this.editdialog.data))
            console.log('编辑框内容：' + this.rightList)
            let object = {}
            object.exercisesTitle = this.editdialog.data.exercisesTitle
            object.exercisesType = this.editdialog.data.exercisesType
            let optionsList = []
            let anasA = {}
            anasA.optionNum = 'A'
            anasA.optionContent = this.editdialog.data.anasA
            let anasB = {}
            anasB.optionNum = 'B'
            anasB.optionContent = this.editdialog.data.anasB
            let anasC = {}
            anasC.optionNum = 'C'
            anasC.optionContent = this.editdialog.data.anasC
            for (let i in this.rightList) {
              let value = this.rightList[i]
              if (value === 'A') {
                anasA.ifRight = 1
              } else if (value === 'B') {
                anasB.ifRight = 1
              } else if (value === 'C') {
                anasC.ifRight = 1
              }
            }
            optionsList.push(anasA)
            optionsList.push(anasB)
            optionsList.push(anasC)
            object.optionsList = optionsList

            if (this.rightList.length == 0) {
              this.errorMsg('正确答案不能为空!')
              return
            }
            //校验单选选修
            if (object.exercisesType == '1' && this.rightList.length > 1) {
              this.errorMsg('单选只可选择一个答案!')
              return
            }

            console.log('入参---：', JSON.stringify(object))
            await this.$store.dispatch(
              `exercise/addExercisesInfo`,
              object
            )
            this.$message({
              type: 'success',
              message: '新增成功!'
            })
            this.editdialog.dialogFormVisible = false
            this.getData()
          } else {
            return
          }
        })
      },
      //提交答案
      async commitTest(data) {
        console.log('提交内容:{}', data)
        //确认提交
        await this.$confirm('是否交卷?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        let object = data
        for (let i in data) {
          let obj = data[i]
          //给用户答案赋值
          if (obj.exercisesType == 1) {
            if (!obj.userRadioAnswer) {
              this.errorMsg('存在题目未完成，无法交卷!')
              return
            }
            obj.userAnswer = obj.userRadioAnswer
          } else {
            let userAnswer = ''
            if (obj.userMultiAnswer.length == 0) {
              this.errorMsg('存在题目未完成，无法交卷!')
              return
            }
            for (let i in obj.userMultiAnswer) {
              if (i == obj.userMultiAnswer.length - 1) {
                userAnswer += obj.userMultiAnswer[i]
              } else {
                userAnswer += obj.userMultiAnswer[i] + '-'
              }
            }
            obj.userAnswer = userAnswer
          }
        }
        console.log('提交答案--入参：', object)
        await this.$store.dispatch(
          `userStageTest/commitTestAnswer`,
          object
        )
        this.$message({
          type: 'success',
          message: '提交成功!'
        })
        //更改完成跳转路由到阶段学校
        this.$router.push({ path: 'userStageTest', query: {} })
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
      /**
       * 校验集合是否包含这个值
       * */
      contains(array, value) {
        let res = false
        for (let i in array) {
          if (array[i] == value) {
            return true
          }
        }
        return res
      }

    },

    /*数据*/
    data() {
      return {
        listQuery: {},
        tableData: [],
        editdialog: {
          dialogFormVisible: false,
          dialogStatus: '',
          title: '详细信息',
          data: {}
        },
        rightList: [],
        options: allOptions,
        exercisesTypeEnum: [
          { id: 1, content: '单选' },
          { id: 2, content: '多选' }
        ],
        username: '',
        stageInfo: {},
        radio: '',
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }

  /deep/ .el-radio {
    display: block;
    line-height: 25px;
    white-space: normal;
    margin-right: 0;
  }

  /deep/ .el-checkbox {
    display: block;
    line-height: 25px;
    white-space: normal;
    margin-right: 0;
  }
</style>

