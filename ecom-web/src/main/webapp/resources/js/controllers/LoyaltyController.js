'use strict';

/**
 * LoyaltyController
 * @constructor
 */
var LoyaltyController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','LoyaltyControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,LoyaltyControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loyaltySuccess = false;
	$scope.loyaltyError = false;
	$scope.companyLoyalty = {};
	$scope.gui = {};
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = LoyaltyControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.loyaltyLoadedFully = false;
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
				$scope.companyLoyalty = $scope.data;
				if($scope.companyLoyalty.loyaltyEnabled=="true"){
					$scope.gui.loyaltyEnabled = true;
				}else{
					$scope.gui.loyaltyEnabled = false;
				}
				if($scope.companyLoyalty.loyaltyBonusEnabled=="true"){
					$scope.gui.loyaltyBonusEnabled = true;
				}else{
					$scope.gui.loyaltyBonusEnabled = false;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.updateCompanyLoyalty = function() {
		$scope.loyaltySuccess = false;
		$scope.loyaltyError = false;
		$scope.companyLoyaltyBean = angular.copy($scope.companyLoyalty);
		if(parseInt($scope.companyLoyaltyBean.loyaltyInvoiceAmount)<=parseInt($scope.companyLoyaltyBean.loyaltyAmount)){
			$scope.loyaltyError = true;
			$scope.loyaltyErrorMessage = "Loyalty invoice amount should be greater than Loyalty amount.";
			return;
		}
		$scope.loading = true;
		$scope.companyLoyaltyBean.loyaltyEnabled =	$scope.gui.loyaltyEnabled;
		$scope.companyLoyaltyBean.loyaltyBonusEnabled =	$scope.gui.loyaltyBonusEnabled;
		$http.post('loyalty/updateCompanyLoyalty/'+$scope._s_tk_com, $scope.companyLoyaltyBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.loyaltySuccess = true;
				$scope.loyaltySuccessMessage = Response.data;
				$timeout(function(){
					$scope.loyaltySuccess = false;
					
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.loyaltyError = true;
				$scope.loyaltyErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.loyaltyError = true;
				$scope.loyaltyErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.loyaltyError = true;
			$scope.loyaltyErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();
	
	$rootScope.globalPageLoader = false;
}];