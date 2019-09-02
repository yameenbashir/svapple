'use strict';

/**
 * StockReturntoWarehouseActionsController
 * @constructor
 */
var StockReturntoWarehouseActionsController = ['$scope', '$http', '$window', '$timeout', '$cookieStore', '$route', '$rootScope', 'SessionService','StockReturntoWarehouseActionsControllerPreLoad',function($scope, $http, $window, $timeout, $cookieStore, $route, $rootScope, SessionService,StockReturntoWarehouseActionsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.disableProcess = false;
	$scope.showConfirmCancelPopup = false;
	$scope.showConfirmSendReturnPopup = false;
	$scope.stockOrderDetailBeansList = [];



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
			$scope.data = StockReturntoWarehouseActionsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			if($scope.stockOrderBean.statusId == "3" || $scope.stockOrderBean.statusId == "8"){ //3 completed and 8 closed
				$scope.disableProcess = true;
			}
			$scope.isAdmin();
			$scope.stockOrderBean.itemCount = 0;
			$scope.calculateItemCount();
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
		$rootScope.stockReturntoWarehouseActionsLoadedFully = false;
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

			if($scope.data!=null){
				if($scope.data != "No record found !"){
					$scope.stockOrderDetailBeansList = $scope.data;
					$scope.calculateTotalAll();
				}
				if($scope.isAdmin() == false){
					$scope.calculateTotalAllAdmin();
				}
				var printHeader =  '<div style="margin-left: 10px  !important;"><div class="row !important;">'
					+'<div class="col-md-2 !important;"></div> '
					+'<div class="col-md-2 !important;"><h4><label> Order No: </label> </h4></div>'
					+'<div class="col-md-2 !important;"><h4>'+$scope.stockOrderBean.orderNo+' </h4></div> '
					+'<div class="col-md-6 !important;"></div> '
					+'</div>'
					+'<div class="row">'
					+'<div class="col-md-2"></div> '
					+'<div class="col-md-2"><h4><label> Delivery due: </label> </h4></div>'
					+'<div class="col-md-2"><h4>'+$scope.stockOrderBean.diliveryDueDate+' </h4></div> '
					+'<div class="col-md-6"></div> '
					+'</div>'
					+'<div class="row">'
					+'<div class="col-md-2"></div> '
					+'<div class="col-md-2"><h4><label> Order from: </label> </h4></div>'
					+'<div class="col-md-2"><h4>'+$scope.stockOrderBean.sourceOutletName+' </h4></div> '
					+'<div class="col-md-6"></div> '
					+'</div>'
					+'<div class="row">'
					+'<div class="col-md-2"></div> '
					+'<div class="col-md-2"><h4><label> Address: </label> </h4></div>'
					+'<div class="col-md-2"><h4>'+$scope.stockOrderBean.sourceOutletAddress+' </h4></div> '
					+'<div class="col-md-6"></div> '
					+'</div>'
					+'<div class="row">'
					+'<div class="col-md-2"></div> '
					+'<div class="col-md-2"><h4><label> Deliver to: </label> </h4></div>'
					+'<div class="col-md-2"><h4>'+$scope.stockOrderBean.outletName+' </h4></div> '
					+'<div class="col-md-6"></div> '
					+'</div>'
					+'<div class="row">'
					+'<div class="col-md-2"></div> '
					+'<div class="col-md-2"><h4><label> Address: </label> </h4></div>'
					+'<div class="col-md-2"><h4>'+$scope.stockOrderBean.outletAddress+' </h4></div> '
					+'<div class="col-md-6"></div> '
					+'</div> <div>';
				setTimeout(
						function() 
						{
							$('#myTable1').DataTable(
									{
										responsive : true,
										dom : 'Bfrtip',
										caption : "Mike Smith",
										buttons : {
											buttons : [
													{
													customize: function ( win ) {
										                    $(win.document.body)
									                        .css( 'font-size', '10pt' )
									                        .prepend(printHeader);},
														extend : 'print',
														text : '<i class="fa fa-print"></i> Print',
														title : $('h1').text(),
														key : {
															key : 'p',
															altkey : true
														},
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true,
														autoPrint : true
													},
													{
														extend : 'excel',
														text : '<i class="fa fa-book"></i> Excel',
														title : $('h1').text(),
														key : {
															key : 'p',
															altkey : true
														},
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true,
														autoPrint : false
													},
													{
														extend : 'pdf',
														text : '<i class="fa fa-file-pdf-o"></i> PDF',
														title : $('h1').text(),
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true
													} ],
											dom : {
												container : {
													className : 'dt-buttons'
												},
												button : {
													className : 'btn btn-default'
												}
											}
										}
									});
					
						}, 100);
				setTimeout(
						function() 
						{
							$('#myTable').DataTable(
									{
										responsive : true,
										dom : 'Bfrtip',
										caption : "Mike Smith",
										buttons : {
											buttons : [
													{
													customize: function ( win ) {
										                    $(win.document.body)
									                        .css( 'font-size', '10pt' )
									                        .prepend(printHeader);},
														extend : 'print',
														text : '<i class="fa fa-print"></i> Print',
														title : $('h1').text(),
														key : {
															altkey : true
														},
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true,
														autoPrint : true
													},
													{
														extend : 'excel',
														text : '<i class="fa fa-book"></i> Excel',
														title : $('h1').text(),
														key : {
															key : 'p',
															altkey : true
														},
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true,
														autoPrint : false
													},
													{
														extend : 'pdf',
														text : '<i class="fa fa-file-pdf-o"></i> PDF',
														title : $('h1').text(),
														exportOptions : {
															columns : ':not(.no-print)'
														},
														footer : true
													} ],
											dom : {
												container : {
													className : 'dt-buttons'
												},
												button : {
													className : 'btn btn-default'
												}
											}
										}
									});
					
						}, 100);
			
			}
		}
		$rootScope.globalPageLoader = false;
	};


	$scope.calculateItemCount = function(){
		$scope.stockOrderBean.itemCount = 0;
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.itemCount = parseInt($scope.stockOrderBean.itemCount) + parseInt($scope.stockOrderDetailBeansList[i].orderProdQty);
				if(isNaN($scope.stockOrderBean.itemCount)){
					$scope.stockOrderBean.itemCount = "0";
				}
			}
		}
	};

	$scope.calculateItemCountAdmin = function(){
		$scope.stockOrderBean.itemCountRecv = 0;
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.itemCountRecv = parseInt($scope.stockOrderBean.itemCountRecv) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
				if(isNaN($scope.stockOrderBean.itemCountRecv) || $scope.stockOrderBean.itemCountRecv == ""){
					$scope.stockOrderBean.itemCountRecv = "0";
				}
			}
		}
	};
	
	$scope.showCancelStockOrder = function(){
		$scope.showConfirmCancelPopup = true;
	};	

	$scope.cancelStockOrder = function(){		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "8"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.diliveryDueDate = new Date($scope.stockOrderBean.diliveryDueDate);
		$http.post('purchaseOrder/updateStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.stockOrderBean.stockOrderId = Response.data;
				$scope.showConfirmCancelPopup = false;
				$timeout(function(){
					$scope.success = false;
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


	$scope.calculateTotal = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				if($scope.stockOrderBean.retailPriceBill == true){
					value.total = value.retailPrice * value.orderProdQty;
				}
				else{
					value.total = value.ordrSupplyPrice * value.orderProdQty;
				}				
				if(isNaN(value.total)){
					value.total = "0";
				}
				if(parseInt(value.productVariantCurrInventory) < parseInt(value.orderProdQty)){
					value.greaterThanStock = true;
				}
				else{
					value.greaterThanStock = false;
				}
			}
		});	
		$scope.calculateGrandTotal();
	};

	$scope.calculateTotalAdmin = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				if($scope.stockOrderBean.retailPriceBill == true){
					value.recvTotal = value.retailPrice * value.recvProdQty;
				}
				else{
					value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
				}				
				if(isNaN(value.recvTotal)){
					value.recvTotal = "0";
				}
				if(parseInt(value.productVariantCurrInventory) < parseInt(value.recvProdQty)){
					value.greaterThanStock = true;
				}
				else{
					value.greaterThanStock = false;
				}
			}
		});
		$scope.calculateItemCountAdmin();
		$scope.calculateGrandrecvTotal();
	};
	
	$scope.calculateTotalAll = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.calculateTotal($scope.stockOrderDetailBeansList[i].productVariantId);

			}
		}
	};

	$scope.calculateTotalAllAdmin = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.calculateTotalAdmin($scope.stockOrderDetailBeansList[i].productVariantId);

			}
		}
		$scope.calculateItemCountAdmin();
	};

	$scope.calculateGrandTotal = function(){
		$scope.grandTotal = "0";
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.stockOrderDetailBeansList[i].total);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
			}
		}
	};

	$scope.calculateGrandrecvTotal = function(){
		$scope.grandrecvTotal = "0";
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if(isNaN($scope.stockOrderDetailBeansList[i].recvTotal)){
					$scope.stockOrderDetailBeansList[i].recvTotal = "0";
				}
				$scope.grandrecvTotal = parseFloat($scope.grandrecvTotal) + parseFloat($scope.stockOrderDetailBeansList[i].recvTotal);
				if(isNaN($scope.grandrecvTotal)){
					$scope.grandrecvTotal = "0";
				}
			}
		}
	};
	
	$scope.showSendTransferStockOrder = function(){
		if($scope.checkStockOrderDetailList() == false){
			$window.location = "/app/#/stockReturntoWarehouseEditProducts";
		}
		else{
			$scope.showConfirmSendTransferPopup = true;
		}
	};

	$scope.checkStockOrderDetailList = function() {
		if ($scope.stockOrderDetailBeansList.length > 0) { // your question said "more than one element"
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if($scope.stockOrderDetailBeansList[i].orderProdQty == "" || $scope.stockOrderDetailBeansList[i].orderProdQty == null){
					return false;
				}
				else if($scope.stockOrderDetailBeansList[i].ordrSupplyPrice == "" || $scope.stockOrderDetailBeansList[i].ordrSupplyPrice == null){
					return false;
				}		   
				else if($scope.stockOrderDetailBeansList[i].greaterThanStock == true){
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	};	

	$scope.transferStockOrder = function(){		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.calculateGrandTotal();
		$scope.stockOrderBean.statusId = "3"; // Completed status
		$http.post('purchaseOrderDetails/updateAndReturntoHeadOffice/'+$scope._s_tk_com+'/'+$scope.grandrecvTotal, $scope.stockOrderDetailBeansList)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.stockOrderBean.stockOrderId = Response.data;
				$scope.showConfirmSendTransferPopup = false;
				$timeout(function(){
					$scope.success = false;
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



	$scope.purchaseOrderReceive = function() {
		$window.location = "/app/#/purchaseOrderReceive";
	};
	$scope.purchaseOrderEditProducts = function() {
		$window.location = "/app/#/stockReturntoWarehouseEditProducts";
	};
	$scope.purchaseOrderEditDetails = function() {
		$window.location = "/app/#/stockReturntoWarehouseEditDetails";
	};
	$scope.sessionValidation();
}];