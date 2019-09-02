'use strict';

/**
 * CashManagmentController
 * 
 * @constructor
 */
var CashManagmentController =['$scope', '$http', '$window', '$cookieStore','$rootScope', 'SessionService', 'CashManagmentControllerPreLoad','$timeout','$route' ,function($scope, $http, $window, $cookieStore,$rootScope, SessionService, CashManagmentControllerPreLoad,$timeout,$route) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.registerEnableCashStatus = false;
	$scope.addCashProcessingButton = false;
	$scope.removeCashProcessingButton = false;
	$scope.addCashInFunc = false;
	$scope.addCashOutFunc = false;
	$scope.cashManagmentBean = {};
	$scope.selectedExpenseType="-1";
	$scope.cashModalButtonLabel = "";
	$scope.expenseTypeFunc = function(selectedExpenseType) {
		$scope.selectedExpenseType=selectedExpenseType;
	};
	$scope.addCash = function() {
		$scope.showCashModal = true;
		$scope.cashModalButtonLabel = "Add Cash";
		$scope.addCashInFunc = true;
		
	};
	$scope.removeCash = function() {
		$scope.showCashModal = true;
		$scope.cashModalButtonLabel = "Remove Cash";
		$scope.addCashOutFunc = true;
	};
	$scope.sessionValidation = function(){
		
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.enableCashStatus();
			$scope.data = CashManagmentControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};
	
	$scope.fetchData = function() {
		$rootScope.brandsLoadedFully = false;
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

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

		}else if($scope.data == 'Please open Register.If you want to check Cash Management.'){
			$scope.error = true;
			$scope.disableMngmnt = true;
			$scope.errorMessage = 'Please open Register.If you want to check Cash Management.';
			//alert('Please open Register.If you want to check Cash Management.');
			//$window.location = '/app/#/login';

		}
		else{

			if($scope.data!=null){
				$scope.cashManagmentList = $scope.data.cashManagmentControllerBeans;
				$scope.expenseTypes = $scope.data.expenseTypes;
				
			}
		}
		$rootScope.cashManagementLoadedFully = false;
		$rootScope.globalPageLoader = false;
	};

	
	$scope.addCashIn = function() {
		$scope.addCashProcessingButton = true;
		if($scope.addCashInFunc){
			$scope.cashManagmentBean.cashManagmentType = "IN";
		}else{
			$scope.cashManagmentBean.cashManagmentType = "OUT";
		}
	
		$scope.cashInOut();
		
	};
	
	$scope.cashInOut = function() {
		$scope.cashManagmentBean.expenseType=$scope.selectedExpenseType;
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
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
		
	};
	$scope.enableCashStatus = function() {
		$http.post('cashManagement/enableCashStatus/' + $scope._s_tk_com).success(
				function(Response) {
					$rootScope.emergencyInfoLoadedFully = false;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.registerEnableCashStatus = true;
						
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
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};
	
	$scope.cashManagement = function() {
		$http.post('cashManagement/enableCash/' + $scope._s_tk_com).success(
				function(Response) {
					$rootScope.emergencyInfoLoadedFully = false;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.registerBean = Response.data;
						$cookieStore.put('_e_cOr_gra', "");
						$cookieStore.put('_e_cOr_gra', $scope.registerBean.registerId);
						$window.location = "/app/#/manageRegister";

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
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};
	$scope.sessionValidation();
}];