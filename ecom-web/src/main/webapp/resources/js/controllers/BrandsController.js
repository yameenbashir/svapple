'use strict';

/**
 * BrandsController
 * @constructor
 */
var BrandsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','BrandsControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,BrandsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.brandSuccess = false;
	$scope.brandError = false;
	$scope.brandBean = {};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = BrandsControllerPreLoad.loadControllerData();
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

		}
		else{

			if($scope.data!=null){
				$scope.brandList = $scope.data;
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

	$scope.showAddBrandPopup = function(){
		$scope.brandBean = {};
		$scope.showNewBrandModal = true;
	};

	$scope.addBrand = function() {
		$scope.brandSuccess = false;
		$scope.brandError = false;
		$scope.loading = true;
		$http.post('brands/addBrand/'+$scope._s_tk_com, $scope.brandBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.brandBean = {};
				$scope.brandSuccess = true;
				$scope.brandSuccessMessage = Response.data;
				$scope.showNewBrandModal = false;
				$timeout(function(){
					$scope.brandSuccess = false;
					$timeout(function(){
						$route.reload();
					}, 500);
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.brandError = true;
			$scope.brandErrorMessage = $scope.systemBusy;
		});
	};

	$scope.showUpdateBrandPopup = function(brand){
		$scope.updatebrandBean = {};
		$scope.updatebrandBean = angular.copy(brand); 
		$scope.showUpdateBrandModal = true;
	};

	$scope.updateBrand = function() {
		$scope.brandSuccess = false;
		$scope.brandError = false;
		$scope.loading = true;
		$http.post('brands/updateBrand/'+$scope._s_tk_com, $scope.updatebrandBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.updatebrandBean = {};
				$scope.brandSuccess = true;
				$scope.brandSuccessMessage = Response.data;
				$scope.showUpdateBrandModal = false;
				$timeout(function(){
					$scope.brandSuccess = false;
					$timeout(function(){
						$route.reload();
					}, 500);
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.brandError = true;
			$scope.brandErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();
	
}];