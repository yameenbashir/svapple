'use strict';

/**
 * ProductTagsController
 * @constructor
 */
var ProductTagsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','SessionService','ProductTagsControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,SessionService,ProductTagsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.tagSuccess = false;
	$scope.tagError = false;
	$scope.tagBean = {};
	$scope.tagList = [];
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ProductTagsControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.productTagsLoadedFully = false;
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
		}
		else if($scope.data == 'SYSTEMBUSY'){
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$scope.errorMessage = 'An exception occured while validating session !';
			$window.location = '/app/#/login';

		}
		else{

			if($scope.data!=null){
				$scope.tagList = $scope.data;
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								paging: true,
								searching:true,
								bInfo : true
							} );


						}, 10);
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.showAddTagPopup = function(){
		$scope.tagBean = {};
		$scope.showNewTagModal = true;
	};
	
	$scope.addTag = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.tagSuccess = false;
		$scope.tagError = false;
		$scope.loading = true;
		for(var i=0;i<$scope.tagList.length;i++){
			if($scope.tagList[i].tagName.toLowerCase()==$scope.tagBean.tagName.toLowerCase()){
				$scope.tagError = true;
				$scope.tagErrorMessage = "Tag already exist with name: "+$scope.tagBean.tagName;
				$scope.showNewTagModal = false;
				$scope.loading = false;
				$timeout(function(){
					
					$scope.tagError = false;
					
				    }, 2000);
				return;
			}
		}
		$http.post('productTags/addTag/'+$scope._s_tk_com, $scope.tagBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.tagBean = {};
				$scope.tagSuccess = true;
				$scope.tagSuccessMessage = 'Request Processed Successfully!';
				$scope.showNewTagModal = false;
				$timeout(function(){
					$scope.tagSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.tagError = true;
				$scope.tagErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.tagError = true;
				$scope.tagErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.tagError = true;
			$scope.tagErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.showUpdateTagPopup = function(tag){
		$scope.updateTagBean = {};
		$scope.updateTagBean = angular.copy(tag); 
		$scope.showUpdateTagModal = true;
	};
	
	$scope.updateTag = function() {
		$rootScope.impersonate = $cookieStore.get("impersonate");
		if($rootScope.impersonate){
			$rootScope.permissionDenied();
			return;
		}
		$scope.tagSuccess = false;
		$scope.tagError = false;
		$scope.loading = true;
		for(var i=0;i<$scope.tagList.length;i++){
			if($scope.tagList[i].tagName.toLowerCase()==$scope.updateTagBean.tagName.toLowerCase()){
				$scope.tagError = true;
				$scope.tagErrorMessage = "Tag already exist with name: "+$scope.updateTagBean.tagName;
				$scope.showUpdateTagModal = false;
				$scope.loading = false;
				$timeout(function(){
					
					$scope.tagError = false;
					
				    }, 2000);
				return;
			}
		}
		$http.post('productTags/updateTag/'+$scope._s_tk_com, $scope.updateTagBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.updateTagBean = {};
				$scope.tagSuccess = true;
				$scope.tagSuccessMessage = Response.data;
				$scope.showUpdateTagModal = false;
				$timeout(function(){
					$scope.tagSuccess = false;
					$timeout(function(){
					      $route.reload();
					    }, 500);
				    }, 2000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.tagError = true;
				$scope.tagErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.tagError = true;
				$scope.tagErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.tagError = true;
			$scope.tagErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.fetchAllTags = function() {
		$http.post('productTags/getAllTags/'+$scope._s_tk_com).success(function(Response) {
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus== 'SUCCESSFUL') {
					$scope.tagList = Response.data;
					setTimeout(
			    			  function() 
			    			  {
			    			  $('#myTable').DataTable( {
			    				  responsive: true,
			    			        paging: true,
			    			        searching:true,
			    			        bInfo : true
			    			    } );
			    			
			    				
			    			  }, 10);
					
				}else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});

		};
		

	
	$scope.sessionValidation();
}];