<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Theme Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/theme.css" rel="stylesheet">

</head>

<body>

<!-- Fixed navbar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header plugin-list">
            <ul class="nav navbar-nav">
                <li class="active">
                    <select class="form-control" id="readerPlugins">
                        <option>点击选择读插件</option>
          <#list readerPlugins?keys as key>
            <option>${key}</option>
          </#list>
                    </select>
                </li>
            </ul>
        </div>
        <div id="navbar" class="navbar-collapse collapse plugin-list navbar-right">
            <ul class="nav navbar-nav">
                <li class="active">
                    <select class="form-control" id="writerPlugins">
                        <option>点击选择写插件</option>
          <#list writerPlugins?keys as key>
            <option>${key}</option>
          </#list>
                    </select>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>


<div class="container theme-showcase" role="main">
    <form class="form-signin" id="formid">
        <input class="form-control" type="input" id="jobname" name="jobname" placeholder="作业名">
        <div class="row padding0">
            <div class="col-md-6">
                <div class="table">
                    <div class="form-group"><label>通道数</label><input class="form-control" type="input"
                                                                     id="speed.channel" name="speed.channel"></div>
                    <div class="form-group"><label>传输速度</label><input class="form-control" type="input" id="speed.byte"
                                                                      name="speed.byte"></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="table">
                    <div class="form-group"><label>错误记录数</label><input class="form-control" type="input"
                                                                       id="errorLimit.record" name="errorLimit.record">
                    </div>
                    <div class="form-group"><label>错误百分比</label><input class="form-control" type="input"
                                                                       id="errorLimit.percentage"
                                                                       name="errorLimit.percentage"></div>
                </div>
            </div>
        </div>
        <div class="alert alert-info plugin-list" role="alert">
            <strong>提示！</strong> 请先阅读向导手册，以便配置参数正确无误。
            <i class="glyphicon glyphicon-hand-right"></i>
            <a target="_blank" href="guide">点击查看</a>
            <span id="save" class="btn btn-sm btn-default navbar-right center-vertical marginright">保存</span>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="table" id="divrps">
                </div>
            </div>
            <div class="col-md-6">
                <div class="table" id="divwps">
                </div>
            </div>
        </div>
    </form>
</div> <!-- /container -->


<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/docs.min.js"></script>

<script type="text/javascript">
    // 异步请求插件的模板信息；并动态生成html标签（主要是输入表单）
    function ajaxPluginTemplate(rootdiv, plugin) {
        var requestData = {
            "plugin": plugin
        };
        var successHandler = function (data) {
            for (var i = 0, l = data.length; i < l; i++) {
                var div = document.createElement('div');
                $(div).addClass("form-group");

                var label = document.createElement('label');
                if (data[i]["desc"] == null)
                    $(label).text(data[i]["keys"]);
                else
                    $(label).text(data[i]["desc"]);
                $(div).append(label);

                var input = document.createElement('input');
                $(input).addClass("form-control");
                $(input).attr("type", "input");
                $(input).attr("id", plugin + "." + data[i]["keys"]);
                $(input).attr("name", plugin + "." + data[i]["keys"]);
                $(div).append(input);

                $(rootdiv).append(div);
                //alert(data[i]["keys"] + "---" + data[i]["desc"] + "---" + data[i]["type"]);
            }
        };

        $.post("pluginTemplate", requestData, successHandler, "json");
    }

    // 读插件、写插件选择事件
    $("#readerPlugins").change(function () {
        var plugin = $(this).children('option:selected').val();
        // 清除div内容
        $("#divrps").empty();
        ajaxPluginTemplate($(divrps), plugin);
    });
    $("#writerPlugins").change(function () {
        var plugin = $(this).children('option:selected').val();
        // 清除div内容
        $("#divwps").empty();
        ajaxPluginTemplate($(divwps), plugin);
    });
    // 提交配置信息
    $("#save").click(function () {
        // 检查是否选择了读和写插件
        var readerPlugin = $("#readerPlugins").val();
        var writerPlugin = $("#writerPlugins").val();
        var jobname = $("#jobname").val();
        if (readerPlugin == "点击选择读插件") {
            alert("请选择读插件");
            return;
        }
        if (writerPlugin == "点击选择写插件") {
            alert("请选择写插件");
            return;
        }
        if (jobname == "") {
            alert("请填写作业名");
            return;
        }
        // 获取 表单FROM所有的元素（过滤掉 被禁用的 元素）
        var formdata = $("#formid").serializeArray();
        var requestData = {};
        for (entry in formdata) {
            requestData[$.trim(formdata[entry].name)] = $.trim(formdata[entry].value);
        }

        var successHandler = function (data) {
            alert(data.message);
            // 跳转到查看作业的 json 信息页面
            $(location).attr('href', 'download?jobname=' + $("#jobname").val());
        };

        $.post("save", requestData, successHandler, "json")
                .error(function (xhr, status, info) {
                    alert("保存失败，请检查配置参数是否符合要求！");
                });
    });
</script>

</body>
</html>
