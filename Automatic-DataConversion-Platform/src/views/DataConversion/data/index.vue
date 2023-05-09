<template>
  <div class="big_wrapper">
    <el-card class="card-container">
      <div class="big-wrapper" style="margin-top: 10px; margin-bottom: 50px">
        <div class="card-title">
          <div class="card-title-line"></div>
          <div class="card-title-content">Input Example</div>
        </div>
      </div>
      <el-table :data="testDatas" border style="width: 100%">
        <!-- 额外添加的编号项（可删除） -->
        <el-table-column
          v-if="columnList.length > 0"
          type="index"
          :label="'编号'"
          :width="50"
        ></el-table-column>
        <!-- 自定义表项 -->
        <el-table-column v-for="column in columnList" :key="column.prop">
          <!-- 自定义表头 -->
          <template #header>
            <!-- 段落：show为true -->
            <p v-show="column.show" @dblclick="column.show = false">
              {{ column.label }}
              <i class="el-icon-edit-outline" @click="column.show = false"></i>
            </p>
            <!-- 输入框：show为false -->
            <el-input
              size="mini"
              v-show="!column.show"
              v-model="column.label"
              @blur="column.show = true"
            >
            </el-input>
          </template>

          <!-- 自定义表项/单元格内容 -->
          <template #default="scope">
            <!-- 双击文字或点击修改图标以更改"show"属性 -->
            <!-- scope.row为元数据，column.col为该列的'键' -->
            <p
              v-show="scope.row[column.prop].show"
              @dblclick="scope.row[column.prop].show = false"
            >
              {{ scope.row[column.prop].content }}
              <i
                class="el-icon-edit-outline"
                @click="scope.row[column.prop].show = false"
              />
            </p>
            <!-- 失去焦点时更改"show"属性，显示文本 -->
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              v-show="!scope.row[column.prop].show"
              v-model="scope.row[column.prop].content"
              @blur="scope.row[column.prop].show = true"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <div class="change_div">
      <el-select v-model="value" placeholder="Select" style="margin-top: 300px">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
          :disabled="item.disabled"
        />
      </el-select>
      <el-button type="primary" @click="changeData" style="margin-top: 10px"
        >点击转换</el-button
      >
    </div>
    <el-card class="card-container">
      <div class="big-wrapper" style="margin-top: 10px; margin-bottom: 50px">
        <div class="card-title">
          <div class="card-title-line"></div>
          <div class="card-title-content">Output Example</div>
        </div>
      </div>
      <el-table v-if="dataShow" :data="testDatass" border style="width: 100%">
        <el-table-column
          prop="index"
          label="编号"
          :width="50"
        ></el-table-column>
        <el-table-column prop="A" label="A" />
        <el-table-column prop="B" label="B" />
        <el-table-column prop="C" label="C" />
      </el-table>
      <el-table v-if="dataShow2" :data="testDatass3" border style="width: 100%">
        <el-table-column
          prop="index"
          label="编号"
          :width="50"
        ></el-table-column>
        <el-table-column prop="A" label="A" />
        <el-table-column prop="B" label="B" />
        <el-table-column prop="C" label="C" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
const value = ref("");
const options = [
  {
    value: "1",
    label: "2018-19",
  },
  {
    value: "2",
    label: "2015-16",
  },
];

const dataShow2 = ref(false);

const columnList = reactive([
  { prop: "A", label: "A", show: true },
  { prop: "B", label: "B", show: true },
  { prop: "C", label: "C", show: true },
  { prop: "D", label: "D", show: true },
]);
// 数据
/* const testDatas = reactive([
  {
    A: { content: "Burean of I.A.", show: true },
    B: { content: "*", show: true },
    C: { content: "*", show: true },
    D: { content: "*", show: true },
    E: { content: "*", show: true },
  },
  {
    A: { content: "Regional Director", show: true },
    B: { content: "Numbers", show: true },
    C: { content: "*", show: true },
    D: { content: "*", show: true },
    E: { content: "*", show: true },
  },
  {
    A: { content: "Nikes C.", show: true },
    B: { content: "Tel:(800)645-8397", show: true },
    C: { content: "*", show: true },
    D: { content: "*", show: true },
    E: { content: "*", show: true },
  },
  {
    A: { content: "*", show: true },
    B: { content: "Fax:(907)586-7252", show: true },
    C: { content: "*", show: true },
    D: { content: "*", show: true },
    E: { content: "*", show: true },
  },
  {
    A: { content: "*", show: true },
    B: { content: "*", show: true },
    C: { content: "*", show: true },
    D: { content: "*", show: true },
    E: { content: "*", show: true },
  },
]); */

