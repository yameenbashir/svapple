'use strict';

/**
 * InventoryReportProductWiseController.js
 * @constructor
 */

var InventoryReportProductWiseController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','InventoryReportProductWiseControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,InventoryReportProductWiseControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.inventoryReportSuccess = false;
	$scope.inventoryReportError = false;
	$scope.inventoryReportError = false;
	$scope.inventoryReportBean = {};
	$scope.inventoryReport = [];
	$scope.outlets = [];
	
	$scope.outletSelected = $rootScope.inventoryReportOutletName;
	$scope.sesssionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = InventoryReportProductWiseControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};


	$scope.fetchData = function() {
		
		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){

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
				if($scope.data.productBeanList!=null){
					$scope.productBeanList = $scope.data.productBeanList;
					/*if($scope.inventoryReport.length>0){
						var totalCurrentStock = 0;
						var totalCurrentValue = 0;
						for(var i=0;i<$scope.inventoryReport.length;i++){
							totalCurrentStock = totalCurrentStock+parseFloat($scope.inventoryReport[i].currentStock);
							totalCurrentValue = totalCurrentValue+parseFloat($scope.inventoryReport[i].stockValue);
						}
						$scope.totalCurrentStock = totalCurrentStock+"";
						$scope.totalCurrentValue = totalCurrentValue+"";
					}*/
				}
				if($scope.data.outletBeans!=null){
					$scope.outlets = $scope.data.outletBeans;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
		
	
	
	$scope.changeOutletName = function() {
		$rootScope.inventoryReportOutletName = $scope.outletName;
		$route.reload();
	};
	$scope.sesssionValidation();
}];