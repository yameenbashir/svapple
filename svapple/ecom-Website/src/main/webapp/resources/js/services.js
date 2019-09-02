'use strict';

/* Services */

var AppServices = angular.module('AngularSpringApp.services', []);

AppServices.value('version', '0.1');

AppServices.service('loggedInStatus', function() {
	var loggedIn = '';
	return {
		getStatus : function() {
			return loggedIn;
		},
		setStatus : function(value) {
			loggedIn = value;
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