<template>
  <div id="histogram3D"></div>
</template>

<script setup name="histogram3D">
import { onMounted } from "vue";
import * as echarts from "echarts";
import "echarts-gl";

onMounted(() => {
  initHistogram();
});

const props = defineProps({
  xData: {
    type: Array,
    default: () => [
      "12a",
      "1a",
      "2a",
      "3a",
      "4a",
      "5a",
      "6a",
      "7a",
      "8a",
      "9a",
      "10a",
      "11a",
      "12p",
      "1p",
      "2p",
      "3p",
      "4p",
      "5p",
      "6p",
      "7p",
      "8p",
      "9p",
      "10p",
      "11p",
    ],
  },
  yData: {
    type: Array,
    default: () => [
      "Saturday",
      "Friday",
      "Thursday",
      "Wednesday",
      "Tuesday",
      "Monday",
      "Sunday",
    ],
  },
  tableData: {
    type: Array,
    default: () => [
      {
        "2020-01-01": 11,
        "2020-01-02": 22,
        "2020-01-03": 33,
        "2020-01-04": 44,
      },
      {
        "2020-01-01": 11,
        "2020-01-02": 22,
        "2020-01-03": 33,
        "2020-01-04": 44,
      },
      {
        "2020-01-01": 11,
        "2020-01-02": 22,
        "2020-01-03": 33,
        "2020-01-04": 44,
      },
      {
        "2020-01-01": 11,
        "2020-01-02": 22,
        "2020-01-03": 33,
        "2020-01-04": 44,
      },
    ],
  },
});
const { xData, yData, tableData } = props;

function initHistogram() {
  let chartDom = document.querySelector("#histogram3D");
  let myChart = echarts.init(chartDom);
  let option;

  const value = [];
  for (let i = 0; i < xData.length; i++) {
    const xItem = xData[i];
    for (let j = 0; j < yData.length; j++) {
      const yItem = yData[j];
      value.push([xItem, yItem, tableData[i][yItem]]);
    }
  }

  option = {
    tooltip: {
      formatter: (param) => {
        const { value } = param;
        return `-${value[0]}<br/>-日期:${value[1]}<br/>-高度:${value[2]}`;
      },
    },
    visualMap: {
      max: 20,
      inRange: {
        color: [
          "#313695",
          "#4575b4",
          "#74add1",
          "#abd9e9",
          "#e0f3f8",
          "#ffffbf",
          "#fee090",
          "#fdae61",
          "#f46d43",
          "#d73027",
          "#a50026",
        ],
      },
    },
    xAxis3D: {
      type: "category",
      data: xData,
    },
    yAxis3D: {
      type: "category",
      data: yData,
    },
    zAxis3D: {
      type: "value",
    },
    grid3D: {
      boxWidth: 200,
      boxDepth: 80,
      viewControl: {
        // projection: 'orthographic'
      },
      light: {
        main: {
          intensity: 1.2,
          shadow: true,
        },
        ambient: {
          intensity: 0.3,
        },
      },
    },
    series: [
      {
        type: "bar3D",
        data: value.map(function (item) {
          return {
            value: [item[0], item[1], item[2]],
          };
        }),
        shading: "lambert",
        label: {
          fontSize: 16,
          borderWidth: 1,
        },
        emphasis: {
          label: {
            fontSize: 20,
            color: "#900",
          },
          itemStyle: {
            color: "#900",
          },
        },
      },
    ],
  };

  option && myChart.setOption(option);
}
</script>