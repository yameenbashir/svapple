'use strict';

/* Services */

var AppServices = angular.module('AngularSpringApp.services', []);

AppServices.value('version', '0.1');


AppServices.service('loggedInStatus', function($cookieStore,$http) {
	var loggedIn = false;
	return {
		validSession : function() {
			$http.post('session/ValidateSession/'+ $cookieStore.get('sessionId')).success(function(Response) {
				
				if (Response.status== 'SUCCESSFUL') {
					loggedIn = true;
					return loggedIn;
					
				}else {
					loggedIn = false;
					return loggedIn;
				}
				}).error(function() {
					loggedIn = false;
			});
			
		}
	};
});
AppServices.service('testUserStatus', function() {
	var sessionId = '';
	var name = '';
	var testCode = '';
	var email = '';
	var testTakerId = '';
	return {
		getSessionId : function() {
			return sessionId;
		},
		setSessionId : function(value) {
			sessionId = value;
		},
		getName : function() {
			return name;
		},
		setName : function(value) {
			name = value;
		},
		getTestCode : function() {
			return testCode;
		},
		setTestCode : function(value) {
			testCode = value;
		},
		getEmail : function() {
			alert(email);
			return email;
		},
		setEmail : function(value) {
			alert(value);
			email = value;
		},
		getTestTakerId : function() {
			return testTakerId;
		},
		setTestTakerId : function(value) {
			testTakerId = value;
		}
	};
	
});