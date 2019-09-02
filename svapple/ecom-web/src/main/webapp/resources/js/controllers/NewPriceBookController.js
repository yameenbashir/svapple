'use strict';

/**
 * OutletsController
 * @constructor
 */
var NewPriceBookController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$filter','$timeout','SessionService','NewPriceBookControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$filter,$timeout,SessionService,NewPriceBookControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.priceBookSuccess = false;
	$scope.priceBookError = false;
	$scope.priceBookBean = {};
	$scope.priceBookBean.flatDiscount = "0.00";
	$scope.gui = {};
	$scope.gui.flatSale = false;
	$scope.gui.activePriceBook = true;
	$scope.validOn = {
			value: 'Yes'
	      };
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = NewPriceBookControllerPreLoad.loadControllerData();
			
			/*$('#startDate').data("DateTimePicker").MinDate(new Date());
			$('#startDate').data("DateTimePicker").Date(new Date());
			$('#endDate').data("DateTimePicker").MinDate(new Date());
			$('#endDate').data("DateTimePicker").Date(new Date());*/
			$scope.fetchData();
			$scope.setDefaultDates();
		}
	};
	
	$scope.toggle = function(){
		$scope.gui.flatSale = !$scope.gui.flatSale;
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
				
				if($scope.data.outletBeans!=null){
					$scope.outletsList = $scope.data.outletBeans;
				}
				if($scope.data.customerGroupBeansList!=null){
					$scope.customerGroupList = $scope.data.customerGroupBeansList;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.setDefaultDates = function(){
		var today = new Date(); 
		var month = today.getUTCMonth() + 1;
		if(month<10){
			month = '0'+month;
		}
		var startDate = today.getDate();
		if(startDate<10){
			startDate = '0'+startDate;
		}
		var endDate = new Date();
		var numberOfDaysToAdd = 30;
		endDate.setDate(endDate.getDate() + numberOfDaysToAdd);
		var endMonth = endDate.getUTCMonth() + 1;
		if(month<10){
			endMonth = '0'+endMonth;
		}
		var endDate1 = endDate.getDate();
		if(endDate1<10){
			endDate1 = '0'+endDate1;
		}
		$scope.priceBookBean.validFrom = today.getFullYear()+'-'+month+'-'+startDate;
		$scope.priceBookBean.validTo = endDate.getFullYear()+'-'+endMonth+'-'+endDate1;
		
	};
	 
	$("#startDate").on("change", function(event){
		var innerText = document.getElementById("startDate1").value;
		$scope.priceBookBean.validFrom = innerText;
		$scope.$apply();
		
	});
	
	$("#endDate").on("change", function(event){
		var innerText = document.getElementById("endDate1").value;
		$scope.priceBookBean.validTo = innerText;
		$scope.$apply();
		
	});
	
	$scope.addPriceBook = function(){
		
		$scope.priceBookSuccess = false;
		$scope.priceBookError = false;
		
		if($filter('date')($scope.priceBookBean.validFrom, 'yyyy-MM-dd')> $filter('date')($scope.priceBookBean.validTo, 'yyyy-MM-dd')){
			$scope.alertMessage = 'From Date should be before Valid To Date.';
			$('#alertBox').modal('show');
			return;
		}
		if($filter('date')($scope.priceBookBean.validFrom, 'yyyy-MM-dd')< $filter('date')(new Date(), 'yyyy-MM-dd')){
			$scope.alertMessage = 'From Date should be today or future Date.';
			$('#alertBox').modal('show');
			return;
		}
		if($scope.validOn.value=="Yes"){
			$scope.priceBookBean.isValidOnStore = "true";
			$scope.priceBookBean.isValidOnEcom = "false";
		}else{
			$scope.priceBookBean.isValidOnStore = "false";
			$scope.priceBookBean.isValidOnEcom = "true";
		}
		if($scope.gui.flatSale){
			$scope.priceBookBean.flatSale = "true";
		}else{
			$scope.priceBookBean.flatSale = "false";
		}
		if($scope.gui.activePriceBook){
			$scope.priceBookBean.active = "true";
			if($scope.gui.flatSale){
				var discount = parseFloat($scope.priceBookBean.flatDiscount);
				if(isNaN(discount)){
					$scope.alertMessage = 'Discount not in valid format. Valid formats e.g (1.00,1)';
					$('#alertBox').modal('show');
					return;
				}
				/*if(discount<1){
					$scope.alertMessage = 'Discount should not be less than 1%';
					$('#alertBox').modal('show');
					return;
				}*/
			}
			
			
		}else{
			$scope.priceBookBean.active = "false";
		}
		
		$scope.loading = true;
		$http.post('newPriceBook/addPriceBook/'+$scope._s_tk_com, $scope.priceBookBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.loading = true;
				$scope.priceBookSuccess = true;
				$scope.priceBookSuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.priceBookSuccess = false;
					$scope.loading = false;
					$scope.priceBookBean = {};
					$cookieStore.put('_e_cPri_gra',Response.data) ;
					$window.location = "/app/#/managePriceBook";
					/*if(Response.layOutPath=='true'){
						$window.location = "/app/#/priceBook";
					}else{
						$window.location = "/app/#/managePriceBook";
					}*/
					
				}, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'
			|| $scope.responseStatus == 'ADDRESTRICATION') {
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = Response.data;
				if($scope.responseStatus == 'INVALIDSESSION')
					$window.location = Response.layOutPath;
			}else {
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.priceBookError = true;
			$scope.priceBookErrorMessage = $scope.systemBusy;
		});
		
	};
	
	$scope.sessionValidation();
	
}];