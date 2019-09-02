'use strict';

/**
 * OutletsController
 * 
 * @constructor
 */
var NewSupplierController = ['$scope', '$http', '$window', '$cookieStore','$route', '$rootScope','SessionService','$timeout','NewSupplierControllerPreLoad',function($scope, $http, $window, $cookieStore,$route, $rootScope, SessionService, $timeout,NewSupplierControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.Success = false;
	$scope.Error = false;
	$scope.supplierBean = {};
	$scope.addressList = [];
	$scope.tempAddress = [];
	$scope.physicalAddressBean = null;
	$scope.postalAddressBean = null;
	$scope.supplierScreenMode = 'New Supplier';
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = NewSupplierControllerPreLoad.loadControllerData();
			$scope.fetchData();
			
			$scope.cookieSupplierBean = $cookieStore.get('supplier');

			if ($scope.cookieSupplierBean) {
				$scope.supplierBean = $scope.cookieSupplierBean;
				$scope.supplierScreenMode = 'Manage Supplier';
				if ($scope.cookieSupplierBean.addresses) {
					$scope.tempAddress = $scope.cookieSupplierBean.addresses
							.filter(GetPhyscialAddress);
					if ($scope.tempAddress != null && $scope.tempAddress.length > 0) {
						$scope.physicalAddressBean = $scope.tempAddress[0]
					}
					$scope.tempAddress = [];
					$scope.tempAddress = $scope.cookieSupplierBean.addresses
							.filter(GetPostalAddress);
					if ($scope.tempAddress != null && $scope.tempAddress.length > 0) {
						$scope.postalAddressBean = $scope.tempAddress[0]
					}
				}
			}
		}
	};

	$scope.fetchData = function() {
		$rootScope.newSupplierLoadedFully = false;
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
			
		}
		$rootScope.globalPageLoader = false;
	};
	$scope.sessionValidation();
	

	$scope.addSupplier = function() {
		$scope.Success = false;
		$scope.Error = false;
		$scope.loading = true;
		if ($scope.physicalAddressBean) {
			$scope.physicalAddressBean.addressType = 'Physical Address';
			$scope.addressList.push($scope.physicalAddressBean);
		}
		if ($scope.postalAddressBean) {
			$scope.postalAddressBean.addressType = 'Postal Address';
			$scope.addressList.push($scope.postalAddressBean);
		}
		$scope.supplierBean.addresses = angular.copy($scope.addressList);

		$http.post('newSupplier/addSupplier/' + $scope._s_tk_com,
				$scope.supplierBean).success(
				function(Response) {
					

					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {

						$scope.Success = true;
						$scope.SuccessMessage = Response.data;
						
						$timeout(function() {
							$scope.loading = false;
							$scope.Success = false;
							$scope.supplierBean = {};
							$scope.addressList = [];
							$window.location = Response.layOutPath;
						}, 1500);
						$scope.physicalAddressBean = null;
						$scope.postalAddressBean = null;

					} else if ($scope.responseStatus == 'INVALIDSESSION'
							|| $scope.responseStatus == 'SYSTEMBUSY') {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
					}
				}).error(function() {
			$scope.loading = false;
			$scope.Error = true;
			$scope.ErrorMessage = $scope.systemBusy;
		});
	};

	
	$scope.updateSupplier = function() {
		$scope.Success = false;
		$scope.Error = false;
		$scope.loading = true;
		if ($scope.physicalAddressBean) {
			$scope.physicalAddressBean.addressType = 'Physical Address';
			$scope.addressList.push($scope.physicalAddressBean);
		}
		if ($scope.postalAddressBean) {
			$scope.postalAddressBean.addressType = 'Postal Address';
			$scope.addressList.push($scope.postalAddressBean);
		}
		$scope.supplierBean.addresses = angular.copy($scope.addressList);

		$http.post('newSupplier/updateSupplier/' + $scope._s_tk_com,
				$scope.supplierBean).success(
				function(Response) {
					

					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {

						$scope.Success = true;
						$scope.SuccessMessage = Response.data;
						
						$timeout(function() {
							$scope.Success = false;
							$scope.loading = false;
							$scope.supplierBean = {};
							$scope.addressList = [];
							$window.location = Response.layOutPath;
						}, 1500);
						$scope.physicalAddressBean = null;
						$scope.postalAddressBean = null;

					} else if ($scope.responseStatus == 'INVALIDSESSION'
							|| $scope.responseStatus == 'SYSTEMBUSY') {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
					}
				}).error(function() {
			$scope.loading = false;
			$scope.Error = true;
			$scope.ErrorMessage = $scope.systemBusy;
		});
	};
	
	
	
	$scope.cancelClick = function() {
		$window.location = "/app/#/suppliers";
	};

	$scope.populatePhysicalAddress = function() {
		if ($scope.postalAddressBean == null) {

			$scope.postalAddressBean = {};
		}

		if ($scope.physicalAddressBean) {
			$scope.postalAddressBean.contactName = $scope.physicalAddressBean.contactName;
			$scope.postalAddressBean.firstName = $scope.physicalAddressBean.firstName;
			$scope.postalAddressBean.lastName = $scope.physicalAddressBean.lastName;
			$scope.postalAddressBean.email = $scope.physicalAddressBean.email;
			$scope.postalAddressBean.phone = $scope.physicalAddressBean.phone;
			$scope.postalAddressBean.fax = $scope.physicalAddressBean.fax;

			$scope.postalAddressBean.website = $scope.physicalAddressBean.website;
			$scope.postalAddressBean.twitter = $scope.physicalAddressBean.twitter;

			$scope.postalAddressBean.street = $scope.physicalAddressBean.street;
			$scope.postalAddressBean.suburb = $scope.physicalAddressBean.suburb;
			$scope.postalAddressBean.city = $scope.physicalAddressBean.city;
			$scope.postalAddressBean.postalCode = $scope.physicalAddressBean.postalCode;
			$scope.postalAddressBean.state = $scope.physicalAddressBean.state;
			$scope.postalAddressBean.countryId = $scope.physicalAddressBean.countryId;

			$scope.postalAddressBean.addressType = 'Postal Address'
		}
	};

	function GetPhyscialAddress(address) {
		
		var val = 'Physical Address';
		if (address.addressType == val) {
			return address;
		}

	};

	function GetPostalAddress(address) {
		if (address.addressType == "Postal Address") {
			return address;
		}

	};

}];