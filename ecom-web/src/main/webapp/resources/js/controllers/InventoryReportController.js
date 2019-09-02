'use strict';

/**
 * InventoryController
 * @constructor
 */

var InventoryReportController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','InventoryReportControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,InventoryReportControllerPreLoad) {
	
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
			$scope.data = InventoryReportControllerPreLoad.loadControllerData();
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
				$scope.hideSalesDetails = $scope.data.hideSalesDetails;
				if($scope.data.inventoryReportBeansList!=null){
					$scope.inventoryReport = $scope.data.inventoryReportBeansList;
					if($scope.inventoryReport.length>0){
						var totalCurrentStock = 0;
						var totalCurrentValue = 0;
						for(var i=0;i<$scope.inventoryReport.length;i++){
							totalCurrentStock = totalCurrentStock+parseFloat($scope.inventoryReport[i].currentStock);
							totalCurrentValue = totalCurrentValue+parseFloat($scope.inventoryReport[i].stockValue);
						}
						$scope.totalCurrentStock = totalCurrentStock+"";
						$scope.totalCurrentValue = totalCurrentValue+"";
					}
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