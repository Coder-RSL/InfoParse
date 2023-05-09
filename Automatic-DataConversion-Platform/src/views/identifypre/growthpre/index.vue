<template>
  <div style="width:100%;min-height: calc(100vh - 84px);background-color:#EEEEEE">
    <el-container style="padding:20px;border: 1px solid #eee" v-loading="loading" :element-loading-text="loadingText"
      element-loading-background="rgba(0, 0, 0, 0.8)">
      <!--树-->
      <el-aside width="20%" class="mokuai card shadow" style="min-height: calc(100vh - 180px)">
        <el-tree ref="tree" :data="routesData" :props="defaultProps" node-key="treeId" default-expand-all
          highlight-current :current-node-key="1" @node-click="rowClick" class="permission-tree" />
      </el-aside>
      <!-- //右边的盒子 -->
      <el-main width="78%" style="padding:0" class="right-box">
        <ShowCSVTable :url="curFileUrl" max-custom-h="200px" @getDates="getDates" />

        <div v-show="fileList.length">
          <div class="time_title_container">
            <div class="time_title">
              时间选择
            </div>            
          </div>
          <el-checkbox :disabled="histogramLoading === true " v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">Check all
          </el-checkbox>
          <el-checkbox-group :disabled="histogramLoading === true " v-model="declaredDates" @change="handleCheckedDatesChange">
            <el-checkbox :disabled="histogramLoading === true " v-for="date in fileList" :key="date" :label="date">{{
                date.dateTime
            }}</el-checkbox>
          </el-checkbox-group>
        </div>

        <div style="margin-top: 20px;" v-show="dates.length">
          <el-button v-if="trueResult" @click.passive="startPre" round class="start_pre">
            <Opportunity style="width: 1em; height: 1em; color: orange; margin: auto;"></Opportunity>
            开始预测
          </el-button>
          <el-button v-else @click.passive="startPre" round class="start_pre">
            <Opportunity style="width: 1em; height: 1em; color: orange; margin: auto;"></Opportunity>
            查看结果
          </el-button>
          <el-button @click.passive="downloadPreResult" circle class="download_result">
            <Download style="width: 1.5em; height: 1.5em; margin: auto;" />
          </el-button>
        </div>
        <Histogram3D v-if="!histogramLoading && fileList.length && histogrM3DShow" :xData="xData" :yData="yData" :tableData="tableData"
          class="histogram" />
        <div v-else class="histogram" v-loading="histogramLoading"></div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup name="growthPre">
import { ref, getCurrentInstance, nextTick } from 'vue';
import Histogram3D from './histogram3D.vue';
import { getTree } from '@/api/tree';
import { getJsonByCSV } from '@/utils/tree';
import ShowCSVTable from '@/views/infomanage/phenotype/ShowCSVTable.vue';
import { getTableData, getPredictGrow } from '@/api/identifypre/growthpre';
import { listFile } from '@/api/infomanage/phenoType';
import useUserStore from '@/store/modules/user';

// vue实例
const { proxy: { $modal, $download } } = getCurrentInstance();
const { roles } = useUserStore();

// 加载
const loading = ref(false);
const histogramLoading = ref(false);
const trueResult = ref(true)
const loadingText = ref('加载中...');
const histogrM3DShow = ref(false)

// 表格数据

const tableData = ref([
  {
    '2020-01-01': 11,
    '2020-01-02': 22,
    '2020-01-03': 33,
    '2020-01-04': 44,
  },
  {
    '2020-01-01': 11,
    '2020-01-02': 22,
    '2020-01-03': 33,
    '2020-01-04': 44,
  },
  {
    '2020-01-01': 11,
    '2020-01-02': 22,
    '2020-01-03': 33,
    '2020-01-04': 44,
  },
  {
    '2020-01-01': 11,
    '2020-01-02': 22,
    '2020-01-03': 33,
    '2020-01-04': 44,
  }
]);



const curFileUrl = ref('');// 表格日期对应的文件url
// 表格日期
const dates = ref([]);
const fileList = ref([]);  // 文件列表

// 请求文件列表
function getList() {
  listFile({
    treeId: tree.value.getCurrentNode().treeId,
  }).then(res => {
    fileList.value = res.rows.map(item => ({
      ...item,
      fileStatus: item.fileStatus === 1
    }));
    total.value = res.total;
  }).catch(err => {
    //console.log(fileList);
  });
}

// 获取表格日期
function getTableDate() {
  return Object.keys(tableData.value[0]);
}


// 全选框
const checkAll = ref(false);
const isIndeterminate = ref(true);
// 全选函数
function handleCheckAllChange(val) {
  checkAll.value = val;
  isIndeterminate.value = false;
  checkedDates.value = val ? dates.value : [];
  declaredDates.value = val ? fileList.value : [];
}

