'use strict';

/**
 * OrgHierarchyController
 * @constructor
 */
var OrgHierarchyController = ['$scope', '$http','$rootScope','$cookieStore','$compile','$window','OrgHierarchyControllerPreLoad','$timeout',function($scope, $http,$rootScope,$cookieStore,$compile,$window,OrgHierarchyControllerPreLoad,$timeout) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	
	$cookieStore.put('EmployeeInfo', '');
	$cookieStore.put('isUpdateCall', '');
	$cookieStore.put('WorkplaceInfo', '');
	$cookieStore.put('isWorkplaceUpdateCall', '');
	$cookieStore.put('invoiceNo','');
	$cookieStore.put('departmentsInfo','');
	$cookieStore.put('ScheduleInfo','');
	$cookieStore.put('isScheduleUpdateCall', '');
	$cookieStore.put('ScheduleEmployeesListInfo','');
	$cookieStore.put('ticketBean','');
	$cookieStore.put('ticketActivities','');
	$cookieStore.put('_hirearchy_comp_Id',false);
	$scope.user = $cookieStore.get('email') ;
	$scope.sessionId =  $cookieStore.get('_s_tk_com') ;
	
	$scope.validate = function() {
		OrgHierarchyControllerPreLoad.loadControllerData();
		$rootScope.globalPageLoader = false;
		  $timeout(function() {
		    	$scope.canvas = document.getElementById("hirearchyPage");
		    	angular.element(document).injector().invoke(function($compile) {
		  	    var newElement = $compile($scope.canvas)($scope);
		     });
			},1000);

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
	 $scope.changeSkinChange = function(skin) {
			$.each(my_skins, function (i) {
			      $("body").removeClass(my_skins[i]);
			    });
	
			    $("body").addClass(skin);
			    store('skin', skin);
		}
	
	function store(name, val) {
		    if (typeof (Storage) !== "undefined") {
		     localStorage.setItem(name, val);
		    } else {
		      window.alert('Please use a modern browser to properly view this template!');
		    }
		  }
	    
	
	$scope.updateCompanyId = function(companyId,companyLevel){
		$scope.error = false;
		$scope.errorMessage = '';
		//alert('calling change Hierarchy level');
		if(companyId == '' || typeof companyId == 'undefined'){
			return;
		}

		
		$http.post('orgHierarchy/changeHierarchyLevel/'+$scope.sessionId+'/'+companyId).success(function(Response) {

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				if(companyLevel=="top-level"){
					$scope.changeSkinChange('skin-blue');
					
				}else if(companyLevel=="middle-level"){
					
					$scope.changeSkinChange('skin-green');
				}
				else if(companyLevel=="bottom-level"){
					$scope.changeSkinChange('skin-red');
					
				}else{
					
					$scope.changeSkinChange('skin-yellow');
				}
				$window.location = '/app/#/home';
				$cookieStore.put('_s_tk_oId', "");
				$cookieStore.put('_s_tk_oId', companyId);
				if( companyId!="" &&
						(companyId != $cookieStore.get("_Orig_comp_Id"))){
					$cookieStore.put('_hirearchy_comp_Id',true);
					
				}	
				$rootScope.companyName = Response.data.compnayName;
				$cookieStore.put("companyName",Response.data.compnayName);
				$rootScope.impersonate = Response.data.impersonate;
				$cookieStore.put("impersonate",Response.data.impersonate);
			}else if($scope.responseStatus == 'ACCESSDENIED'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$timeout(function(){
					$scope.error = false;
					$scope.errorMessage = '';
				}, 5000);
			}else if($scope.responseStatus == 'NORECORDFOUND'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.error = true;
				$window.location = '/app/#/login';

			}

		}).error(function() {
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};
	
  
	

	    $scope.validate();
	  

}];