'use strict';

/**
 * HomeController
 * @constructor
 */
var HomeController = function($scope, $http, $window,$cookieStore,$rootScope,$timeout) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showModalUpdate = false;
	$rootScope.homeContactSuccess = false;
	$rootScope.homeContactError = false;
	$rootScope.homeContactUsSuccess = false;
	$rootScope.homeContactUsError = false;
	$rootScope.sendMessage = false;
	$rootScope.messageSend = false;
	$scope.systemBusy = 'Sorry for the inconvenience please try again at a later time.';
	
	$rootScope.contactUs = function(){
		$rootScope.homeContactSuccess = false;
		$rootScope.homeContactError = false;
		$rootScope.messageSend = true;
		$http.post('home/addContactusRequest/', $rootScope.contactUsBean)
		.success(function(Response) {
			$rootScope.messageSend = false;
			$scope.responseStatus = Response.status;
			
			if ($scope.responseStatus== 'SUCCESSFUL') {
				
				$rootScope.homeContactSuccess = true;
				$rootScope.homeContactSuccessMessage = Response.data;
				$timeout(function(){
					$rootScope.homeContactSuccess = false;
					$('#myModal2').modal('hide');
				    }, 1000);
				$rootScope.contactUsBean = {};
				
			}else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'){
				$rootScope.homeContactError = true;
				$rootScope.homeContactErrorMessage = Response.data;
				
				
			} else {
				$rootScope.homeContactError = true;
				$rootScope.homeContactErrorMessage = Response.data;
			}
		}).error(function() {
			$rootScope.messageSend = false;
			$rootScope.homeContactError = true;
			$rootScope.homeContactErrorMessage = Response.data;
		});
	};
	
	$rootScope.showCotactUsPopup = function(){
		$rootScope.homeContactUsSuccess = false;
		$rootScope.homeContactUsError = false;
		$rootScope.contactUsPopupBean = {};
		$('#myModal2').modal('show');
	};
	
	$rootScope.contactUsOnPopup = function(){
		$rootScope.homeContactUsSuccess = false;
		$rootScope.homeContactUsError = false;
		$rootScope.sendMessage = true;
		$http.post('home/addContactusRequest/', $rootScope.contactUsPopupBean)
		.success(function(Response) {
			$rootScope.sendMessage = false;
			$scope.responseStatus = Response.status;
			
			if ($scope.responseStatus== 'SUCCESSFUL') {
				
				$rootScope.homeContactUsSuccess = true;
				$rootScope.homeContactUsSuccessMessage = Response.data;
				$timeout(function(){
					$rootScope.homeContactUsSuccess = false;
					$('#myModal2').modal('hide');
				    }, 1000);
				
				$rootScope.contactUsPopupBean = {};
				
			}else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'){
				$rootScope.homeContactUsError = true;
				$rootScope.homeContactUsErrorMessage = Response.data;
				
				
			} else {
				$rootScope.homeContactUsError = true;
				$rootScope.homeContactUsErrorMessage = Response.data;
			}
		}).error(function() {
			$rootScope.sendMessage = false;
			$rootScope.homeContactUsError = true;
			$rootScope.homeContactUsErrorMessage = Response.data;
		});
	};
	
	
	
	
	$http.get('home/visitWebsite/')
	.success(function(Response) {

		$scope.responseStatus = Response.status;
		
		if ($scope.responseStatus== 'SUCCESSFUL') {
			
			
			
		}else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
				||$scope.responseStatus =='ERROR'){
			
			
		} else {
			
		}
	}).error(function() {
		
	});
	
	
	
	
	
	
	
	
};