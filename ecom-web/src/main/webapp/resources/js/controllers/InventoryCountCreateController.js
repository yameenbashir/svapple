'use strict';

/**
 * PurchaseOrderController
 * @constructor ....
 */
var InventoryCountCreateController = ['$scope', '$filter', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','InventoryCountCreateControllerPreLoad',function($scope, $filter, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,InventoryCountCreateControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.inventoryCountBean = {};
	$scope.HHMM = $filter('date')(new Date(), 'HH:mm');
	$scope.inventoryCountBean.inventoryCountRefNo = "SC-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.data = InventoryCountCreateControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.inventoryCountBean.outletId= $scope.userOutletId;
			$scope.inventoryCountBean.inventoryCountTypeId = '-1';
		}
	};

	$scope.fetchData = function() {
		$rootScope.inventoryCountCreateLoadedFully = false;
		if($scope.data == 'NORECORDFOUND'){

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
			
			if($scope.data.outletBeansList!=null){
				$scope.outletList = $scope.data.outletBeansList;
			}
			if($scope.data.inventoryCountTypeBeansList!=null){
				$scope.inventoryCountTypeBeansList = $scope.data.inventoryCountTypeBeansList;
			}
			if($scope.data.auditTransfer != null){
				$scope.inventoryCountBean.auditTransfer = $scope.data.auditTransfer;
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.addInventoryCount = function() {		
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$scope.inventoryCountBean.statusId = "1"; // Initiated status at Inventory Count Creation page
			$http.post('inventoryCountCreate/addInventoryCount/'+$scope._s_tk_com, $scope.inventoryCountBean)
			.success(function(Response) {
				$scope.loading = false;				
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.productTypeBean = {};
					$scope.success = true;
					$scope.successMessage = "Request Processed successfully!";
					$scope.inventoryCountBean.inventoryCountId = Response.data;
					$timeout(function(){
						$scope.success = false;						
						angular.forEach($scope.outletList, function(value,key){
							if(value.outletId == $scope.inventoryCountBean.outletId){
								$scope.inventoryCountBean.outletName = value.outletName;
							}
						});
						angular.forEach($scope.inventoryCountTypeBeansList, function(value,key){
							if(value.inventoryCountTypeId == $scope.inventoryCountBean.inventoryCountTypeId){
								$scope.inventoryCountBean.inventoryCountTypeDesc = value.inventoryCountTypeDesc;
							}
						});
						$cookieStore.put('_ct_sc_ost',$scope.inventoryCountBean);
						$window.location = Response.layOutPath;
					    }, 3000);
				}
			else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'
						||$scope.responseStatus =='INVALIDSESSION'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			}
			else if($scope.responseStatus == 'WARNING'){
				$scope.warning = true;
				$scope.warningMessage = Response.data;
				//$window.location = Response.layOutPath;
			} 
			else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}

		}).error(function() {
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});
	
	};
	
	$scope.sessionValidation();	
}];