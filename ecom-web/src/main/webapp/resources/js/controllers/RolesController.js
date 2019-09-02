'use strict';

/**
 * OutletsController
 * @constructor
 */
var RolesController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','RolesControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,RolesControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	SessionService.validate();

	$scope.roleDetails = function(roleId) {
		$cookieStore.put('_m_rd_gra',roleId) ;
		$window.location = "/app/#/manageRole";
	};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = RolesControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.rolesLoadedFully = false;
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
				$scope.rolesList = $scope.data;
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