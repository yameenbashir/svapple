'use strict';

/**
 * NewCustomerController
 * @constructor
 */
var NewCustomerController = ['$scope', '$http', '$filter', '$timeout','$window','$cookieStore','$rootScope','SessionService','NewCustomerControllerPreLoad',function($scope, $http, $filter, $timeout,$window,$cookieStore,$rootScope,SessionService,NewCustomerControllerPreLoad) {

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
			$scope.data = NewCustomerControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.newCustomerLoadedFully = false;
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
				$scope.customerGroupList = $scope.data;
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.populatePosatlAddress = function(){
		angular.copy($scope.customerBean.addressBeanList[0],$scope.customerBean.addressBeanList[1]);		
	};


	$scope.fetchAllCustomerGroups = function() {
		$http.post('newCustomer/getAllCustomerGroups').success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.customerGroupList = Response.data;
			}else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'
						||$scope.responseStatus =='INVALIDSESSION'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}

		}).error(function() {
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});

	};

	$scope.addNewCustomer = function() {
		$scope.newCustomerSuccess = false;
		$scope.newCustomerError = false;
		$scope.addNewCustomerButton = true;
		$scope.customerBean.addressBeanList[0].addressType = "Physical Address";
		$scope.customerBean.addressBeanList[1].addressType = "Postal Address";
		$scope.loading = true;	
		$http.post('newCustomer/addNewCustomer/'+$scope._s_tk_com, $scope.customerBean)
		.success(function(Response) {
						
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.addNewCustomerButton = false;
				$scope.newCustomerSuccess = true;
				$scope.newCustomerSuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.newCustomerSuccess = false;
					$scope.loading = false;	
					$scope.customerBean = {};
					$window.location = Response.layOutPath;
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.newCustomerError = true;
				$scope.newCustomerErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.newCustomerError = true;
				$scope.newCustomerErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.newCustomerError = true;
			$scope.newCustomerErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();

}];