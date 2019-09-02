'use strict';

/**
 * TicketActivityController
 * @constructor
 */
var TicketActivityController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','TicketActivityControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,TicketActivityControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.dasBoard = 'Documentation';
	$scope.controlPanel ='Product Help';
	$scope.home ='Home';
	$scope.support = 'Support';
	$scope.pageName = 'Ticket Activity';
	$scope.manageTicketActivity = 'Manage Ticket';
	$scope.addOrManageTickets = 'Ticket Activities';
	$scope.ticketInfo = 'Ticket Information';
	$scope.selectSeverityText = 'Select Severity';
	$scope.describeIssue = 'Describe Issue...';
	$scope.ticketButtonText = 'Close Ticket';
	$scope.ticketReOpenButtonText = 'Reopen Ticket';
	$scope.severityText = 'Severity';
	$scope.statusText = 'Status';
	$scope.ticketDetailText = 'Detail';
	$scope.systemBusy = 'System is Busy Please try again';


	$cookieStore.put('redirectedThroughManageEmployee', false);
	$cookieStore.put('isLogin', '');
	$cookieStore.put('EmployeeInfo', '');
	$cookieStore.put('isUpdateCall', '');
	$cookieStore.put('CompanyInfo', '');
	$cookieStore.put('isCompanyUpdateCall', '');
	$cookieStore.put('WorkplaceInfo', '');
	$cookieStore.put('isWorkplaceUpdateCall', '');
	$cookieStore.put('departmentsInfo','');
	$cookieStore.put('invoiceNo','');
	$cookieStore.put('ScheduleInfo','');
	$cookieStore.put('isScheduleUpdateCall', '');
	$cookieStore.put('ScheduleEmployeesListInfo','');
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$rootScope.ticketActivityLoadedFully = true;
			$scope.data = TicketActivityControllerPreLoad.loadControllerData();
			$rootScope.ticketActivityLoadedFully = false;
			$scope.fetchData();
		}
	};
	
	
	$scope.fetchData = function() {

		
		$rootScope.supportLoadedFully = false;
		$rootScope.supportProcessing = false;
		$cookieStore.put('supportProcessing',$rootScope.supportProcessing);
		if($scope.data == 'NORECORDFOUND'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
		}
		else if($scope.data == 'SYSTEMBUSY'){
			$scope.error = true;
			$scope.errorMessage = "System Busy";
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$window.location = '/app/#/login';
		}
		else{
			if($scope.data!=null){
				if($scope.data.ticketActivitiesList!=null){
					$scope.ticketActivities = $scope.data.ticketActivitiesList;
				}
				if($scope.data.ticketBean!=null){
					$scope.ticketBean = $scope.data.ticketBean;
					$scope.ticketBeanTemp = angular.copy($scope.data.ticketBean);
				}
			}
			
		}
		$rootScope.globalPageLoader = false;
	};


	$scope.addTicketActivity= function(description){
		
		if($scope.ticketBean.ticketStatus=='Closed'){
			$scope.alertMessage = 'Please reopen ticket if you want to add ticket activity.';
			$('#myAlertBox').modal('show');
			return;
		}
		$scope.ticketBeanTemp.description = description;
		$scope.description ='';
		$http.post('ticketActivity/addTicketActivity/'+$scope._s_tk_com,$scope.ticketBeanTemp).success(function(Response) {

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.getTicketActivities($scope.ticketBean.ticketId);

			}else if($scope.responseStatus == 'NORECORDFOUND'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.scheduleError = true;
				$window.location = '/app/#/login';

			}

		}).error(function() {
			$scope.scheduleError = true;
			$scope.scheduleErrorMessage = $scope.systemBusy;
		});
	};

	$scope.getTicketActivities = function(ticketId){

		$http.post('ticketActivity/getTicketActivitiesByTicketId/'+$scope._s_tk_com+'/'+ticketId).success(function(Response) {

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.ticketActivities = Response.data;
			}else if($scope.responseStatus == 'NORECORDFOUND'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.scheduleError = true;
				$window.location = '/app/#/login';

			}

		}).error(function() {
			$scope.scheduleError = true;
			$scope.scheduleErrorMessage = $scope.systemBusy;
		});

	};
	$scope.closeTicketConfirmation = function(){
		$scope.alertMessage = 'Are you sure you want to close the ticket.';
		$('#alertBox').modal('show');
	};
	$scope.closeTicket =function (){
		
		$http.post('ticketActivity/closeTicket/'+$scope._s_tk_com,$scope.ticketBean).success(function(Response) {

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				
				$scope.getTicketActivities($scope.ticketBean.ticketId);
				$scope.ticketBean.ticketStatus  = 'Closed';
			}else if($scope.responseStatus == 'NORECORDFOUND'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.scheduleError = true;
				$window.location = '/app/#/login';

			}

		}).error(function() {
			$scope.scheduleError = true;
			$scope.scheduleErrorMessage = $scope.systemBusy;
		});
	};
	$scope.reOpenTicketConformation = function(){
		$scope.alertMessage = 'Are you sure you want to reopn the ticket.';
		$('#reOpenAlertBox').modal('show');
	};
	
	$scope.reOpenTicket = function(){
		
		$http.post('ticketActivity/reOpenTicket/'+$scope._s_tk_com,$scope.ticketBean).success(function(Response) {

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.getTicketActivities($scope.ticketBean.ticketId);
				$scope.ticketBean.ticketStatus  = 'Open';
			}else if($scope.responseStatus == 'NORECORDFOUND'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'){
				$scope.scheduleError = true;
				$scope.scheduleErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.scheduleError = true;
				$window.location = '/app/#/login';

			}

		}).error(function() {
			$scope.scheduleError = true;
			$scope.scheduleErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();
}];