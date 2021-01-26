'use strict';

/*
 * Store Controller.js
 */
var StoreController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$route','StoreControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$route,StoreControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.companyBean = {};
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = StoreControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.storeLoadedFully = false;
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
			if($scope.data.timeZoneBeans!=null){
				$scope.timeZonesList = $scope.data.timeZoneBeans;
			}
			if($scope.data.currencyBeans!=null){
				$scope.currencyList = $scope.data.currencyBeans;
			}
			if($scope.data.printerFormatBeans!=null){
				$scope.printerFormatsList = $scope.data.printerFormatBeans;
			}
			if($scope.data.companyBean!=null){
				$scope.companyBean = $scope.data.companyBean;
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.updateCompany = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.companySuccess = false;
		$scope.companyError = false;
		$scope.loading = true;
		$http.post('store/updateCompany/'+$scope._s_tk_com, $scope.companyBean)
		.success(function(Response) {
			$scope.loading = false;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				
				$scope.companySuccess = true;
				$scope.companyBean = {};
				$scope.companyBean = Response.data;
				$scope.companySuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.companySuccess = false;
					$route.reload();
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.companyError = true;
			$scope.companyErrorMessage = $scope.systemBusy;
		});
	};

	$scope.updateContactInformation = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.companySuccess = false;
		$scope.companyError = false;
		$scope.contactInformationloading = true;
		$http.post('store/updateContactInformation/'+$scope._s_tk_com, $scope.companyBean)
		.success(function(Response) {
			$scope.contactInformationloading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				
				$scope.companySuccess = true;
				$scope.companySuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.companySuccess = false;
					$route.reload();
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.contactInformationloading = false;
			$scope.companyError = true;
			$scope.companyErrorMessage = $scope.systemBusy;
		});
	};

	$scope.updateAddress = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.companySuccess = false;
		$scope.companyError = false;
		$scope.addressInformationloading = true;
		$http.post('store/updateAddress/'+$scope._s_tk_com, $scope.companyBean)
		.success(function(Response) {
			$scope.addressInformationloading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				
				$scope.companySuccess = true;
				$scope.companySuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.companySuccess = false;
					$route.reload();
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.companyError = true;
				$scope.companyErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.addressInformationloading = false;
			$scope.companyError = true;
			$scope.companyErrorMessage = $scope.systemBusy;
		});
	};

	
	
	$scope.cancelClick = function() {
		$window.location = "/app/#";
	};
	
	
	$scope.populatePhysicalAddress = function() {
		
		if($scope.companyBean != null && $scope.companyBean.addresses != null && $scope.companyBean.addresses.length > 0 )
			{
			
			
			$scope.companyBean.addresses[1].contactName = $scope.companyBean.addresses[0].contactName;
			$scope.companyBean.addresses[1].firstName = $scope.companyBean.addresses[0].firstName;
			$scope.companyBean.addresses[1].lastName = $scope.companyBean.addresses[0].lastName;
			$scope.companyBean.addresses[1].email = $scope.companyBean.addresses[0].email;
			$scope.companyBean.addresses[1].phone = $scope.companyBean.addresses[0].phone;
			$scope.companyBean.addresses[1].fax = $scope.companyBean.addresses[0].fax;
			
			$scope.companyBean.addresses[1].website = $scope.companyBean.addresses[0].website;
			$scope.companyBean.addresses[1].twitter = $scope.companyBean.addresses[0].twitter;
			
			$scope.companyBean.addresses[1].street = $scope.companyBean.addresses[0].street;
			$scope.companyBean.addresses[1].suburb = $scope.companyBean.addresses[0].suburb;
			$scope.companyBean.addresses[1].city = $scope.companyBean.addresses[0].city;
			$scope.companyBean.addresses[1].postalCode = $scope.companyBean.addresses[0].postalCode;
			$scope.companyBean.addresses[1].state = $scope.companyBean.addresses[0].state;
			$scope.companyBean.addresses[1].countryId = $scope.companyBean.addresses[0].countryId;
			
			$scope.companyBean.addresses[1].addressType = 'Physcial Address'
			
			}
		
		};
	
		$scope.sessionValidation();
}];