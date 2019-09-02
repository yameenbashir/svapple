'use strict';

/**
 * PurchaseOrderController
 * @constructor
 */
var PurchaseOrderController = ['$scope', '$filter', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','PurchaseOrderControllerPreLoad',function($scope, $filter, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,PurchaseOrderControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.stockOrderBean = {};
	$scope.stockOrderBean.diliveryDueDate = new Date();
	$scope.HHMM = $filter('date')(new Date(), 'HH:mm');
	$scope.stockOrderBean.orderNo = "PO-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.stockOrderBean.stockRefNo = "PO-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.data = PurchaseOrderControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean.outletId= $scope.userOutletId;
		}
	};

	$scope.fetchData = function() {
		$rootScope.purchaseOrderLoadedFully = false;
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
			if($scope.data.stockOrderTypeBeansList!=null){
				$scope.stockOrderTypeBeansList = $scope.data.stockOrderTypeBeansList;
			}
			if($scope.data.supplierBeansList!=null){
				$scope.supplierList = $scope.data.supplierBeansList;
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.addStockOrder = function() {		
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
			$scope.stockOrderBean.stockOrderTypeId = "1"; // Supplier Order
			$http.post('purchaseOrder/addStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
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
						angular.forEach($scope.supplierList, function(value,key){
							if(value.supplierId == $scope.stockOrderBean.supplierId){
								$scope.stockOrderBean.supplierName = value.supplierName;
							}
						});
						angular.forEach($scope.outletList, function(value,key){
							if(value.outletId == $scope.stockOrderBean.outletId){
								$scope.stockOrderBean.outletName = value.outletName;
							}
						});
						angular.forEach($scope.stockOrderTypeBeansList, function(value,key){
							if(value.stockOrderTypeId == $scope.stockOrderBean.stockOrderTypeId){
								$scope.stockOrderBean.stockOrderTypeDesc = value.stockOrderTypeDesc;
							}
						});
						$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
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