<template>
  <div
    style="
      width: 100%;
      min-height: calc(100vh - 84px);
      background-color: #eeeeee;
    "
  >
    <el-container
      style="padding: 20px; border: 1px solid #eee"
      v-loading="loading"
      :element-loading-text="loadingText"
      element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <el-aside
        width="20%"
        class="mokuai card shadow"
        style="min-height: calc(100vh - 180px)"
      >
        <el-tree
          ref="tree"
          :data="routesData"
          :props="defaultProps"
          node-key="treeId"
          default-expand-all
          highlight-current
          :current-node-key="1"
          @node-click="rowClick"
          class="permission-tree"
        />
      </el-aside>
      <!-- //右边的盒子 -->
      <el-main width="78%" style="padding: 0" class="right-box">
        <div style="width: 100%">
          <el-button
            type="primary"
            class="filter-item addNode-button"
            style="margin: 10px"
            @click.prevent="addChildNode"
            v-hasPermi="['system:node:add']"
          >
            添加子节点</el-button
          >
          <el-button
            type="danger"
            class="filter-item"
            style="margin: 10px"
            @click.prevent="deleteNode"
            v-hasPermi="['system:node:remove']"
            >删除节点</el-button
          >
          <el-button
            type="info"
            class="filter-item"
            style="margin: 10px"
            @click.prevent="updateChildNode"
            v-hasPermi="['system:node:update']"
            >修改节点</el-button
          >
        </div>
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
        >
          <el-form-item label="文件ID" prop="fileId">
            <el-input
              v-model="queryParams.fileId"
              placeholder="请输入文件ID"
              clearable
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="文件名称" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名称"
              clearable
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="文件描述" prop="description">
            <el-input
              v-model="queryParams.description"
              placeholder="请输入文件描述"
              clearable
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="日期" prop="dateTime">
            <el-date-picker
              v-model="queryParams.dateTime"
              type="datetime"
              placeholder="选择一个日期"
            />
          </el-form-item> -->
          <el-form-item>
            <el-button
              type="primary"
              icon="Search"
              class="search-button"
              size="small"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="Refresh" size="small" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
        <!-- 操作部分 -->
        <el-row class="mb8" :gutter="15">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="Plus"
              size="small"
              class="addExcel"
              @click="handleAdd"
              v-hasPermi="['system:logininfor:add']"
              >新增</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="Delete"
              size="small"
              @click="handleDelete"
              :disabled="deleteDisabled"
              v-hasPermi="['system:logininfor:remove']"
              >删除</el-button
            >
          </el-col>
        </el-row>
        <!-- 表格部分 -->
        <el-table
          v-loading="tableLoading"
          :data="fileList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="文件ID" align="center" prop="fileId" />
          <el-table-column label="文件名" align="center" prop="fileName" />
          <el-table-column label="描述" align="center" prop="description" />
          <el-table-column
            label="是否公开"
            align="center"
            prop="fileStatus"
            v-hasPermi="['system:file:edit']"
          >
            <template #default="scope">
              <el-switch
                v-model="fileList[scope.$index].fileStatus"
                @change="updateFileStatus(scope.row)"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="文件时间" align="center" prop="dateTime" />
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template #default="scope">
              <el-button
                size="small"
                type="text"
                icon="Download"
                @click="handleDownload(scope.row)"
                >下载
              </el-button>
              <el-button
                size="small"
                type="text"
                icon="Edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:file:edit']"
                >修改</el-button
              >
              <el-button
                size="small"
                type="text"
                icon="Delete"
                @click="deleteFile(scope.row)"
                v-hasPermi="['system:file:remove']"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
          v-show="total > 0"
          :total="total"
          v-model:currentPage="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="getList"
          @current-change="getList"
          :background="false"
        />
      </el-main>
    </el-container>
    <!-- 节点对话框 -->
    <el-dialog
      :title="textMap[dialogTreeStatus]"
      v-model="dialogTreeFormVisible"
      center
      draggable
      width="30%"
    >
      <el-form
        ref="dataTreeForm"
        :model="treeForm"
        :rules="treeRules"
        label-position="left"
        label-width="110px"
      >
        <el-form-item label="节点新名称：" prop="treeName">
          <el-input v-model="treeForm.treeName" placeholder="输入节点新名称" />
        </el-form-item>
        <el-form-item label="是否公开：" prop="isShow">
          <el-switch v-model="treeForm.isShow" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button
            type="primary"
            @click.passive="
              dialogTreeStatus === 'createNode'
                ? createTreeData()
                : updateTreeData()
            "
          >
            保存
          </el-button>
          <el-button @click="dialogTreeFormVisible = false">取消</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog
      :title="textMap[dialogStatus]"
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
      @close="dialogClosed"
      center
      draggable
      width="30%"
    >
      <el-form
        ref="form"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="100px"
      >
        <el-form-item label="文件名称：" prop="fileName">
          <el-input v-model="dataForm.fileName" placeholder="输入文件名称" />
        </el-form-item>
        <el-form-item label="文件描述" prop="description">
          <el-input v-model="dataForm.description" placeholder="输入文件描述" />
        </el-form-item>
        <el-form-item label="日期" prop="dateTime">
          <el-date-picker
            v-model="dataForm.dateTime"
            type="datetime"
            placeholder="选择一个日期"
          />
        </el-form-item>
        <el-form-item label="是否公开" prop="fileStatus">
          <el-switch v-model="dataForm.fileStatus" />
        </el-form-item>
        <el-form-item
          label="上传文件"
          prop="file"
          v-show="dialogStatus === 'create'"
        >
          <el-upload
            v-model:file-list="fileList"
            class="upload-demo"
            ref="upload"
            :limit="1"
            accept=".csv"
            :action="uploadUrl"
            :auto-upload="false"
            :headers="{ Authorization: 'Bearer ' + getToken() }"
            :on-error="uploadFileError"
            :on-success="uploadFileSuccess"
            :on-exceed="handleExceed"
            :on-change="handleUploadFile"
            :before-upload="handleBeforeUpload"
          >
            <el-button type="primary">Click to upload</el-button>
            <template #tip>
              <div class="el-upload__tip">select a file to upload</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="dialogStatus === 'create' ? createData() : updateData()"
            :disabled="isDisabled"
          >
            保存
          </el-button>
          <el-button @click="deleteUploadData()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup name="phenoType">
