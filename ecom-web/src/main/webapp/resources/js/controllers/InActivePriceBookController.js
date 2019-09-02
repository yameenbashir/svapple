'use strict';

/**
 * InActivePriceBookController
 * @constructor
 */
var InActivePriceBookController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','InActivePriceBookControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,InActivePriceBookControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = InActivePriceBookControllerPreLoad.loadControllerData();
			$scope.fetchData();
					
		}
	};
	
	$scope.fetchData = function() {

		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){

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
				$scope.priceBookList = $scope.data;
				$scope.roledId = $cookieStore.get('_s_tk_rId');
				setTimeout(
						function() 
						{
							$('#myTable').DataTable( {
								responsive: true,
								order: [[ 6, "Created At" ]],
								paging: true,
								searching:true,
								bInfo : true
							} );
						}, 10);
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.addPriceBook = function() {
		$window.location = "/app/#/newPriceBook";
	};
	$scope.editPriceBook = function(priceBook){
		$cookieStore.put('_e_cPri_gra',priceBook.priceBookId) ;
		$window.location = "/app/#/managePriceBook";
	};
	
	$scope.sessionValidation();
}];