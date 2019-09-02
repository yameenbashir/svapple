'use strict';

/**
 * manageCustomerController
 * @constructor
 */
var ManageCustomerController = ['$scope', '$http', '$filter', '$timeout','$window','$cookieStore','$rootScope','SessionService','ManageCustomerControllerPreLoad',function($scope, $http, $filter, $timeout,$window,$cookieStore,$rootScope,SessionService,ManageCustomerControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.customerBean = {};
	$scope.customerBean.addressBeanList = [];
	$scope.customerBean.addressBeanList[0] = {};
	$scope.customerBean.addressBeanList[1]= {};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ManageCustomerControllerPreLoad.loadControllerData();
			$scope.fetchData();

		}
	};

	$scope.fetchData = function() {
		$rootScope.manageCustomerLoadedFully = false;
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
				$scope.customerGroupList = $scope.data;
				$scope.customerBean = $scope.data.value;

			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.populatePosatlAddress = function(){
		angular.copy($scope.customerBean.addressBeanList[0],$scope.customerBean.addressBeanList[1]);	
	};

	$scope.updateCustomer = function() {
		$scope.manageCustomerSuccess = false;
		$scope.manageCustomerError = false;
		$scope.loading = true;
		$scope.customerBean.addressBeanList[0].addressType = "Physical Address";
		$scope.customerBean.addressBeanList[1].addressType = "Postal Address";
		$http.post('manageCustomer/updateCustomer/'+$scope._s_tk_com, $scope.customerBean)
		.success(function(Response) {
			$scope.loading = false;					
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.customerBean = {};
				$scope.manageCustomerSuccess = true;
				$scope.manageCustomerSuccessMessage = Response.data;
				$timeout(function(){
					$scope.manageCustomerSuccess = false;
					$window.location = Response.layOutPath;
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.manageCustomerError = true;
				$scope.manageCustomerErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.manageCustomerError = true;
				$scope.manageCustomerErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.manageCustomerError = true;
			$scope.manageCustomerErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();
	$rootScope.globalPageLoader = false;
}];