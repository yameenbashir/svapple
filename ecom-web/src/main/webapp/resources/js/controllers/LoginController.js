'use strict';

/**
 * LoginController
 * 
 * @constructor
 */
var LoginController = ['$scope', '$http', '$window', '$cookieStore','$rootScope', 'SessionService',function($scope, $http, $window, $cookieStore,$rootScope, SessionService) {
	$rootScope.MainSideBarhideit = true;
	$rootScope.MainHeaderideit = true;
	$scope.loading = false;
	$scope.error = false;
	$scope.userBean = {};
	$rootScope.IsLoggedIn = false;
	$rootScope.isDisabled = false;
	SessionService.setSession("");
	$cookieStore.put('_s_tk_com', '');
	$cookieStore.put('email', '');
	$cookieStore.put('menuMap', '');
	$cookieStore.put('isLogin', '');
	$cookieStore.put('EmployeeInfo', '');
	$cookieStore.put('isUpdateCall', '');
	$cookieStore.put('CompanyInfo', '');
	$cookieStore.put('isCompanyUpdateCall', '');
	$cookieStore.put('WorkplaceInfo', '');
	$cookieStore.put('isWorkplaceUpdateCall', '');
	$cookieStore.put('departmentsInfo', '');
	$cookieStore.put('invoiceNo', '');
	$cookieStore.put('ScheduleInfo', '');
	$cookieStore.put('isScheduleUpdateCall', '');
	$cookieStore.put('ScheduleEmployeesListInfo', '');
	$cookieStore.put('ticketBean', '');
	$cookieStore.put('ticketActivities', '');
	$cookieStore.put('_hirearchy_comp_Id', false);
	$rootScope.shiftProcessing = false;
	$cookieStore.put('shiftProcessing', $rootScope.shiftProcessing);
	$rootScope.locationProcessing = false;
	$cookieStore.put('locationProcessing', $rootScope.locationProcessing);
	$rootScope.employeeProcessing = false;
	$cookieStore.put('employeeProcessing', $rootScope.employeeProcessing);
	$rootScope.payRollProcessing = false;
	$cookieStore.put('payRollProcessing', $rootScope.payRollProcessing);
	$rootScope.reportProcessing = false;
	$cookieStore.put('reportProcessing', $rootScope.reportProcessing);
	$rootScope.dashBoardProcessing = false;
	$cookieStore.put('dashBoardProcessing', $rootScope.dashBoardProcessing);
	$rootScope.timeLoggingProcessing = false;
	$cookieStore.put('timeLoggingProcessing', $rootScope.timeLoggingProcessing);
	$rootScope.supportProcessing = false;
	$cookieStore.put('supportProcessing', $rootScope.supportProcessing);
	$rootScope.personalInfoProcessing = false;
	$cookieStore.put('personalInfoProcessing',
			$rootScope.personalInfoProcessing);
	$cookieStore.put("_Orig_comp_Id", "");
	$scope.systemBusy = 'System is Busy Please try again';
	$scope.manage = 'Shop';
	$scope.worker = 'Vitals';
	$scope.signIn = 'Sign In';
	$scope.enterYourDetailToStartYourSession = 'Enter your details to start your session';
	$scope.signInAsaDifferentUser = 'Or sign in as a different user';

	$scope.emailPlaceholder = 'Email...';
	$scope.PasswordPlaceholder = 'Password...';
	$scope.LoginButtonText = 'LOGIN';
	$scope.LoginText = 'LOGIN';
	$scope.requireLogin = 'Require Login';
	var my_skins = [ "skin-blue", "skin-black", "skin-red", "skin-yellow",
			"skin-purple", "skin-green", "skin-blue-light", "skin-black-light",
			"skin-red-light", "skin-yellow-light", "skin-purple-light",
			"skin-green-light" ];
	$scope.changeSkinChange = function(skin) {
		$.each(my_skins, function(i) {
			$("body").removeClass(my_skins[i]);
		});

		$("body").addClass(skin);
		store('skin', skin);
	};
	$scope.clearFields = function() {
		$scope.userBean = {};
	};

	function store(name, val) {
		if (typeof (Storage) !== "undefined") {
			localStorage.setItem(name, val);
		} else {
			window
					.alert('Please use a modern browser to properly view this template!');
		}
	}
	
	$scope.changeSkinChange('skin-purple-light');
	$scope.doLogin = function(userBean) {
		$rootScope.isDisabled = true;
		$scope.loading = true;
		if (typeof $scope.userBean.email != 'undefined'
				&& typeof $scope.userBean.password != 'undefined') {
			$http.post('login/doLogin/', userBean).success(function(Response) {
								$rootScope.online = true;
								$scope.responseStatus = Response.status;

								if ($scope.responseStatus == 'SUCCESSFUL') {
									$scope.loginSUccessFUll(Response);

								} else if ($scope.responseStatus == 'SYSTEMBUSY'
										|| $scope.responseStatus == 'INVALIDUSER'
										|| $scope.responseStatus == 'ERROR'
											|| $scope.responseStatus == 'OUTLETCLOSED') {
									$scope.loading = false;
									$scope.error = true;
									$scope.errorMessage = Response.data;
									$rootScope.isDisabled = false;

								} else {
									$scope.loading = false;
									$scope.error = true;
									$scope.errorMessage = Response.data;
									$rootScope.isDisabled = false;
								}
							}).error(function() {localforage.getItem('kk_vv').then(function(data) {
													$rootScope.userMap = data;
													if (window.atob($rootScope.userMap[$scope.userBean.email]) == $scope.userBean.password) {
														$rootScope.online = false;
														SessionService.setUser($scope.userBean.email);
														$rootScope.IsSuperUser = true;
														$rootScope.IsManager = false;
														$window.location = '/app/#/sell';
													} else {
														$scope.loading = false;
														$scope.error = true;
														$scope.errorMessage = $scope.systemBusy;
														$rootScope.isDisabled = false;
													}

												});

							});
		}

	};

	$scope.loginSUccessFUll = function(Response) {
//		window.location.reload(true);
//		var appCache = window.applicationCache;
//		appCache.update(); //this will attempt to update the users cache and changes the application cache status to 'UPDATEREADY'.
//		if (appCache.status == window.applicationCache.UPDATEREADY) {
//		  appCache.swapCache(); //replaces the old cache with the new one.
//		}
		$cookieStore.put('_s_tk_com', '');
		$cookieStore.put('_s_tk_rId', '');
		$cookieStore.put('_s_tk_oId', '');
		$cookieStore.put('_s_tk_iho','');
		$cookieStore.put('_s_tk_rId', Response.data.userRole);
		$cookieStore.put('_s_tk_oId', Response.data.outletId);
		$cookieStore.put('_s_tk_iho',Response.data.isHeadOffice);
		$cookieStore.put('_s_tk_com', Response.data.sessionId);
		$rootScope.userRole =  $cookieStore.get('_s_tk_rId');
		SessionService.setSession(Response.data.sessionId);
		$cookieStore.put('userName', Response.data.userName);
		$cookieStore.put('email', $scope.userBean.email);
		$cookieStore.put('companyImagePath', Response.data.companyImagePath);
		SessionService.setUser($scope.userBean.email);
		$rootScope.userName = Response.data.userName;
		$rootScope.userId = Response.data.userId;
		localforage.setItem('kk_vv', Response.data.userMap);
		$cookieStore.put('menuMap', "");
		$cookieStore.put('menuMap', Response.data.mapMenu);
		$rootScope.menuMap = Response.data.mapMenu;
		if (Response.data.userRole == 3) {
			$rootScope.IsManager = true;
			$rootScope.IsSuperUser = false;
		} else if (Response.data.userRole == 1) {
			$rootScope.IsSuperUser = true;
			$rootScope.IsManager = false;
		} else {
			$rootScope.IsSuperUser = false;
			$rootScope.IsManager = false;
		}
		$cookieStore.put('IsSuperUser', $rootScope.IsSuperUser);
		$cookieStore.put('IsManager', $rootScope.IsManager);

		$rootScope.IsLoggedIn = true;
		$cookieStore.put('isLogin', $rootScope.IsLoggedIn);
		// $scope.loading= false;
		$window.location = Response.layOutPath;
		$rootScope.userImage = "/app/resources/dist/img/avatar5.png";
//		$rootScope.userImage = "/app/resources/images/selfie_"
//				+ $scope.userBean.email + ".jpg";
//		var url = $rootScope.userImage;
//		var request = new XMLHttpRequest();
//		request.open('HEAD', url, false);
//		request.send();
//		if (request.status == 200) {
//
//		} else {
//
//			$rootScope.userImage = "/app/resources/dist/img/avatar5.png";
//		}
		//$rootScope.synchsales();
	};
	$scope.validator = function(val) {
		return val.length < 1;
	};

}];