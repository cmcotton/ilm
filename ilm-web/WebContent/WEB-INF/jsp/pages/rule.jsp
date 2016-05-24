
<div id="page-wrapper" ng-controller="RuleCtrl">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">規則管理</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">適用條文</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<div class="col-lg-12">

						<div class="dataTable_wrapper">

							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th data-field="no">編號</th>
										<th data-field="name">名稱</th>
										<th data-field="desc">即時操作</th>
									</tr>
								</thead>
								<tr ng-repeat="x in currentRules | orderBy: 'id'" ng-click="showDesc(x)">
									<td>{{x.id}}</td>
									<td>{{x.name}}</td>
									<td>
										<button type="button" class="btn btn-primary" id="run"
											ng-click="run(x.id)">立刻執行</button>
									</td>
								</tr>
							</table>
						</div>

					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->				
			</div>
    
			<a class="btn btn-large btn-info" href="/account/plugin/store.do">瀏覽 Plugin Store</a>
			
<!-- 
			<button type="button" class="btn btn-warning" id="btn-modify">修改</button>
			<button type="button" class="btn btn-danger" id="btn-delete">刪除</button>


			<div class="btn btn-primary btn-upload" upload-button url="/account/upload/uploadFile.html"
				id="file" on-success="onSuccess(response)">Upload</div>
-->
			<!-- /.col-lg-12 -->

		</div>
		<!-- /.row -->

		<!-- /.row -->

		<!-- /.row -->

		<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->