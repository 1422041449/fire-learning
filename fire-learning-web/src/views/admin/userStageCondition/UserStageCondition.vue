<template>
  <div class="app-container">
    <el-header>
      <div class="filter-container">
        <el-input
          v-model="listQuery.username"
          clearable
          placeholder="用户名"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.name"
          clearable
          placeholder="昵称"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.realName"
          clearable
          placeholder="姓名"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.stageNum"
          clearable
          placeholder="阶段"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-select v-model="listQuery.ifFinishLearn" placeholder="是否完成学习" clearable style="width: 160px;margin-right: 10px">
          <el-option
            v-for="item in ifFinishEnum"
            :key="item.id"
            :label="item.content"
            :value="item.id">
          </el-option>
        </el-select>
        <el-select v-model="listQuery.ifFinishTest" placeholder="是否完成考试" clearable>
          <el-option
            v-for="item in ifFinishEnum"
            :key="item.id"
            :label="item.content"
            :value="item.id">
          </el-option>
        </el-select>
        <el-button
          v-waves
          type="success"
          style="margin-left: 10px"
          @click="() => getData()"
        >搜索
        </el-button>
        <el-button
          v-waves
          type="primary"
          style="margin-left: 10px"
          @click="add(null, 'create')"
        >新增
        </el-button>
      </div>
    </el-header>
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
          label="阶段号"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.stageNum }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="阶段名称"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.stageName }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="阶段标题"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.stageTitle }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="用户名"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.username }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="昵称"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="姓名"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.realName }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="学习进度"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.progress }}%</span>
          </template>
        </el-table-column>

        <el-table-column
          label="考试成绩"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.score }}</span>
          </template>
        </el-table-column>

      </el-table>
    </div>
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
  const allOptions = ['A', 'B', 'C']
  export default {
    /*初始化执行方法*/
    async created() {
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
        let res = await this.$store.dispatch(
          'userStageCondition/userCondition',
          object
        )
        console.log('查询返回内容--:' + JSON.stringify(res))
        //处理数据.
        this.tableData = res.data
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
        ifFinishEnum:[
          { id: 1, content: '是' },
          { id: 2, content: '否' }
        ]
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

