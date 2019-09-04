'use strict';

/**
 * ContactUsController
 * @constructor
 */
var ContactUsController = function($scope, $http, $window,$cookieStore,$rootScope,$timeout) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showModalUpdate = false;
	$rootScope.homeContactSuccess = false;
	$rootScope.homeContactError = false;
	$rootScope.homeContactUsSuccess = false;
	$rootScope.homeContactUsError = false;
	$rootScope.sendMessage = false;
	$rootScope.messageSend = false;
	$scope.contactUsBeanList = [];
	$scope.systemBusy = 'Sorry for the inconvenience please try again at a later time.';
	
	$scope.contactUs = function(){
		$rootScope.homeContactSuccess = false;
		$rootScope.homeContactError = false;
		$rootScope.messageSend = true;
		$http.post('contactUs/getContactUsControllerData/')
		.success(function(Response) {
			$rootScope.messageSend = false;
			$scope.responseStatus = Response.status;
			
			if ($scope.responseStatus== 'SUCCESSFUL') {
				
				//$rootScope.homeContactSuccess = true;
				$scope.contactUsBeanList = Response.data;
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								pageLength: 10,
								searching:false,
								bInfo : true,
								dom : 'Bfrtip',
								buttons :$rootScope.buttonsView
							} );
							
						}, 1);
			}else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'){
				$rootScope.homeContactError = true;
				$rootScope.homeContactErrorMessage = Response.data;
				
				
			} else {
				$rootScope.homeContactError = true;
				$rootScope.homeContactErrorMessage = Response.data;
			}
		}).error(function() {
			$rootScope.messageSend = false;
			$rootScope.homeContactError = true;
			$rootScope.homeContactErrorMessage = Response.data;
		});
	};
	
	
	$scope.contactUs();
	
	
};