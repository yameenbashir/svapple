'use strict';

/**
 * ManageRegisterController
 * @constructor
 */
var ManageRegisterController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','ManageRegisterControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,ManageRegisterControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.registerSuccess = false;
	$scope.registerError = false;
	$scope.registerBean = {};
	$scope.systemBusy = 'System is Busy Please try again';
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
			$scope.data = ManageRegisterControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.manageRegisterLoadedFully = false;
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
				$scope.registerBean = $scope.data;
				$scope.cashManagement.value = $scope.registerBean.cacheManagementEnable;
				$scope.userNextSale.value = $scope.registerBean.selectNextUserForSale;
				$scope.receiptEmail.value = $scope.registerBean.emailReceipt;
				$scope.receiptPrint.value = $scope.registerBean.printReceipt;
				$scope.receiptPrintNotes.value = $scope.registerBean.printNotesOnReceipt;
				$scope.discountsOnReceipt.value = $scope.registerBean.showDiscountOnReceipt;
			}
			
		}
		$rootScope.globalPageLoader = false;
	};
	
	
	$scope.sesstionValidation = function(){
		
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.registerBean = $cookieStore.get('_e_cOr_gra');
			
			$scope.cashManagement.value = $scope.registerBean.cacheManagementEnable;
			$scope.userNextSale.value = $scope.registerBean.selectNextUserForSale;
			$scope.receiptEmail.value = $scope.registerBean.emailReceipt;
			$scope.receiptPrint.value = $scope.registerBean.printReceipt;
			$scope.receiptPrintNotes.value = $scope.registerBean.printNotesOnReceipt;
			$scope.discountsOnReceipt.value = $scope.registerBean.showDiscountOnReceipt;
			
		}
	};
	
	
	
	$scope.updateRegister = function() {
		$scope.registerSuccess = false;
		$scope.registerError = false;
		$scope.loading = true;
		//$scope.registerBean.outletId = $scope.outletBean.outletId;
		$scope.registerBean.cacheManagementEnable = $scope.cashManagement.value;
		$scope.registerBean.selectNextUserForSale = $scope.userNextSale.value;
		$scope.registerBean.emailReceipt = $scope.receiptEmail.value;
		$scope.registerBean.printReceipt = $scope.receiptPrint.value;
		$scope.registerBean.printNotesOnReceipt = $scope.receiptPrintNotes.value;
		$scope.registerBean.showDiscountOnReceipt = $scope.discountsOnReceipt.value;
				
		$http.post('manageRegister/updateRegister/'+$scope._s_tk_com, $scope.registerBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
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
		
}];