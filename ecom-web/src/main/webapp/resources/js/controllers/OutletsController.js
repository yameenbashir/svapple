'use strict';

/**
 * OutletsController
 * @constructor
 */
var OutletsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','OutletsControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,OutletsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.systemBusy = 'System is Busy Please try again';

	$scope.addOutlet = function() {
		$window.location = "/app/#/outlet";
	};
	$scope.addRegister = function(outlet) {
		localforage.setItem('_r_cO_gra',outlet);
		$window.location = "/app/#/register";
	};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.data = OutletsControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.isHeadOffice = function(){
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "true"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	};

	$scope.showDetails= function(outlet) {
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "true"){
				$cookieStore.put('sp_oto', outlet.outletId);
				if(outlet.isHeadOffice == "false"){
					$window.location = '/app/#/outletPaymentDetails';
				}
			}
			else{
				if(outlet.outletId == $scope.userOutletId){
					$cookieStore.put('sp_oto', outlet.outletId);
					if(outlet.isHeadOffice == "false"){
						$window.location = '/app/#/outletPaymentDetails';
					}
				}
			}
		}
		else{
			if(outlet.outletId == $scope.userOutletId){
				$cookieStore.put('sp_oto', outlet.outletId);
				if(outlet.isHeadOffice == "false"){
					$window.location = '/app/#/outletPaymentDetails';
				}
			}
		}

	};

	$scope.fetchData = function() {
		$rootScope.outletsLoadedFully = false;
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
				$scope.outletsList = $scope.data;
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.editOutlet = function(outlet){
		$cookieStore.put('_e_cOt_gra',outlet.outletId);
		$window.location = "/app/#/manageOutlet";

	};

	$scope.updateRegister = function(registerBean){
		$cookieStore.put('_e_cOr_gra',registerBean.registerId) ;
		$window.location = "/app/#/manageRegister";
	};

	$scope.sessionValidation();
}];