<jsp:include page="js.jsp"/>
    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    
    var ilmApp = angular.module('ilmApp', []);
    
    ilmApp.config(['$httpProvider', function ($httpProvider) {    
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    }]);
    
    ilmApp.controller('TablesCtrl', function($scope, $attrs, $http) {
    	var vm = $scope;
    	
    	vm.reports = [{id:1, name:'內部關係人事件', genTime: '2016-01-20'}]
    	
    	vm.download = function(report) {
    		console.log(report);
    		window.open('/account/download/aaa.do?fileName=' + report.name + ".docx", '', '');
        };
    });
    
        $('#submit').click(function() {
            
            $('#dataTables-example').DataTable({
                "ajax" : 'getReportList.html',
                responsive : true
            });           
            
        });
    
    </script>