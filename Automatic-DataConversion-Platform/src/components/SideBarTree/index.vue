<template>
  <el-tree ref="tree" :data="routesData" :props="defaultProps" node-key="treeId" default-expand-all highlight-current
    :current-node-key="1" @node-click="rowClick" class="permission-tree" />
</template>

<script setup>
import { ref, watchEffect } from 'vue';
const routesData = ref([{
  treeName: 'Level one 1',
  treeId: '1',
  children: [
    {
      treeName: 'Level two 1-1',
      treeId: '11',
      children: [
        {
          treeName: 'Level three 1-1-1',
          treeId: '111',
        },
      ],
    },
  ],
},
{
  treeName: 'Level one 2',
  treeId: '2',
  children: [
    {
      treeName: 'Level two 2-1',
      treeId: '21',
      children: [
        {
          treeName: 'Level three 2-1-1',
          treeId: '211',
        },
      ],
    },
    {
      treeName: 'Level two 2-2',
      treeId: '21',
      children: [
        {
          treeName: 'Level three 2-2-1',
          treeId: '221`'
        },
      ],
    },
  ],
},
{
  treeName: 'Level one 3',
  treeId: '3',
  children: [
    {
      treeName: 'Level two 3-1',
      treeId: '31',
      children: [
        {
          treeName: 'Level three 3-1-1',
          treeId: '311',
        },
      ],
    },
    {
      treeName: 'Level two 3-2',
      treeId: '32',
      children: [
        {
          treeName: 'Level three 3-2-1',
          treeId: '321',
        },
      ],
    },
  ],
}]);

const defaultProps = ref({
  children: 'children',
  label: 'treeName'
});
const tree = ref(null);
function addChildNode() {
  dialogStatus.value = 'create';
  dialogFormVisible.value = true;
}

function deleteNode() {
  //console.log('delete', tree.value.getCurrentNode());
}

async function rowClick(nodeObj, node, event) {
  /* console.log('nodeObj', nodeObj);
  console.log('node', node);
  console.log('event', event); */
  loading.value = true;
  imageSrcList.value = await getImageList();
  loading.value = false;
}
</script>

<style scoped>
</style>
