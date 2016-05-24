
<div id="page-wrapper" ng-controller="LogSelectCtrl">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">符合性檢視作業</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">抽選條件</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<form role="form">
								<!-- 
                                        <div class="form-group">
                                            <label>條文</label>
                                            <select class="form-control" id="regu-select" >
                                            </select>
                                        </div>
                                    -->
								<div class="form-group col-lg-4">
									<label>規則</label> <select class="form-control" id="report-name"
										ng-init="ruleNameSel = options[0].value"
										ng-model="ruleNameSel"
										data-ng-options="a.no as a.name for a in ruleName">
									</select>
								</div>
								<div class="form-group col-lg-4">
									<label>開始時間</label>
									<md-datepicker ng-model="date1" md-placeholder="Enter date"></md-datepicker>
								</div>
								<div class="form-group col-lg-4">
									<label>結束時間</label>
									<md-datepicker ng-model="date2" md-placeholder="Enter date"></md-datepicker>
								</div>

								<div class="form-group col-lg-12">
									<button type="button" class="btn btn-primary"
										id="submitLogSelect">送出</button>
									<button type="reset" class="btn btn-default">重新輸入</button>
								</div>

								<div class="form-group col-lg-12">
									<label>關鍵字搜尋</label> <input class="form-control"
										placeholder="用空白隔開" ng-model="keyword">
								</div>

							</form>

							<!-- table -->
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example">
									<thead>
										<tr>
											<th>項次</th>
											<th>使用者代號</th>
											<th>使用者姓名</th>
											<th>時間</th>
											<th>IP 位址</th>
											<th>內容</th>
											<th>嚴重程度</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="x in events | filter : keyword"
											ng-class="{danger: x.severity > 4, warning: x.severity > 2}">
											<td>{{$index + 1}}</td>
											<td>{{x.userId}}</td>
											<td>{{x.userName}}</td>
											<td>{{x.evtTime}}</td>
											<td>{{x.ip}}</td>
											<td>{{x.content}}</td>
											<td>{{x.severity}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

					</div>
					<!-- /.row (nested) -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

