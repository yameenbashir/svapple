'use strict';

/**
 * StatusController
 * 
 * @constructor
 */
var StatusController = ['$scope', '$http', '$window', '$cookieStore','$rootScope', '$timeout', '$route', 'SessionService','StatusControllerPreLoad', function($scope, $http, $window, $cookieStore,$rootScope, $timeout, $route, SessionService,StatusControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.brandSuccess = false;
	$scope.brandError = false;
	$scope.brandBean = {};
	
	$scope.sessionValidation = function() {
		/* $rootScope.newflag = false;
		
		 localforage.getItem('invoiceMainBeanNewList').then(function(value) {
			 $scope.invoiceMainBeanStatusList = value;
			 localforage.setItem('InvoiceMainBeanList', $scope.invoiceMainBeanStatusList);
			
		 });*/
		$scope.fetchData();
	};

	$scope.fetchData = function() {
		
		$scope._s_tk_com = $cookieStore.get('_s_tk_com');
		$scope.invoiceMainBeanStatusList = StatusControllerPreLoad.loadControllerData();
		if($scope.invoiceMainBeanStatusList==null){
			localforage.getItem('invoiceMainBeanNewList').then(function(value) {
				 $scope.invoiceMainBeanStatusList = value;
				 
				 localforage.setItem('InvoiceMainBeanList', $scope.invoiceMainBeanStatusList);
				 if(value!=null){
					//alert("new has data so $route.reload();");
					 $route.reload();
				 }
				
			 });
		}
		$rootScope.globalPageLoader = false;
		
	};

	$scope.showAddBrandPopup = function() {
		$scope.brandBean = {};
		$scope.showNewBrandModal = true;
	};

	$scope.sessionValidation();

}];