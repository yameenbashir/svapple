'use strict';

/**
 * CustomersController
 * @constructor
 */
var CustomersController = ['$scope', '$http','$sce','$timeout', '$window','$cookieStore','$rootScope','SessionService','CustomersControllerPreLoad',function($scope, $http,$sce,$timeout, $window,$cookieStore,$rootScope,SessionService,CustomersControllerPreLoad){

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = CustomersControllerPreLoad.loadControllerData();
			$scope.fetchData();
			if($cookieStore.get('_t_f_lly')=="true"){
				$scope.filterBean = {};
				$scope.filterBean.search = $cookieStore.get('_e_ctt_llz');
				$cookieStore.put('_t_f_lly',"false");
				$cookieStore.put('_e_ctt_llz',"") ;
			}
		}
	};

	$scope.fetchData = function() {
		$rootScope.customersLoadedFully = false;
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
				$scope.customersList = $scope.data;
				$scope.comparisonCustomersList = $scope.data;
				$scope.lodAllCustomersAsynchrnously();
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
	
	$scope.activeInactiveAll = function(isActive) {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.loading = true;
		$http.get('customers/activeInactiveAllCustomers/'+$scope._s_tk_com+'/'+isActive)
		.success(function(Response) {
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				customer = Response.data;
				localforage.setItem('_e_cOt_jir',customer);
				$window.location = "/app/#/manageCustomer";
				
			}else  {
			
				$window.location = Response.layOutPath;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
		
	};
	

	$scope.addCustomer = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$window.location = "/app/#/newCustomer";
	};
	$scope.editCustomer = function(customer) {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		
		$http.get('customers/updateSelectedCustomer/'+$scope._s_tk_com+'/'+customer.customerId)
		.success(function(Response) {
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				for (var i = 0; i < $scope.customersList.length; i++) {
					if($scope.customersList[i].customerId==customer.customerId){
						if($scope.customersList[i].activeIndicator=='true'){
							$scope.customersList[i].activeIndicator='false';
						}else{
							$scope.customersList[i].activeIndicator='true';
						}
						break;
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
								bInfo : true,
								dom : 'Bfrtip',
								pageLength: 5,
								
								buttons :$rootScope.buttonsView
							} );
						}, 10);
			}else  {
			
				$window.location = Response.layOutPath;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
		
	};
	$scope.showCustomer = function(customer) {
		
		
		$http.get('customers/getSelectedCustomer/'+$scope._s_tk_com+'/'+customer.customerId)
		.success(function(Response) {
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				customer = Response.data;
				$cookieStore.put('_cD_cDt_gra',customer.customerId) ;
				localforage.setItem('_e_cOt_jir',customer);
				$window.location = "/app/#/customerDetails";
				
			}else  {
			
				$window.location = Response.layOutPath;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
		
	};

	$scope.updateCustomer = function(customer) {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$http.get('customers/getSelectedCustomer/'+$scope._s_tk_com+'/'+customer.customerId)
		.success(function(Response) {
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				customer = Response.data;
				localforage.setItem('_e_cOt_jir',customer);
				$window.location = "/app/#/manageCustomer";
				
			}else  {
			
				$window.location = Response.layOutPath;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
		
	};

	$scope.lodAllCustomersAsynchrnously = function(){
		$scope.dataLoading = false;
		$http.post('customers/getAllCustomers/'+$cookieStore.get('_s_tk_com')+'/'+"true").success(function(Response) {
			$scope.data = Response.data;
			
			$scope.dataLoading = true;
		}).error(function() {
			$scope.dataLoading = true;
			$window.location = '/app/#/login';
		});
		$scope.lodAllProductsGraph();
		
	};
	
	$scope.lodAllCustomers = function(){
		$scope.dataLoading = false;
		
		for(var i=0;i<$scope.data.length;i++){
			if(!checkCustomerExist($scope.data[i])){
				$scope.customersList.push($scope.data[i]);
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
						bInfo : true,
						dom : 'Bfrtip',
						pageLength: 5,
						buttons :$rootScope.buttonsView
					} );
					
				}, 1);
		$scope.dataLoading = true;
	};

	function checkCustomerExist(customer) {
		if($scope.comparisonCustomersList!='undefined' && $scope.comparisonCustomersList!=null && $scope.comparisonCustomersList.length>0){
			for(var i=0;i<$scope.comparisonCustomersList.length;i++){
				if($scope.comparisonCustomersList[i].customerId==customer.customerId){
					return true;
				}
			}
		}
		return false;
	}
	
	$scope.lodAllProductsGraph = function(){
		$scope.dataLoading = false;
		$http.post('customers/getCustomerDashBoard/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
			$scope.data = Response.data;
			 $scope.chartCustomerCount = new CanvasJS.Chart("chartContainerProductCount", {
			        theme: 'theme1',
			        height: 150,
			       
			        axisY: {
			            labelFontSize: 10,
			        },
			        axisX: {
			            labelFontSize: 10,
			            labelAngle: -45
			        },
			        data: [              
			            {
			                type: "area",
			                color: "rgba(40,175,101,0.6)",
			                dataPoints: $scope.data.dataPointsCustomerCount
			            }
			        ]
			    });
			$scope.chartCustomerCount.render(); //render the chart for the first time
			   
			$scope.dataLoadingGraph = true;
		}).error(function() {
			//$window.location = '/app/#/login';
		});
	};
	       
	
	$scope.autoCompleteOptionsCustomers = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				var customerResults = _.filter($scope.data, function(val) {
					if(val.contactName){
						return val.contactName.toLowerCase().includes(term)||val.phoneNumber.toLowerCase().includes(term)||val.customerBalance.toLowerCase().includes(term);
					}


				});
				return customerResults;
			},
			renderItem : function(item) {
			
			var result = {
					value : item.contactName,
					label : $sce.trustAsHtml("<table class='auto-complete'>"
							+ "<tbody>" + "<tr>" + "<td style='width: 30%'>"
							+ item.contactName + "</td>"
							+ "<td style='width: 10%'>" + item.phoneNumber + "</td>"
						+ "<td style='width: 10%'>" + item.customerBalance + "</td>"
							
							+ "</tr>" + "</tbody>" + "</table>")
			};
			return result;
			},
			
			
			
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.CustomerName = $scope.selectedItem;
				
				$scope.showCustomer($scope.selectedItem.item);
				
			}
	};

	$scope.sessionValidation();
}];