'use strict';

/**
 * HomeController
 * @constructor
 */
var StockControlController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','StockControlControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,StockControlControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sesssionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com');	
			//$scope.fetchAllStockOrders();			
		}
	};	
	
	$scope.addReceiveOrder = function(){
		$window.location = "/app/#/poCreateandReceive";
	};
	
	$scope.addPurchaseOrder = function() {
		$window.location = "/app/#/purchaseOrder";
	};
	$scope.addStockReturn = function() {
		$window.location = "/app/#/stockReturn";
	};
	$scope.addStockSupplierTransfer = function() {
		$window.location = "/app/#/stockSupplierTransfer";
	};
	
	$scope.addStockTransfer = function() {
		$window.location = "/app/#/stockTransfer";
	};
	$scope.addStockReturntoWarehouse = function() {
		$window.location = "/app/#/stockReturntoWarehouse";
	};
	$scope.addInventoryCount = function() {
		$window.location = "/app/#/inventoryCount";
	};
	$scope.purchaseOrderActions = function(stockOrderBean) {
		$cookieStore.put('_ct_bl_ost',stockOrderBean);
		if(stockOrderBean.stockOrderTypeId == "1"){ //supplier Order
			$window.location = "/app/#/purchaseOrderActions";
		}
		else if(stockOrderBean.stockOrderTypeId == "2"){ //Return Order
			if($scope.roleId != 1){
				var supplierName = stockOrderBean.outletName;
				stockOrderBean.outletName = stockOrderBean.supplierName;					
				stockOrderBean.supplierName = supplierName;
			}
			$cookieStore.put('_ct_bl_ost',stockOrderBean);
			$window.location = "/app/#/stockReturnActions";
		}		
		else if(stockOrderBean.stockOrderTypeId == "5"){ //Suppliert Stock Transfer
			if($scope.roleId != 1){
				var supplierName = stockOrderBean.outletName;
				stockOrderBean.outletName = stockOrderBean.supplierName;					
				stockOrderBean.supplierName = supplierName;
			}
			$cookieStore.put('_ct_bl_ost',stockOrderBean);
			$window.location = "/app/#/stockSupplierTransferActions";
		}		
		else if(stockOrderBean.stockOrderTypeId == "3"){  //Transfer Order
			$window.location = "/app/#/stockTransferActions";
		}
		else if(stockOrderBean.stockOrderTypeId == "4"){  //Return to Warehouse
			$window.location = "/app/#/stockReturntoWarehouseActions";
		}
	};
	
	$scope.purchaseOrderEditDetails = function(stockOrderBean) {
		if(stockOrderBean.statusId == "3" || stockOrderBean.statusId == "8"){ //3 completed and 8 closed
			
		}
		else{			
			if(stockOrderBean.stockOrderTypeId == "1"){ //supplier Order
				$cookieStore.put('_ct_bl_ost',stockOrderBean);
				$window.location = "/app/#/poCreateandReceiveEdit";
			}
			else if(stockOrderBean.stockOrderTypeId == "2"){ //Return Order
				if($scope.roleId != 1){
					var supplierName = stockOrderBean.outletName;
					stockOrderBean.outletName = stockOrderBean.supplierName;					
					stockOrderBean.supplierName = supplierName;
				}
				$cookieStore.put('_ct_bl_ost',stockOrderBean);
				$window.location = "/app/#/stockReturnEditDetails";
			}		
			else if(stockOrderBean.stockOrderTypeId == "3"){  //Transfer Order
				$cookieStore.put('_ct_bl_ost',stockOrderBean);
				$window.location = "/app/#/stockTransferEditDetails";
			}
			else if(stockOrderBean.stockOrderTypeId == "4"){  //Return to Warehouse
				$cookieStore.put('_ct_bl_ost',stockOrderBean);
				$window.location = "/app/#/stockReturntoWarehouseEditDetails";
			}
		}
	};
	

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.data = StockControlControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.isAdmin();
		}
	};

	$scope.isAdmin = function(){
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "true"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	};
	
	$scope.fetchData = function() {
		$rootScope.stockControlLoadedFully = false;
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

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
				$scope.stockTransferToSupplier = $scope.data.stockTransferToSupplier;
				$scope.stockOrderBeansList = $scope.data.stockOrderBeansList
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								bInfo : true
							} );


						}, 10);
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.sessionValidation();
	
}];