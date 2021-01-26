'use strict';

/**
 * ManageRoleController
 * @constructor
 */
var ManageRoleController = ['$scope', '$http','$window','$cookieStore','$rootScope','$timeout','$route','SessionService','ManageRoleControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,ManageRoleControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showAddSalesTaxModal = false;
	$scope.manageRoleSuccess = false;
	$scope.manageRoleError = false;
	$scope.isCashAdded = false;
	$scope.isCreditCardAdded = false;
	$scope.manageRoleAddError = false;
	$scope.loadingUpdate = false;
	$scope.loading = false;
	$scope.manageRoleBean = {};
	
	$scope.systemBusy = 'System is Busy Please try again';
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ManageRoleControllerPreLoad.loadControllerData();
			$scope.fetchData();
			var roleId = $cookieStore.get('_m_rd_gra');
			if(roleId=='1'){
				$scope.roleName = 'Administrator';
			}
			if(roleId=='2'){
				$scope.roleName = 'Manager';
			}
			if(roleId=='3'){
				$scope.roleName = 'Cashier';
			}
			
		}
	};

	$scope.fetchData = function() {
		$rootScope.manageRoleLoadedFully = false;
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
			if($scope.data!=null){
				$scope.menuBeanList = $scope.data;
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

	$scope.selectUnselectAll = function(value){

		if($scope.menuBeanList!=null && $scope.menuBeanList.length>0){
			for(var index=0;index<$scope.menuBeanList.length;index++){
				$scope.menuBeanList[index].activeIndicatior = value;
			}
		}
	};

	
	$scope.updatePageRights = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.manageRoleSuccess = false;
		$scope.manageRoleError = false;
		$scope.loadingUpdate = true;
		$http.post('manageRole/updatePageRightsByRoleId/'+$scope._s_tk_com, $scope.menuBeanList)
		.success(function(Response) {
			$scope.loadingUpdate = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.manageRoleSuccess = true;
				$scope.manageRoleSuccessMessage = Response.data;
				$scope.showUpdatemanageRoleModal = false;
				$timeout(function(){
					$scope.manageRoleSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
					//$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.manageRoleError = true;
				$scope.manageRoleErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.manageRoleError = true;
				$scope.manageRoleErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loadingUpdate = false;
			$scope.manageRoleError = true;
			$scope.manageRoleErrorMessage = $scope.systemBusy;
		});
	};
	
	
	$scope.sessionValidation();
}];