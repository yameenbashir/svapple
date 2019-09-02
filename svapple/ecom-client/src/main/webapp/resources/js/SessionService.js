/*jshint undef: false, unused: false, indent: 2*/
/*global angular: false */

'use strict';

angular.module('AngularSpringApp').service('SessionService', function ($cookieStore,$window,$http) {
	var  user = "" ;
	var sessionId = "";
	var responseStatus ,error ,errorMessage;
	
  return {
	  setSession : function(session) {
		  sessionId = session;
	  },
	  setUser : function(userId) {
		  user = userId;
	  },
	  validate:  function() {
			if (user == '' || typeof user == 'undefined'
				|| sessionId == ''
					|| typeof sessionId == 'undefined') {
				sessionId = $cookieStore.get('_s_tk_com',sessionId);
				user = $cookieStore.get('_s_us_com',user);
				sessionId = $cookieStore.get('_s_tk_com');
				if (user == '' || typeof user == 'undefined'
					|| sessionId == ''
						|| typeof sessionId == 'undefined') {
					$window.location = '/app/#/login';
					$window.location.reload();
				}else{
					this.isValidSession();
					return true;
				}
			}
			else{
				$cookieStore.put('_s_tk_com',sessionId);
				$cookieStore.put('_s_us_com',user);
				this.isValidSession();
				return true;
			}

		},

		isValidSession: function() {

			$http.post('session/ValidateSession/'+sessionId).success(function(Response) {

				responseStatus = Response.status;
				if (responseStatus== 'SUCCESSFUL') {
					//$window.location = Response.layOutPath;
					
				}else if(responseStatus == 'SYSTEMBUSY'
					||responseStatus=='INVALIDUSER'
						||responseStatus =='ERROR' ||responseStatus == 'INVALIDSESSION'){
					error = true;
					errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					error = true;
					errorMessage = Response.data;
				}
			}).error(function() {
				error = true;
				$window.location = '/app/#/login';

			});
		}
   
  };
});
