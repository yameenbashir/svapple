'use strict';

/**
 * CustomerDetailsController
 * @constructor
 */
var CustomerDetailsController = ['$scope', '$http','$interval','$timeout', '$window','$cookieStore','$rootScope','SessionService','CustomerDetailsControllerPreLoad',function($scope, $http,$interval,$timeout, $window,$cookieStore,$rootScope,SessionService,CustomerDetailsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.customerBean = {};
	$scope.customerPaymentsBeans = [];
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = CustomerDetailsControllerPreLoad.loadControllerData();
			$scope.InvoiceMainBeans = localStorage.getItem('salesHistory');
			$scope.InvoiceMainBeans =  JSON.parse($scope.InvoiceMainBeans);
			$scope.fetchData();
			//$scope.customerBean = $cookieStore.get('_e_cOt_jir');
			
		}
	};

	$scope.fetchData = function() {
		$rootScope.customerDetailsLoadedFully = false;
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
			$scope.customerBean = $scope.data;
			/*setTimeout(
					function() 
					{
						$('#myTable').DataTable( {
							responsive: true,
							paging: true,
							searching:true,
							order: [[ 1, "asc" ]],
							bInfo : true
						} );
					}, 900);*/
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.loadPaymentHistory = function(){
		if($scope.data!=null){
			
			
			if($scope.customerPaymentsBeans.length>0){
				var table = $('#myTable').DataTable();
				table.destroy();
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								order: [[ 1, "asc" ]],
								bInfo : true
							} );
						}, 10);
			}else{
				$scope.customerPaymentsBeans = [];
				$scope.customerPaymentsBeans = angular.copy($scope.customerBean.customerPaymentsBeans);
			}
			/*var table = $('#myTable').DataTable();
			table.destroy();
			*/
			}
	};
	
	$interval(function() {
		 if($cookieStore.get("salesHostoryLoaded")){
			 $scope.validate ();
			 $cookieStore.put("salesHostoryLoaded",false);
		 }
		}, 1);
	  
	    
	    
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
	
	$scope.updateCustomer = function() {
		
		$window.location = "/app/#/manageCustomer";
	};
	$scope.sessionValidation();
}];