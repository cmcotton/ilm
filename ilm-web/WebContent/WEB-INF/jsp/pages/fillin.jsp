
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">自行填報作業</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">Basic Form Elements</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<form role="form">
								<div class="form-group">
									<label>條文</label> <select class="form-control" id="regu-select">
									</select>
								</div>
								<div class="form-group">
									<label>規則</label> <select class="form-control"	id="report-name">
									</select>
								</div>
								<div class="form-group">
									<label>開始時間</label>
									<div class='input-group date' id='datetimepicker1'>
										<input type='text' class="form-control" /> <span
											class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
									<p class="help-block">區間起始日</p>
								</div>
								<div class="form-group">
									<label>結束時間</label>
									<div class='input-group date' id='datetimepicker1'>
										<input type='text' class="form-control" /> <span
											class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
									<p class="help-block">區間起始日</p>
								</div>
								<div class="form-group">
									<label>上傳佐證資料</label> <input type="file" id="file-upload">
								</div>
							</form>

							<button type="button" class="btn btn-info btn-circle btn-xl" id="pass">
								<i class="fa fa-check"></i>
							</button>
                            <button type="button" class="btn btn-warning btn-circle btn-xl" id="violate">
                                <i class="fa fa-times"></i>
                            </button>							
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

