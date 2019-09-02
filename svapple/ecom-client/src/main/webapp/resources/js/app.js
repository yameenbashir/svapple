'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.bootstrap','ngRoute','uiSlider','ngCookies',
                                              'AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'
                                              ]);


App.run(function($rootScope, $templateCache,$cookieStore) {
	
	$rootScope.MainSideBarhideit = true;
	$rootScope.MainHeaderideit = true;
	$rootScope.cart = {};
	$rootScope.cart.amount=0;
	$rootScope.cart.products=[];
	$rootScope.userName = $cookieStore.get('userName');
	$rootScope.userCreatedDate = $cookieStore.get('userCreatedDate');
	$rootScope.IsManager = $cookieStore.get('IsManager');
	$rootScope.IsSuperUser = $cookieStore.get('IsSuperUser');
	$rootScope.toDoList = $cookieStore.get('toDoList');
	$rootScope.notificationList = $cookieStore.get('NotificationList');
	$rootScope.sessionId = $cookieStore.get('sessionId');
	$rootScope.IsLoggedIn = $cookieStore.get('isLogin');
	
	   $rootScope.$on('$viewContentLoaded', function() {
		  
		    $templateCache.removeAll();
	   });
	   
	   $rootScope.doLogoutFromEcom = function() {
			$cookieStore.put('_s_tk_com','');
			$rootScope.IsLoggedIn = false;
			$cookieStore.put('isLogin', $rootScope.IsLoggedIn);
			$window.location = '/ecom/#/login';
		};
	});
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

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ( $routeProvider,$scope,$http) {
	
	
	  $routeProvider.when('/category', {
	        templateUrl: 'category/layout',
	        controller: CategoryController,
	        resolve: {
    			"CategoryControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
    				var myDefer = $q.defer();
    				var controllerData ='';
    				$rootScope.globalPageLoader = true;

    				controllerData = $http.post('category/getAllProducts/'+$rootScope.productTypeName+'/'+$rootScope.productTypeId).success(function(Response) {
    						controllerData = Response;
    						$timeout(function(){
    							myDefer.resolve({
    								loadControllerData: function() {
    									return 	controllerData;  
    								}
    							});
    						},10);
    					}).error(function() {
    						$window.location = '/ecom/#/login';
    					});

    		
    				return myDefer.promise;
    			}
    		}
	    });
	    $routeProvider.when('/home', {
	        templateUrl: 'home/layout',
	        controller: HomeController,
	    	resolve: {
	    			"HomeControllerPreLoad": function( $q, $timeout,$http ,$cookieStore,$window,$rootScope) {
	    				var myDefer = $q.defer();
	    				var controllerData ='';
	    				$rootScope.globalPageLoader = true;

	    				controllerData = $http.post('home/getHomeData/10').success(function(Response) {
	    						controllerData = Response.data;
	    						$timeout(function(){
	    							myDefer.resolve({
	    								loadControllerData: function() {
	    									return 	controllerData;  
	    								}
	    							});
	    						},10);
	    					}).error(function() {
	    						$window.location = '/ecom/#/login';
	    					});

	    		
	    				return myDefer.promise;
	    			}
	    		}

	    	
	    });
    $routeProvider.when('/cart', {
        templateUrl: 'cart/layout',
        controller: CartController,
    	
    });
    $routeProvider.when('/checkout', {
        templateUrl: 'checkout/layout',
        controller: CheckoutController,
    	
    });
    $routeProvider.when('/register', {
        templateUrl: 'register/layout',
        controller: RegisterController,
    	
    });
    $routeProvider.when('/login', {
        templateUrl: 'login/layout',
        controller: LoginController,
    	
    });
    $routeProvider.otherwise({redirectTo: '/home'});
   
}]);

