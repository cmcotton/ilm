<div id="page-wrapper" ng-controller="IndexCtrl">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">儀表板</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-4 col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-list fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge" id="regu-applicable">{{applicableRegusSize}}</div>
							<div>適用項目數</div>
						</div>
					</div>
				</div>
				<a href="#myModalFeasible" data-toggle="modal">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>

				<!-- Modal HTML -->
				<div id="myModalFeasible" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Confirmation</h4>
							</div>
							<div class="modal-body" id="regu-applicable-list">
								<table>
									<tr ng-repeat="x in applicableRegus">
										<td>{{x}}</td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-6">
			<div class="panel panel-green">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-check fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge" id="regu-pass">{{passRegusSize}}</div>
							<div>符合項目數</div>
						</div>
					</div>
				</div>
				<a href="#myModalPass" data-toggle="modal">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>

				<!-- Modal HTML -->
				<div id="myModalPass" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">符合項目列表</h4>
							</div>
							<div class="modal-body" id="regu-pass-list">
								<table>
									<tr ng-repeat="x in passRegus">
										<td>{{x}}</td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-lg-4 col-md-6">
			<div class="panel panel-red">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-exclamation fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">
							<div class="huge" id="regu-violate">{{violateRegusSize}}</div>
							<div>違反項目數</div>
						</div>
					</div>
				</div>
				<a href="#myModalViolated" data-toggle="modal">
					<div class="panel-footer">
						<span class="pull-left">View Details</span> <span
							class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
						<div class="clearfix"></div>
					</div>
				</a>

				<!-- Modal HTML -->
				<div id="myModalViolated" class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">違反規範項目列表</h4>
							</div>
							<div class="modal-body" id="regu-violate-list">
								<table>
									<tr ng-repeat="x in violateRegus">
										<td>{{x}}</td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.row -->
	

	<div class="row">
		<!-- /.col-lg-8 -->
		<div class="col-lg-12">

			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="fa fa-table fa-fw"></i>最新事件
				</div>
				<div class="panel-body">
					<div class="col-lg-12">

						<div class="dataTable_wrapper">

							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th data-field="no">規則名稱</th>
										<th data-field="name">內容</th>
										<th data-field="ip">IP位址</th>
										<th data-field="time">時間</th>
									</tr>
								</thead>
								<tr ng-repeat="x in latestRules">
									<td>{{x.name}}</td>
									<td>{{x.content}}</td>
									<td>{{x.ip}}</td>
									<td>{{x.evtTime}}</td>
								</tr>
							</table>
						</div>

					</div>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->


		</div>
		<!-- /.col-lg-4 -->
	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-lg-7">
			<div class="panel panel-default">
				<div class="fb-comments"
					data-href="http://localhost:18080/account/regu/dashboard.do"
					data-width="600" data-numposts="10"></div>
			</div>
		</div>
		
	
        <div class="col-lg-5">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="fa fa-line-chart fa-fw"></i>歷史日誌量趨勢圖
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <div id="morris-area-chart"></div>
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->

        </div>      
        <!-- /.col-lg-6 -->
    
	</div>

</div>
<!-- /#page-wrapper -->

