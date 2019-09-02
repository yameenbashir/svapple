'use strict';

/**
 * RegisterController
 * @constructor
 */
var RegisterController = function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$sce) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.customerBean = {};
	$scope.customerBean.addressBeanList = [];
	$scope.customerBean.addressBeanList[0] = {};
	$scope.customerBean.addressBeanList[1]= {};
	$scope.validOn = {
			value: 'Yes'
	      };
	
	
	$scope.addNewCustomer = function() {
		$scope.newCustomerSuccess = false;
		$scope.newCustomerError = false;
		$scope.addNewCustomerButton = true;
		$scope.customerBean.addressBeanList[0].addressType = "Physical Address";
		$scope.customerBean.addressBeanList[1].addressType = "Postal Address";
		if($scope.validOn.value=="Yes"){
			$scope.customerBean.newsletter = "true";
		}else{
			$scope.customerBean.newsletter = "false";
		}
		$scope.loading = true;	
		$http.post('register/addNewCustomer/'+$scope._s_tk_com, $scope.customerBean)
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

};