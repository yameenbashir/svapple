'use strict';

/**
 * NewUserController
 * @constructor
 */
var NewUserController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','NewUserControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,NewUserControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.userSuccess = false;
	$scope.userError = false;
	$scope.userBean = {};
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = NewUserControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.newUserLoadedFully = false;
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
			if($scope.data.outletBeans!=null){
				$scope.outletsList = $scope.data.outletBeans;
			}
			if($scope.data.rolesBeanList!=null){
				$scope.rolesList = $scope.data.rolesBeanList;
			}
			
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.addUser = function() {
		$scope.userSuccess = false;
		$scope.userError = false;
		$scope.loading = true;
		$http.post('newUser/addUser/'+$scope._s_tk_com, $scope.userBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.userBean = {};
				$scope.userSuccess = true;
				$scope.userSuccessMessage = Response.data;
				$timeout(function(){
					$scope.userSuccess = false;
					$window.location = Response.layOutPath;
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.userError = true;
				$scope.userErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.userError = true;
				$scope.userErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.userError = true;
			$scope.userErrorMessage = $scope.systemBusy;
		});
	};
	$scope.sessionValidation();
}];