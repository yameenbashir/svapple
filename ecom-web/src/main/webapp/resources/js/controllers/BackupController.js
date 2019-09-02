'use strict';

/**
 * BrandsController
 * @constructor
 */
var BackupController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.success = false;
	$scope.error = false;
	$scope.loading1 = false;
	$scope.success1 = false;
	$scope.error1 = false;
	$scope.uploadme = {};
	$scope.filepath="";
	$scope.uploadme.src = "";
	
	$scope.sessionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
		}
	};
	
	$scope.takeBackup = function(){

		$scope.loading = true;
		$http.post('backup/takeBakup/' + $scope._s_tk_com).success(
				function(Response) {
					$scope.loading = false;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.success = true;
						$scope.successMessage = Response.data;
						
						$timeout(function(){
							$scope.success = false;
						}, 2000);
					} else if ($scope.responseStatus == 'SYSTEMBUSY'
						|| $scope.responseStatus == 'INVALIDUSER'
							|| $scope.responseStatus == 'ERROR') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$timeout(function(){
							$scope.error = false;
						}, 2000);
						//$window.location = Response.layOutPath;
					} else if ($scope.responseStatus == 'INVALIDSESSION') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
					}
					else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
					}

				}).error(function() {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = $rootScope.systemBusy;
				});

	};
	
	$scope.reStoreBackup = function(){

		$scope.loading1 = true;
		$http.post('backup/reStoreBackup/' + $scope._s_tk_com).success(
				function(Response) {
					$scope.loading1 = false;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.success1 = true;
						$scope.successMessage1 = Response.data;
						
						$timeout(function(){
							$scope.success1 = false;
						}, 2000);
					} else if ($scope.responseStatus == 'SYSTEMBUSY'
						|| $scope.responseStatus == 'INVALIDUSER'
							|| $scope.responseStatus == 'ERROR') {
						$scope.error1 = true;
						$scope.errorMessage1 = Response.data;
						$timeout(function(){
							$scope.error1 = false;
						}, 2000);
						//$window.location = Response.layOutPath;
					} else if ($scope.responseStatus == 'INVALIDSESSION') {
						$scope.error1 = true;
						$scope.errorMessage1 = Response.data;
						$window.location = Response.layOutPath;
					}
					else {
						$scope.error1 = true;
						$scope.errorMessage1 = Response.data;
					}

				}).error(function() {
					$scope.loading1 = false;
					$scope.error1 = true;
					$scope.errorMessage = $rootScope.systemBusy;
				});

	};
	
	$scope.sessionValidation();
	
}];