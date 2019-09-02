'use strict';

/**
 * OutletsController
 * @constructor
 */
var UsersController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','UsersControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,UsersControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	SessionService.validate();

	$scope.newUsers = function() {
		$window.location = "/app/#/newUser";
	};

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = UsersControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.usersLoadedFully = false;
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
				$scope.usersList = $scope.data;
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
	$scope.editUser = function(userBean){
		$cookieStore.put('_e_uob_gra',userBean.userId);
		$window.location = '/app/#/manageUser';
	};
	
	$scope.sessionValidation();


}];