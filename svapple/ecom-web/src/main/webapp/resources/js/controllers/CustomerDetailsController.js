'use strict';

/**
 * CustomerDetailsController
 * @constructor
 */
var CustomerDetailsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','CustomerDetailsControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,CustomerDetailsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.customerBean = {};
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = CustomerDetailsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			//$scope.customerBean = $cookieStore.get('_e_cOt_jir');
			
		}
	};

	$scope.fetchData = function() {
		$rootScope.customerDetailsLoadedFully = false;
		if($scope.data == 'NORECORDFOUND'){

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
				$scope.customerBean = $scope.data;
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								order: [[ 1, "asc" ]],
								bInfo : true
							} );
						}, 10);
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.updateCustomer = function() {
		
		$window.location = "/app/#/manageCustomer";
	};
	$scope.sessionValidation();
}];