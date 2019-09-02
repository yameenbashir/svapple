'use strict';

/**
 * SalesLedgerController
 * @constructor
 */

var SalesLedgerController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','SalesLedgerControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,SalesLedgerControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	
	$scope.sesssionValidation = function(){
			if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			SalesLedgerControllerPreLoad.loadControllerData();
			$rootScope.globalPageLoader = false;
		}
	};
	$scope.sesssionValidation();
	
	$scope.LoadAllSales = function(){
		$rootScope.limit = 0;
							$route.reload();
	};
	
}];
