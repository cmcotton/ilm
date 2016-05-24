<jsp:include page="js.jsp"/>
<script>
//Angular.js
var ilmApp = angular.module('ilmApp', []);

ilmApp.config(['$httpProvider', function ($httpProvider) {    
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);

ilmApp.controller('StoreCtrl', function($scope, $http) {

    var vm = $scope;
    
    vm.plugins = [];
    
    // load rule blocks when document ready
    angular.element(document).ready(function () {
           console.log('start store');
           $http.post('getAllPlugins.do').then(function(res) {
            	console.log(res);
            	
                var plugins = res.data;
                
                for (p of plugins) {
                    vm.plugins.push(p);
                }
                
            }, function(response) {
              $scope.data = response.data || "Request failed";
              $scope.status = response.status;
          });
    });
    
    vm.buy = function(ruleId) {
    	$http.post('buyPlugin.do','ruleId=' + ruleId).
    	then(function(res) {
            console.log(res);
            
            for (var p of vm.plugins) {
            	if (p.id == ruleId) {
            		p.installed = true;
            		break;
            	}
            }
            
            alert('購買成功');
            
        }, function(response) {
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
      });
    };
    
    vm.del = function(ruleId) {
        $http.post('delPlugin.do','ruleId=' + ruleId).
        then(function(res) {
            console.log(res);
            
            for (var p of vm.plugins) {
                if (p.id == ruleId) {
                    p.installed = false;
                    break;
                }
            }
            
            alert('已移除');
            
        }, function(response) {
          $scope.data = response.data || "Request failed";
          $scope.status = response.status;
      });
    };
    
    
});

</script>