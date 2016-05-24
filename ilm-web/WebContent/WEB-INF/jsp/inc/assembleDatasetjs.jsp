<jsp:include page="js.jsp" />
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>

//Angular.js
var ilmApp = angular.module('ilmApp', ['chieffancypants.loadingBar', 'ngAnimate']).config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = true;
});

ilmApp.config(['$httpProvider', function ($httpProvider) {    
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);

ilmApp.controller('AssembleDatasetCtrl', function($scope, $http, $timeout, cfpLoadingBar) {

	var vm = $scope;
		
	vm.currentRules = [];
	
	vm.dataSources1 = [];   
	vm.dataSources2 = [];
	
	var ruleBlocks = {};
	
	vm.inputMode = false;
	
	vm.columns =  [{'value':'', 'text':''}];
	vm.selectedCols = [];
	
	// private member
	var opersAgainstStr = ['contains', 'regex', 'greater', 'less'];
	
	// load rule blocks when document ready
	angular.element(document).ready(function () {
		   console.log('load rule blocks');
		   $http.post('/account/regu/loadRuleBlock.do').
	        then(function(response) {
	        	var ruleBlocks = response.data;
	        	var rb;
	        	for (rb of ruleBlocks) {
	        		vm.dataSources1.push({id: rb.id, name: rb.name});
	        		vm.dataSources2.push({id: rb.id, name: rb.name});
	        		
	        		ruleBlocks[rb.id] = rb.name;
	        	}
	        	
	        }, function(response) {
	          $scope.data = response.data || "Request failed";
	          $scope.status = response.status;
	      });
		   
		  console.log('load existing rule chains');
		  $http.post('/account/regu/loadRuleChain.do').
          then(function(response) {
              var rcs = response.data;
              var rc;
              for (rc of rcs) {
            	  vm.currentRules.push(rc);
            	  
            	  // rule chain as input, this is pipeline
            	  vm.dataSources1.push({id: rc.id, name: rc.ruleChainName});
                  vm.dataSources2.push({id: rc.id, name: rc.ruleChainName});
              }
          }, function(response) {
            $scope.data = response.data || "Request failed";
            $scope.status = response.status;
        });
		  
	});
	
	vm.addRule = function() {

		vm.source2 = vm.source2 || {name:'string', id:vm.needleStr};
			
		console.log(vm.source2);
		
		vm.currentRules.push({
			source1: vm.source1,
			source2: vm.source2,
			oper: vm.oper,
			ruleChainName: vm.ruleChainName
		});
		
		// insert into DB
		$http.post('/account/regu/addRuleChain.do', 'name=' + vm.ruleChainName + 
                '&operand1=' + vm.source1.id + '&operand1Name=' + vm.source1.name +  
                '&oper=' + vm.oper + '&operand2=' + vm.source2.id + '&operand2Name=' + vm.source2.name).
        then(function(response) {
          $scope.status = response.status;
          $scope.data = response.data;
          vm.source2 = undefined;
        }, function(response) {
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
        });
		
		//vm.source2 = undefined;
	};
	
   vm.delRule = function(index, x) {
	   // persist
       $http.post('/account/regu/delRuleChain/' + x.oper + '/'+ x.source1.id + '/' + x.source2.id + '.do').
       then(function(response) {
         $scope.status = response.status;
         $scope.data = response.data;
         
         // UI
         vm.currentRules.splice(index, 1);         
       }, function(response) {
         $scope.data = response.data || "Request failed";
         $scope.status = response.status;
       });
        
    };
    
    vm.operChange = function(index) {
        
    	if (opersAgainstStr.indexOf(vm.oper) >= 0) {    	
        	vm.inputMode = true;
        } else {
        	console.log("in or not in");
        	vm.inputMode = false;
        }
    };
    
    vm.source1Change = function(index) {
    	if (index.id == 'rbFile1') {
    		vm.columns = [{'value':'datetime', 'text':'日期時間'},
                          {'value':'name', 'text':'帳號'},
                          {'value':'content', 'text':'內容'},
                          {'value':'ip', 'text':'IP 位址'},
                          ]	
    	} else {
    		vm.columns = [];
    	}
        
    };
    
    vm.run = function(index) {
    	cfpLoadingBar.start();
    	
    	$http.post('/account/regu/runRuleChain.do', 'ruleId=' + index + '&name=' + vm.currentRules[index].ruleChainName + 
    			'&operand1=' + vm.currentRules[index].source1.id + 
    			'&oper=' + vm.currentRules[index].oper + '&operand2=' + vm.currentRules[index].source2.id).
        then(function(response) {
        	cfpLoadingBar.complete();
        	
          $scope.status = response.status;
          $scope.data = response.data;          
        }, function(response) {
        	cfpLoadingBar.complete();
        	
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
        });
    };
    
    vm.toggleSelection = function(value) {
    	console.log(value);
    	vm.selectedCols.push(value);
    };
    
});
	
</script> 