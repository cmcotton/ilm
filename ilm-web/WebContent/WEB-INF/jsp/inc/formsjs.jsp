<jsp:include page="js.jsp" />
<Script>

//Angular.js
var ilmApp = angular.module('ilmApp', ['ngMaterial', 'chieffancypants.loadingBar', 'ngAnimate']).config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = true;
});

ilmApp.config(['$httpProvider', function ($httpProvider) {    
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);

ilmApp.controller('LogSelectCtrl', function($scope, $http, $filter, $timeout, cfpLoadingBar) {

	var vm = $scope;
	vm.ruleName = [];
	
	// table
	vm.events = [];
	vm.sort = {       
            sortingOrder : 'evtTime',
            reverse : false
        };
    $scope.gap = 5;
    $scope.filteredItems = [];
    $scope.groupedItems = [];
    $scope.itemsPerPage = 5;
    $scope.pagedItems = [];
	vm.currentPage = 0;
	
	
	
	vm.ruleNameSel = '';
	vm.date1 = new Date();
	var numberOfDaysToAdd = -30;
	vm.date1.setDate(vm.date1.getDate() + numberOfDaysToAdd); 
	vm.date2 = new Date();
	
	vm.keyword = '';
	
    var forms = {
	    ruleSel: function() {
	        var rule = $("#report-name").find(":selected").val();
	        return  rule;
	        },
	    getRule: function() {	        
	    },
	    getTable: function() {
	    	return $('#dataTables-example');
	    },
	    postPass: function(rule) {
	        $.post('fillinRule.do', {'oper':'pass', 'rule':rule , 'attach':$('#file-upload').val()}).done(function(json) {
	        });
	    },
	    postViolate: function(rule) {
	        $.post('fillinRule.do', {'oper':'violate', 'rule':rule , 'attach':$('#file-upload').val()}).done(function(json) {
	        });
	    }
	};
	
    angular.element(document).ready(function () {
    	cfpLoadingBar.start();
    	
    	$http.post('/account/regu/getAllRuleEntity.do').then(function(json) {
            console.log(json.data);            

            for (r of json.data) {
                var rule = $.parseJSON(r);
                vm.ruleName.push(rule);
            }
            cfpLoadingBar.complete();
        });
    	
    	console.log('load existing rule chains');
        $http.post('/account/regu/loadRuleChain.do').then(function(response) {
            var rcs = response.data;
            var rc;
            for (rc of rcs) {
                vm.ruleName.push({'no':rc.id,'name':rc.ruleChainName});
            }
        }, function(response) {
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
      });
    });
	
	var loadContent = function() {
		var selVal = vm.ruleNameSel,
		   option = 'postGridData';
		
		vm.events = [];
		
			//columns = col;
			var date1 = vm.date1.getFullYear() + "-" + (vm.date1.getMonth() + 1) + '-' + vm.date1.getDate(),
			    date2 = vm.date2.getFullYear() + "-" + (vm.date2.getMonth() + 1) + '-' + vm.date2.getDate();
			
			$http({
			    method: 'POST',
			    url: '/account/audit/postGridData.do',
			    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformRequest: function(obj) {
			        var str = [];
			        for(var p in obj) {
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));	
			        }
			        return str.join("&");
			    },
			    data: {rule:selVal,date1:date1,date2:date2}
			}).then(function(json) { 
                if (json.error) {
                    console.log(json.error);
                } else {
                    for (var evt of json.data) {
                        vm.events.push(evt);    
                    }
                }               
            });
			
			/*
			$http.post('/account/audit/postGridData.do', {rule:selVal,date1:date1,date2:date2}).then(function(json) {	
				if (json.error) {
					console.log(json.error);
				} else {
					for (evt of json.data) {
						vm.events.push(evt);	
					}
				}				
	        });
			*/
		//});
	}

	function long2ip(ip) {
		  //  discuss at: http://phpjs.org/functions/long2ip/
		  // original by: Waldo Malqui Silva
		  //   example 1: long2ip( 3221234342 );
		  //   returns 1: '192.0.34.166'

		  if (!isFinite(ip))
		    return false;

		  return [ip >>> 24, ip >>> 16 & 0xFF, ip >>> 8 & 0xFF, ip & 0xFF].join('.');
		}
	
	$('#submitLogSelect').click(function() {
		cfpLoadingBar.start();
		loadContent();
		cfpLoadingBar.complete();
	});
	
});
</Script>
