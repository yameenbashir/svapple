'use strict';

/**
 * SupportController
 * @constructor
 */
var SupportController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$route','SessionService','SupportControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$route,SessionService,SupportControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showProcessing = false;
	$scope.dasBoard = 'Documentation';
	$scope.controlPanel ='Product Help';
	$scope.home ='Home';
	$scope.pageName = 'Support';
	$scope.manageTicket = 'Manage Tickets';
	$scope.addOrManageTickets = 'Add or Manage Ticket';
	$scope.createTicket = 'Create Ticket';
	$scope.selectSeverityText = 'Select Severity';
	$scope.describeIssuePlaceHolder = 'Ticket Detail...';
	$scope.ticketNamePlaceHolder = 'Ticket Description...';
	$scope.ticketButtonText = 'Add Ticket';

	$scope.systemBusy = 'System is Busy Please try again';

	$scope.ticketId = 'Id';
	$scope.ticketName = 'Ticket Description';
	$scope.severity = 'Severity';
	$scope.status = 'Status';
	$scope.createdBy = 'Created By';
	$scope.createdDate = 'Created Date';

	$scope.tickets = [];
	$scope.ticket = {};

	$cookieStore.put('redirectedThroughManageEmployee', false);

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$rootScope.supportProcessing = false;
			$cookieStore.put('supportProcessing',$rootScope.supportProcessing);
			$scope.data = SupportControllerPreLoad.loadControllerData();
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
				if($scope.data.ticketsList!=null){
					$scope.tickets = $scope.data.ticketsList;
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
				if($scope.data.severityList!=null){
					$scope.severities = $scope.data.severityList;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.addTicket = function(ticket){

		if(ticket.severityAssociationId=="-1"){
			$scope.alertMessage = 'Please select severity.';
			$('#alertBox').modal('show');
			return;
		}
		$scope.showProcessing = true;

		$http.post('support/addTicket/'+$scope._s_tk_com,ticket).success(function(Response) {
			$scope.showProcessing = false;
			$scope.ticket  = {};
			$scope.ticket.severityAssociationId = '-1';
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$route.reload();
				//$window.location = '/app/#/support';


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
			$scope.showProcessing = false;
			$scope.scheduleError = true;
			$scope.scheduleErrorMessage = $scope.systemBusy;
		});
	};

	$scope.getTicketActivities = function(ticketBean){

		$cookieStore.put('_t_tob_gra',ticketBean.ticketId);
		$window.location = '/app/#/ticketActivity';

	};

	$scope.sessionValidation();
}];