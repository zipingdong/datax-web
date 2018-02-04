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

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-6">
            <div class="table" id="divrps">
<#list readerPlugins as key>
                <div class="form-group">
                    <a target="_blank" href="markdown?plugin=${key}">${key}</a>
                </div>
</#list>
            </div>
        </div>
        <div class="col-md-6">
            <div class="table" id="divwps">
<#list writerPlugins as key>
                <div class="form-group">
                    <a target="_blank" href="markdown?plugin=${key}">${key}</a>
                </div>
</#list>
            </div>
        </div>
    </div>
</div> <!-- /container -->


<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/docs.min.js"></script>

</body>
</html>
