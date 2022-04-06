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
          label="完成进度"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.progress }}%</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作栏"
          align="center"
          width="220"
        >
          <template slot-scope="{ row, $index }">
            <el-button
              type="primary"
              size="small"
              @click="add(row, 'update')"
            >
              学习
            </el-button>
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
        <el-form-item label="题目">
          <el-select v-model="editdialog.data.exercisesNum" placeholder="选择题目" filterable>
            <el-option
              v-for="item in allExercises"
              :key="item.exercisesNum"
              :label="item.exercisesTypeName+'_'+item.exercisesTitle"
              :value="item.exercisesNum">
            </el-option>
          </el-select>
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
      this.username = getUsername();
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
        console.log("查询--入参：{}",object)
        let res = await this.$store.dispatch(
          'userStageLearn/listStageLearnInfo',
          object
        )
        console.log('查询返回内容--:' + JSON.stringify(res))
        //处理数据.
        this.tableData = res.data
      },

      //新增修改控制模态框开启
      async add(row, state) {
        console.log('编辑行数据--：{}', row)
        this.editdialog.data = Object.assign({}, row)
        //获取当前阶段未被选择的题目信息
        let res = await this.$store.dispatch(
          'exercise/listStageLearnExercises',
          this.stageInfo.stageNum
        )
        this.allExercises = res.data
        for (let i in this.allExercises) {
          let obj = this.allExercises[i]
          if (obj.exercisesType == 1) {
            this.allExercises[i].exercisesTypeName = '单选'
          } else if (obj.exercisesType == 2) {
            this.allExercises[i].exercisesTypeName = '多选'
          }
        }
        console.log('所有题目数据：', this.allExercises)

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
        let object = Object.assign({}, row)
        object.stageNum = this.stageInfo.stageNum
        console.log("删除---入参：",object)
        let res = await this.$store.dispatch(
          `stageLearn/deleteStageLearn`,
          object
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

      //新增
      async create() {
        this.$refs['form'].validate(async(valid) => {
          if (valid) {
            console.log('编辑框内容：' + JSON.stringify(this.editdialog.data))
            console.log('编辑框内容：' + this.rightList)
            let object = {}
            object.stageNum = this.stageInfo.stageNum
            object.exercisesNum = this.editdialog.data.exercisesNum
            console.log('入参---：', JSON.stringify(object))
            await this.$store.dispatch(
              `stageLearn/addStageLearn`,
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
          data: {
            exercises: ''
          }
        },
        rightList: [],
        options: allOptions,
        exercisesTypeEnum: [
          { id: 1, content: '单选' },
          { id: 2, content: '多选' }
        ],
        stageInfo: {},
        allExercises: [],
        username:''
      }
    }

  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

