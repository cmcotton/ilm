<div id="page-wrapper" ng-controller="StoreCtrl">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">Plugin Store</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">Plugin Store 隨時都有新內容登場。</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="col-lg-12">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th data-field="id">編號</th>
										<th data-field="name">名稱</th>
										<th data-field="type">類別</th>
										<th data-field="desc">描述</th>
										<th data-field="price">價格</th>
										<th data-field="buy">購買</th>
									</tr>
								</thead>
								<tr ng-repeat="x in plugins | orderBy:'id'" ng-click="">
									<td>{{x.id}}</td>
									<td>{{x.name}}</td>
									<td>{{x.type}}</td>
									<td>{{x.desc}}</td>
									<td>{{x.price | currency:undefined:0}}</td>
									<td><button type="button" class="btn btn-primary" ng-hide="x.installed" ng-click=buy(x.id) >確認</button>
									    <button type="button" class="btn btn-warning" ng-show="x.installed" ng-click=del(x.id) >移除</button></td>
								</tr>
							</table>
						</div>

					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>

<!--     
			<a class="btn btn-large btn-info" href="/account/plugin/store.html">çè¦½ Plugin Store</a>
-->
			<!-- /.col-lg-12 -->

		</div>
		<!-- /.row -->

		<!-- /.row -->

		<!-- /.row -->

		<!-- /.row -->
	</div>
	<!-- /#page-wrapper -->