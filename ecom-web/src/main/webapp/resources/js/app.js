'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.bootstrap','ngRoute','uiSlider','webcam','timer','ngCookies','googlechart',
                                              'AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'
                                              ,'ui.calendar','ui.multiselect','as.sortable','nvd3','ngMap','ngSanitize','ngFileUpload', 
                                              'ngImgCrop','multipleDatePicker','ngPrint','LocalForageModule','autoCompleteModule','angular-barcode']);


App.run(['$rootScope', '$templateCache','$cookieStore','$window','$http','$timeout','$route',function($rootScope, $templateCache,$cookieStore,$window,$http,$timeout,$route ) {
	//Kites,Xpressions,STYLEANDSTYLE
	$rootScope.clientName = "Xpressions";
	$rootScope.buttonsView = {
			buttons : [
						{
							extend : 'print',
							text : '<i class="fa fa-print"></i> Print',
							title : $('h1').text(),
							key : {
								key : 'p',
								altkey : true
							},
							exportOptions : {
								columns : ':not(.no-print)'
							},
							footer : true,
							autoPrint : false
						},
						{
							extend : 'excel',
							text : '<i class="fa fa-book"></i> Excel',
							title : $('h1').text(),
							key : {
								key : 'p',
								altkey : true
							},
							exportOptions : {
								columns : ':not(.no-print)'
							},
							footer : true,
							autoPrint : false
						},
						{
							extend : 'pdf',
							text : '<i class="fa fa-file-pdf-o"></i> PDF',
							title : $('h1').text(),
							exportOptions : {
								columns : ':not(.no-print)'
							},
							footer : true
						} ],
				dom : {
					container : {
						className : 'dt-buttons'
					},
					button : {
						className : 'btn btn-default'
					}
				}
			};
	var my_skins = [
	                "skin-blue",
	                "skin-black",
	                "skin-red",
	                "skin-yellow",
	                "skin-purple",
	                "skin-green",
	                "skin-blue-light",
	                "skin-black-light",
	                "skin-red-light",
	                "skin-yellow-light",
	                "skin-purple-light",
	                "skin-green-light"
	                ];
	function store(name, val) {
		if (typeof (Storage) !== "undefined") {
			localStorage.setItem(name, val);
		} else {
			window.alert('Please use a modern browser to properly view this template!');
		}
	}
	$rootScope.changeSkinChange = function(skin) {
		$.each(my_skins, function (i) {
			$("body").removeClass(my_skins[i]);
		});

		$("body").addClass(skin);
		store('skin', skin);
	}	
	$rootScope.changeSkinChange('skin-purple-light');
	$rootScope.MainSideBarhideit = true;
	$rootScope.MainHeaderideit = true;
	$rootScope.globalPageLoader = false;
	$rootScope.menuMap = $cookieStore.get('menuMap');
	$rootScope.userName = $cookieStore.get('userName');
	$rootScope.companyId = $cookieStore.get("companyId");
	$rootScope.companyName = $cookieStore.get("companyName");
	$rootScope.userEmail = $cookieStore.get('email');
	$rootScope.userCreatedDate = $cookieStore.get('userCreatedDate');
	$rootScope.IsManager = $cookieStore.get('IsManager');
	$rootScope.IsSuperUser = $cookieStore.get('IsSuperUser');
	$rootScope.toDoList = $cookieStore.get('toDoList');
	$rootScope.notificationList = $cookieStore.get('NotificationList');
	$rootScope.countNotifications = $cookieStore.get('countNotifications');
	$rootScope.sessionId = $cookieStore.get('sessionId');
	$rootScope.locationProcessing = $cookieStore.get('locationProcessing');
	$rootScope.shiftProcessing = $cookieStore.get('shiftProcessing');
	$rootScope.employeeProcessing = $cookieStore.get('employeeProcessing');
	$rootScope.payRollProcessing = $cookieStore.get('payRollProcessing');
	$rootScope.reportProcessing = $cookieStore.get('reportProcessing');
	$rootScope.dashBoardProcessing = $cookieStore.get('dashBoardProcessing');
	$rootScope.timeLoggingProcessing = $cookieStore.get('timeLoggingProcessing');
	$rootScope.workTraceProcessing = $cookieStore.get('workTraceProcessing');
	$rootScope.supportProcessing = $cookieStore.get('supportProcessing');
	$rootScope.organizationProcessing = $cookieStore.get('organizationProcessing');
	$rootScope.manageCompanyProcessing = $cookieStore.get('manageCompanyProcessing');
	$rootScope.personalInfoProcessing = $cookieStore.get('personalInfoProcessing');
	$rootScope.userRole =  $cookieStore.get('_s_tk_rId');
	$rootScope.inventoryReportType = "Inventory on Hand";
	$rootScope.salesReportType = "Revenue";
	$rootScope.expenseReportType = "IN";
	$rootScope.salesReportDateType = "Daily";
	$rootScope.inventoryReportOutletName = "All Outlets";
	localStorage.removeItem('startSalesDate');
	localStorage.removeItem('endSalesDate');
	localStorage.removeItem('startUserSalesDate');
	localStorage.removeItem('endUserSalesDate');
	localStorage.removeItem('startOutletSalesDate');
	localStorage.removeItem('endOutletSalesDate');
	localStorage.removeItem('startBrandSalesDate');
	localStorage.removeItem('endBrandSalesDate');
	localStorage.removeItem('startProductTypeSalesDate');
	localStorage.removeItem('endProductTypeSalesDate');
	localStorage.removeItem('startSupplierSalesDate');
	localStorage.removeItem('endSupplierSalesDate');
	localStorage.removeItem('startCustomerSalesDate');
	localStorage.removeItem('endCustomerSalesDate');
	localStorage.removeItem('startCustomerGroupSalesDate');
	localStorage.removeItem('endCustomerGroupSalesDate');
	localStorage.removeItem('startPaymentSalesDate');
	localStorage.removeItem('endPaymentSalesDate');
	if(!$rootScope.userEmail){
		$rootScope.userImage = "/app/resources/dist/img/avatar5.png";
	}else{
		//$rootScope.userImage = "/app/resources/images/selfie_"+$rootScope.userEmail+".jpg";
		$rootScope.userImage = "/app/resources/dist/img/avatar5.png";
	}
	$rootScope.systemBusy = 'System is busy while handling request. Please try later.';
	$rootScope.complianceTextForReporting = "Report have compliance with Today's 9:00 AM Data." ;
	$rootScope.complianceTextForInventoryReport = "Report Data have compliance with every 59th Minute of clock." ;
	                                              
	$rootScope.online = true;
	$rootScope.heartBeat = function() {
		$http.get('session/heartBeat').success(function(Response) {

			$rootScope.online = true;
//			var url = $rootScope.userImage;
//			var request = new XMLHttpRequest();
//			request.open('HEAD', url, false);
//			request.send();   
		}).error(function() {
			$rootScope.userImage = "/app/resources/dist/img/avatar5.png";
			$rootScope.online = false;
			//	$window.location = '/app/#/sell';
		});
	}

	$rootScope.heartBeat();
	if(!$rootScope.limit)
	{$rootScope.limit = 10;}
	$rootScope.synchsales = function() {
		$rootScope.successSynchsales = false;
		$rootScope.errorSynchsales = false;
		

		localforage.getItem('invoiceMainBeanNewList').then(function(value) {
			// This code runs once the value has been loaded
			// from the offline store.

			if(value!=null && $cookieStore.get('_s_tk_com')!=null && typeof $cookieStore.get('_s_tk_com') != 'undefined'){
				$rootScope.invoiceMainBeanNewRootList = value;
				$rootScope.loadingSynchsales = true;
				$http.post('sell/synchsales/' + $cookieStore.get('_s_tk_com'),$rootScope.invoiceMainBeanNewRootList).success(function(Response) {

					if (Response.status == 'SUCCESSFUL') {

						if($rootScope.invoiceMainBeanNewRootList != null)
						{

							/*$rootScope.invoiceMainBeanNewRootList = _.filter($rootScope.invoiceMainBeanNewRootList, function(val) {
								return val.synchedInd == 'true'

							});*/

							//updating the sale data in index DB
							localforage.setItem('invoiceMainBeanNewList', null);
							localforage.setItem('InvoiceMainBeanList', null);
						}

						$rootScope.successSynchsales = true;
						$rootScope.successMessageSynchsales = 'Request Processed successfully.';
						$rootScope.loadingSynchsales = false;
						$timeout(function() {
							$rootScope.successSynchsales = false;
							$window.location = Response.layOutPath;
						}, 1500);

					} else if (Response.status == 'INVALIDSESSION'
						|| Response.status == 'SYSTEMBUSY') {
						$rootScope.errorSynchsales = true;
						$rootScope.errorMessageSynchsales = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$rootScope.errorSynchsales = true;
						$rootScope.errorMessageSynchsales = "Remove sale no " + Response.data[0].invoiceRefNbr + " to proceed retry errored sales ";
						if( Response.data != null){
							localforage.setItem('invoiceMainBeanNewList', null);
							localforage.setItem('InvoiceMainBeanList', null);
						}
						
						localforage.setItem('invoiceMainBeanNewList',  Response.data);
						localforage.setItem('InvoiceMainBeanList', Response.data);
						$timeout(function() {
							$rootScope.loadingSynchsales = false;
							//$window.location = Response.layOutPath;
							$route.reload();
						}, 1500);
					}
				}).error(function() {

					$rootScope.errorSynchsales = true;
					$rootScope.errorMessageSynchsales = Response.data;
					$timeout(function() {
						$rootScope.errorSynchsales = false;
						$window.location = Response.layOutPath;
					}, 1500);

					$rootScope.loadingSynchsales
					$rootScope.errorSynchsales = true;
					$rootScope.errorMessageSynchsales = "System is busy, please try later!";
				});

			}



		});
	};

	$rootScope.doLogout = function() {
		$cookieStore.put('_s_tk_com','');
		$window.location = '/app/#/login';
	};



	$rootScope.$on('$viewContentLoaded', function() {

		$templateCache.removeAll();
	});'angularMultiSelect'
}]);

