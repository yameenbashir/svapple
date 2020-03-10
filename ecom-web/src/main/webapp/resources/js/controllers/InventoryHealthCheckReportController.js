'use strict';

/**
 * InventoryHealthCheckReportController
 * @constructor
 */

var InventoryHealthCheckReportController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','InventoryHealthCheckReportControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,InventoryHealthCheckReportControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.inventoryReportSuccess = false;
	$scope.inventoryReportError = false;
	$scope.inventoryReportError = false;
	$scope.inventoryReportBean = {};
	$scope.inventoryReport = [];
	$scope.inventoryHealthReport = [];
	$scope.outlets = [];	
	//$scope.outletSelected = $rootScope.inventoryReportOutletName;
	$scope.sesssionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = InventoryHealthCheckReportControllerPreLoad.loadControllerData();
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
				/*if($scope.data.tableData!=null){
					$scope.salesReport = $scope.data.tableData;
				}*/
				/*if($scope.data.tableData.rows!=null){
					$scope.inventoryHealthCheckReportBeansList = $scope.data.tableData.rows;
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
				}*/
				if($scope.data.outletBeans!=null){
					$scope.inventoryHealthReport.outlets = $scope.data.outletBeans;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};



	$scope.changeOutletName = function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$http.post('inventoryHealthCheckReport/getInventoryHealthCheckReportCustom/'+$scope._s_tk_com+'/'+$scope.inventoryHealthReport.outletId)
		.success(function(Response) {
			$scope.loading = false;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.success = true;
				if($scope.data!=null){
					if(Response.data !=null){
						$scope.inventoryHealthCheckReportBeansList = Response.data;
					}
				}
			}
			else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'
						||$scope.responseStatus =='INVALIDSESSION'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}

		}).error(function() {
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});		
	};

	$scope.sesssionValidation();
}];