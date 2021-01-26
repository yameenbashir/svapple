'use strict';

/**
 * RegisterController
 * @constructor
 */
var RegisterController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','RegisterControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,RegisterControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.registerSuccess = false;
	$scope.registerError = false;
	$scope.registerBean = {};
	$scope.outletBean = {};
	$scope.systemBusy = 'System is Busy Please try again';
	$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
	/*localforage.getItem('_r_cO_gra').then(function(value) {
		$scope.outletBean = value;
	});*/
	
	
	$scope.cashManagement = {
	        value: 'No'
	      };
	$scope.userNextSale = {
	        value: 'No'
	      };
	$scope.receiptEmail = {
	        value: 'No'
	      };
	$scope.receiptPrint = {
	        value: 'Yes'
	      };
	$scope.receiptPrintNotes = {
	        value: 'No'
	      };
	$scope.discountsOnReceipt = {
	        value: 'Yes'
	      };
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = RegisterControllerPreLoad.loadControllerData();
			$rootScope.registerLoadedFully = false;
			$scope.outletBean = $scope.data;
			
		}
	};
	
	
	
	$scope.addRegister = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.registerSuccess = false;
		$scope.registerError = false;
		$scope.loading = true;
		$scope.registerBean.outletId = $scope.outletBean.outletId;
		$scope.registerBean.cacheManagementEnable = $scope.cashManagement.value;
		$scope.registerBean.selectNextUserForSale = $scope.userNextSale.value;
		$scope.registerBean.emailReceipt = $scope.receiptEmail.value;
		$scope.registerBean.printReceipt = $scope.receiptPrint.value;
		$scope.registerBean.printNotesOnReceipt = $scope.receiptPrintNotes.value;
		$scope.registerBean.showDiscountOnReceipt = $scope.discountsOnReceipt.value;
				
		$http.post('register/addRegister/'+$scope._s_tk_com, $scope.registerBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.registerSuccess = true;
				$scope.registerSuccessMessage = Response.data;
				$timeout(function(){
					$scope.registerSuccess = false;
					$window.location = Response.layOutPath;
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.registerError = true;
				$scope.registerErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.registerError = true;
				$scope.registerErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.registerError = true;
			$scope.registerErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.sessionValidation();
	$rootScope.globalPageLoader = false;
}];