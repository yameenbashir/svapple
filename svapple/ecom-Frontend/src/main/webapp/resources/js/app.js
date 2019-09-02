'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['ui.bootstrap','ngRoute','uiSlider','webcam','timer','ngCookies','googlechart',
                                              'AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives'
                                              ]);


App.run(function($rootScope, $templateCache,$cookieStore) {
	
	$rootScope.MainSideBarhideit = true;
	$rootScope.MainHeaderideit = true;
	
	
	$rootScope.userName = $cookieStore.get('userName');
	$rootScope.userCreatedDate = $cookieStore.get('userCreatedDate');
	$rootScope.IsManager = $cookieStore.get('IsManager');
	$rootScope.IsSuperUser = $cookieStore.get('IsSuperUser');
	$rootScope.toDoList = $cookieStore.get('toDoList');
	$rootScope.notificationList = $cookieStore.get('NotificationList');
	$rootScope.sessionId = $cookieStore.get('sessionId');
	
	   $rootScope.$on('$viewContentLoaded', function() {
		  
		    $templateCache.removeAll();
	   });
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
	
    
    $routeProvider.when('/home', {
        templateUrl: 'home/layout',
        controller: HomeController
    });
 
    $routeProvider.otherwise({redirectTo: '/home'});
   
}]);

