
<div id="page-wrapper" ng-controller="AssembleDatasetCtrl">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">自組規則管理</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">選擇需要的資料來源及要加入的處理動作</div>
				<!-- /.panel-heading -->
				<div class="panel-body">

					<div class="col-lg-12">
						<form name="form" role="form" novalidate>
							<div class="form-group col-lg-12 "
								ng-class="{'has-error': form.rule_chain_name.$error.required}">
								<label>規則名稱</label> <input type="text" class="form-control"
									name="rule_chain_name" ng-model="ruleChainName"
									placeholder='輸入好記的規則名稱' required />

								<div ng-show="form.$submitted || form.rule_chain_name.$touched">
									<div ng-show="form.rule_chain_name.$error.required">
										<p class="text-danger">必填</p>
									</div>
								</div>
							</div>


							<div class="form-group col-lg-5">
								<label>資料來源1</label> <select class="form-control"
									ng-model="source1" ng-change="source1Change(source1)"
									data-ng-options="a.id+': '+a.name for a in dataSources1 | orderBy:'id'">
								</select>

								<div class="checkbox form-group">
									<label class="checkbox-inline" ng-repeat="col in columns">
										<input type="checkbox" name="colSels[]" value="{{col.value}}"
										ng-checked="selection.indexOf(col.value) > -1"
										ng-click="toggleSelection(col.value)"> {{col.text}}<br>
									</label>
								</div>
							</div>



							<div class="form-group col-lg-2">
								<label>動作</label> <select class="form-control" ng-model="oper"
									ng-change="operChange()">
									<option value="in">IN</option>
									<option value="not_in">NOT IN</option>
									<option value="greater">greater than</option>
									<option value="less">less than</option>
									<option value="contains">contains</option>
									<option value="regex">regular expression</option>
								</select>
							</div>

							<div class="form-group col-lg-5" ng-hide="inputMode">
								<label>資料來源2</label> <select class="form-control"
									ng-model="source2"
									data-ng-options="a.id+': '+a.name for a in dataSources2 | orderBy:'id'">
								</select>
							</div>

							<div class="form-group col-lg-5" ng-show="inputMode" ng-class="{'has-error': form.needle_str.$error.required}">
								<label>字串</label> 
								<input type="text" name="needle_str" class="form-control" ng-model="needleStr" required >
								<div ng-show="form.$submitted || form.needle_str.$touched">
									<div ng-show="form.needle_str.$error.required">
										<p class="text-danger">必填</p>
									</div>
								</div>
							</div>
                        </form>

						<div class="form-group col-lg-12">
							<button type="button" class="btn btn-primary" id="submit"
								ng-click="addRule()">加入</button>
						</div>
					</div>

					<div class="dataTable_wrapper">
						<table
							class="table table-striped table-bordered table-hover dataTable"
							id="dataTables-example">
							<thead>
								<tr>
									<th>項次</th>
									<th>規則名稱</th>
									<th>資料來源1</th>
									<th class="sorting">比較</th>
									<th>資料來源2</th>
									<th></th>
								</tr>
							</thead>
							<tr ng-repeat="x in currentRules | orderBy:'name'">
								<td>{{$index + 1}}</td>
								<td>{{x.ruleChainName}}</td>
								<td>{{x.source1.id + ': ' + x.source1.name}}</td>
								<td>{{x.oper}}</td>
								<td>{{x.source2.id + ': ' + x.source2.name}}</td>
								<td><button type="button" class="btn btn-error" id="delete"
										ng-click="delRule($index, x)">刪除</button>
									<button type="button" class="btn btn-primary" id="run"
										ng-click="run($index)">立刻執行</button></td>
							</tr>
						</table>
					</div>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
			<a class="btn btn-large btn-info" href="/account/plugin/store.do">瀏覽 Plugin Store</a>
			
		</div>		
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<!-- /.row -->

	<!-- /.row -->

	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