import { ref, getCurrentInstance, nextTick, onMounted } from "vue";
import { getTree, addNode, updateNode, deleteNodes } from "@/api/tree.js";
import { listFile, updateFile, delFile } from "@/api/infomanage/phenoType";
import useUserStore from "@/store/modules/user";
import { getToken } from "@/utils/auth";
import ShowCSVTable from "./ShowCSVTable.vue";
import { parseTime } from "@/utils/param";
import { getTreeNodeIdsByNode } from "@/utils/tree";
import { ElMessage } from "element-plus";

// vue实例
const {
  proxy: { $modal, $download },
} = getCurrentInstance();
// 登录用户权限
const { roles } = useUserStore();
const isDisabled = ref(false);

// 加载
const loading = ref(false);
const loadingText = ref("加载中...");
const tableLoading = ref(false);

// 对话框
const dialogFormVisible = ref(false);
const dialogStatus = ref("create");
const textMap = {
  create: "添加文件",
  update: "修改文件",
  createNode: "添加节点",
  updateNode: "修改节点",
};

// 表单实例
const form = ref(null);

// 表单参数
const dataForm = reactive({
  fileId: null,
  fileName: "",
  description: "",
  fileStatus: true,
  dateTime: null,
});

//删除按钮状态
const deleteDisabled = ref(false);

// 校验规则
const rules = reactive({
  fileName: [{ required: true, message: "请输入文件名", trigger: "blur" }],
  description: [{ required: true, message: "请输入文件描述", trigger: "blur" }],
  dateTime: [{ required: true, message: "请选择一个日期", trigger: "blur" }],
});

const drawer = ref(false); // 文件详情窗口开启状态
const fileName = ref(""); // 选中文件名
const curFileUrl = ref(""); //文件路径

//表单重置
function resetForm() {
  dataForm.fileId = null;
  dataForm.fileName = "";
  dataForm.description = "";
  dataForm.fileStatus = true;
  dataForm.dateTime = null;
}

