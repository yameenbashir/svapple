'use strict';

/**
 * RegisterDetailController
 * @constructor
 */
var RegisterDetailController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','RegisterDetailControllerPreLoad','$timeout','$route',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,RegisterDetailControllerPreLoad,$timeout,$route) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.registerOpen = false;
	$scope.sesssionValidation = function() {

		if (SessionService.validate()) {
			$scope._s_tk_com = $cookieStore.get('_s_tk_com');

			$scope.controllerBean = RegisterDetailControllerPreLoad.loadControllerData();
			if($scope.controllerBean=="No record found !"){
				$scope.registerOpen = true;
			}else{
				$scope.dailyRegisterBean = $scope.controllerBean.dailyRegisterBean;
				$scope.cashManagmenBeans = $scope.controllerBean.cashManagmenBeans;
				$scope.lastDayilyRegisterExist = $scope.controllerBean.lastDayilyRegisterExist;
				$scope.registerOpen = !$scope.controllerBean.openDayilyRegisterExist;
				$rootScope.registerCloseLoadedFully = false;
			}
			
			$rootScope.globalPageLoader = false;
			
		}
	};
	
	$scope.closeRegister = function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$http.post('registerClose/closeRegister/' + $scope._s_tk_com,
				$scope.dailyRegisterBean)
				.success(
						function(Response) {
							$scope.loading = false;

							$scope.responseStatus = Response.status;
							if ($scope.responseStatus == 'SUCCESSFUL') {
								$scope.success = true;
								// $scope.InvoiceMainBean = {};
								$scope.success = true;
								$scope.successMessage = Response.data;
								// $scope.salesComplete = true;
								$timeout(function() {
									$scope.success = false;
									$route.reload();
								}, 1500);

							} else if ($scope.responseStatus == 'INVALIDSESSION'
								|| $scope.responseStatus == 'SYSTEMBUSY') {
								$scope.error = true;
								$scope.errorMessage = Response.data;
								$window.location = Response.layOutPath;
							} else {
								$scope.error = true;
								$scope.errorMessage = Response.data;
							}
						}).error(function() {
							$scope.loading = false;
							$scope.error = true;
							$scope.errorMessage = $scope.systemBusy;
						});
	};
	
	$scope.openRegister = function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loadingOpenRegister = true;
		$http.post('sell/openRegister/' + $scope._s_tk_com).success(
				function(Response) {
					$scope.loading = false;

					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.successOpen = true;
						$scope.registerOpen = false;
						$scope.loadingOpenRegister = false;
						$scope.successMessage = Response.data;
						$timeout(function() {
							$scope.successOpen = false;
							$scope.showCashModal = true;
						}, 1000);
					} else if ($scope.responseStatus == 'INVALIDSESSION'
						|| $scope.responseStatus == 'SYSTEMBUSY') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
					}
				}).error(function() {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = $scope.systemBusy;
				});
	};
	$scope.sesssionValidation();
	$scope.cashManagmentBean = {};
	$scope.cashManagmentBean.cashType = "Cash";
	$scope.cashManagmentBean.cashManagmentType = "IN";
	$scope.cashManagmentBean.notes = "Opening float";
	
	$scope.cashInOut = function() {
		$scope.addCashProcessingButton = true;
		$http.post('cashManagement/cashInOut/' + $scope._s_tk_com,$scope.cashManagmentBean).success(
				function(Response) {
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.showCashModal = false;
						$scope.cashManagmentList = Response.data;
						$scope.addCashProcessingButton = false;
						$scope.removeCashProcessingButton = false;
						$timeout(function(){
						      $route.reload();
						    }, 1000);
					} else if ($scope.responseStatus == 'SYSTEMBUSY'
							|| $scope.responseStatus == 'INVALIDUSER'
							|| $scope.responseStatus == 'ERROR'
							|| $scope.responseStatus == 'INVALIDSESSION') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
					}

				}).error(function() {
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
		
	};
}];