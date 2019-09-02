/*jshint undef: false, unused: false, indent: 2*/
/*global angular: false */

'use strict';

angular.module('AngularSpringApp').service('AppService', function ($cookieStore,$window,$http) {
	var outletList;
  return {
	  
	  fetchAllOutlets:  function() {
			$http.post('purchaseOrder/getAllOutlets').success(function(Response) {
				 var responseStatus = Response.status;
				if (responseStatus== 'SUCCESSFUL') {
					 outletList = Response.data;
					 return outletList;
				}else if(responseStatus == 'SYSTEMBUSY'
					||responseStatus=='INVALIDUSER'
						||responseStatus =='ERROR'
							||responseStatus =='INVALIDSESSION'){
					return "";
				} else {
					return "";
				}

			}).error(function() {
				return "";
				});
			return outletList;
		}
   
  };
});
