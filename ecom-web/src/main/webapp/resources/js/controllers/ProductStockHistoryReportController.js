'use strict';

/**
 * ProductStockHistoryReportController
 * @constructor
 */

var ProductStockHistoryReportController = ['$scope', '$http', '$sce', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','ProductStockHistoryReportControllerPreLoad',function($scope, $http, $sce, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,ProductStockHistoryReportControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.productStockHistoryReportSuccess = false;
	$scope.productStockHistoryReportError = false;
	$scope.productStockHistoryReportError = false;
	$scope.productStockHistoryReportBean = {};
	$scope.productBean = {};
	$scope.productVariantSelList = [];
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBeansList = [];
	$scope.stockOrderDetailBeansList1 = [];
	$scope.stockOrderDetailBeansList2 = [];
	$scope.stockOrderDetailBeansList3 = [];
	var buttonsView = {
			buttons : [
						{
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
							autoPrint : false
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
			};
	var innerText = document.getElementById("reportrange").innerText;
	//alert(innerText);
	$scope.dateRange = "";
	$scope.dateRange = innerText;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ProductStockHistoryReportControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$scope.purchaseOrderDetailsLoadedFully = false;
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

			if($scope.data.productVariantBeansList!=null){
				$scope.productVariantBeansList = $scope.data.productVariantBeansList;
			}
			if($scope.data.productBeansList != null){
				$scope.productBeansList = $scope.data.productBeansList;
				for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
					$scope.productBeansList.push($scope.productVariantBeansList[i]);
				}
			}
			$scope.stockOrder = true;
			$scope.stockTransfer = true;
			$scope.stockReturn = true;
			$scope.stockReturnToWarehouse = true;
		}
		$scope.globalPageLoader = false;
	};

	$("#reportrange").on("change", function(event){
		var innerText = document.getElementById("reportrange").innerText;
		//alert(innerText);
		$scope.dateRange = "";
		$scope.dateRange = innerText;
		$scope.$apply();
		$scope.checkProductStatus();
	});
	$scope.changeReportDataType = function() {
		$scope.productStockHistoryReportType = $scope.reportType;
	};

	$scope.changeOutletName = function() {
		$scope.inventoryReportOutletName = $scope.outletName;
	};

	$scope.checkProductStatus = function(){
		if($scope.productVariantBean != null){
			if($scope.productVariantBean.isVariant.toString() == "true"){
				$scope.productVariantSelList.push($scope.productVariantBean);
				$scope.fetchProductStockHistoryReportByDateRange();
			}
			else{
				$scope.hit = 0;
				if($scope.productVariantBean.isProduct.toString() == "true"){
					$scope.productVariantSelList.push($scope.productVariantBean);
					$scope.fetchProductStockHistoryReportByDateRange();
					$scope.hit++;
				}
				else{
					for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
						var productVariant = null;
						if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
							productVariant = angular.copy($scope.productVariantBean);
							$scope.productVariantBean = angular.copy($scope.productVariantBeansList[i]);
							$scope.productVariantSelList.push($scope.productVariantBean);
							$scope.productVariantBean = angular.copy(productVariant);
							$scope.hit++;
						}
					}
					$scope.fetchProductStockHistoryReportByDateRange();
				}
				if($scope.hit < 1){
					$scope.productVariantBean.isProduct = "true";
					$scope.fetchProductStockHistoryReportByDateRange();
				}
			}
		}
	};


	$scope.fetchProductStockHistoryReportByDateRange = function(){
		if ($scope.productVariantSelList.length > 0) {
			if($scope.productStockHistoryReportType != null && $scope.productStockHistoryReportType != ''){
				$scope.productStockHistoryReportSuccess = false;
				$scope.productStockHistoryReportError = false;
				$scope.loading = true;
				var dateRang = $scope.dateRange.split("-");
				$scope.productStockHistoryReportStartDate = dateRang[0].trim(); 
				$scope.productStockHistoryReportEndDate = dateRang[1].trim();				
				$scope.productBean.totalReceived = 0;
				$scope.productBean.totalReturn = 0;
				$scope.productBean.totalTransfered = 0;
				$scope.productBean.totalRtw = 0;
				$scope.productBean.totalItems = 0;
				$scope.stockOrderDetailBeansList = [];
				$scope.stockOrderDetailBeansList1 = [];
				$scope.stockOrderDetailBeansList2 = [];
				$scope.stockOrderDetailBeansList3 = [];
				if($scope.stockOrderDetailBeansList.length==0){
					var table = $('#myTable').DataTable();
					if(table){
						 table.destroy();
					}
					 
				}
				if($scope.stockOrderDetailBeansList1.length==0){
					var table = $('#myTable2').DataTable();
					if(table){
						 table.destroy();
					}
					 
				}
				if($scope.stockOrderDetailBeansList2.length==0){
					var table = $('#myTable1').DataTable();
					if(table){
						 table.destroy();
					}
					 
				}
				if($scope.stockOrderDetailBeansList3.length==0){
					var table = $('#myTable3').DataTable();
					if(table){
						 table.destroy();
					}
					 
				}
				$http.post('productStockHistoryReport/getProductStockHistoryReportByDateRange/'+$cookieStore.get('_s_tk_com')+"/"+ $scope.productStockHistoryReportStartDate+"/"+$scope.productStockHistoryReportEndDate + "/" + $scope.productStockHistoryReportType, $scope.productVariantSelList)
				.success(function(Response) {	
					if($scope.stockOrderDetailBeansList.length==0){
						var table = $('#myTable').DataTable();
						if(table){
							 table.destroy();
						}
						 
					}
					if($scope.stockOrderDetailBeansList1.length==0){
						var table = $('#myTable2').DataTable();
						if(table){
							 table.destroy();
						}
						 
					}
					if($scope.stockOrderDetailBeansList2.length==0){
						var table = $('#myTable1').DataTable();
						if(table){
							 table.destroy();
						}
						 
					}
					if($scope.stockOrderDetailBeansList3.length==0){
						var table = $('#myTable3').DataTable();
						if(table){
							 table.destroy();
						}
						 
					}
					if($scope.productStockHistoryReportType == "1"){
						$scope.stockOrderDetailBeansList = Response.data;
						$scope.stockOrderDetailBeansList1 = [];
						$scope.stockOrderDetailBeansList2 = [];
						$scope.stockOrderDetailBeansList3 = [];
						if($scope.stockOrderDetailBeansList != null){
						for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {							
							$scope.productBean.totalReceived = parseInt($scope.productBean.totalReceived) + parseInt($scope.stockOrderDetailBeansList[i].total);
							$scope.productBean.totalItems = parseInt($scope.productBean.totalItems) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
						}
						}
						$scope.stockOrder = false;
						$scope.stockTransfer = true;
						$scope.stockReturn = true;
						$scope.stockReturnToWarehouse = true;
						
						setTimeout(
								function() 
								{
									
									
									$('#myTable').DataTable( {
										responsive: true,
										paging: true,
										searching:true,
										bInfo : true,
										dom : 'Bfrtip',
										buttons :buttonsView
									} );


								}, 10);
					}else if($scope.productStockHistoryReportType == "2"){
						$scope.stockOrderDetailBeansList1 = Response.data;
						$scope.stockOrderDetailBeansList = [];
						$scope.stockOrderDetailBeansList2 = [];
						$scope.stockOrderDetailBeansList3 = [];
						if($scope.stockOrderDetailBeansList1 != null){
						for (var i = 0; i < $scope.stockOrderDetailBeansList1.length; i++) {							
							$scope.productBean.totalReturn = parseInt($scope.productBean.totalReturn) + parseInt($scope.stockOrderDetailBeansList1[i].total);
							$scope.productBean.totalItems = parseInt($scope.productBean.totalItems) + parseInt($scope.stockOrderDetailBeansList1[i].orderProdQty);
						}
						}
						$scope.stockOrder = true;
						$scope.stockReturn = false;
						$scope.stockTransfer = true;
						$scope.stockReturnToWarehouse = true;
						var table = $('#myTable2').DataTable();
						if(table){
							 table.destroy();
						}
						setTimeout(
								function() 
								{
									$('#myTable2').DataTable( {
										responsive: true,
										paging: true,
										searching:true,
										bInfo : true,
										dom : 'Bfrtip',
										buttons :buttonsView
									} );


								}, 10);
					}else if($scope.productStockHistoryReportType == "3"){
						$scope.stockOrderDetailBeansList2 = Response.data;
						$scope.stockOrderDetailBeansList1 = [];
						$scope.stockOrderDetailBeansList = [];
						$scope.stockOrderDetailBeansList3 = [];
						if($scope.stockOrderDetailBeansList2 != null){
						for (var i = 0; i < $scope.stockOrderDetailBeansList2.length; i++) {							
							$scope.productBean.totalTransfered = parseInt($scope.productBean.totalTransfered) + parseInt($scope.stockOrderDetailBeansList2[i].total);
							$scope.productBean.totalItems = parseInt($scope.productBean.totalItems) + parseInt($scope.stockOrderDetailBeansList2[i].orderProdQty);
						}
						}
						$scope.stockOrder = true;
						$scope.stockReturn = true;
						$scope.stockTransfer = false;
						$scope.stockReturnToWarehouse = true;
						var table = $('#myTable1').DataTable();
						if(table){
							 table.destroy();
						}
						setTimeout(
								function() 
								{
									$('#myTable1').DataTable( {
										responsive: true,
										paging: true,
										searching:true,
										bInfo : true,
										dom : 'Bfrtip',
										buttons :buttonsView
									} );


								}, 10);
					}else if($scope.productStockHistoryReportType == "4"){
						$scope.stockOrderDetailBeansList3 = Response.data;
						$scope.stockOrderDetailBeansList1 = [];
						$scope.stockOrderDetailBeansList2 = [];
						$scope.stockOrderDetailBeansList = [];
						if($scope.stockOrderDetailBeansList3 != null){
						for (var i = 0; i < $scope.stockOrderDetailBeansList3.length; i++) {							
							$scope.productBean.totalRtw = parseInt($scope.productBean.totalRtw) + parseInt($scope.stockOrderDetailBeansList3[i].total);
							$scope.productBean.totalItems = parseInt($scope.productBean.totalItems) + parseInt($scope.stockOrderDetailBeansList3[i].orderProdQty);
						}
						}
						$scope.stockOrder = true;
						$scope.stockReturn = true;
						$scope.stockTransfer = true;
						$scope.stockReturnToWarehouse = false;
						var table = $('#myTable3').DataTable();
						if(table){
							 table.destroy();
						}
						setTimeout(
								function() 
								{
									$('#myTable3').DataTable( {
										responsive: true,
										paging: true,
										searching:true,
										bInfo : true,
										dom : 'Bfrtip',
										buttons :buttonsView
									} );


								}, 10);
					}					$scope.loading = false;	
					$scope.productVariantSelList = [];				
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.success = true;
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
					$scope.emergencyInfoLoadedFully = false;
					$scope.error = true;
					$scope.errorMessage  = $scope.systemBusy;
				});
			}
		}
	};

	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.productVariantsBeans = [];

				var customerResults = _.filter($scope.productBeansList, function(val) {
					return val.variantAttributeName.toLowerCase().includes(term) || val.sku.toLowerCase().includes(term);
				});				
				var customerVariantResults = _.filter($scope.productBeansList, function(val) {
					var skuLowercase =  val.sku.toLowerCase();
					return skuLowercase == term;

				});
				if(customerVariantResults && customerVariantResults.length>0){
					$scope.productVariantBean = customerVariantResults[0];
					$scope.checkProductStatus();
					$scope.selectedItem = {};
					$scope.selectedItem.item = customerVariantResults[0];
//					$scope.variantSkuFound =  true;
//					$scope.airportName = [];
				}
				return customerResults;
			},
			renderItem : function(item) {

				var result = {
						value : item.variantAttributeName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
								+ item.variantAttributeName + "</td>"
								//+ "<td style='width: 10%'>" + '<a ng-click="editVariant()" >Edit Variants</a>' + "</td>"
								+ "<td style='width: 10%'>" + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};

				return result;
			},
			itemSelected : function(item) {

				$scope.productVariantBean = item.item;
				//	$scope.airportName = [];

			}
	};
	$scope.sessionValidation();
}];