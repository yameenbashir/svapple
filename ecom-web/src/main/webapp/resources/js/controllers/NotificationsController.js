'use strict';

/**
 * NotificationsController
 * @constructor
 */
var NotificationsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','NotificationsControllerPreLoad',function($scope, $http,$window,$cookieStore,$rootScope,$timeout,$route,SessionService,NotificationsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.success = false;
	$scope.error = false;
	$scope.dataLoading = false;
	$scope.tagBean = {};
	$scope.notificationBeanList = [];
	$scope.comparisonNotificationBeanList = [];
	$scope.readLoader = false;
	$scope.notificationsId = null;
	$scope.processing = false;
	$scope.userRoll == 1;
	$scope.unReadMessagesCount="";
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = NotificationsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.getAllUnReadNotifications();
		}
	};

	$scope.fetchData = function() {
		$rootScope.productTagsLoadedFully = false;
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
			$cookieStore.put('countNotifications',0);
			$rootScope.countNotifications = $cookieStore.get('countNotifications');
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
				$scope.notificationBeanList = $scope.data;
				$cookieStore.put('countNotifications',$scope.notificationBeanList.length);
				$rootScope.countNotifications = $cookieStore.get('countNotifications');
				$scope.comparisonNotificationBeanList = $scope.data;
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
				//$scope.lodAllNotificationsAsynchrnously();
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.lodAllNotificationsAsynchrnously = function() {
		$http.post('notifications/getAllNotifications/'+$cookieStore.get('_s_tk_com')+'/'+"true").success(function(Response) {
				$scope.responseStatus = Response.status;
				$scope.dataLoading = true;
				if ($scope.responseStatus== 'SUCCESSFUL') {
					$scope.data = Response.data;
					$scope.success = true;
					$scope.sucessMessage = Response.data;
					
				}else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});

		};
		
		
		
		
		
		
		$scope.activeInactiveAll = function(isActive) {
			
			$scope.loading = true;
			$http.get('notifications/activeInactiveAllNotifications/'+$scope._s_tk_com+'/'+isActive)
			.success(function(Response) {
				
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					notifications = Response.data;
					localforage.setItem('_e_cOt_jir',notifications);
					$window.location = "/app/#/manageNoftifications";
					
				}else  {
				
					$window.location = Response.layOutPath;
				}
			}).error(function() {
				$scope.loading = false;
				$scope.outletError = true;
				$scope.outletErrorMessage = $scope.systemBusy;
			});
			
		};
		
		
		
		
		$scope.editNotification = function(notification) {
			$scope.notificationsId = notification.notificationId;
			$scope.readLoader = true;
			$http.get('notifications/updateSelectedNotifications/'+$scope._s_tk_com+'/'+notification.notificationId)
			.success(function(Response) {
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.success = true;
					$scope.successMessage = Response.data;
					$timeout(function(){
						$scope.success = false;
						$scope.readLoader = false;
						$route.reload();
						
					    }, 1000);
				}else if($scope.responseStatus == 'SYSTEMBUSY') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$timeout(function(){
							$scope.error = false;
						$route.reload();
						
					    }, 1500);
				}else if  ($scope.responseStatus == 'INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;

				   }else{
					 	$scope.error = true;
						$scope.errorMessage = Response.data;
				}
				}).error(function() {
				$scope.loading = false;
				$scope.error = true;
				$scope.errorMessage = $scope.systemBusy;
			});
			
		};
		
		
		
		$scope.loadAllNotifications = function(){
			$scope.dataLoading = false;
			
			for(var i=0;i<$scope.data.length;i++){
				if(!checkNotificationExist($scope.data[i])){
					$scope.notificationBeanList.push($scope.data[i]);
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
		
		$scope.markAllAsRead = function(){
				$scope.processing = true;
				$scope.userRoll == 1;
			$http.get('notifications/markAllAsReadByOutletIdCompanyId/'+$scope._s_tk_com)
					.success(function(Response) {
						$scope.responseStatus = Response.status;
						if ($scope.responseStatus == 'SUCCESSFUL'){
							$scope.success = true;
							$scope.successMessage = Response.data;
							$timeout(function(){
								$scope.success = false;
								$route.reload();
								
							    }, 1000);
						}else  {
						
							$window.location = Response.layOutPath;
						}
					}).error(function() {
						$scope.loading = false;
						$scope.outletError = true;
						$scope.outletErrorMessage = $scope.systemBusy;
					});
	};
	
	$scope.getAllUnReadNotifications = function(){
		$scope.unReadNotifications = 0;
		$http.get('notifications/getAllUnReadNotifications/'+$scope._s_tk_com)
		.success(function(Response) {

				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL'){
					$scope.unReadNotifications = Response.data;
					
				}
				
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});

	};	
		

	
	$scope.sessionValidation();
}];