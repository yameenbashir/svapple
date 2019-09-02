'use strict';

/**
 * OrdersController
 * @constructor
 */
var OrdersController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','OrdersControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,OrdersControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = OrdersControllerPreLoad.loadControllerData();
			$scope.fetchData();
						
		}
	};

	$scope.fetchData = function() {
		$rootScope.ordersLoadedFully = false;
		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){
			$scope.error = true;
			$scope.errorMessage = "No record found";
		}
		else if($scope.data == 'SYSTEMBUSY'){
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$scope.errorMessage = 'An exception occured while validating session !';
			$window.location = '/app/#/login';
		}
		else{
			if($scope.data!=null){
				$scope.orderMainBeansList = $scope.data;
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								bInfo : true
							} );


						}, 10);
			}
		}
		$rootScope.globalPageLoader = false;
	};
	$scope.sessionValidation();	

}];