// 开启文件详情窗口
async function openDrawer(row) {
  fileName.value = row.fileName;
  curFileUrl.value = row.fileUrl;
  drawer.value = true;
}

// 上传实例
const upload = ref(null);
// 文件上传
const uploadUrl = ref("");

//文件上传前触发
//文件格式验证
const handleBeforeUpload = (file) => {
  // 拿到文件后缀名
  const fileType = file.name.substring(file.name.lastIndexOf(".") + 1);
  const isCsv = fileType === "csv";
  if (!isCsv) {
    /* $modal.msgError(
      "只能上传csv格式的文件！",
      "error",
      "vab-hey-message-error"
    ); */
    ElMessage({
      message: "只能上传csv格式的文件！",
      type: "error",
    });
    return false;
  }
  return isCsv;
};

// 文件创建
function createData() {
  form.value.validate((valid) => {
    if (valid) {
      uploadUrl.value = `${
        import.meta.env.VITE_APP_UPLOAD_URL
      }/system/file/upload?fileName=${dataForm.fileName}&description=${
        dataForm.description
      }&fileStatus=${dataForm.fileStatus ? 1 : 0}&treeId=${
        tree.value.getCurrentNode().treeId
      }&dateTime=${parseTime(dataForm.dateTime)}`;
      nextTick(async () => {
        tableLoading.value = false;
        await upload.value.submit();
        isDisabled.value = true;
        getList();
      });
    }
  });
  dialogFormVisible.value = false;
  getList();
  setTimeout(() => {
    getList();
  }, 1000);
}

//取消文件对话框
function deleteUploadData() {
  dialogFormVisible.value = false;
  isDisabled.value = false;
  getList();
}

//关闭添加文件窗口
const dialogClosed = () => {
  getList();
};

// 文件上传成功回调
async function uploadFileSuccess() {
  /* $modal.msgSuccess("上传成功"); */
  ElMessage({
    message: "上传成功",
    type: "success",
  });

  isDisabled.value = false;
  const curNode = tree.value.getCurrentNode();

  getList();
  rowClick(curNode);
  dialogFormVisible.value = false;
}

// 文件上传失败回调
function uploadFileError() {
  $modal.msgError("上传失败");
}

// 文件替换
function handleExceed(files) {
  upload.value?.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  upload.value?.handleStart(file);
}

// 文件修改
async function updateData() {
  form.value.validate((valid) => {
    if (valid) {
      tableLoading.value = true;
      updateFile({ ...dataForm, dateTime: parseTime(dataForm.dateTime) })
        .then((res) => {
          tableLoading.value = false;
          dialogFormVisible.value = false;
          getList();
          /* ElMessage({
            message: "修改成功",
            type: "success",
          }); */
          ElMessage({
            message: "导入失败，系统故障，请稍后再试",
            type: "error",
          });
        })
        .catch((err) => {
          loading.value = false;
          $modal.msgError("修改失败");
        });
    }
  });
}

// 文件表格
const fileList = ref([]); // 文件列表
const ids = ref([]); // 选中数组
const multiple = ref(false); // 是否多选

// 打开添加文件对话框
function handleAdd() {
  dialogStatus.value = "create";
  fileList.value = [];
  resetForm();
  dialogFormVisible.value = true;
  isDisabled.value = false;
}

// 删除文件
function handleDelete() {
  if (ids.value.length == 0) {
    $modal.msg("您没有选择文件！");
  } else {
    $modal.confirm("是否删除文件?").then(() => {
      delFile(ids.value)
        .then((res) => {
          getList();
        })
        .catch((err) => {
          $modal.msgError("删除失败");
        });
    });
  }
}

// 请求文件列表
function getList() {
  tableLoading.value = true;
  listFile({
    ...queryParams,
    treeId: tree.value.getCurrentNode().treeId,
    fileStatus: roles[0] === "admin" ? null : 1,
  })
    .then((res) => {
      tableLoading.value = false;
      fileList.value = res.rows.map((item) => ({
        ...item,
        fileStatus: item.fileStatus === 1,
      }));
      total.value = res.total;
    })
    .catch((err) => {
      tableLoading.value = false;
      $modal.msgError("获取列表失败");
    });
}

