'use strict';

/**
 * SendSmsController
 * 
 * @constructor
 */
var SendSmsController = ['$scope', '$http', '$window', '$cookieStore', '$rootScope','SessionService', '$timeout', '$sce', 'SendSmsControllerPreLoad','$route',function($scope, $http, $window, $cookieStore, $rootScope,SessionService, $timeout, $sce, SendSmsControllerPreLoad,$route) {
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.successMessage = "Message Sent Successfuly!";
	$scope.errorMessage = "Message Sent Error Occurred!";
	$scope.success = false;
	$scope.error = false;
	$scope.message = "";
	$scope.sendAllSms=false;
	
	$scope.sesssionValidation = function() {

		if (true) {
			$scope._s_tk_com = $cookieStore.get('_s_tk_com');

			$scope.controllerBean = SendSmsControllerPreLoad.loadControllerData();
			$rootScope.globalPageLoader = false;
			$scope.smsUsedCount = $scope.controllerBean.smsUsedCount;
			$scope.smsRemained = $scope.controllerBean.smsRemained;
			$scope.isExpired = $scope.controllerBean.isExpired;
			
			
		}



	};


	$scope.selectedCustomers = []; 
	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
			term = term.toLowerCase();
			var customerResults = _.filter($scope.controllerBean.customersBean, function(val) {

					if(val.firstName){
						return val.firstName.toLowerCase().includes(term) || val.phoneNumber.toLowerCase().includes(term);
					}else{
						if(val.phoneNumber)
						{
							return val.phoneNumber.toLowerCase().includes(term);
						}
					}

				});
				return customerResults;
			},
			renderItem : function(item) {
				var result = {
						value : item.firstName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
								+ item.firstName +' '+ item.lastName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
								+ "<td style='width: 10%'>" + 'Customer' + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};
				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.selectedCustomer = {};
				$scope.selectedCustomer.customerId= $scope.selectedItem.item.customerId;
				$scope.selectedCustomer.firstName= $scope.selectedItem.item.firstName;
				$scope.selectedCustomer.lastName= $scope.selectedItem.item.lastName;
				$scope.selectedCustomer.companyId= $scope.selectedItem.item.companyId;
				$scope.selectedCustomer.phoneNumber= $scope.selectedItem.item.phoneNumber;
				if($scope.selectedCustomers.length==0){
					$scope.selectedCustomers.push($scope.selectedCustomer);
				}else{
					for(var i=0;i<$scope.selectedCustomers.length;i++){
						if($scope.selectedCustomers[i].customerId!=$scope.selectedCustomer.customerId){
							$scope.selectedCustomers.push($scope.selectedCustomer);
								
						}
					}
				}
				
				$scope.airportName = [];
			}
	};
	$scope.loading = false;
	
	$scope.removeCustomer = function(value) {
		for(var i=0;i<$scope.selectedCustomers.length;i++){
			if($scope.selectedCustomers[i].customerId==value){
				$scope.selectedCustomers.splice(i,1);
				
			}
		}
	}

	$scope.sendMessage = function() {
		$scope.loading = true;
		$scope.sendSMSBean = {}; 
		$scope.sendSMSBean.customerSmsBeans = $scope.selectedCustomers;
		$scope.sendSMSBean.message = $scope.message;
		$scope.sendSMSBean.sendAllSms = $scope.sendAllSms;
		$scope.sendSMSBean.user = $rootScope.clientName;
		if($scope.isExpired=='true'){
			$scope.error = true;
			$scope.loading = false;
			$scope.message= "";
			$scope.selectedCustomers = [];
			$timeout(function() {
				$scope.error = false;
			}, 2500);
			$scope.errorMessage = "Message Package Time Expired!";
		}
		else if($scope.sendAllSms){
			if($scope.controllerBean.customersBean.length > $scope.smsRemained){
				$scope.error = true;
				$scope.loading = false;
				$scope.message= "";
				$scope.selectedCustomers = [];
				$timeout(function() {
					$scope.error = false;
				}, 2500);
				$scope.errorMessage = "Message Package Limit Expired!";
			}else{
				$timeout(function() {
					$scope.loading = false;
					$scope.sendAllSms=false;
					$scope.message= "";
					$scope.success = true;
					$scope.selectedCustomers = []; 
					$scope.smsUsedCount =  $scope.controllerBean.customersBean.length + parseInt($scope.smsUsedCount);
					$scope.smsRemained = $scope.smsRemained - $scope.controllerBean.customersBean.length;
				}, 2500);
				$timeout(function() {
					$scope.success = false;
				}, 4500);
			}
			
		}
		else if($scope.sendSMSBean.customerSmsBeans.length > $scope.smsRemained){
			$scope.error = true;
			$scope.loading = false;
			$scope.message= "";
			$scope.selectedCustomers = [];
			$timeout(function() {
				$scope.error = false;
			}, 2500);
			$scope.errorMessage = "Message Package Limit Expired!";
		}else{
			$timeout(function() {
				$scope.loading = false;
				$scope.sendAllSms=false;
				$scope.message= "";
				$scope.success = true;
				$scope.selectedCustomers = []; 
				$scope.smsUsedCount =  $scope.sendSMSBean.customerSmsBeans.length + parseInt($scope.smsUsedCount);
				$scope.smsRemained = $scope.smsRemained - $scope.sendSMSBean.customerSmsBeans.length;
			}, 2500);
			$timeout(function() {
				$scope.success = false;
			}, 4500);
		}
		
	
		$http.post('sendSms/sendMessage/' + $scope._s_tk_com,$scope.sendSMSBean).success(
				function(Response) {
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
//						$scope.loading = false;
//						$scope.sendAllSms=false;
//						$scope.message= "";
//						$scope.success = true;
//						$scope.selectedCustomers = []; 
//						$timeout(function() {
//							$scope.success = false;
//						}, 2500);
					} else {
//						$scope.error = true;
//						$scope.loading = false;
//						$scope.message= "";
//						$scope.selectedCustomers = [];
//						$timeout(function() {
//							$scope.error = false;
//						}, 2500);
//						$scope.errorMessage = Response.data;
					}
				}).error(function() {
					$scope.error = true;
					$scope.loading = false;
					$scope.newCustomerErrorMessage = $scope.systemBusy;
				});
	};
	$scope.sesssionValidation();


	



}];
