<jsp:include page="js.jsp" />

<!-- Morris Charts JavaScript -->
<script src="../bower_components/raphael/raphael-min.js"></script>
<script src="../bower_components/morrisjs/morris.min.js"></script>
<script src="../js/morris-data.js"></script>

<!-- Flot Charts JavaScript -->
<script src="../bower_components/flot/excanvas.min.js"></script>
<script src="../bower_components/flot/jquery.flot.js"></script>
<script src="../bower_components/flot/jquery.flot.pie.js"></script>
<script src="../bower_components/flot/jquery.flot.resize.js"></script>
<script src="../bower_components/flot/jquery.flot.time.js"></script>
<script
	src="../bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
<script src="../js/flot-data.js"></script>


<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/zh_TW/sdk.js#xfbml=1&version=v2.5";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<script>
	var indexObj = {};
	
	// Angular.js
	var ilmApp = angular.module('ilmApp', []);

	ilmApp.controller('IndexCtrl', function($scope, $http, $timeout) {
	    var vm = $scope;
		
		vm.applicableRegus = [];
		vm.nonapplicableRegus = [];
		vm.passRegus = [];
		vm.nonexecRegus = [];
		vm.violateRegus = [];
		vm.latestRules = [
		                  {
		                	  name: '內部關係人',
		                	  content: '王曉明修改父薪資金額',
		                	  ip: '1.1.1.1',
		                	  time: '2015-12-23 16:11:23'
		                  }
		                  ];
		
		vm.initReguInfo = function() {
			vm.applicableRegus = [];
			vm.nonapplicableRegus = [];
			vm.passRegus = [];
			vm.nonexecRegus = [];
			vm.violateRegus = [];

			$http.post('/account/regu/getAllReguEntity.do').success(function(data, status, headers, config) {
				var size = data.length || 0;

				for (var i = 0; i < size; i++) {
				 var reguEntity = $.parseJSON(data[i]);

				 if (reguEntity.applicable == 1) {
					 vm.applicableRegus.push(reguEntity.no + " " + reguEntity.name);
				    
				     // pass
				     if (reguEntity.pass == 1) {
				    	 vm.passRegus.push(reguEntity.no + " " + reguEntity.name);
				     } else if (reguEntity.nonexec == 1) { // nonexec
				    	 vm.nonexecRegus.push(reguEntity.no + " " + reguEntity.name);
				     } else if (reguEntity.violate == 1) { // violate
				    	 vm.violateRegus.push(reguEntity.no + " " + reguEntity.name);
				     }

				 } else {
					 vm.nonapplicableRegus.push(reguEntity.no + " " + reguEntity.name);
				 }
				}

				vm.applicableRegusSize = vm.applicableRegus.length;
				vm.passRegusSize = vm.passRegus.length;
				vm.nonexecRegusSize = vm.nonexecRegus.length;
				vm.violateRegusSize = vm.violateRegus.length;

				vm.initReguDetail(); // run once
			});
			  
			$http.post('/account/audit/getRecentEvent.do').success(function(data, status, headers, config) {
				var size = data.length || 0;
				console.log(size);
				vm.latestRules = data; 
			});
		};
		
		vm.initReguDetail = function() {
			var reguList = '';
			
			vm.applicableRegus.forEach(function(element, index) {
			    reguList += element;
			});

			reguList += "<br><br>不適用<br>";

			vm.nonapplicableRegus.forEach(function(element, index) {
			    reguList += element;
			});

			vm.reguApplicableList = reguList;
			
			// 符合項目數detail
			vm.passRegus.forEach(function(element, index) {
			    vm.passList += element;
			});			
			
			// 未執行項目數detail
			vm.nonexecRegus.forEach(function(element, index) {
			    vm.nonexecList += element;
			});			
			
			// 違反項目數detail
			vm.violateRegus.forEach(function(element, index) {
			    vm.violateList += element;
			}); 
		};
	
		// vm.initReguInfo(); // run once
		
	      // refresh all info per minute
        (function update() {
            $timeout(update, 60000);
            console.log('refresh');
            vm.initReguInfo();
        }());		
	});
</script>
