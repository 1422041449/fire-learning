<template>
  <div class="app-container">
    <el-header>
      <div class="filter-container">
        <el-input
          v-model="listQuery.stageName"
          clearable
          placeholder="输入阶段名称"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.stageTitle"
          clearable
          placeholder="输入阶段标题"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-select v-model="listQuery.ifPublish" placeholder="发布状态" clearable>
          <el-option
            v-for="item in ifPublishEnum"
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
        max-height="500"
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
          label="发布状态"
          align="center">
          <template slot-scope="{ row }">
            <span class="state" v-for="item in ifPublishEnum">{{row.ifPublish == item.id ? item.content : ''}}</span>
          </template>
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作栏"
          align="center"
          width="500"
        >
          <template slot-scope="{ row, $index }">
            <el-button
              type="success"
              size="small"
              @click="add(row, 'update')"
            >
              发布
            </el-button>
            <el-button
              type="warning"
              size="small"
              @click="routerStageLearn(row)"
            >
              阶段学习题
            </el-button>
            <el-button
              type="warning"
              size="small"
              @click="add(row, 'update')"
            >
              阶段考试题
            </el-button>
            <el-button
              type="primary"
              size="small"
              @click="add(row, 'update')"
            >
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="del(row, $index)">
              删除
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
        <el-form-item label="阶段号">
          <el-input v-model="editdialog.data.stageNum" disabled placeholder="新增自动生成"/>
        </el-form-item>
        <el-form-item label="阶段名称">
          <el-input v-model="editdialog.data.stageName"/>
        </el-form-item>
        <el-form-item label="阶段标题">
          <el-input v-model="editdialog.data.stageTitle"/>
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
  export default {
    /*初始化执行方法*/
    async created() {
      this.getData()
    },

    /*方法*/
    methods: {
      //查询
      async getData() {
        let object = {}
        let keys = Object.keys(this.listQuery)
        keys.forEach(v => {
          if (this.listQuery[v]) {
            object[v] = this.listQuery[v]
          }
        })
        let res = await this.$store.dispatch(
          'stage/listStageInfo',
          object
        )
        console.log(JSON.stringify(res))
        this.tableData = res.data
      },

      //新增修改控制模态框开启
      async add(row, state) {
        if (this.$refs.form) {
          this.$refs.form.resetFields()
        }

        let obj = Object.assign({}, row)
        this.editdialog.dialogFormVisible = true
        this.editdialog.data = obj
        this.editdialog.dialogStatus = state
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
          `stage/deleteStageInfo`,
          row.stageNum
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
            console.log(this.editdialog.data)
            await this.$store.dispatch(
              `stage/editStageInfo`,
              this.editdialog.data
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
      // 新增
      create() {
        this.$refs['form'].validate(async(valid) => {
          if (valid) {
            console.log(this.editdialog.data)
            await this.$store.dispatch(
              `stage/addStageInfo`,
              this.editdialog.data
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
      //跳转阶段学习页面
      routerStageLearn(row){
        this.$router.push({ path: 'stageLearn', query: { stageInfo: JSON.stringify(row)} })
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
        ifPublishEnum: [
          { id: 1, content: '已发布' },
          { id: 2, content: '未发布' }
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

