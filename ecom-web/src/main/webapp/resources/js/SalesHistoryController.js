'use strict';

/**
 * SalesHistoryController
 * @constructor
 */
var SalesHistoryController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','SalesHistoryControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,SalesHistoryControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$rootScope.applyDateRange = false;
	$scope.standardReceipt = true;
	
	$scope.sesssionValidation = function(){
			if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.companyImagePath = $cookieStore.get('companyImagePath');
			$scope.termsAndConditions = $cookieStore.get('termsAndConditions');
			SalesHistoryControllerPreLoad.loadControllerData();
			$rootScope.globalPageLoader = false;
			$scope.InvoiceMainBeans = localStorage.getItem('salesHistory');
			$scope.InvoiceMainBeans =  JSON.parse($scope.InvoiceMainBeans);
			if(!$scope.InvoiceMainBeans.receiptConfigurationBean.starndardReceipt){
				$scope.standardReceipt = false;
			}
			$scope.termsAndConditions = $scope.InvoiceMainBeans.termsAndConditions;
		}
	};
	$scope.sesssionValidation();
	$scope.processPayment = function(InvoiceMainId){
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$http.post('salesHistory/processPayment/' + $scope._s_tk_com +'/'+InvoiceMainId).success(
						function(Response) {
							$scope.loading = false;

							$scope.responseStatus = Response.status;
							if ($scope.responseStatus == 'SUCCESSFUL') {
								// $scope.InvoiceMainBean = {};
								
								localforage.setItem('_s_tk_sell',Response.data);
								$window.location = Response.layOutPath;

							} else if ($scope.responseStatus == 'INVALIDSESSION'
									|| $scope.responseStatus == 'SYSTEMBUSY') {
								$scope.error = true;
								$scope.errorMessage = Response.data;
								$window.location = Response.layOutPath;
							} else {
								$scope.error = true;
								$scope.errorMessage = Response.data;
							}
						}).error(function() {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = $scope.systemBusy;
				});
	};
	
	$scope.LoadAllSales = function(){
		$rootScope.limit = 0;
							$route.reload();
							
	};
	$cookieStore.put("salesHostoryLoaded",false);
	

	 $interval(function() {
		 if($cookieStore.get("salesHostoryLoaded")){
			 $scope.validate ();
			 $cookieStore.put("salesHostoryLoaded",false);
		 }
		}, 100);
	  
	    
	    
	$scope.validate = function(){
		
		 $timeout(function() {
		    	$scope.canvas = document.getElementById("hirearchyPage");
		    	angular.element(document).injector().invoke(function($compile) {
		  	    //var newElement = $compile($scope.canvas)($scope);
			  	  var scope = angular.element($scope.canvas ).scope();
			      $compile($scope.canvas )(scope);
		  	 });
			},1);
		 
	};
	$scope.printReceipt = function(invoiceId){
		$scope.printReceiptModal = true;
		for (var i = 0; i < $scope.InvoiceMainBeans.data.length; i++) {
			if($scope.InvoiceMainBeans.data[i].invoiceId == invoiceId){
				$scope.InvoiceMainBean = $scope.InvoiceMainBeans.data[i];
				
			}
		}
		 $timeout(function() {
			 JsBarcode(".barcode").init();
			},100);
		
	};
	
	function checkInvoiceExist(invoice) {
		if($scope.InvoiceMainBeans!='undefined' && $scope.InvoiceMainBeans.data!=null && $scope.InvoiceMainBeans.data.length>0){
			for(var i=0;i<$scope.InvoiceMainBeans.data.length;i++){
				if($scope.InvoiceMainBeans.data[i].invoiceId==invoice.invoiceId){
					return true;
				}
			}
		}
	
		return false;
	}
	
	$("#reportrange").on("change", function(event){
		var innerText = document.getElementById("reportrange").innerText;
		//alert(innerText);
		$scope.dateRange = "";
		$scope.dateRange = innerText;
		$scope.$apply();
		$scope.fetchSalesReportByDateRange();
	});
	
	$scope.fetchSalesReportByDateRange = function() {
		$scope.salesReportSuccess = false;
		$scope.salesReportError = false;
		$scope.loading = true;
		var dateRang = $scope.dateRange.split("-");
		$rootScope.salesReportStartDate = dateRang[0].trim(); 
		$rootScope.salesReportEndDate = dateRang[1].trim(); 
		$rootScope.limit = 0;
		$rootScope.applyDateRange = true;
		$route.reload();

	};
	
	
}];