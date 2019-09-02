'use strict';

/**
 * ProductPriceHistoryController.js
 * @constructor
 */
var ProductPriceHistoryController = ['$scope', '$http','$sce', '$window','$cookieStore','$rootScope','SessionService','ProductPriceHistoryControllerPreLoad',function($scope, $http,$sce, $window,$cookieStore,$rootScope,SessionService,ProductPriceHistoryControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.roledId = $cookieStore.get('_s_tk_rId');
	$scope.dataLoading = false;
	
	
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.productName = $cookieStore.get('_e_cPiN_gra') ;
			$scope.data = ProductPriceHistoryControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};
	
	$scope.fetchData = function(){
		
		
		if($scope.data == 'NORECORDFOUND' ||$scope.data == 'No record found !'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
			$scope.dataLoading = true;
		}
		else if($scope.data == 'SYSTEMBUSY'){
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
			$scope.dataLoading = true;
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$scope.errorMessage = 'An exception occured while validating session !';
			$window.location = '/app/#/login';

		}
		else{
			if($scope.data!=null){
				$scope.productPriceHistoryList = $scope.data;
				setTimeout(
					function() 
					{
						$('#myTable').DataTable( {
							responsive: true,
							paging: true,
							pageLength: 10,
							searching:true,
							bInfo : true,
							dom : 'Bfrtip',
							buttons :$rootScope.buttonsView
						} );
						
					}, 1);
			$scope.dataLoading = true;
			}else{
				$scope.dataLoading = true;
			}
				

				//$scope.lodAllProductsGraph();
				
				
			
		}
		
		$rootScope.globalPageLoader = false;
	};
	
	
	

	
	
	
	
	$scope.sessionValidation();
}];