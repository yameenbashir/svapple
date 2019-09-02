'use strict';

/**
 * ChangePasswordController
 * @constructor
 */
var ChangePasswordController = ['$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService',function($scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.passwordBean = {};
	$scope.employeeId = $cookieStore.get('EmployeeId');
	$scope.userDetailBean = {};
	//Below beans are part of userDetailBean
	$scope.homeContactBean = {};
	$scope.workContactBean = {};

	$scope.email = $cookieStore.get('email') ;
	$scope.employeeDetailBean = $cookieStore.get('EmployeeInfo');
	$scope.loading = false;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
		}
	};

	$scope.changePasswordReq= function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		if ($scope.passwordBean.newPassword != '' || $scope.passwordBean.confirmPassword !='') {
			if ($scope.passwordBean.newPassword == $scope.passwordBean.confirmPassword) {
				$http.post('changePassword/changePasswordReq/'+$scope._s_tk_com+ '/' +$scope.email + '/' + $scope.passwordBean.password + '/' + $scope.passwordBean.newPassword).success(function(Response){					
					$scope.loading = false;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						if(Response.data.toString() != "false"){
							$scope.successMessage = 'Password has been changed successfully';
							$scope.success = true;							
							$timeout(function(){
								$scope.success = false;
								$scope.passwordBean.newPassword = '';
								$scope.passwordBean.confirmPassword = '';
								$scope.passwordBean.password = '';
								$window.location = Response.layOutPath;
							}, 2000);
						}
						else{
							$timeout(function(){
								$scope.error = false;
								//$window.location = '/app/#/personalInfo';
							}, 3000);
							$scope.error = true;
							$scope.errorMessage = 'Current password is not correct';
							$scope.passwordBean.newPassword = '';
							$scope.passwordBean.confirmPassword = '';
							$scope.passwordBean.password = '';
						}
					}
					else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
						$scope.passwordBean.newPassword = '';
						$scope.passwordBean.confirmPassword = '';
						$scope.passwordBean.password = '';
					}else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$scope.passwordBean.newPassword = '';
						$scope.passwordBean.confirmPassword = '';
						$scope.passwordBean.password = '';
					}
				}).error(function() {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = $scope.systemBusy;
					$scope.passwordBean.newPassword = '';
					$scope.passwordBean.confirmPassword = '';
					$scope.passwordBean.password = '';
				});
			}
			else{

				$timeout(function(){
					$scope.error = false;
					//$window.location = '/app/#/personalInfo';
				}, 3000);
				$scope.error = true;
				$scope.errorMessage = 'Password does not match the confirm password';
				$scope.passwordBean.newPassword = '';
				$scope.passwordBean.confirmPassword = '';
				$scope.passwordBean.password = '';
			}
		}
		else{

			$timeout(function(){
				$scope.error = false;
				//$window.location = '/app/#/personalInfo';
			}, 3000);
			$scope.error = true;
			$scope.errorMessage = 'Please enter new password in both fields';
			$scope.passwordBean.newPassword = '';
			$scope.passwordBean.confirmPassword = '';
			$scope.passwordBean.password = '';
		}

	};
	$scope.sessionValidation();
}];
