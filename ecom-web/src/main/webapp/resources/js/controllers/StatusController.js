'use strict';

/**
 * StatusController
 * 
 * @constructor
 */
var StatusController = ['$scope', '$http', '$window', '$cookieStore','$rootScope', '$timeout', '$route', 'SessionService','StatusControllerPreLoad', function($scope, $http, $window, $cookieStore,$rootScope, $timeout, $route, SessionService,StatusControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.brandSuccess = false;
	$scope.brandError = false;
	$scope.brandBean = {};
	
	$scope.sessionValidation = function() {
		/* $rootScope.newflag = false;
		
		 localforage.getItem('invoiceMainBeanNewList').then(function(value) {
			 $scope.invoiceMainBeanStatusList = value;
			 localforage.setItem('InvoiceMainBeanList', $scope.invoiceMainBeanStatusList);
			
		 });*/
		$scope.fetchData();
	};

	$scope.fetchData = function() {
		
		$scope._s_tk_com = $cookieStore.get('_s_tk_com');
		$scope.invoiceMainBeanStatusList = StatusControllerPreLoad.loadControllerData();
		if($scope.invoiceMainBeanStatusList==null){
			localforage.getItem('invoiceMainBeanNewList').then(function(value) {
				 $scope.invoiceMainBeanStatusList = value;
				 
				 localforage.setItem('InvoiceMainBeanList', $scope.invoiceMainBeanStatusList);
				 if(value!=null){
					//alert("new has data so $route.reload();");
					 $route.reload();
				 }
				
			 });
		}
		$rootScope.globalPageLoader = false;
		
	};
	
	

	

	  $scope.saveFile = function(){
			
			$scope.invoiceMainBeanStatusList = StatusControllerPreLoad.loadControllerData();
			if($scope.invoiceMainBeanStatusList==null){
				
					$scope.data1 = $scope.invoiceMainBeanStatusList;
				
			}
	    	console.log($scope.invoiceMainBeanStatusList);
	    	
	    	
	    	
	    	$scope.loading = true;
	    	var currentdate = new Date(); 
			var invoiceRefNbr = "SV-" + currentdate.getDate() + 
			+ (currentdate.getMonth()+1)  +  
			+ currentdate.getFullYear() + 
			+ currentdate.getHours() +   
			+ currentdate.getMinutes()+  
			+ currentdate.getSeconds(); 
	        var textToBLOB = new Blob([JSON.stringify($scope.invoiceMainBeanStatusList)], { type: 'text/plain' });
	    	
	        var sFileName = invoiceRefNbr+'-OfflineSaleData.txt';	   // The file to save the data.
	        $scope.loading = false;

	        var newLink = document.createElement("a");
	        newLink.download = sFileName;

	        if (window.webkitURL != null) {
	            newLink.href = window.webkitURL.createObjectURL(textToBLOB);
	        }
	        else {
	            newLink.href = window.URL.createObjectURL(textToBLOB);
	            newLink.style.display = "none";
	            document.body.appendChild(newLink);
	        }

	        newLink.click();
	    };
	    
	    
	$scope.showAddBrandPopup = function() {
		$scope.brandBean = {};
		$scope.showNewBrandModal = true;
	};

	$scope.sessionValidation();

}];