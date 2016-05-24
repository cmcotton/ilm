<jsp:include page="js.jsp"/>
<script>
//Angular.js
var ilmApp = angular.module('ilmApp', ['lr.upload', 'ngMaterial', 'chieffancypants.loadingBar', 'ngAnimate']).config(function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = true;
});

ilmApp.config(['$httpProvider', function ($httpProvider) {    
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);

ilmApp.controller('RuleCtrl', function($scope, $http, $timeout, cfpLoadingBar) {

    var vm = $scope;
    
    vm.currentRules = [];
    vm.ruleClicked = '';
    
    // load rule blocks when document ready
    angular.element(document).ready(function () {
    	cfpLoadingBar.start();
           console.log('Hello World');
           $http.post('getAllRuleEntity.do').then(function(response) {
                var rules = response.data;
                var r;
                for (r of rules) {
                	r = angular.fromJson(r);
                    vm.currentRules.push({id: r.no, name: r.name, desc: r.desc});
                }
                cfpLoadingBar.complete();
            }, function(response) {
            	cfpLoadingBar.complete();
              $scope.data = response.data || "Request failed";
              $scope.status = response.status;
          });
    });
    
    vm.delRule = function(index) {
    	cfpLoadingBar.start();
        
    	vm.currentRules.splice(index, 1);
        
    	cfpLoadingBar.complete();
    };
    
    vm.run = function(index) {
    	cfpLoadingBar.start();
    	
    	
        $http.post('/account/regu/runRule.do', 'ruleId=' + index).
        then(function(response) {
        	cfpLoadingBar.complete();
        	console.log('complete');
        	
          $scope.status = response.status;
          $scope.data = response.data;
        }, function(response) {
        	cfpLoadingBar.complete();
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
      });
    };
    
    vm.showDesc = function(index) {
        vm.ruleClicked = index.desc;
    };
});

</script>