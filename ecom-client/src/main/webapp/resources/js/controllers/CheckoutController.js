'use strict';

/**
 * CategoryController
 * @constructor
 */
var CheckoutController = function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$sce) {

	$rootScope.MainSideBarhideit = true;
	$rootScope.MainHeaderideit = false;
	$scope.customerBean = {};
	$scope.customerBean.addressBeanList = [];
	$scope.customerBean.addressBeanList[0] = {};
	$scope.customerBean.addressBeanList[1]= {};
	$scope.checkoutBean = {};
	
	$scope.checkout = function(){
		$scope.checkoutBean.billAmount = $rootScope.cart.amount;
		$scope.checkoutBean.customerBean = $scope.customerBean;
		$scope.checkoutBean.productBeans = $scope.cart.products;
		$http.post('checkout/checkout/', $scope.checkoutBean)
		.success(function(Response) {
			$rootScope.messageSend = false;
			$scope.responseStatus = Response.status;
			
			if ($scope.responseStatus== 'SUCCESSFUL') {
				
				$rootScope.homeContactSuccess = true;
				$rootScope.homeContactSuccessMessage = Response.data;
				$timeout(function(){
					$rootScope.homeContactSuccess = false;
					$('#myModal2').modal('hide');
				    }, 1000);
				$rootScope.contactUsBean = {};
				
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
};