var dom = document.getElementById("container");
var myChart = echarts.init(dom);
var app = {};
option = null;
option = {
	 title : {
        text: '本周收入统计',
        subtext: '2018年6月24日 - 2018年6月30日收入统计',
        x:'center'
    },
    xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar'
    }]
};
;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}



var doms = document.getElementById("container2");
var myChart = echarts.init(doms);
var app = {};
option = null;
option = {
    title : {
        text: '各商品收益统计',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['刘二妈米皮','豆花面','羊肉粉','茅台酒','朝天椒']
    },
    series : [
        {
            name: '销售总数',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:435, name:'刘二妈米皮'},
                {value:310, name:'豆花面'},
                {value:234, name:'羊肉粉'},
                {value:135, name:'茅台酒'},
                {value:18, name:'朝天椒'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
;
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
