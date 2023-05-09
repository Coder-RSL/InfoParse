import * as xlsx from 'xlsx';
import { getFileContent } from '@/api/infomanage/phenotype';

export function getTreeNodeIdsByNode(tree) {
  let result = [];
  result.push(tree.treeId);
  if (tree.children) {
    tree.children.forEach(child => {
      result = result.concat(getTreeNodeIdsByNode(child));
    });
  }
  return result;
}

export function getImageUrlByUrl(url) {
  return `${import.meta.env.VITE_APP_UPLOAD_URL}/image/${url}`
}

// 将根据url获取的数据转换为json
export async function getJsonByCSV(url) {
  const data = await getFileContent(url);
  //console.log(data,'9090')
  let utf8decoder = new TextDecoder('gbk');
  let binary = utf8decoder.decode(data);
  const ctx = xlsx.read(binary, { type: 'binary', codepage: 936 });
  const result = xlsx.utils.sheet_to_json(ctx.Sheets[ctx.SheetNames[0]], { header: 1 });
  //console.log(url,'6667');
  //console.log(result,'7776');    
  return result
}

// 将json数据转换为表格数据
export function jsonToTable(data) {
  data = data.filter(item => {
    return item.length ? true:false
  })
  let res = new Array(data.length - 1).fill('').map(item => ({}));
  for (let i = 0; i < data[0].length; i++) {
    const prop = data[0][i];
    for (let j = 0; j < res.length; j++) {
      const obj = res[j];
      obj[prop] = data[j + 1][i] ? data[j + 1][i] : 0;
    }
  }
  return res
}