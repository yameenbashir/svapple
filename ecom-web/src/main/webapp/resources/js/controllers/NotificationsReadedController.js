'use strict';
/**
 * NotificationsReadedController
 * @constructor
 */
var NotificationsReadedController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','NotificationsReadedControllerPreLoad',function($scope, $http,$window,$cookieStore,$rootScope,$timeout,$route,SessionService,NotificationsReadedControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;	$scope.success = false;
	$scope.error = false;
	$scope.dataLoading = false;
	$scope.loadAll = true;
	$scope.tagBean = {};
	$scope.notificationBeanList = [];
	$scope.comparisonNotificationBeanList = [];
	$scope.readLoader = false;
	$scope.notificationsId = null;
	$scope.processing = false;
	$scope.userRoll == 1;
	$scope.readedMessagesCount="";
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = NotificationsReadedControllerPreLoad.loadControllerData();
			$scope.fetchData();
			
			
		}
	};

	$scope.fetchData = function() {
		$rootScope.notificationsLoadedFully = false;
		
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

			$scope.error = true;
			$scope.errorMessage = 'No record found !';
			
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
					$scope.notificationsList = $scope.data;
				$scope.comparisonNotificationBeanList = $scope.data;
				$scope.lodAllNotificationsAsynchrnously();
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								bInfo : true,
								dom : 'Bfrtip',
								pageLength: 5,
								
								buttons :$rootScope.buttonsView
							} );
						}, 10);
		}
		}
		
		$rootScope.globalPageLoader = false;
	};
	
	
	$scope.lodAllNotificationsAsynchrnously = function(){
		$scope.dataLoading = false;
		$http.post('notificationsReaded/getAllNotifications/'+$cookieStore.get('_s_tk_com')+'/'+"true").success(function(Response){
			$scope.data = Response.data;
			
			$scope.dataLoading = true;
		}).error(function() {
			$window.location = '/app/#/login';
		});
	};

		
	
		
		$scope.loadAllNotifications = function(){
			$scope.dataLoading = false;
			for(var i=0;i<$scope.data.length;i++){
				if(!checkNotificationExist($scope.data[i])){
					$scope.notificationsList.push($scope.data[i]);
				}
			}
			var table = $('#myTable').DataTable();
			if(table){
				 table.destroy();
			}
			setTimeout(
					function() 
					{
						$('#myTable').DataTable( {
							destroy : true,
							responsive: true,
							paging: true,
							searching:true,
							pageLength: 5,
							bInfo : true,
							dom : 'Bfrtip',
							buttons :$rootScope.buttonsView
						} );
						
					}, 1);
			$scope.dataLoading = true;
			$scope.loadAll = false;
		};

		function checkNotificationExist(notification) {
			if($scope.comparisonNotificationBeanList!='undefined' && $scope.comparisonNotificationBeanList!=null && $scope.comparisonNotificationBeanList.length>0){
				for(var i=0;i<$scope.comparisonNotificationBeanList.length;i++){
					if($scope.comparisonNotificationBeanList[i].notificationId==notification.notificationId){
						return true;
					}
				}
			}
			return false;
		};
		
	
	
	$scope.sessionValidation();
}];