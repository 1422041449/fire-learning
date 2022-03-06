<template>
  <div class="app-container">
    <el-header>
      <div class="filter-container">
        <el-input
          v-model="listQuery.name"
          clearable
          placeholder="输入昵称"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.realName"
          clearable
          placeholder="输入姓名"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-input
          v-model="listQuery.phone"
          clearable
          placeholder="输入手机号"
          style="width: 160px;margin-right: 10px"
          class="filter-item"
          @keyup.enter.native="() =>  getData()"
        />
        <el-button
          v-waves
          type="success"
          @click=""
          style="margin-left: 10px"
          @click="() => getData()"
        >搜索
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
          label="用户名"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.username }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="密码"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.password }}</span>
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
          label="手机号"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.phone }}</span>
          </template>
        </el-table-column>

        <el-table-column
          label="真实姓名"
          align="center">
          <template slot-scope="{ row }">
            <span>{{ row.realName }}</span>
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
        :model="editdialog.date"
        label-width="100px"
      >
        <el-form-item label="用户名">
          <el-input v-model="editdialog.date.username" disabled/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editdialog.date.password"/>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="editdialog.date.name"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="editdialog.date.phone"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="editdialog.date.realName"/>
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
        'userinfo/listUserInfo',
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
      this.editdialog.date = obj
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
        `userinfo/deleteUserInfo`,
        row.username
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
      this.$refs["form"].validate(async (valid) => {
        if (valid) {
          console.log(this.editdialog.date);
          await this.$store.dispatch(
            `userinfo/updateUserInfo`,
            this.editdialog.date
          );
          this.$message({
            type: "success",
            message: "编辑成功!",
          });
          this.editdialog.dialogFormVisible = false;
          this.getData();
        } else {
          return;
        }
      });
    },



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
        date: {}
      }
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>

