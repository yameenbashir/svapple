'use strict';

/**
 * HomeController
 * @constructor
 */
var StockTransferController = ['$scope', '$filter', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','StockTransferControllerPreLoad',function($scope, $filter, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,StockTransferControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;$scope.stockOrderBean = {};
	$scope.stockOrderBean.diliveryDueDate = new Date();
	$scope.HHMM = $filter('date')(new Date(), 'HH:mm');
	$scope.stockOrderBean.orderNo = "ST-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.stockOrderBean.stockRefNo = "ST-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');			
			$scope.data = StockTransferControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean.sourceOutletId= $scope.userOutletId;
		}
	};

	$scope.fetchData = function() {
		$rootScope.stockTransferLoadedFully = false;
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
				for (var i = 0; i < $scope.destOutletList.length; i++) {
					if($scope.destOutletList[i].outletId == $scope.userOutletId){
						$scope.destOutletList.splice(i,1);
					}
				}
			}
			if($scope.data.stockOrderTypeBeansList!=null){
				$scope.stockOrderTypeBeansList = $scope.data.stockOrderTypeBeansList;
			}
			if($scope.data.supplierBeansList!=null){
				$scope.supplierList = $scope.data.supplierBeansList;
			}
			if($scope.data.retailPriceBill!=null){
				if($scope.data.retailPriceBill == "true"){
					$scope.stockOrderBean.retailPriceBill = true;
				}
				else{
					$scope.stockOrderBean.retailPriceBill = false;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.addStockOrder = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "3"; // Stock Transfer
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
					$cookieStore.put('_e_cOt_pio',$scope.stockOrderBean);
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