// 选择文件项
function handleSelectionChange(selection) {
  ids.value = [];
  selection.forEach((item) => {
    ids.value.push(item.fileId);
  });
}

// 更新是否公开选项
async function updateFileStatus(row) {
  updateFile({
    fileId: row.fileId,
    fileStatus: row.fileStatus,
    fileName: row.fileName,
    description: row.description,
    dateTime: row.dateTime,
  })
    .then((res) => {
      $modal.msgSuccess("更新成功");
    })
    .catch((err) => {
      $modal.msgError("更新失败");
    });
}

// 下载文件
function handleDownload(row) {
  $download.resource(row.fileUrl);
}

// 修改文件
function handleUpdate(row) {
  dataForm.fileId = row.fileId;
  dataForm.fileName = row.fileName;
  dataForm.description = row.description;
  dataForm.fileStatus = row.fileStatus;
  dataForm.dateTime = row.dateTime;
  dialogFormVisible.value = true;
  dialogStatus.value = "update";
}

// 删除文件
function deleteFile(row) {
  $modal.confirm("是否删除文件?").then(() => {
    delFile([row.fileId])
      .then((res) => {
        getList();
      })
      .catch((err) => {
        $modal.msgError("删除失败");
      });
  });
}

// 表单提交
const total = ref(2);
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  treeId: "",
  fileId: "",
  fileName: "",
  description: "",
  fileStatus: "",
  dateTime: "",
});
const showSearch = ref(true);
const queryForm = ref(null); // 查询表单dom元素
const handleQuery = async () => {
  // 查询回调
  queryParams.treeId = tree.value.getCurrentNode().treeId;
  getList();
};

const resetQuery = () => {
  queryParams.pageNum = 1;
  queryParams.pageSize = 10;
  queryParams.treeId = 0;
  queryParams.fileId = "";
  queryParams.fileName = "";
  queryParams.description = "";
  queryParams.fileStatus = true;
};

// 树控件
const routesData = ref([]);

// 树表单
const treeForm = reactive({
  treeName: "",
  isShow: true,
});

const dialogTreeFormVisible = ref(false); // 树表单可见
const dataTreeForm = ref(null); // 树表单dom实例
const dialogTreeStatus = ref("createNode");
//树表单验证规则
const treeRules = reactive({
  treeName: [
    { required: true, message: "Please input Activity name", trigger: "blur" },
  ],
  isShow: [{ required: true, message: "Please select", trigger: "blur" }],
});

// 树组件节点属性
const defaultProps = ref({
  children: "children",
  label: "treeName",
});

const treeType = ref(3); // 树的种类
const tree = ref(null); // 数的dom实例

// 获取整棵树
const getTreeList = () => {
  getTree(treeType.value, 0, 1).then((res) => {
    routesData.value = res.data.children;
    console.log(routesData.value, "9090");
    nextTick(() => {
      if (!tree.value.getCurrentNode())
        tree.value.setCurrentNode(routesData.value[0]);
      rowClick(tree.value.getCurrentNode());
    });
  });
};

// 重置树表单
function resetTreeForm() {
  treeForm.treeName = "";
  treeForm.isShow = true;
}

// 添加节点
function addChildNode() {
  if (!tree.value.getCurrentNode() && routesData.value.length !== 0) {
    $modal.msgWarning("请选择所要添加节点的父节点");
    return;
  }
  resetTreeForm();
  dialogTreeStatus.value = "createNode";
  dialogTreeFormVisible.value = true;
}

// 修改节点
function updateChildNode() {
  if (!tree.value.getCurrentNode()) {
    $modal.msgWarning("请选择所要修改节点的父节点");
    return;
  }
  dialogTreeStatus.value = "updateNode";
  dialogTreeFormVisible.value = true;
}