App.factory('LocationService', function() {

	return {
		locationX : '51.508515',
		locationY : '-0.125487',
		address:'',
		city:'',
		country:'',
		title:'',
		postalAddress:'',
		description:''

	};
});
App.config(['$localForageProvider', function($localForageProvider){
	$localForageProvider.config({
		driver      : 'localStorageWrapper', // if you want to force a driver
		name        : 'myApp', // name of the database and prefix for your data, it is "lf" by default
		version     : 1.0, // version of the database, you shouldn't have to use this
		storeName   : 'keyvaluepairs', // name of the table
		description : 'some description'
	});
}]);
//Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ( $routeProvider,$scope,$http) {

	$routeProvider.when('/home', {
		templateUrl: 'resources/html/home/layout.html',
		controller:HomeController,
		resolve: {
			"HomeControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				var homeControllerbean = {};
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["home"]==true){
					$rootScope.outletLoadedFully = true;
					controllerData = $http.post('home/getDashBoard/'+$cookieStore.get('_s_tk_com'),homeControllerbean).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/login', {
		templateUrl: 'resources/html/login/layout.html',
		controller: LoginController
	});
	$routeProvider.when('/supplierDetails', {
		templateUrl: 'resources/html/supplierDetails/layout.html',
		controller: SupplierDetailsController,
		resolve: {
			"SupplierDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["supplierDetails"]==true){
					controllerData = $http.post('supplierDetails/getSupplierDetailsControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('sp_cto')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    					
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/outletPaymentDetails', {
		templateUrl: 'resources/html/outletPaymentDetails/layout.html',
		controller: OutletPaymentDetailsController,
		resolve: {
			"OutletPaymentDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["outletPaymentDetails"]==true){
					controllerData = $http.post('supplierDetails/getOutletDetailsControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('sp_oto')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    					
				}
				return myDefer.promise;
			}
		}
	});
	
	
	$routeProvider.when('/companySetup', {
		templateUrl: 'resources/html/companySetup/layout.html',
		controller: CompanySetupController,

	});

	$routeProvider.when('/outlets', {
		templateUrl: 'resources/html/outlets/layout.html',
		controller: OutletsController,
		resolve: {
			"OutletsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["outlets"]==true){
					$rootScope.outletsLoadedFully = true;
					controllerData = $http.post('outlets/getOutlets/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/outlet', {
		templateUrl: 'resources/html/outlet/layout.html',
		controller: OutletController,
		resolve: {
			"OutletControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["outlet"]==true){
					$rootScope.outletLoadedFully = true;
					controllerData = $http.post('outlet/getOutletControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    				
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/register', {
		templateUrl: 'resources/html/register/layout.html',
		controller: RegisterController,
		resolve: {
			"RegisterControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["register"]==true){
					$rootScope.registerLoadedFully = true;
					localforage.getItem('_r_cO_gra').then(function(value) {
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
									controllerData = value
									return controllerData;  
							}
						});
					},10);
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    				
				}
				return myDefer.promise;
			}
		}
	});
	$routeProvider.when('/store', {
		templateUrl: 'resources/html/store/layout.html',
		controller: StoreController,
		resolve: {
			"StoreControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["store"]==true){
					$rootScope.storeLoadedFully = true;
					controllerData = $http.post('store/getStoreControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	

				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/paymentType', {
		templateUrl: 'resources/html/paymentType/layout.html',
		controller: PaymentTypeController,
		resolve: {
			"PaymentTypeControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["paymentType"]==true){
					$rootScope.paymentTypeLoadedFully = true;
					controllerData = $http.post('paymentType/getAllPaymentTypes/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    				
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/salesTax', {
		templateUrl: 'resources/html/salesTax/layout.html',
		controller: SalesTaxController,
		resolve: {
			"SalesTaxControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesTax"]==true){
					$rootScope.salesTaxLoadedFully = true;
					controllerData = $http.post('salesTax/geSalesTaxControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    				
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/loyalty', {
		templateUrl: 'resources/html/loyalty/layout.html',
		controller: LoyaltyController,
		resolve: {
			"LoyaltyControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["loyalty"]==true){
					$rootScope.loyaltyLoadedFully = true;
					controllerData = $http.post('loyalty/getCompanyLoyalty/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/users', {
		templateUrl: 'resources/html/users/layout.html',
		controller: UsersController,
		resolve: {
			"UsersControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["users"]==true){
					$rootScope.usersLoadedFully = true;
					controllerData = $http.post('users/getAllUsers/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/roles', {
		templateUrl: 'resources/html/roles/layout.html',
		controller: RolesController,
		resolve: {
			"RolesControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["roles"]==true){
					$rootScope.rolesLoadedFully = true;
					controllerData = $http.post('roles/getAllRoles/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	  					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/printLabels', {
		templateUrl: 'resources/html/printLabels/layout.html',
		controller: PrintLabelsController,
		resolve: {
			"PrintLabelsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["printLabels"]==true){
					$rootScope.roleDetailsLoadedFully = true;
					controllerData = $http.get('sell/getAllProductsPrintLableOnly/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/roleDetails', {
		templateUrl: 'resources/html/roleDetails/layout.html',
		controller: RoleDetailsController,
		resolve: {
			"RoleDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["roleDetails"]==true){
					$rootScope.roleDetailsLoadedFully = true;
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/storeCredit', {
		templateUrl: 'resources/html/storeCredit/layout.html',
		controller: StoreCreditController,
		resolve: {
			"StoreCreditControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["storeCredit"]==true){
					$rootScope.storeCreditLoadedFully = true;
					controllerData = $http.post('storeCredit/getCompanyDetailsByCompanyID/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/products', {
		templateUrl: 'resources/html/products/layout.html',
		controller: ProductsController,

		resolve: {
			"ProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["products"]==true){
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);
//					controllerData = $http.post('products/getAllProducts/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
//						controllerData = Response.data;
//						$timeout(function(){
//							myDefer.resolve({
//								loadControllerData: function() {
//									return 	controllerData;  
//								}
//							});
//						},10);
//					}).error(function() {
//						$window.location = '/app/#/login';
//					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/downloadProducts', {
		templateUrl: 'resources/html/downloadProducts/layout.html',
		controller: DownloadProductsController,

		resolve: {
			"DownloadProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["products"]==true){
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);
//					controllerData = $http.post('products/getAllProducts/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
//						controllerData = Response.data;
//						$timeout(function(){
//							myDefer.resolve({
//								loadControllerData: function() {
//									return 	controllerData;  
//								}
//							});
//						},10);
//					}).error(function() {
//						$window.location = '/app/#/login';
//					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/manageProduct', {
		templateUrl: 'resources/html/manageProduct/layout.html',
		controller: ManageProductController,

		resolve: {
			"ManageProductControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageProduct"]==true){
					$rootScope.manageProductLoadedFully = true;

					controllerData = $http.post('newProduct/getNewProductControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cPi_gra')+'/'+$cookieStore.get('_e_cOi_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/manageCompositeProduct', {
		templateUrl: 'resources/html/manageCompositeProduct/layout.html',
		controller: ManageCompositeProductController,

		resolve: {
			"ManageCompositeProductControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageCompositeProduct"]==true){
					$rootScope.manageProductLoadedFully = true;

					controllerData = $http.post('newCompositeProduct/getNewCompositeProductControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cPi_gra')+'/'+$cookieStore.get('_e_cOi_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	}); 


	$routeProvider.when('/stockControl', {
		templateUrl: 'resources/html/stockControl/layout.html',
		controller: StockControlController,
		resolve: {
			"StockControlControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockControl"]==true){
					$rootScope.stockControlLoadedFully = true;
					controllerData = $http.post('stockControl/getAllStockOrders/'+$cookieStore.get('_s_tk_com')+'/'+"false").success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/purchaseOrder', {
		templateUrl: 'resources/html/purchaseOrder/layout.html',
		controller: PurchaseOrderController,
		resolve: {
			"PurchaseOrderControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrder"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/stockReturn', {
		templateUrl: 'resources/html/stockReturn/layout.html',
		controller: StockReturnController,
		resolve: {
			"StockReturnControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturn"]==true){
					$rootScope.stockReturnLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	  					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/stockSupplierTransfer', {
		templateUrl: 'resources/html/stockSupplierTransfer/layout.html',
		controller: StockSupplierTransferController,
		resolve: {
			"StockSupplierTransferControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockSupplierTransfer"]==true){
					$rootScope.stockReturnLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	  					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/stockTransfer', {
		templateUrl: 'resources/html/stockTransfer/layout.html',
		controller: StockTransferController,
		resolve: {
			"StockTransferControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockTransfer"]==true){
					$rootScope.stockTransferLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/priceBook', {
		templateUrl: 'resources/html/priceBook/layout.html',
		controller: PriceBookController,
		resolve: {
			"PriceBookControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["priceBook"]==true){
					controllerData = $http.post('priceBook/getAllPriceBooks/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/inActivePriceBook', {
		templateUrl: 'resources/html/inActivePriceBook/layout.html',
		controller: InActivePriceBookController,
		resolve: {
			"InActivePriceBookControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["priceBook"]==true){
					controllerData = $http.post('priceBook/getAllInActivePriceBooks/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/newPriceBook', {
		templateUrl: 'resources/html/newPriceBook/layout.html',
		controller: NewPriceBookController,
		resolve: {
			"NewPriceBookControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newPriceBook"]==true){
					controllerData = $http.post('newPriceBook/getNewPriceBookControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/managePriceBook', {
		templateUrl: 'resources/html/managePriceBook/layout.html',
		controller: ManagePriceBookController,
		resolve: {
			"ManagePriceBookControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["managePriceBook"]==true){
					controllerData = $http.post('managePriceBook/getManagePriceBookControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cPri_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	}); 


	$routeProvider.when('/manageOutlet', {
		templateUrl: 'resources/html/manageOutlet/layout.html',
		controller: ManageOutletController,
		resolve: {
			"ManageOutletControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageOutlet"]==true){
					$rootScope.manageOutletLoadedFully = true;
					controllerData = $http.post('manageOutlet/getManageOutletControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cOt_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/manageCustomer', {
		templateUrl: 'resources/html/manageCustomer/layout.html',
		controller: ManageCustomerController,
		resolve: {
			"ManageCustomerControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageCustomer"]==true){
					$rootScope.manageCustomerLoadedFully = true;
					controllerData = $http.post('newCustomer/getAllCustomerGroups/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						localforage.getItem('_e_cOt_jir').then(function(value) {
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									 	controllerData.value = value;
									 	return controllerData;
								}
							});
						},10);
						});
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/manageRegister', {
		templateUrl: 'resources/html/manageRegister/layout.html',
		controller: ManageRegisterController,
		resolve: {
			"ManageRegisterControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageRegister"]==true){
					$rootScope.manageRegisterLoadedFully = true;
					controllerData = $http.post('manageRegister/getRegisterByRegisterId/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cOr_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/newProduct', {
		templateUrl: 'resources/html/newProduct/layout.html',
		controller: NewProductController,
		resolve: {
			"NewProductControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var productId = "DEFAULT";//Handle edit product scenario, because in case of edit we are sending productId for same method
				var myDefer = $q.defer();
				var controllerData ='';
				var success =false;
				$rootScope.globalPageLoader = true;
				$rootScope.newProductLoadedFully = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newProduct"]==true){
					controllerData = $http.post('newProduct/getNewProductControllerData/'+$cookieStore.get('_s_tk_com')+'/'+productId+'/'+productId).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	  					
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/newCompositeProduct', {
		templateUrl: 'resources/html/newCompositeProduct/layout.html',
		controller: NewCompositeProductController,
		resolve: {
			"NewCompositeProductControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var productId = "DEFAULT";//Handle edit product scenario, because in case of edit we are sending productId for same method
				var myDefer = $q.defer();
				var controllerData ='';
				var success =false;
				$rootScope.globalPageLoader = true;
				$rootScope.newProductLoadedFully = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newCompositeProduct"]==true){
					controllerData = $http.post('newCompositeProduct/getNewCompositeProductControllerData/'+$cookieStore.get('_s_tk_com')+'/'+productId+'/'+productId).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	  					
				}
				return myDefer.promise;
			}
		}
	}); 

	$routeProvider.when('/productDetails', {
		templateUrl: 'resources/html/productDetails/layout.html',
		controller: ProductDetailsController,
		resolve: {
			"ProductDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productDetails"]==true){
					$rootScope.productDetailsLoadedFully = true;
					controllerData = $http.post('productDetails/getProductDetailsControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_d_cPi_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/productType', {
		templateUrl: 'resources/html/productType/layout.html',
		controller: ProductTypeController,
		resolve: {
			"ProductTypeControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productType"]==true){
					$rootScope.productTypeLoadedFully = true;
					controllerData = $http.post('productType/getAllProductTypes/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/newUser', {
		templateUrl: 'resources/html/newUser/layout.html',
		controller: NewUserController,
		resolve: {
			"NewUserControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newUser"]==true){
					$rootScope.newUserLoadedFully = true;
					controllerData = $http.post('newUser/getNewUserControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/manageUser', {
		templateUrl: 'resources/html/manageUser/layout.html',
		controller: ManageUserController,
		resolve: {
			"ManageUserControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageUser"]==true){
					controllerData = $http.post('manageUser/getManageUserControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_uob_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	}); 

	$routeProvider.when('/suppliers', {
		templateUrl: 'resources/html/suppliers/layout.html',
		controller: SuppliersController,
		resolve: {
			"SuppliersControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["suppliers"]==true){
					$rootScope.suppliersLoadedFully = true;
					controllerData = $http.post('suppliers/getAllSuppliers/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/newSupplier', {
		templateUrl: 'resources/html/newSupplier/layout.html',
		controller: NewSupplierController,
		resolve: {
			"NewSupplierControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newSupplier"]==true){
					$rootScope.newSupplierLoadedFully = true;
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/brands', {
		templateUrl: 'resources/html/brands/layout.html',
		controller: BrandsController,
		resolve: {
			"BrandsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["brands"]==true){
					$rootScope.brandsLoadedFully = true;
					controllerData = $http.post('brands/getAllBrands/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/productTags', {
		templateUrl: 'resources/html/productTags/layout.html',
		controller: ProductTagsController,
		resolve: {
			"ProductTagsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTags"]==true){
					$rootScope.productTagsLoadedFully = true;
					controllerData = $http.post('productTags/getAllTags/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/notifications', {
		templateUrl: 'resources/html/notifications/layout.html',
		controller: NotificationsController,
		resolve: {
			"NotificationsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["notifications"]==true){
					$rootScope.productTagsLoadedFully = true;
					controllerData = $http.post('notifications/getAllNotifications/'+$cookieStore.get('_s_tk_com')+'/'+"false").success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/notificationsReaded', {
		templateUrl: 'resources/html/notificationsReaded/layout.html',
		controller:NotificationsReadedController,
		resolve: {
			"NotificationsReadedControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["notificationsReaded"]==true){
					$rootScope.productTagsLoadedFully = true;
					controllerData = $http.post('notificationsReaded/getAllNotifications/'+$cookieStore.get('_s_tk_com')+'/'+"false").success(function(Response) {
						controllerData = Response.data;
						//$log.info(Response);
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/sell', {
		templateUrl: 'resources/html/sell/layout.html',
		controller: SellController,
		resolve: {
			"SellControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var responseStatus;
				var controllerData ='';
				var success =false;
				$rootScope.sellLoadedFully = true;
				$rootScope.globalPageLoader = true;  
				if(!$rootScope.online){
					localforage.getItem('allProducts').then(function(data) {
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									controllerData =  data;
									return 	controllerData;  
								}
							, secondMehod: function( ) {
								return "";
							}
							});
						},10);


					});
					return myDefer.promise;

				}else{
					controllerData = $http.get('sell/getAllProducts/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						responseStatus = Response.status;
						controllerData = Response;
						$http.post('newCustomer/getAllCustomerGroups/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
							$timeout(function(){
								myDefer.resolve({
									loadControllerData: function() {
										controllerData.getAllCustomerGroups = Response.data;
										
										return 	controllerData;  
									}
								, secondMehod: function( ) {
									return "";
								}
								});
							},10);


						}).error(function() {

						});


						return controllerData;
					}).error(function() {
						controllerData =  localforage.getItem('allProducts');
						$window.location = '/app/#/login';
					});
					return myDefer.promise;

				}

			}
		}

	}); 
	$routeProvider.when('/registerClose', {
		templateUrl: 'resources/html/registerClose/layout.html',
		controller: RegisterCloseController,
		resolve: {
			"RegisterCloseControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.registerCloseLoadedFully = true;
				$rootScope.globalPageLoader = true; 			
				controllerData = $http.post('registerClose/loadRegister/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
					controllerData = Response.data;
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);

					return controllerData;
				}).error(function() {
					$window.location = '/app/#/login';
				});
				return myDefer.promise;
			}
		}
	});
	$routeProvider.when('/salesHistory', {
		templateUrl: 'resources/html/salesHistory/layout.html',
		controller: SalesHistoryController,
		resolve: {
			"SalesHistoryControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				$rootScope.dataLoading = true;
				if($rootScope.limit === undefined)
				{$rootScope.limit = 10;}
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesHistory"]==true){
					controllerData = $http.get('salesHistory/getData/'+$cookieStore.get('_s_tk_com') + '/' + $rootScope.limit+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate+'/'+$rootScope.applyDateRange).success(function(Response) {
						controllerData = Response;
						//$rootScope.InvoiceMainBeansBindedCopy = controllerData;
						localStorage.setItem("salesHistory", JSON.stringify(controllerData));
						$rootScope.limit = undefined;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									controllerData = localStorage.getItem("salesHistory");
									return 	controllerData;  
								}
							});
						},10);
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}


	});
	$routeProvider.when('/cashManagement', {
		templateUrl: 'resources/html/cashManagement/layout.html',
		controller: CashManagmentController,
		
		resolve: {
			"CashManagmentControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["cashManagement"]==true){
					$rootScope.productTagsLoadedFully = true;
					controllerData = $http.post('cashManagement/getCashInOut/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/salesLedger', {
		templateUrl: 'resources/html/salesLedger/layout.html',
		controller: SalesLedgerController,
		resolve: {
			"SalesLedgerControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.dataLoading = true;
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesLedger"]==true){
					controllerData = $http.get('salesHistory/getData/'+$cookieStore.get('_s_tk_com')+ '/' + $rootScope.limit).success(function(Response) {
						controllerData = Response;
						localStorage.setItem("salesHistory", JSON.stringify(controllerData));
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									controllerData = localStorage.getItem("salesHistory");
									return 	controllerData;  
								}
							});
						},10);
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/companySetupImages', {
		templateUrl: 'resources/html/companySetupImages/layout.html',
		controller: CompanySetupImagesController,
		
		resolve: {
			"CompanySetupImagesControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["companySetupImages"]==true){
					$rootScope.productTagsLoadedFully = true;
					controllerData = $http.post('companySetupImages/getCompanySetupImagesControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/customers', {
		templateUrl: 'resources/html/customers/layout.html',
		controller: CustomersController,
		resolve: {
			"CustomersControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["customers"]==true){
					$rootScope.customersLoadedFully = true;
					controllerData = $http.post('customers/getAllCustomers/'+$cookieStore.get('_s_tk_com')+'/'+"false").success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/newCustomer', {
		templateUrl: 'resources/html/newCustomer/layout.html',
		controller: NewCustomerController,
		resolve: {
			"NewCustomerControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["newCustomer"]==true){
					$rootScope.newCustomerLoadedFully = true;
					controllerData = $http.post('newCustomer/getAllCustomerGroups/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/customerDetails', {
		templateUrl: 'resources/html/customerDetails/layout.html',
		controller: CustomerDetailsController,
		resolve: {
			"CustomerDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["customerGroup"]==true){
					$rootScope.customerGroupLoadedFully = true;
					controllerData = $http.post('customerDetails/loadCustomerDetails/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_cD_cDt_gra')).success(function(Response) {
						controllerData = Response.data;
						localStorage.setItem("salesHistory", JSON.stringify(controllerData.salesHistory));
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/customerGroup', {
		templateUrl: 'resources/html/customerGroup/layout.html',
		controller: CustomerGroupController,
		resolve: {
			"CustomerGroupControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["customerGroup"]==true){
					$rootScope.customerGroupLoadedFully = true;
					controllerData = $http.post('customerGroup/getCustomerGroupControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/ecommerce', {
		templateUrl: 'resources/html/ecommerce/layout.html',
		controller: EcommerceController,
		resolve: {
			"EcommerceControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["ecommerce"]==true){
					$rootScope.ecommerceLoadedFully = true;
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/status', {
		templateUrl: 'resources/html/status/layout.html',
		controller: StatusController,
		resolve: {
			"StatusControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["status"]==true){

					
					localforage.getItem('InvoiceMainBeanList').then(function(value) {
						$rootScope.invoiceMainBeanStatusList = value;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									controllerData = $rootScope.invoiceMainBeanStatusList;
									return 	controllerData;  
								}
							});
						},10);

					});


				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/ecomProducts', {
		templateUrl: 'resources/html/ecomProducts/layout.html',
		controller: EcomProductsController,
		resolve: {
			"EcomProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["ecomProducts"]==true){
					$rootScope.ecomProductsLoadedFully = true;
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						});
					},10);

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/orders', {
		templateUrl: 'resources/html/orders/layout.html',
		controller: OrdersController,
		resolve: {
			"OrdersControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["orders"]==true){
					$rootScope.OrderDetailsLoadedFully = true;
					controllerData = $http.post('ecomOrder/getOrderByOutletId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});


				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/purchaseOrderDetails', {
		templateUrl: 'resources/html/purchaseOrderDetails/layout.html',
		controller: PurchaseOrderDetailsController,
		resolve: {
			"PurchaseOrderDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrderDetails"]==true){
					$rootScope.purchaseOrderDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrderDetails/getPurchaseOrderDetailsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/purchaseOrderActions', {
		templateUrl: 'resources/html/purchaseOrderActions/layout.html',
		controller: PurchaseOrderActionsController,
		resolve: {
			"PurchaseOrderActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrderActions"]==true){
					$rootScope.purchaseOrderActionsLoadedFully = true;
					controllerData = $http.post('purchaseOrderActions/getAllDetailsByStockOrderId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/purchaseOrderReceive', {
		templateUrl: 'resources/html/purchaseOrderReceive/layout.html',
		controller: PurchaseOrderReceiveController,
		resolve: {
			"PurchaseOrderReceiveControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrderReceive"]==true){
					$rootScope.purchaseOrderReceiveLoadedFully = true;
						controllerData = $http.post('poCreateandReceiveEdit/getPOCreateandReceiveEditControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {	
					controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/purchaseOrderEditProducts', {
		templateUrl: 'resources/html/purchaseOrderEditProducts/layout.html',
		controller: PurchaseOrderEditProductsController,
		resolve: {
			"PurchaseOrderEditProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrderEditProducts"]==true){
					$rootScope.purchaseOrderEditProductsLoadedFully = true;
					controllerData = $http.post('purchaseOrderEditProducts/getPurchaseOrderEditProductsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/purchaseOrderEditDetails', {
		templateUrl: 'resources/html/purchaseOrderEditDetails/layout.html',
		controller: PurchaseOrderEditDetailsController,
		resolve: {
			"PurchaseOrderEditDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrderEditDetails"]==true){
					$rootScope.purchaseOrderEditDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturnDetails', {
		templateUrl: 'resources/html/stockReturnDetails/layout.html',
		controller: StockReturnDetailsController,
		resolve: {
			"StockReturnDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturnDetails"]==true){
					$rootScope.stockReturnDetailsLoadedFully = true;
						controllerData = $http.post('purchaseOrderDetails/getPurchaseOrderReturnsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
							controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/stockSupplierTrasnferDetails', {
		templateUrl: 'resources/html/stockSupplierTrasnferDetails/layout.html',
		controller: StockSupplierTransferDetailsController,
		resolve: {
			"StockSupplierTransferDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockSupplierTrasnferDetails"]==true){
					$rootScope.stockReturnDetailsLoadedFully = true;
						controllerData = $http.post('purchaseOrderDetails/getPurchaseOrderReturnsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
							controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturnEditDetails', {
		templateUrl: 'resources/html/stockReturnEditDetails/layout.html',
		controller: StockReturnEditDetailsController,
		resolve: {
			"StockReturnEditDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturnEditDetails"]==true){
					$rootScope.stockReturnEditDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/stockSupplierTransferEditDetails', {
		templateUrl: 'resources/html/stockSupplierTransferEditDetails/layout.html',
		controller: StockSupplierTransferEditDetailsController,
		resolve: {
			"StockSupplierTransferEditDetailsController": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockSupplierTransferEditDetails"]==true){
					$rootScope.stockReturnEditDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturnEditProducts', {
		templateUrl: 'resources/html/stockReturnEditProducts/layout.html',
		controller: StockReturnEditProductsController,
		resolve: {
			"StockReturnEditProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturnEditProducts"]==true){
					$rootScope.stockReturnEditProductsLoadedFully = true;
					controllerData = $http.post('purchaseOrderEditProducts/getPurchaseOrderReturnEditProductsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/stockSupplierTransferEditProducts', {
		templateUrl: 'resources/html/stockSupplierTransferEditProducts/layout.html',
		controller: StockSupplierTransferEditProductsController,
		resolve: {
			"StockSupplierTransferEditProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockSupplierTransferEditProducts"]==true){
					$rootScope.stockReturnEditProductsLoadedFully = true;
					controllerData = $http.post('purchaseOrderEditProducts/getPurchaseOrderReturnEditProductsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturnActions', {
		templateUrl: 'resources/html/stockReturnActions/layout.html',
		controller: StockReturnActionsController,
		resolve: {
			"StockReturnActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturnActions"]==true){
					$rootScope.stockReturnActionsLoadedFully = true;
					controllerData = $http.post('purchaseOrderActions/getAllDetailsByStockOrderId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/stockSupplierTransferActions', {
		templateUrl: 'resources/html/stockSupplierTransferActions/layout.html',
		controller: StockSupplierTransferActionsController,
		resolve: {
			"StockSupplierTransferActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockSupplierTransferActions"]==true){
					$rootScope.stockReturnActionsLoadedFully = true;
					controllerData = $http.post('purchaseOrderActions/getAllDetailsByStockOrderId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockTransferDetails', {
		templateUrl: 'resources/html/stockTransferDetails/layout.html',
		controller: StockTransferDetailsController,
		resolve: {
			"StockTransferDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockTransferDetails"]==true){
					$rootScope.stockTransferDetailsLoadedFully = true;
						controllerData = $http.post('purchaseOrderDetails/getPurchaseOrderTransferControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockTransferEditDetails', {
		templateUrl: 'resources/html/stockTransferEditDetails/layout.html',
		controller: StockTransferEditDetailsController,
		resolve: {
			"StockTransferEditDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockTransferEditDetails"]==true){
					$rootScope.stockTransferEditDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockTransferEditProducts', {
		templateUrl: 'resources/html/stockTransferEditProducts/layout.html',
		controller: StockTransferEditProductsController,
		resolve: {
			"StockTransferEditProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockTransferEditProducts"]==true){
					$rootScope.stockTransferEditProductsLoadedFully = true;
					controllerData = $http.post('purchaseOrderEditProducts/getPurchaseOrderTransferEditProductsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockTransferActions', {
		templateUrl: 'resources/html/stockTransferActions/layout.html',
		controller: StockTransferActionsController,
		resolve: {
			"StockTransferActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockTransferActions"]==true){
					$rootScope.stockTransferActionsLoadedFully = true;
					controllerData = $http.post('purchaseOrderActions/getAllDetailsByStockOrderId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/layoutDesign', {
		templateUrl: 'resources/html/layoutDesign/layout.html',
		controller: LayoutDesignController,
		resolve: {
			"LayoutDesignControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				var responseStatus;
				var success =false;
				$rootScope.layoutDesignLoadedFully = true;
				$rootScope.globalPageLoader = true; 			
				controllerData = $http.get('layoutDesign/getAllProducts/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
					responseStatus = Response.status;
					controllerData = Response;
					// localforage.setItem('allProducts', Response);
					$timeout(function(){
						myDefer.resolve({
							loadControllerData: function() {
								return 	controllerData;  
							}
						, secondMehod: function( ) {
							return "";
						}
						});
					},10);

					return controllerData;
				}).error(function() {
					controllerData =  localforage.getItem('allProducts');
					$window.location = '/app/#/login';
				});
				return myDefer.promise;
			}
		}

	}); 
	$routeProvider.when('/manageRole', {
		templateUrl: 'resources/html/manageRole/layout.html',
		controller: ManageRoleController,
		resolve: {
			"ManageRoleControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["manageRole"]==true){

					controllerData = $http.post('manageRole/getPageRightsListByRoleId/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_m_rd_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/support', {
		templateUrl: 'resources/html/support/layout.html',
		controller: SupportController,
		resolve: {
			"SupportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["support"]==true){
					$rootScope.supportLoadedFully = true;
					$rootScope.supportProcessing = true;
					controllerData = $http.post('support/getSupportControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					$window.location = '/app/#/login';
					$rootScope.showErrorLoginModal = true;
					$timeout(function(){
						$rootScope.showErrorLoginModal = false;
					}, 2000);	    					
				}
				return myDefer.promise;
			}
		}
	});

	$routeProvider.when('/ticketActivity', {
		templateUrl: 'resources/html/ticketActivity/layout.html',
		controller: TicketActivityController,
		resolve: {
			"TicketActivityControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["support"]==true){
					$rootScope.supportLoadedFully = true;
					$rootScope.supportProcessing = true;
					controllerData = $http.post('ticketActivity/getTicketActivityControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_t_tob_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}
	});

	$routeProvider.when('/changePassword', {
		templateUrl: 'resources/html/changePassword/layout.html',
		controller: ChangePasswordController,
	});
	$routeProvider.when('/poCreateandReceive', {
		templateUrl: 'resources/html/poCreateandReceive/layout.html',
		controller: POCreateandReceiveController,
		resolve: {
			"POCreateandReceiveControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrder"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('poCreateandReceive/getPOCreateandReceiveControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}

	}); 	  
	
	$routeProvider.when('/poCreateandReceiveEdit', {
		templateUrl: 'resources/html/poCreateandReceiveEdit/layout.html',
		controller: POCreateandReceiveEditController,
		resolve: {
			"POCreateandReceiveEditControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["purchaseOrder"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('poCreateandReceiveEdit/getPOCreateandReceiveEditControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}

	}); 	  


	
	$routeProvider.when('/orgHierarchy', {
		templateUrl: 'resources/html/orgHierarchy/layout.html',
		controller: OrgHierarchyController,
		resolve: {
			"OrgHierarchyControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["orgHierarchy"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
				
					controllerData = $http.post('orgHierarchy/getCompanies/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
					controllerData = Response.status;
						if (controllerData == 'SUCCESSFUL') {
							$timeout(function(){
								myDefer.resolve({
									loadControllerData: function() {
										$('#chart-container').orgchart({
											  'data' :  Response.data,
											  'depth': 10,
											  'nodeContent': 'title',
											  'nodeID': 'id',
											  'verticalDepth': 0,
											  'createNode': function($node, data) {
											    var secondMenuIcon = $('<i>', {
											      'class': 'fa fa-info-circle second-menu-icon',
											      click: function() {
											        $(this).siblings('.second-menu').toggle();
											      }
											    });
											//    var secondMenu = '<div class="second-menu"><img compile-data ng-click="showdiv()" class="avatar" src="resources/dist/img/avatar.png"></div>';
											 //   $node.append(secondMenuIcon).append(secondMenu);
											  }
											});
										return 	controllerData;  
									}
								});
							},10);
						
							
						}

					}).error(function() {
						$scope.error = true;
						$scope.errorMessage = $scope.systemBusy;
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/salesReportCurrentDate', {
		templateUrl: 'resources/html/salesReportCurrentDate/layout.html',
		controller: SalesReportCurrentDateController,
		resolve: {
			"SalesReportCurrentDateControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportCurrentDate/getSalesReportCurrentDateControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadSalesReportCurrentDate', {
		templateUrl: 'resources/html/downloadSalesReportCurrentDate/layout.html',
		controller: SalesReportCurrentDateController,
		resolve: {
			"SalesReportCurrentDateControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportCurrentDate/getSalesReportCurrentDateControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 

	$routeProvider.when('/inventoryReportLive', {
		templateUrl: 'resources/html/inventoryReportLive/layout.html',
		controller: InventoryReportLiveController,
		resolve: {
			"InventoryReportLiveControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryReport/getInventoryReportControllerDataLive/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/inventoryHealthCheckReport', {
		templateUrl: 'resources/html/inventoryHealthCheckReport/layout.html',
		controller: InventoryHealthCheckReportController,
		resolve: {
			"InventoryHealthCheckReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryHealthCheckReport/getInventoryHealthCheckReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadInventoryReportLive', {
		templateUrl: 'resources/html/downloadInventoryReportLive/layout.html',
		controller: InventoryReportLiveController,
		resolve: {
			"InventoryReportLiveControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryReport/getInventoryReportControllerDataLive/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 

	$routeProvider.when('/inventoryReport', {
		templateUrl: 'resources/html/inventoryReport/layout.html',
		controller: InventoryReportController,
		resolve: {
			"InventoryReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryReport/getInventoryReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadInventoryReport', {
		templateUrl: 'resources/html/downloadInventoryReport/layout.html',
		controller: InventoryReportController,
		resolve: {
			"InventoryReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryReport/getInventoryReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/inventoryReportProductWise', {
		templateUrl: 'resources/html/inventoryReportProductWise/layout.html',
		controller: InventoryReportProductWiseController,
		resolve: {
			"InventoryReportProductWiseControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryReportProductWise"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryReportProductWise/getInventoryReportProductWiseControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)
					.success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});

	$routeProvider.when('/inventoryDetailReport', {
		templateUrl: 'resources/html/inventoryDetailReport/layout.html',
		controller: InventoryDetailReportController,
		resolve: {
			"InventoryDetailReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('inventoryDetailReport/getInventoryDetailReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 

	
	$routeProvider.when('/expenseReport', {
		templateUrl: 'resources/html/expenseReport/layout.html',
		controller: ExpenseReportController,
		resolve: {
			"ExpenseReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('expenseReport/getExpenseReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.expenseReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName)
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	$routeProvider.when('/salesReport', {
		templateUrl: 'resources/html/salesReport/layout.html',
		controller: SalesReportController,
		resolve: {
			"SalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReport/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+
							$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/salesReportLive', {
		templateUrl: 'resources/html/salesReportLive/layout.html',
		controller: SalesReportController,
		resolve: {
			"SalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReport/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+
							$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"true")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadSalesReportLive', {
		templateUrl: 'resources/html/downloadSalesReportLive/layout.html',
		controller: SalesReportController,
		resolve: {
			"SalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReport/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+
							$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"true")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadSalesReport', {
		templateUrl: 'resources/html/downloadSalesReport/layout.html',
		controller: SalesReportController,
		resolve: {
			"SalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReport/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+
							$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	$routeProvider.when('/salesReportWithOutSale', {
		templateUrl: 'resources/html/salesReportWithoutSale/layout.html',
		controller: SalesReportWithOutSaleController,
		resolve: {
			"SalesReportWithOutSaleControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReportWithOutSale"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportWithOutSale/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadsSalesReportWithoutSale', {
		templateUrl: 'resources/html/downloadsSalesReportWithoutSale/layout.html',
		controller: SalesReportWithOutSaleController,
		resolve: {
			"SalesReportWithOutSaleControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReportWithOutSale"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportWithOutSale/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/salesReportWithSale', {
		templateUrl: 'resources/html/salesReportWithSale/layout.html',
		controller: SalesReportWithSaleController,
		resolve: {
			"SalesReportWithSaleControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReportWithSale"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportWithSale/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadSalesReportWithSale', {
		templateUrl: 'resources/html/downloadSalesReportWithSale/layout.html',
		controller: SalesReportWithSaleController,
		resolve: {
			"SalesReportWithSaleControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesReportWithSale"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesReportWithSale/getSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	
	$routeProvider.when('/userSalesReport', {
		templateUrl: 'resources/html/userSalesReport/layout.html',
		controller: UserSalesReportController,
		resolve: {
			"UserSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["userSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('userSalesReport/getUserSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadUserSalesReport', {
		templateUrl: 'resources/html/downloadUserSalesReport/layout.html',
		controller: UserSalesReportController,
		resolve: {
			"UserSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["userSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('userSalesReport/getUserSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/salesDetailReport', {
		templateUrl: 'resources/html/salesDetailReport/layout.html',
		controller: SalesDetailReportController,
		resolve: {
			"SalesDetailReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesDetailReport/getSalesDetailReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadSalesDetailReport', {
		templateUrl: 'resources/html/downloadSalesDetailReport/layout.html',
		controller: SalesDetailReportController,
		resolve: {
			"SalesDetailReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('salesDetailReport/getSalesDetailReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/creditCardSalesReport', {
		templateUrl: 'resources/html/creditCardSalesReport/layout.html',
		controller: CreditCardSalesReportController,
		resolve: {
			"CreditCardSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('creditCardSalesReport/getCreditCardSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+
							'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadCreditCardSalesReport', {
		templateUrl: 'resources/html/downloadCreditCardSalesReport/layout.html',
		controller: CreditCardSalesReportController,
		resolve: {
			"CreditCardSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('creditCardSalesReport/getCreditCardSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+
							$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/cashSalesReport', {
		templateUrl: 'resources/html/cashSalesReport/layout.html',
		controller: CashSalesReportController,
		resolve: {
			"CashSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('cashSalesReport/getCashSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadCashSalesReport', {
		templateUrl: 'resources/html/downloadCashSalesReport/layout.html',
		controller: CashSalesReportController,
		resolve: {
			"CashSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["salesDetailReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('cashSalesReport/getCashSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/outletSalesReport', {
		templateUrl: 'resources/html/outletSalesReport/layout.html',
		controller: OutletSalesReportController,
		resolve: {
			"OutletSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["outletSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('outletSalesReport/getOutletSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadOutletSalesReport', {
		templateUrl: 'resources/html/downloadOutletSalesReport/layout.html',
		controller: OutletSalesReportController,
		resolve: {
			"OutletSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["outletSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('outletSalesReport/getOutletSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/brandSalesReport', {
		templateUrl: 'resources/html/brandSalesReport/layout.html',
		controller: BrandSalesReportController,
		resolve: {
			"BrandSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["brandSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('brandSalesReport/getBrandSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadBrandSalesReport', {
		templateUrl: 'resources/html/downloadBrandSalesReport/layout.html',
		controller: BrandSalesReportController,
		resolve: {
			"BrandSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["brandSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('brandSalesReport/getBrandSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/productTypeSalesReport', {
		templateUrl: 'resources/html/productTypeSalesReport/layout.html',
		controller: ProductTypeSalesReportController,
		resolve: {
			"ProductTypeSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('productTypeSalesReport/geProductTypeSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadProductTypeSalesReport', {
		templateUrl: 'resources/html/downloadProductTypeSalesReport/layout.html',
		controller: ProductTypeSalesReportController,
		resolve: {
			"ProductTypeSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('productTypeSalesReport/geProductTypeSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/supplierSalesReport', {
		templateUrl: 'resources/html/supplierSalesReport/layout.html',
		controller: SupplierSalesReportController,
		resolve: {
			"SupplierSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["supplierSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('supplierSalesReport/getSupplierSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadSupplierSalesReport', {
		templateUrl: 'resources/html/downloadSupplierSalesReport/layout.html',
		controller: SupplierSalesReportController,
		resolve: {
			"SupplierSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["supplierSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('supplierSalesReport/getSupplierSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/customerSalesReport', {
		templateUrl: 'resources/html/customerSalesReport/layout.html',
		controller: CustomerSalesReportController,
		resolve: {
			"CustomerSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('customerSalesReport/getCustomerSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/customerGroupSalesReport', {
		templateUrl: 'resources/html/customerGroupSalesReport/layout.html',
		controller: CustomerGroupSalesReportController,
		resolve: {
			"CustomerGroupSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('customerGroupSalesReport/getCustomerGroupSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadCustomerGroupSalesReport', {
		templateUrl: 'resources/html/downloadCustomerGroupSalesReport/layout.html',
		controller: CustomerGroupSalesReportController,
		resolve: {
			"CustomerGroupSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('customerGroupSalesReport/getCustomerGroupSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadCustomerSalesReport', {
		templateUrl: 'resources/html/downloadCustomerSalesReport/layout.html',
		controller: CustomerSalesReportController,
		resolve: {
			"CustomerSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productTypeSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('customerSalesReport/getCustomerSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/paymentReport', {
		templateUrl: 'resources/html/paymentReport/layout.html',
		controller: PaymentReportController,
		resolve: {
			"PaymentReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["brandSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('paymentReport/getPaymentReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadPaymentReport', {
		templateUrl: 'resources/html/downloadPaymentReport/layout.html',
		controller: PaymentReportController,
		resolve: {
			"PaymentReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["brandSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('paymentReport/getPaymentReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/registerReport', {
		templateUrl: 'resources/html/registerReport/layout.html',
		controller: RegisterReportController,
		resolve: {
			"RegisterReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["registerReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('registerReport/getRegisterReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/downloadRegisterReport', {
		templateUrl: 'resources/html/downloadRegisterReport/layout.html',
		controller: RegisterReportController,
		resolve: {
			"RegisterReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["registerReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('registerReport/getRegisterReportControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.inventoryReportOutletName)//+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType)
					.success(function(Response) {
						controllerData = Response.data;
						//$rootScope.salesReportStartDate = "undefined";
						//$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/stockReturntoWarehouse', {
		templateUrl: 'resources/html/stockReturntoWarehouse/layout.html',
		controller: StockReturntoWarehouseController,
		resolve: {
			"StockReturntoWarehouseControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturntoWarehouse"]==true){
					$rootScope.stockReturntoWarehouseLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 

		$routeProvider.when('/stockReturntoWarehouseDetails', {
		templateUrl: 'resources/html/stockReturntoWarehouseDetails/layout.html',
		controller: StockReturntoWarehouseDetailsController,
		resolve: {
			"StockReturntoWarehouseDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturntoWarehouseDetails"]==true){
					$rootScope.stockReturntoWarehouseDetailsLoadedFully = true;
						controllerData = $http.post('purchaseOrderDetails/getPurchaseOrderTransferControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_e_cOt_pio')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturntoWarehouseEditDetails', {
		templateUrl: 'resources/html/stockReturntoWarehouseEditDetails/layout.html',
		controller: StockReturntoWarehouseEditDetailsController,
		resolve: {
			"StockReturntoWarehouseEditDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturntoWarehouseEditDetails"]==true){
					$rootScope.stockReturntoWarehouseEditDetailsLoadedFully = true;
					controllerData = $http.post('purchaseOrder/getPurchaseOrderControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturntoWarehouseEditProducts', {
		templateUrl: 'resources/html/stockReturntoWarehouseEditProducts/layout.html',
		controller: StockReturntoWarehouseEditProductsController,
		resolve: {
			"StockReturntoWarehouseEditProductsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturntoWarehouseEditProducts"]==true){
					$rootScope.stockReturntoWarehouseEditProductsLoadedFully = true;
					controllerData = $http.post('purchaseOrderEditProducts/getPurchaseOrderTransferEditProductsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	   					
				}
				return myDefer.promise;
			}
		}

	});
	$routeProvider.when('/stockReturntoWarehouseActions', {
		templateUrl: 'resources/html/stockReturntoWarehouseActions/layout.html',
		controller: StockReturntoWarehouseActionsController,
		resolve: {
			"StockReturntoWarehouseActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["stockReturntoWarehouseActions"]==true){
					$rootScope.stockReturntoWarehouseActionsLoadedFully = true;
					controllerData = $http.post('purchaseOrderActions/getAllDetailsByStockOrderId/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_bl_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});

	$routeProvider.when('/orders', {
		templateUrl: 'resources/html/orders/layout.html',
		controller: OrdersController,
		resolve: {
			"OrdersControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["orders"]==true){
					$rootScope.ordersLoadedFully = true;
					controllerData = $http.post('orders/getAllOrders/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});


				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/inventoryCount', {
		templateUrl: 'resources/html/inventoryCount/layout.html',
		controller: InventoryCountController,
		resolve: {
			"InventoryCountControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryCount"]==true){
					$rootScope.inventoryCountLoadedFully = true;
					controllerData = $http.post('inventoryCount/getAllInventoryCounts/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});


				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/inventoryCountCreate', {
		templateUrl: 'resources/html/inventoryCountCreate/layout.html',
		controller: InventoryCountCreateController,
		resolve: {
			"InventoryCountCreateControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryCountCreate"]==true){
					$rootScope.inventoryCountCreateLoadedFully = true;
					controllerData = $http.post('inventoryCountCreate/getInventoryCountCreateControllerData/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});


				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/inventoryCountDetails', {
		templateUrl: 'resources/html/inventoryCountDetails/layout.html',
		controller: InventoryCountDetailsController,
		resolve: {
			"InventoryCountDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryCountDetails"]==true){
					$rootScope.inventoryCountDetailsLoadedFully = true;
					controllerData = $http.post('inventoryCountDetails/getInventoryCountDetailsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_sc_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/inventoryCountEditDetails', {
		templateUrl: 'resources/html/inventoryCountEditDetails/layout.html',
		controller: InventoryCountEditDetailsController,
		resolve: {
			"InventoryCountEditDetailsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryCountEditDetails"]==true){
					$rootScope.inventoryCountDetailsLoadedFully = true;
					controllerData = $http.post('inventoryCountEditDetails/getInventoryCountEditDetailsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_sc_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/inventoryCountActions', {
		templateUrl: 'resources/html/inventoryCountActions/layout.html',
		controller: InventoryCountActionsController,
		resolve: {
			"InventoryCountActionsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["inventoryCountActions"]==true){
					$rootScope.inventoryCountActionsLoadedFully = true;
					controllerData = $http.post('inventoryCountEditDetails/getInventoryCountEditDetailsControllerData/'+$cookieStore.get('_s_tk_com'),$cookieStore.get('_ct_sc_ost')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/productStockHistoryReport', {
		templateUrl: 'resources/html/productStockHistoryReport/layout.html',
		controller: ProductStockHistoryReportController,
		resolve: {
			"ProductStockHistoryReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productStockHistoryReport"]==true){
					$rootScope.productStockHistoryLoadedFully = true;
					controllerData = $http.post('productStockHistoryReport/getProductStockHistoryReportControllerData/'+$cookieStore.get('_s_tk_com'))
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.productStockHistoryReportStartDate = "undefined";
						$rootScope.productStockHistoryReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/productStockHistoryReport', {
		templateUrl: 'resources/html/productStockHistoryReport/layout.html',
		controller: ProductStockHistoryReportController,
		resolve: {
			"ProductStockHistoryReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productStockHistoryReport"]==true){
					$rootScope.productStockHistoryLoadedFully = true;
					controllerData = $http.post('productStockHistoryReport/getProductStockHistoryReportControllerData/'+$cookieStore.get('_s_tk_com'))
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.productStockHistoryReportStartDate = "undefined";
						$rootScope.productStockHistoryReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 

 
	$routeProvider.when('/sendSms', {
		templateUrl: 'resources/html/sendSms/layout.html',
		controller: SendSmsController,
		resolve: {
			"SendSmsControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["sendSms"]==true){
					$rootScope.inventoryCountDetailsLoadedFully = true;
					controllerData = $http.get('sendSms/getAllCustomers/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	    					
				}
				return myDefer.promise;
			}
		}

	});
	
	$routeProvider.when('/smsReport', {
		templateUrl: 'resources/html/smsReport/layout.html',
		controller: SmsReportController,
		resolve: {
			"SmsReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["smsReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('smsReport/getSmsReportControllerDataByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName)
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	$routeProvider.when('/smsDetail', {
		templateUrl: 'resources/html/smsDetail/layout.html',
		controller: SmsDetailController,

		resolve: {
			"SmsDetailControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["smsDetail"]==true){
					
					controllerData = $http.post('smsDetail/getAllSmsDetail/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    		    					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/productPriceHistory', {
		templateUrl: 'resources/html/productPriceHistory/layout.html',
		controller: ProductPriceHistoryController,

		resolve: {
			"ProductPriceHistoryControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["products"]==true){
					$rootScope.manageProductLoadedFully = true;

					controllerData = $http.post('productPriceHistory/getProductPriceHistoryByProductId/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_e_cPi_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/registerDetail', {
		templateUrl: 'resources/html/registerDetail/layout.html',
		controller: RegisterDetailController,

		resolve: {
			"RegisterDetailControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["registerDetail"]==true){
					$rootScope.manageProductLoadedFully = true;

					controllerData = $http.post('registerClose/loadRegisterDetail/'+$cookieStore.get('_s_tk_com')+'/'+$cookieStore.get('_d_Ri_gra')).success(function(Response) {
						controllerData = Response.data;
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    	 					
				}
				return myDefer.promise;
			}
		}

	}); 
	
	$routeProvider.when('/tagSalesReport', {
		templateUrl: 'resources/html/tagSalesReport/layout.html',
		controller: TagSalesReportController,
		resolve: {
			"TagSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["tagSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('tagSalesReport/getTagSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	$routeProvider.when('/downloadTagSalesReport', {
		templateUrl: 'resources/html/downloadTagSalesReport/layout.html',
		controller: TagSalesReportController,
		resolve: {
			"TagSalesReportControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;

				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["tagSalesReport"]==true){
					$rootScope.purchaseOrderLoadedFully = true;
					controllerData = $http.post('tagSalesReport/getTagSalesReportByDateRange/'+$cookieStore.get('_s_tk_com')+'/'+$rootScope.salesReportStartDate+"/"+$rootScope.salesReportEndDate + "/"+$rootScope.salesReportType+"/"+$rootScope.salesReportDateType+"/"+$rootScope.inventoryReportOutletName+"/"+"false")
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.salesReportStartDate = "undefined";
						$rootScope.salesReportEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	});
	
	
	$routeProvider.when('/stockDetByProductUuid', {
		templateUrl: 'resources/html/stockDetByProductUuid/layout.html',
		controller: StockDetByProductUuidController,
		resolve: {
			"StockDetByProductUuidControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
				var myDefer = $q.defer();
				var controllerData ='';
				$rootScope.globalPageLoader = true;
				//alert("Welcome "  + $rootScope.menuMap["productStockHistoryReport"]);
				if(typeof ($rootScope.menuMap) !== "undefined" && $rootScope.menuMap["productStockHistoryReport"]==true){
					$rootScope.productStockHistoryLoadedFully = true;
					controllerData = $http.post('stockDetByProductUuid/getStockDetByProductUuidControllerData/'+$cookieStore.get('_s_tk_com'))
					.success(function(Response) {
						controllerData = Response.data;
						$rootScope.productStockDetStartDate = "undefined";
						$rootScope.productStockDetEndDate = "undefined";
						$timeout(function(){
							myDefer.resolve({
								loadControllerData: function() {
									return 	controllerData;  
								}
							});
						},10);
					}).error(function() {
						$window.location = '/app/#/login';
					});

				}else{
					if(typeof ($rootScope.menuMap) != "undefined"){
						$window.location = '/app/#/login';
						$rootScope.showErrorLoginModal = true;
						$timeout(function(){
							$rootScope.showErrorLoginModal = false;
						}, 2000);	
					}else{
						$window.location = '/app/#/login';

					}	    						
				}
				return myDefer.promise;
			}
		}
	}); 
	
	
	
	
	
	
	
	
	 
	
	 
	
	
	
	$routeProvider.when('/backup', {
		templateUrl: 'resources/html/backup/layout.html',
		controller: BackupController
	});
	$routeProvider.when('/undefined', {
		templateUrl: 'resources/html/login/layout.html',
		controller: LoginController
	});
	$routeProvider.otherwise({redirectTo: '/home'});

	
}]);

