'use strict';

/**
 * InventoryCountController
 * @constructor
 */
var InventoryCountController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','InventoryCountControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,InventoryCountControllerPreLoad) {

	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sesssionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com');				
		}
	};	
	
	$scope.addInventoryCount = function(){
		$window.location = "/app/#/inventoryCountCreate";
	};

	$scope.inventoryCountActions = function(inventoryCountBean) {
		$cookieStore.put('_ct_sc_ost',inventoryCountBean);
		$window.location = "/app/#/inventoryCountActions";
	};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.data = InventoryCountControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.isAdmin();
		}
	};

	$scope.isAdmin = function(){
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

	$scope.fetchData = function() {
		$rootScope.inventoryCountLoadedFully = false;
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
				$scope.inventoryCountBeansList = $scope.data;
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

	$scope.sessionValidation();

}];