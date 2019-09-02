'use strict';

/**
 * PaymentTypeController
 * @constructor
 */
var PaymentTypeController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','PaymentTypeControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,PaymentTypeControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showAddSalesTaxModal = false;
	$scope.paymentTypeSuccess = false;
	$scope.paymentTypeError = false;
	$scope.isCashAdded = false;
	$scope.isCreditCardAdded = false;
	$scope.paymentTypeAddError = false;
	$scope.loadingUpdate = false;
	$scope.loading = false;
	$scope.paymentTypeBean = {};
	
	$scope.systemBusy = 'System is Busy Please try again';
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = PaymentTypeControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.paymentTypeLoadedFully = false;
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
				$scope.paymentTypeList = $scope.data;
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.showAddPaymentTypePopup = function(){
		$scope.paymentTypeAddError = false;
		$scope.showAddPaymentTypeModal = true;
	};
	$scope.showUpdatePaymentTypePopup = function(paymentType){
		$scope.updatePaymentTypeBean = angular.copy(paymentType);
		$scope.showUpdatePaymentTypeModal = true;
	};
	
	
	
	$scope.addPaymentType = function() {
		$scope.paymentTypeSuccess = false;
		$scope.paymentTypeError = false;
		$scope.paymentTypeAddError = false;
		
		if($scope.paymentTypeList!=null){
			for(var i=0;i<$scope.paymentTypeList.length;i++){
				if($scope.paymentTypeList[i].paymentTypeValue=="Cash" && $scope.paymentTypeBean.paymentTypeValue=="Cash"){
					$scope.paymentTypeAddError = true;
					$scope.paymentTypeAddErrorMessage = "Payment type Cash already added.";
					$timeout(function(){
						$scope.paymentTypeAddError =  false;
						 }, 1000);
					return;
				}
				if($scope.paymentTypeList[i].paymentTypeValue=="Credit Card" && $scope.paymentTypeBean.paymentTypeValue=="Credit Card"){
					$scope.paymentTypeAddError = true;
					$scope.paymentTypeAddErrorMessage = "Payment type Credit Card already added.";
					$timeout(function(){
						$scope.paymentTypeAddError = false;
						 }, 1000);
					return;
				}
				
			}
		}
		
		$scope.loading = true;
		$http.post('paymentType/addPaymentType/'+$scope._s_tk_com, $scope.paymentTypeBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.paymentTypeSuccess = true;
				$scope.paymentTypeSuccessMessage = Response.data;
				$scope.showAddPaymentTypeModal = false;
				$timeout(function(){
					$scope.paymentTypeSuccess = false;
					 $route.reload();
					//$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.paymentTypeError = true;
				$scope.paymentTypeErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.paymentTypeError = true;
				$scope.paymentTypeErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.paymentTypeError = true;
			$scope.paymentTypeErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.updatePaymentType = function() {
		$scope.paymentTypeSuccess = false;
		$scope.paymentTypeError = false;
		$scope.loadingUpdate = true;
		$http.post('paymentType/updatePaymentType/'+$scope._s_tk_com, $scope.updatePaymentTypeBean)
		.success(function(Response) {
			$scope.loadingUpdate = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.paymentTypeSuccess = true;
				$scope.paymentTypeSuccessMessage = Response.data;
				$scope.showUpdatePaymentTypeModal = false;
				$timeout(function(){
					$scope.paymentTypeSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
					//$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.paymentTypeError = true;
				$scope.paymentTypeErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.paymentTypeError = true;
				$scope.paymentTypeErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loadingUpdate = false;
			$scope.paymentTypeError = true;
			$scope.paymentTypeErrorMessage = $scope.systemBusy;
		});
	};
	
	
	$scope.sessionValidation();
}];