// 多选框
const checkedDates = ref([]);
const declaredDates = ref([]);
function handleCheckedDatesChange(val) {
  const checkedCount = val.length
  checkAll.value = checkedCount === fileList.value.length;
  isIndeterminate.value = checkedCount > 0 && checkedCount < fileList.value.length;
}

// 预测图数据
const xData = ref(new Array(tableData.value.length).fill(0).map((item, index) => `第${index + 1}株`));
const yData = ref(tableData.value.length ? Object.keys(tableData.value[0]) : []);

let predictedUrl = ref('')

async function startPre() {
  histogramLoading.value = true;
  if (declaredDates.value.length > 7) {
    $modal.msgWarning('不能超过七个文件')
    predictedUrl = ''
    histogramLoading.value = false;
  } else if (declaredDates.value.length < 7) {
    $modal.msgWarning('不能少于七个文件')
    histogramLoading.value = false;
  } else {
    histogrM3DShow.value = true
    getPredictGrow({
      "ids": `${declaredDates.value[0].fileId},${declaredDates.value[1].fileId},${declaredDates.value[2].fileId},${declaredDates.value[3].fileId},${declaredDates.value[4].fileId},${declaredDates.value[5].fileId},${declaredDates.value[6].fileId}`
    }).then(res => {
      predictedUrl = res.msg;
    }).catch(err => {
      $modal.msgError('无法预测');
    });
    if (predictedUrl == '') {
      histogramLoading.value = true;
      trueResult.value = false;
      $modal.msgWarning('生成中，点击查看结果')
    } else {
      histogramLoading.value = true;
      const jsonData = await getJsonByCSV(predictedUrl);
      xData.value = jsonData.slice(1).map(item => item[0])
      yData.value = jsonData[0].slice(1)
      tableData.value = new Array(jsonData.length - 1).fill(0).map((item, index) => {
        const obj = {}
        jsonData[0].slice(1).forEach((item, i) => {
          obj[item] = jsonData[index + 1][i + 1]
        })
        
        return obj
      })
      trueResult.value = true;
      histogramLoading.value = false;
    }
    
  }
}

// 下载预测结果
function downloadPreResult() {
  if (predictedUrl === '' || histogramLoading.value === true) {
    $modal.msgWarning('未预测结果，请先预测')
    return
  }
  console.log(predictedUrl,'ooo');
  $download.resource(predictedUrl);
}


// 树控件
const routesData = ref([]);
// 树的属性
const defaultProps = ref({
  children: 'children',
  label: 'treeName'
});

const treeType = 3;
const tree = ref(null); // 树的dom实例

const getTreeList = () => {
  //调用getTree接口
  getTree(treeType, 0, 1).then(res => {
    routesData.value = res.data.children;
    nextTick(() => {
      //getCurrentNode	返回当前被选中节点的数据 (如果没有则返回 null)
      if (!tree.value.getCurrentNode())
        //setCurrentKey	通过 key 设置某个节点的当前选中状态，使用此方法必须设置 node-key  属性
        tree.value.setCurrentKey(routesData.value[0]?.treeId);
      rowClick(tree.value.getCurrentNode());
    });
  });
};

getTreeList();


function rowClick(nodeObj) {
  histogrM3DShow.value = false
  loading.value = true;
  histogramLoading.value = false;
  checkAll.value = false;
  declaredDates.value = [];
  predictedUrl = '';
  //获取表格数据
  getTableData(nodeObj.treeId).then(res => {
    curFileUrl.value = res.msg;
    loading.value = false;
  }, err => {
    loading.value = false;
  });
  getList();

}

function getDates(dateArr) {
  dates.value = dateArr;
}


</script>
<style lang="scss" scoped>
:deep(.el-tree-node__label) {
    font-size: 16px;
    
}

:deep(.el-tree) {
    background-color: rgb(218,227,241);
}

:deep(.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content) {
    background-color: #fff !important;
}

.right-box {
  margin-left: 20px;
}

.mokuai {
  margin-bottom: 0;
  background-color: rgb(218,227,241);
}

.right-box {
  margin-left: 20px;
}

.app-container {
  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
<style  scoped>
.btn {
  margin: auto;
  display: block;
  margin-top: 20px;
  margin-bottom: 20px;
}

.tag_btn {
  background-color: aqua;
}

.histogram {
  margin-top: 20px;
  margin-bottom: 20px;
  width: 100%;
  height: 500px;
}

.start_pre {
  text-align: center;
  width: 120px;
  height: 50px;
  font-size: large;
  margin-left: 400px;
}

.download_result {
  width: 50px;
  height: 50px;
  float: right;
}

.shadow {
  box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.14);
}

.time_title_container{
  margin:15px auto;
  background-color: rgb(218,227,241);
  height: 40px;
  width: 200px;
  border-radius: 50px;
  letter-spacing: 2px;
}

.time_title{
  margin: auto;
  line-height: 40px;
  text-align: center;
  word-spacing: 20px;
}
</style>
