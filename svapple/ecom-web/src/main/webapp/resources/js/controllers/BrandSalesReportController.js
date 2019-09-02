'use strict';

/**
 * BrandSalesReportController
 * @constructor
 */

var BrandSalesReportController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','BrandSalesReportControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,BrandSalesReportControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.salesReportSuccess = false;
	$scope.salesReportError = false;
	$scope.salesReportError = false;
	$scope.salesReportBean = {};
	$scope.outlets = [];
	$scope.checkbox1 =  true;
	$scope.checkbox2 =  false;
	$scope.checkbox3 =  false;
	$scope.checkbox4 =  false;
	$scope.reportTypeSelect = $rootScope.salesReportType;
	$scope.outletSelected = $rootScope.inventoryReportOutletName;
	$scope.sesssionValidation = function(){
		if($rootScope.salesReportDateType == "weekly"){
			$scope.checkbox1 =  false;
			$scope.checkbox2 =  true;
		}else if($rootScope.salesReportDateType == "monthly"){
			$scope.checkbox1 =  false;
			$scope.checkbox3 =  true;
		}else if($rootScope.salesReportDateType == "yearly"){
			$scope.checkbox1 =  false;
			$scope.checkbox4 =  true;
		}
			if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = BrandSalesReportControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};


	$scope.fetchData = function() {
		
		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){

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
				if($scope.data.tableData!=null){
					$scope.salesReport = $scope.data.tableData;
				}
				if($scope.data.outletBeans!=null){
					$scope.outlets = $scope.data.outletBeans;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$("#reportrange").on("change", function(event){
		var innerText = document.getElementById("reportrange").innerText;
		//alert(innerText);
		$scope.dateRange = "";
		$scope.dateRange = innerText;
		$scope.$apply();
		$scope.fetchSalesReportByDateRange();
	});
	$scope.changeReportDataType = function() {
		$rootScope.salesReportType = $scope.reportType;
		$route.reload();
	};
	
	$scope.changeOutletName = function() {
		$rootScope.inventoryReportOutletName = $scope.outletName;
		$route.reload();
	};
	
	$scope.fetchSalesReportByDateRange = function() {
		$scope.salesReportSuccess = false;
		$scope.salesReportError = false;
		$scope.loading = true;
		var dateRang = $scope.dateRange.split("-");
		$rootScope.salesReportStartDate = dateRang[0].trim(); 
		$rootScope.salesReportEndDate = dateRang[1].trim(); 
		$route.reload();

	};
	
	$scope.changeReportType = function(type) {
		
		if(type == "Daily"){
			$scope.checkbox1 =  true;
			$scope.checkbox2 =  false;
			$scope.checkbox3 =  false;
			$scope.checkbox4 =  false;
		}else if(type == "Weekly"){
			$scope.checkbox1 =  false;
			$scope.checkbox2 =  true;
			$scope.checkbox3 =  false;
			$scope.checkbox4 =  false;
		}else if(type == "Monthly"){
			$scope.checkbox1 =  false;
			$scope.checkbox2 =  false;
			$scope.checkbox3 =  true;
			$scope.checkbox4 =  false;
		}else if(type == "Yearly"){
			$scope.checkbox1 =  false;
			$scope.checkbox2 =  false;
			$scope.checkbox3 =  false;
			$scope.checkbox4 =  true;
		}
		$rootScope.salesReportDateType = type;
		$route.reload();
	}
	$scope.sesssionValidation();
}];