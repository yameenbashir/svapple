'use strict';

/**
 * StockSupplierTransferEditDetailsController
 * @constructor
 */
var StockSupplierTransferEditDetailsController = ['$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','StockSupplierTransferEditDetailsController',function($scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,StockSupplierTransferEditDetailsController) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sessionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.stockOrderBean = $cookieStore.get('_ct_bl_ost');
			if($scope.stockOrderBean.retailPriceBill == "true"){
				$scope.stockOrderBean.retailPriceBill = true;
			}
			else{
				$scope.stockOrderBean.retailPriceBill = false;
			}
			$scope.data = StockSupplierTransferEditDetailsController.loadControllerData();
			$scope.fetchData();
			$scope.sReturn = '1';
			$scope.isAdmin();
			$scope.stockOrderBean.diliveryDueDate = new Date($scope.stockOrderBean.diliveryDueDate);
			if($scope.sReturn != '1'){
				$window.location = '/app/#/home';
			}
		}
	};

	$scope.isAdmin = function(){
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "false"){
				$scope.sReturn = '0';
				return true;   // not head office				
			}
			else{
				$scope.sReturn = '1';
				return false; // head office
			}
		}
		else{
			$scope.sReturn = '1';
			return false; // head office
		}
	};
	

	$scope.fetchData = function() {
		$rootScope.stockReturnEditDetailsLoadedFully = false;
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


	$scope.updateStockOrder = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "5"; // Stock Supplier Transfer
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
					$cookieStore.put('_ct_bl_ost',"");
					$cookieStore.remove('_ct_bl_ost');
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