
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">條文與規則對照管理</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">DataTables Advanced Tables</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<div class="col-lg-12">
						<form role="form">
							<div class="form-group col-lg-6">
								<label>條文</label> <select class="form-control" id="regu-select">

								</select>
							</div>
							<div class="form-group col-lg-6">
								<label>規則</label> <select class="form-control"
									id="report-name">
									<option>登出入紀錄</option>
									<option>異常帳號登入紀錄</option>
								</select>
							</div>
						</form>

						<div class="form-group col-lg-12">
							<button type="button" class="btn btn-primary"
								id="submit">加入</button>
						</div>
					</div>

					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>									
									<th data-field="rule-no">規則編號</th>
                                    <th data-field="rule-name">規則名稱</th>
                                    <th data-field="rule-desc">敘述</th>
								</tr>
							</thead>
						</table>
					</div>

				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<!-- /.row -->

	<!-- /.row -->

	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
