'use strict';

/**
 * SupplierDetailsController
 * @constructor
 */
var SupplierDetailsController = ['$scope', '$http', '$window', '$timeout', '$cookieStore', '$route', '$rootScope', 'SessionService','SupplierDetailsControllerPreLoad',function($scope, $http, $window, $timeout, $cookieStore, $route, $rootScope, SessionService,SupplierDetailsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.stockOrderBeansList = [];
	$scope.supplierBean = {};	
	$scope.sId = $cookieStore.get('sp_cto');
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = SupplierDetailsControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};	
	
	$scope.fetchData = function(){
		if($scope.data == 'NORECORDFOUND' || $scope.data =='No record found !'){
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
				if($scope.data.supplierBean!=null){
					$scope.supplierBean = $scope.data.supplierBean;
				}
		
				if($scope.data.supplierPaymentsBeanList!=null){
					$scope.supplierPaymentBeansList = $scope.data.supplierPaymentsBeanList;
					setTimeout(
							function() 
							{
								$('#myTable1').DataTable( {
									responsive: true,
									paging: true,
									searching:true,
									order: [[ 1, "asc" ]],
									bInfo : true
								} );
							}, 10);
				}
				if($scope.data.stockOrderBeansList!=null){
					$scope.stockOrderBeansList = $scope.data.stockOrderBeansList;
					setTimeout(
							function() 
							{
								$('#myTable').DataTable( {
									responsive: true,
									paging: true,
									searching:true,
									order: [[ 1, "desc" ]],
									bInfo : true
								} );
							}, 10);
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	

	$scope.showUpdatePaymentModal =  false;
	$scope.addPayment = function(){
		$scope.showUpdatePaymentModal =  true;
		$scope.supplierPaymentBean = {};
		$scope.supplierPaymentBean.createdDate = new Date();
		$scope.supplierPaymentBean.supplierBalance = angular.copy($scope.supplierBean.supplierBalance);
		$scope.supplierPaymentBean.supplierNewBalance = $scope.supplierPaymentBean.supplierBalance;
		$scope.supplierPaymentBean.supplier = $scope.sId;
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
		//$scope.supplierPaymentBean.createdDate = angular.copy($scope.supplierPaymentBean.createdDate.toString());
		$http.post('supplierDetails/addSupplierPayment/'+$scope._s_tk_com, $scope.supplierPaymentBean)
		.success(function(Response) {			
			$scope.responseStatus = Response.status;
			$scope.loading = false;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
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

	$scope.fetchSupplier = function() {		
		$scope.success = false;
		$scope.error = false;		
		$http.post('supplierDetails/getSupplier/'+$scope._s_tk_com, $scope.sId)
		.success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.success = true;
				$scope.supplierBean = Response.data;
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

	$scope.fetchStockOrders = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.sId = $cookieStore.get('sp_cto');
		$http.post('supplierDetails/getAllStockOrdersBySupplierId/'+$scope._s_tk_com, $scope.sId)
		.success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.success = true;
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

	$scope.fetchSupplierPayments = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.sId = $cookieStore.get('sp_cto');
		$http.post('supplierDetails/getSupplierPayments/'+$scope._s_tk_com, $scope.sId)
		.success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.success = true;
				$scope.supplierPaymentBeansList = Response.data;
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