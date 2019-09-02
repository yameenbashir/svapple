'use strict';

/**
 * SuppliersController
 * @constructor
 */
var SuppliersController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout', '$route','SuppliersControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout, $route,SuppliersControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showUpdatePaymentModal =  false;
	$cookieStore.put('supplier','');
	$scope.supplierBean = {};
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = SuppliersControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};
	$scope.showDetails= function(supplierId) {
		$cookieStore.put('sp_cto', supplierId);
		$window.location = '/app/#/supplierDetails';
	};
	$scope.fetchData = function() {
		$rootScope.suppliersLoadedFully = false;
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
				$scope.supplierBean = $scope.data;
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
	$scope.addSupplier = function() {
		$window.location = "/app/#/newSupplier";
	};

	$scope.EditRecord = function(supplier) {
		$cookieStore.put('supplier',supplier);
		$window.location = "/app/#/newSupplier";
	};

	$scope.showUpdatePaymentModal =  false;
	$scope.addPayment = function(supplierId,supplierBalance){
		$scope.fetchStockOrders(supplierId);
		$scope.showUpdatePaymentModal =  true;
		$scope.supplierPaymentBean = {};
		$scope.supplierPaymentBean.createdDate = new Date();
		$scope.supplierPaymentBean.supplierBalance = angular.copy(supplierBalance);
		$scope.supplierPaymentBean.supplierNewBalance = $scope.supplierPaymentBean.supplierBalance;
		$scope.supplierPaymentBean.supplier = supplierId;
		$scope.supplierPaymentBean.orderRefNum = "-1";
		$scope.supplierPaymentBean.adjustmentType = "Cash Out";
		$scope.supplierPaymentBean.paymentCash = "0";
		$scope.supplierPaymentBean.paymentAmount = "0";
		$scope.supplierPaymentBean.paymentCreditCard = "0";
		$scope.supplierPaymentBean.paymentOtherType = "0";
		$scope.supplierPaymentBean.cashReturn = "0";
	};

	
	$scope.addSuppliersPayment = function(){
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$http.post('supplierDetails/addSupplierPayment/'+$scope._s_tk_com, $scope.supplierPaymentBean)
		.success(function(Response) {			
			$scope.responseStatus = Response.status;
			$scope.loading = false;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.success = true;
				$scope.successMessage = Response.data;
				$scope.showUpdatePaymentModal =  false;
				$timeout(function(){
					$scope.success = false;
					$scope.showUpdatePaymentModal =  false;
					$route.reload();
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

	
	$scope.fetchStockOrders = function(supplierId) {		
		$scope.success = false;
		$scope.error = false;
		$http.post('supplierDetails/getAllStockOrdersBySupplierId/'+$scope._s_tk_com+'/'+ supplierId)
		.success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				//$scope.success = true;
				$scope.stockOrderBeansList = Response.data;

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

	$scope.populateOrderAmount = function(){
		//$scope.supplierPaymentBean.paymentAmount = angular.copy(stockOrder.totalCost);
		angular.forEach($scope.stockOrderBeansList, function(value,key){
			if(value.stockOrderId == $scope.supplierPaymentBean.orderRefNum){
				$scope.supplierPaymentBean.paymentAmount = angular.copy(value.totalCost);
			}
		});
	};

	$scope.calculateNewBalance = function(){
		$scope.totalPayment = (parseInt($scope.supplierPaymentBean.paymentCash) + parseInt($scope.supplierPaymentBean.paymentOtherType) + parseInt($scope.supplierPaymentBean.paymentCreditCard)) - parseInt($scope.supplierPaymentBean.cashReturn);
		if($scope.supplierPaymentBean.adjustmentType == "Cash Out"){
			$scope.supplierPaymentBean.supplierNewBalance = (parseInt($scope.supplierPaymentBean.supplierBalance) - parseInt($scope.totalPayment)).toString();
		}
		else if($scope.supplierPaymentBean.adjustmentType == "Cash In"){
			$scope.supplierPaymentBean.supplierNewBalance = (parseInt($scope.supplierPaymentBean.supplierBalance) + parseInt($scope.totalPayment)).toString();
		}
		if(isNaN($scope.supplierPaymentBean.supplierNewBalance)){
		$scope.supplierPaymentBean.supplierNewBalance = "";
		}
	};


	
	
	$scope.sessionValidation();
}];