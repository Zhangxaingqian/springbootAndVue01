/*日期格式化的js函数*/
function JX_data(data) {
    //日期格式化过滤器
    Date.prototype.format = function (fmt) { //author: meizz
        var o = {
            'M+': this.getMonth() + 1, //月份
            'd+': this.getDate(), //日
            'h+': this.getHours(), //小时
            'm+': this.getMinutes(), //分
            's+': this.getSeconds(), //秒
            'q+': Math.floor((this.getMonth() + 3) / 3), //季度
            'S': this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp('(' + k + ')').test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
        return fmt;
    };

    // 获取年月日

    //data = new Date(data).format("yyyy-MM-dd");

    //获取年月

    data = new Date(data).format("yyyy-MM-dd");

    return data;
}
/*
* 根据对应的jobGradeId获取jobGrade的描述*/
/*function getJobGrade(jobGradeId) {
    /!*
    * 发送ajax请求,去后台查询*!/
    console.log(jobGradeId);

    var result;
    $.get(
        "/jobCategory/getJobGradeById",//Servlet地址
        {jobGradeId:jobGradeId},
        function (resulteInfo){
            if(resulteInfo.flag){
                var data=resulteInfo.data;
                result=data.secondName;
            }else{
                console.log("服务器繁忙")
            }

        },
        "json"
    );
    return result;

}*/