// 创建树节点
function createTreeData() {
  dataTreeForm.value.validate((valid) => {
    if (valid) {
      const id = tree.value.getCurrentNode()
        ? tree.value.getCurrentNode().treeId
        : 0;
      addNode({
        isShow: treeForm.isShow ? 1 : 0,
        treeName: treeForm.treeName,
        parentId: id,
        treeType: treeType.value,
      }).then(
        () => {
          $modal.msgSuccess("添加节点成功");
          getTreeList();
        },
        () => {
          $modal.msgError("添加节点失败");
        }
      );
      dialogTreeFormVisible.value = false;
    }
  });
}

// 更新树节点
function updateTreeData() {
  dataTreeForm.value.validate((valid) => {
    if (valid) {
      updateNode({
        isShow: treeForm.isShow ? 1 : 0,
        treeName: treeForm.treeName,
        treeId: tree.value.getCurrentNode().treeId,
      }).then(
        () => {
          $modal.msgSuccess("修改成功");
          getTreeList();
        },
        () => {
          $modal.msgError("修改失败");
        }
      );
      dialogTreeFormVisible.value = false;
    }
  });
}

//删除节点
function deleteNode() {
  if (!tree.value.getCurrentNode()) {
    $modal.msgWarning("请选择节点");
    return;
  }
  $modal.confirm("是否删除该节点").then(() => {
    const curNode = tree.value.getCurrentNode();
    const curNodeTreeIds = getTreeNodeIdsByNode(curNode);
    deleteNodes(curNodeTreeIds).then(() => {
      $modal.msgSuccess("删除节点成功");
      getTreeList();
    });
  });
}

//树控件点击回调
function rowClick(nodeObj) {
  treeForm.treeName = nodeObj.treeName;
  if (nodeObj.isShow == 1) {
    treeForm.isShow == true;
  } else {
    treeForm.isShow == false;
  }

  queryParams.treeId = nodeObj.treeId;
  getList();
}

onMounted(() => {
  getTreeList();
});
</script>

<style lang="less" scoped>
:deep(.el-tree-node__label) {
  font-size: 16px;
}

:deep(.el-tree) {
  background-color: #fff;
}

:deep(
    .el-tree--highlight-current
      .el-tree-node.is-current
      > .el-tree-node__content
  ) {
  background-color: #f4f4f4 !important;
}

.card {
  background-color: #fff;
  box-shadow: 0 2px 3px 0 rgba(0, 0, 0, 0.2);
  margin-left: auto;
  margin-right: auto;
  box-sizing: border-box;
}

.u-main .el-tag + .el-tag {
  margin-left: 10px;
}

.u-main .button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.u-main .input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.u-main .el-form-item {
  margin: 60px 0;
}

.u-main .el-form-item__label {
  font-size: 20px;
}

/* 新增节点对话框 */
:deep(.el-dialog__header) {
  margin-right: 0px;
  padding-right: 16px;
  background: #fff;
  margin-top: 10px;

  .el-dialog__title {
    color: white;
  }
}

:deep(.dialog-footer) {
  .el-button--primary {
    background: #fff;
  }
}
</style>
<style lang="scss" scoped>
.image_box {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
  max-height: calc(100vh - 180px);
}

.image_item {
  box-sizing: border-box;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 10px;
  width: 25%;
}

.right-box {
  margin-left: 20px;
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
  text-align: center;
  line-height: 50px;
  font-size: 20px;
}

.bg-purple-dark {
  background: #99a9bf;
}

.bg-purple {
  background: #f5f5f5;
}

.bg-purple-light {
  background: rgb(32, 177, 159);
}

.grid-content {
  border-radius: 0px;
  height: 50px;
}

.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.upload {
  width: 100%;
}

.mokuai {
  margin-bottom: 0;
  background-color: #fff;
}

.right-box {
  margin-left: 20px;
}

.u-title {
  width: 98%;
  margin: 0 auto;
  height: 50px;
}

.u-title ul {
  display: flex;
}

.u-title ul li {
  flex: 1;
  text-align: center;
}

.app-container {
  .roles-table {
    margin-top: 30px;
  }

  .permission-tree {
    margin-bottom: 30px;
  }
}
.addNode-button,
.search-button {
  background: rgb(85, 123, 116);
}

.addExcel {
  background: grey;
  color: #fff;
}

.shadow {
  box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.14);
}
</style>