const testDatas = reactive([
  {
    A: { content: "Rest_Crops", show: true },
    B: { content: "Nicobar_Islands", show: true },
    C: { content: "2015-16", show: true },
    D: { content: "888.5", show: true },
  },
  {
    A: { content: "Rest_Crops", show: true },
    B: { content: "Nicobar_Islands", show: true },
    C: { content: "2018-19", show: true },
    D: { content: "558", show: true },
  },
  {
    A: { content: "Rest_Crops", show: true },
    B: { content: "North_and_Middle_Andaman", show: true },
    C: { content: "2015-16", show: true },
    D: { content: "1545", show: true },
  },
  {
    A: { content: "Rest_Crops", show: true },
    B: { content: "North_and_Middle_Andaman", show: true },
    C: { content: "2018-19", show: true },
    D: { content: "2297.49", show: true },
  },
  {
    A: { content: "Rest_Crops", show: true },
    B: { content: "South_Andama", show: true },
    C: { content: "2018-19", show: true },
    D: { content: "1220.2", show: true },
  },
]);

// 第二组数据
let testDatass = reactive([
  {
    index: 1,
    A: "*",
    B: "Rest_Crops",
    C: "Time",
  },
  {
    index: 2,
    A: "Nicobar_Islands",
    B: "558",
    C: "2018-19",
  },
  {
    index: 3,
    A: "North_and_Middle_Andaman",
    B: "2297.49",
    C: "2018-19",
  },
  {
    index: 4,
    A: "South_Andama",
    B: "1220.2",
    C: "2018-19",
  },
]);

// 第二组数据
let testDatass3 = reactive([
  {
    index: 1,
    A: "*",
    B: "Rest_Crops",
    C: "Time",
  },
  {
    index: 2,
    A: "Nicobar_Islands",
    B: "888.5",
    C: "2015-16",
  },
  {
    index: 3,
    A: "North_and_Middle_Andaman",
    B: "1545",
    C: "2015-16",
  },
]);

const dataShow = ref(false);

function changeData() {
  console.log("开始转换");
 /*  ElMessage({
    message: "输入期望样例失败，系统故障，请稍后再试",
    type: "error",
  });  */
  ElMessage({
    message: "输入成功",
    type: "success",
  });
  if (value.value === "1") {
    dataShow2.value = false;
    dataShow.value = true;
  } else if (value.value === "2") {
    dataShow.value = false;
    dataShow2.value = true;
  }
}
</script>

<style lang="less">
.change_div {
  display: flex;
  flex-direction: column;
  width: 110px;
}

.big_wrapper {
  display: flex;
}

.card-container {
  width: 45%;
  border-radius: 50px;
  margin: auto;
  margin-top: 30px;

  :deep(.el-card__body) {
    padding: 15px 20px 20px 20px !important;
  }
}

.big-wrapper {
  width: 95%;
  margin: auto;
}

.card-container .SearchBox-card {
  border-radius: 20px;
}

.input-title span {
  color: rgb(78, 151, 211);
}

/*card的title */
.card-title {
  width: 60%;
  color: rgb(64, 64, 112);
  border-radius: 10px;
  height: 60px;
  display: flex;
}
.card-title-line {
  width: 2%;
  height: 60px;
  background-color: rgb(154, 190, 175);
}

.card-title-content {
  float: left;
  margin-left: 20px;
  font-size: 26px;
  line-height: 60px;
}

.nodeBox {
  :deep(.el-cascader) {
    margin-right: 20px;
  }
}
</style>