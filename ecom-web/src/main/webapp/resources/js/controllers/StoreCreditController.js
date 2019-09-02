'use strict';

/**
 * StoreCreditController
 * @constructor
 */
var StoreCreditController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','StoreCreditControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,StoreCreditControllerPreLoad) {
 
 $rootScope.MainSideBarhideit = false;
 $rootScope.MainHeaderideit = false;
 $scope.loading = false;
 $scope.companyBean = {};
 $scope.enableStoreCreditShow = {};
 
 $scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = StoreCreditControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.storeCreditLoadedFully = false;
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
				$scope.companyBean = $scope.data;
				if($scope.companyBean.enableStoresCredit == 'true')
				{
					$scope.enableStoreCreditShow.value = true;
				}
				else
				{
					$scope.enableStoreCreditShow.value = false;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
 

	$scope.updateStoreCredit = function() {
		$scope.storeCreditSuccess = false;
		$scope.storeCreditError = false;
		$scope.companyBean.enableStoresCredit = $scope.enableStoreCreditShow.value ;
		$scope.loading = true;
		$http.post('storeCredit/updateStoreCreditbyCompany/'+$scope._s_tk_com, $scope.companyBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {				
				$scope.storeCreditSuccess = true;
				$scope.storeCreditSuccessMessage = Response.data;				
				$timeout(function(){
					$scope.storeCreditSuccess = false;
					//$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.storeCreditError = true;
				$scope.storeCreditErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.storeCreditError = true;
				$scope.storeCreditErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.storeCreditError = true;
			$scope.storeCreditErrorMessage = $scope.systemBusy;
		});
	};	
 
	$scope.sessionValidation(); 
}];