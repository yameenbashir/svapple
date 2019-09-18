'use strict';

/**
 * CompanySetupController
 * @constructor
 */
var CompanySetupController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService) {

 $rootScope.MainSideBarhideit = false;
 $rootScope.MainHeaderideit = false;
 $scope.loading = false;
 $scope.companySuccess = false;
 $scope.companyError = false;
 $scope.systemBusy = 'System is Busy Please try again';
 $scope.companyBean = {};

 $scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
 
 $scope.fetchAllCurrencies = function() {

		$http.post('companySetup/getAllCurrencies').success(function(Response) {		
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.currencyList = Response.data;
			}else if($scope.responseStatus == 'SYSTEMBUSY'
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
	$scope.fetchAllCurrencies();
	
	$scope.fetchAllPrinterFormats = function() {
		$http.post('companySetup/getAllPrinterFormats').success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.printerFormatsList = Response.data;
				}else if($scope.responseStatus == 'SYSTEMBUSY'
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
	$scope.fetchAllPrinterFormats();
	
	$scope.fetchAlltimeZones = function() {
	$http.post('companySetup/getAllTimeZones').success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.timeZonesList = Response.data;
			}else if($scope.responseStatus == 'SYSTEMBUSY'
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
	$scope.fetchAlltimeZones();
	$scope.reloadRoute = function() {
		   $window.location.reload();
		};
	

	 $scope.addNewCompany = function() {
		  $scope.companySuccess = false;
		  $scope.companyError = false;
		  $scope.loading = true;
		  $scope.addNewCompanyloading = true;
		  /*if($scope.companyBean.companyName!=  $scope.companiesList.companyName){*/
		    if($scope.companyBean.companyName!=''){
			  	if( $scope.companyBean.email!='' && $scope.companyBean.password!=''){
			  			if( $scope.companyBean.email!= $scope.userBean.email){	
			  $http.post('companySetup/addNewCompany/'+$scope._s_tk_com, $scope.companyBean)
			  .success(function(Response) {
			   $scope.loading = false;
			   $scope.addNewCompanyloading = false;
			   
			   $scope.responseStatus = Response.status;
			   if ($scope.responseStatus == 'SUCCESSFUL') {
			    $scope.companyBean = {};
			    $scope.companySuccess = true;
			    $scope.companySuccessMessage = Response.data;
			    $timeout(function(){
			     $scope.companySuccess = false;
			     $window.location = Response.layOutPath;
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
		   $scope.addNewCompanyloading = false;
		   $scope.companyError = true;
		   $scope.companyErrorMessage = $scope.systemBusy;
		  });
			  }else{
				  $timeout(function(){
						$scope.error = false;
						$window.location = Response.layOutPath;;
					}, 2000);
					$scope.error = true;
					$scope.errorMessage = 'Please enter another email,user with same user email already exist ';
					$scope.addNewCompanyloading = false;
					$scope.companyError = true;
				    $scope.companyErrorMessage = $scope.systemBusy;
			  }
			  	}else{
					  
			  		$timeout(function(){
						$scope.error = false;
						
					}, 3000);
					$scope.error = true;
					$scope.errorMessage = 'Please enter email and  password in both fields';
					$scope.passwordBean.newPassword = '';
					$scope.passwordBean.confirmPassword = '';
					$scope.passwordBean.password = '';
				  }
		 }else{

				$timeout(function(){
					$scope.error = false;
					$window.location = Response.layOutPath;;
				}, 2000);
				$scope.error = true;
				$scope.errorMessage = 'Please enter another name,Company with same name already exist ';
				$scope.addNewCompanyloading = false;
				$scope.companyError = true;
			    $scope.companyErrorMessage = $scope.systemBusy;
			}
			  
			  
			  
		 };

 SessionService.validate();
 $rootScope.globalPageLoader = false;
}];