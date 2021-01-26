'use strict';

/**
 * SalesTaxController
 * @constructor
 */
var SalesTaxController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','SalesTaxControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,SalesTaxControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showAddSalesTaxModal = false;
	$scope.showUpdateSalesTaxModal = false;
	$scope.salesTaxSuccess = false;
	$scope.salesTaxError = false;
	$scope.salesTaxBean = {};
	$scope.updateSalesTax = {};
	$scope.roledId = $cookieStore.get('_s_tk_rId');
	$scope.systemBusy = 'System is Busy Please try again';
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = SalesTaxControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.salesTaxLoadedFully = false;
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
				if($scope.data.salesTaxListBeans!=null){
					$scope.salesTaxList = $scope.data.salesTaxListBeans;
				}
				if($scope.data.outletBeans!=null){
					$scope.outletsList = $scope.data.outletBeans;
					setTimeout(
							function() 
							{
								$('#myTable').DataTable( {
									responsive: true,
									paging: false,
									searching:false,
									bInfo : false
								} );


							}, 10);
				}

			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	
	$scope.showModalForAddSalesTax = function(){
		$scope.showAddSalesTaxModal = true;
	};
	$scope.showModalForUpdateSalesTax = function(salesTax){
		$scope.updateSalesTax = angular.copy(salesTax);
		$scope.showUpdateSalesTaxModal = true;
	};
		
	$scope.addSalesTax = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.salesTaxSuccess = false;
		$scope.salesTaxError = false;
		$scope.loading = true;
		$http.post('salesTax/addSalesTax/'+$scope._s_tk_com, $scope.salesTaxBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.salesTaxSuccess = true;
				$scope.salesTaxSuccessMessage = Response.data;
				$scope.showAddSalesTaxModal = false;
				$timeout(function(){
					$scope.salesTaxSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.salesTaxError = true;
				$scope.salesTaxErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.salesTaxError = true;
				$scope.salesTaxErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.salesTaxError = true;
			$scope.salesTaxErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.editSalesTax = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.salesTaxSuccess = false;
		$scope.salesTaxError = false;
		$scope.loading = true;
		$http.post('salesTax/updateSalesTax/'+$scope._s_tk_com, $scope.updateSalesTax)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.salesTaxSuccess = true;
				$scope.salesTaxSuccessMessage = Response.data;
				$scope.showUpdateSalesTaxModal = false;
				$timeout(function(){
					$scope.salesTaxSuccess = false;
					$window.location = Response.layOutPath;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.salesTaxError = true;
				$scope.salesTaxErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.salesTaxError = true;
				$scope.salesTaxErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.salesTaxError = true;
			$scope.salesTaxErrorMessage = $scope.systemBusy;
		});
	};

	
	
	$scope.editOutlet = function(outlet){
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$cookieStore.put('_e_cOt_gra',outlet.outletId) ;
		$window.location = "/app/#/manageOutlet";

	};
	$scope.sessionValidation();
}];