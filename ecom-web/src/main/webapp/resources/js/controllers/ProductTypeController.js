'use strict';

/**
 * ProductTypeController
 * @constructor
 */
var ProductTypeController = ['$scope', '$http', '$timeout','$route', '$window','$cookieStore','$rootScope','SessionService','ProductTypeControllerPreLoad',function($scope, $http, $timeout, $route, $window,$cookieStore,$rootScope,SessionService,ProductTypeControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showAddProducTypeModal = false;
	$scope.showUpdateProductTypeModal = false;
	$scope.productTypeBean = {};
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ProductTypeControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.productTypeLoadedFully = false;
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
				$scope.productTypeList = $scope.data;
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								bInfo : true
							} );
						}, 10);
			}

		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.showAddProductTypePopup = function(){
		$scope.productTypeBean = {};
		$scope.showAddProducTypeModal = true;
	};
	
	$scope.addProductType = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.productTypeSuccess = false;
		$scope.productTypeError = false;
		$scope.loading = true;
		$http.post('productType/addProductType/'+$scope._s_tk_com, $scope.productTypeBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.productTypeSuccess = true;
				$scope.productTypeSuccessMessage = 'Request Processed successfully!';
				$scope.showAddProducTypeModal = false;
				$timeout(function(){
					$scope.productTypeSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 1500);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productTypeError = true;
			$scope.productTypeErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.showUpdateProductTypePopup = function(productType){
		$scope.updateProductTypeBean = {};
		$scope.updateProductTypeBean = angular.copy(productType); 
		$scope.showUpdateProductTypeModal = true;
	};
	
	$scope.updateProductType = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.productTypeSuccess = false;
		$scope.productTypeError = false;
		$scope.loading = true;
		$http.post('productType/updateProductType/'+$scope._s_tk_com, $scope.updateProductTypeBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.updateProductTypeBean = {};
				$scope.productTypeSuccess = true;
				$scope.productTypeSuccessMessage = Response.data;
				$scope.showUpdateProductTypeModal = false;
				$timeout(function(){
					$scope.productTypeSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productTypeError = true;
			$scope.productTypeErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.sessionValidation(); 
}];