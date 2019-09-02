'use strict';

/**
 * CategoryController
 * @constructor
 */
var LoginController = function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$sce,SessionService) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.error = false;
	$scope.success = false;
	$scope.userBean = {};
	$rootScope.IsLoggedIn = false;
	$rootScope.isDisabled = false;
	SessionService.setSession("");
	$cookieStore.put('_s_tk_com', '');
	
	$scope.doLogin = function(userBean) {
		$rootScope.isDisabled = true;
		$scope.loading = true;
		if (typeof $scope.userBean.email != 'undefined'
			&& typeof $scope.userBean.password != 'undefined') {
			$http.post('login/doLogin/', userBean).success(function(Response) {
				$rootScope.online = true;
				$scope.responseStatus = Response.status;

				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.loginSUccessFUll(Response);

				} else if ($scope.responseStatus == 'SYSTEMBUSY'
					|| $scope.responseStatus == 'INVALIDUSER'
						|| $scope.responseStatus == 'ERROR') {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$rootScope.isDisabled = false;

				} else {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$rootScope.isDisabled = false;
				}
			}).error(function() {
				$scope.loading = false;
				$scope.error = true;
				$scope.errorMessage = $scope.systemBusy;
				$rootScope.isDisabled = false;
			});
		}

	};
	
	$scope.loginSUccessFUll = function(Response) {
		$cookieStore.put('_s_tk_com', '');
		$cookieStore.put('_s_tk_rId', '');
		$cookieStore.put('_s_tk_oId', '');
		$cookieStore.put('_s_tk_rId', Response.data.userRole);
		$cookieStore.put('_s_tk_com', Response.data.sessionId);
		$rootScope.userRole =  $cookieStore.get('_s_tk_rId');
		SessionService.setSession(Response.data.sessionId);
		$cookieStore.put('userName', Response.data.userName);
		$cookieStore.put('email', $scope.userBean.email);
		SessionService.setUser($scope.userBean.email);
		$rootScope.userName = Response.data.userName;
		$rootScope.userId = Response.data.userId;
		
		if (Response.data.userRole == 3) {
			$rootScope.IsManager = true;
			$rootScope.IsSuperUser = false;
		} else if (Response.data.userRole == 1) {
			$rootScope.IsSuperUser = true;
			$rootScope.IsManager = false;
		} else {
			$rootScope.IsSuperUser = false;
			$rootScope.IsManager = false;
		}
		$cookieStore.put('IsSuperUser', $rootScope.IsSuperUser);
		$cookieStore.put('IsManager', $rootScope.IsManager);

		$rootScope.IsLoggedIn = true;
		$cookieStore.put('isLogin', $rootScope.IsLoggedIn);
		// $scope.loading= false;
		$window.location = Response.layOutPath;
		$rootScope.userImage = "/app/resources/dist/img/avatar5.png";

	};

};