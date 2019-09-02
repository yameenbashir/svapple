'use strict';

/**
 * OutletController
 * @constructor
 */
var OutletController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','OutletControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,OutletControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.outletSuccess = false;
	$scope.outletError = false;
	
	$scope.companyBean = {};
	$scope.systemBusy = 'System is Busy Please try again';
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = OutletControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.outletLoadedFully = false;
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
			if($scope.data.timeZoneBeans!=null){
				$scope.timeZonesList = $scope.data.timeZoneBeans;
			}
			if($scope.data.countryListBeans!=null){
				$scope.countryList = $scope.data.countryListBeans;
			}
			if($scope.data.salesTaxListBeans!=null){
				$scope.salesTaxList = $scope.data.salesTaxListBeans;
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
		$scope.addOutlet = function() {
		$scope.outletSuccess = false;
		$scope.outletError = false;
		$scope.loading = true;
		$http.post('outlet/addOutlet/'+$scope._s_tk_com, $scope.outletBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.outletSuccess = true;
				$scope.outletSuccessMessage = Response.data;
				$timeout(function(){
					$scope.outletSuccess = false;
					$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.outletError = true;
				$scope.outletErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.outletError = true;
				$scope.outletErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
	};
	$scope.sessionValidation();
	
	
}];