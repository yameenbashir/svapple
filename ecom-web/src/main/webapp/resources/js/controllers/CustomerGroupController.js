'use strict';

/**
 * CustomerGroupController
 * @constructor
 */
var CustomerGroupController = ['$scope', '$http', '$timeout', '$route', '$window','$cookieStore','$rootScope','SessionService','CustomerGroupControllerPreLoad',function($scope, $http, $timeout, $route, $window,$cookieStore,$rootScope,SessionService,CustomerGroupControllerPreLoad) {
		
		$rootScope.MainSideBarhideit = false;
		$rootScope.MainHeaderideit = false;
		$scope.showAddProducTypeModal = false;
		$scope.showUpdateCustomerGroupModal = false;
		$scope.customerGroupBean = {};
		
		
		
		$scope.sessionValidation = function(){

			if(SessionService.validate()){
				$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
				$scope.data = CustomerGroupControllerPreLoad.loadControllerData();
				$scope.fetchData();
			}
		};

		$scope.fetchData = function() {
			$rootScope.customerGroupLoadedFully = false;
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
				if($scope.data.customerGroupBeanList!=null){
					$scope.customerGroupList = $scope.data.customerGroupBeanList;
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
				if($scope.data.countryListBeans!=null){
					$scope.countryList = $scope.data.countryListBeans;
				}
			}
			$rootScope.globalPageLoader = false;
		};
		
		$scope.showCustomers = function(customerGroup) {
			$cookieStore.put('_e_ctt_llz',customerGroup.customerGroupName) ;
			$cookieStore.put('_t_f_lly',"true");
			$window.location = "/app/#/customers";
		};
		
		$scope.showAddCustomerGroupPopup = function(){
			$scope.customerGroupBean = {};
			$scope.customerGroupBean.countryId='1';
			$scope.showAddCustomerGroupModal = true; 
		};
		
		$scope.addCustomerGroup = function() {
			$rootScope.impersonate = $cookieStore.get("impersonate");
			if($rootScope.impersonate){
				$rootScope.permissionDenied();
				return;
			}
			$scope.customerGroupSuccess = false;
			$scope.customerGroupError = false;
			$scope.loading = true;
			$http.post('customerGroup/addCustomerGroup/'+$scope._s_tk_com, $scope.customerGroupBean)
			.success(function(Response) {
				$scope.loading = false;
				
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.customerGroupBean = {};
					$scope.customerGroupSuccess = true;
					$scope.customerGroupSuccessMessage = Response.data;
					$scope.showAddCustomerGroupModal = false;
					$timeout(function(){
						$scope.customerGroupSuccess = false;
						$timeout(function(){
						      $route.reload();
						    }, 500);
					    }, 1500);
					
				}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
					$scope.customerGroupError = true;
					$scope.customerGroupErrorMessage = Response.data;
					$window.location = Response.layOutPath;
				}else {
					$scope.customerGroupError = true;
					$scope.customerGroupErrorMessage = Response.data;
				}
			}).error(function() {
				$scope.loading = false;
				$scope.customerGroupError = true;
				$scope.customerGroupErrorMessage = $scope.systemBusy;
			});
		};
		
		$scope.showUpdateCustomerGroupPopup = function(customerGroup){
			$scope.updateCustomerGroupBean = {};
			$scope.updateCustomerGroupBean = angular.copy(customerGroup); 
			$scope.showUpdateCustomerGroupModal = true;
		};
		
		$scope.updateCustomerGroup = function() {
			$rootScope.impersonate = $cookieStore.get("impersonate");
			if($rootScope.impersonate){
				$rootScope.permissionDenied();
				return;
			}
			$scope.customerGroupSuccess = false;
			$scope.customerGroupError = false;
			$scope.loading = true;
			$http.post('customerGroup/updateCustomerGroup/'+$scope._s_tk_com, $scope.updateCustomerGroupBean)
			.success(function(Response) {
				$scope.loading = false;
				
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.updateCustomerGroupBean = {};
					$scope.customerGroupSuccess = true;
					$scope.customerGroupSuccessMessage = Response.data;
					$scope.showUpdateCustomerGroupModal = false;
					$timeout(function(){
						$scope.customerGroupSuccess = false;
						$timeout(function(){
						      $route.reload();
						    }, 500);
					    }, 2000);
					
				}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
					$scope.customerGroupError = true;
					$scope.customerGroupErrorMessage = Response.data;
					$window.location = Response.layOutPath;
				}else {
					$scope.customerGroupError = true;
					$scope.customerGroupErrorMessage = Response.data;
				}
			}).error(function() {
				$scope.loading = false;
				$scope.customerGroupError = true;
				$scope.customerGroupErrorMessage = $scope.systemBusy;
			});
		};

/*		
		$scope.fetchAllCustomerGroups = function() {
			$http.post('customerGroup/getAllCustomerGroups/'+$scope._s_tk_com).success(function(Response) {
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus== 'SUCCESSFUL') {
						$scope.customerGroupList = Response.data;
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
	
	$scope.fetchAllCountry = function() {
		$http.post('outlet/getAllCountry').success(function(Response) {
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus== 'SUCCESSFUL') {
					$scope.countryList = Response.data;
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
*/		$scope.sessionValidation(); 
}];