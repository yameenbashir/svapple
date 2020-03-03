'use strict';
 
/**
 * StockReturntoWarehouseEditDetailsController
 * @constructor
 */
var StockReturntoWarehouseEditDetailsController = ['$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','StockReturntoWarehouseEditDetailsControllerPreLoad',function($scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,StockReturntoWarehouseEditDetailsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com');
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.stockOrderBean = $cookieStore.get('_ct_bl_ost');
			if($scope.stockOrderBean.retailPriceBill == "true"){
				$scope.stockOrderBean.retailPriceBill = true;
			}
			else{
				$scope.stockOrderBean.retailPriceBill = false;
			}
			$scope.data = StockReturntoWarehouseEditDetailsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean.diliveryDueDate = new Date($scope.stockOrderBean.diliveryDueDate);
		}
	};

	$scope.fetchData = function() {
		$rootScope.stockReturntoWarehouseEditDetailsLoadedFully = false;
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
				$scope.destOutletList = angular.copy($scope.outletList);
//				for (var i = 0; i < $scope.destOutletList.length; i++) {
//					if($scope.destOutletList[i].outletId == $scope.userOutletId){
//						$scope.destOutletList.splice(i,1);
//					}
//				}
			}
			if($scope.data.stockOrderTypeBeansList!=null){
				$scope.stockOrderTypeBeansList = $scope.data.stockOrderTypeBeansList;
			}
			if($scope.data.supplierBeansList!=null){
				$scope.supplierList = $scope.data.supplierBeansList;
			}
		}
		$rootScope.globalPageLoader = false;
	};


	$scope.updateStockOrder = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "4"; // Stock Return to Warehouse
		$http.post('purchaseOrder/updateStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.stockOrderBean.stockOrderId = Response.data;
				$timeout(function(){
					$scope.success = false;
					angular.forEach($scope.outletList, function(value,key){
						if(value.outletId == $scope.stockOrderBean.outletId){
							$scope.stockOrderBean.outletName = value.outletName;
						}
					});
					angular.forEach($scope.outletList, function(value,key){
						if(value.outletId == $scope.stockOrderBean.sourceOutletId){
							$scope.stockOrderBean.sourceOutletName = value.outletName;
						}
					});
					angular.forEach($scope.stockOrderTypeBeansList, function(value,key){
						if(value.stockOrderTypeId == $scope.stockOrderBean.stockOrderTypeId){
							$scope.stockOrderBean.stockOrderTypeDesc = value.stockOrderTypeDesc;
						}
					});
					$cookieStore.put('_ct_bl_ost',"");
					$cookieStore.remove('_ct_bl_ost');
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					Response.layOutPath = '/app/#/stockReturntoWarehouseEditProducts';
					$window.location = Response.layOutPath;
				}, 1000);
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

	$scope.sessionValidation();	
}];