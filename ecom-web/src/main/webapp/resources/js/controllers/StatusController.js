'use strict';

/**
 * StatusController
 * 
 * @constructor
 */
var StatusController = ['$scope', '$http', '$window', '$cookieStore','$rootScope', '$timeout', '$route', 'SessionService', 'StatusControllerPreLoad',function($scope, $http, $window, $cookieStore,$rootScope, $timeout, $route, SessionService, StatusControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.brandSuccess = false;
	$scope.brandError = false;
	$scope.brandBean = {};

	$scope.sessionValidation = function() {

		$scope._s_tk_com = $cookieStore.get('_s_tk_com');
		$scope.fetchData();
	};

	$scope.fetchData = function() {
		
		$scope.invoiceMainBeanList = StatusControllerPreLoad.loadControllerData();
		if(typeof ($scope.invoiceMainBeanList) == "undefined" || $scope.invoiceMainBeanList==null){
			$scope.invoiceMainBeanList = [];
		}
		$rootScope.globalPageLoader = false;
		
	};

	$scope.showAddBrandPopup = function() {
		$scope.brandBean = {};
		$scope.showNewBrandModal = true;
	};

	$scope.sessionValidation();

}];