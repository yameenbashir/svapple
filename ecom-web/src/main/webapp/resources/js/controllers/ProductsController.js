'use strict';

/**
 * OutletsController
 * @constructor
 */
var ProductsController = ['$scope', '$http','$sce', '$window','$cookieStore','$rootScope','SessionService','ProductsControllerPreLoad',function($scope, $http,$sce, $window,$cookieStore,$rootScope,SessionService,ProductsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.gui = {};
	$scope.gui.showProductPriceHistory = false;
	$scope.roledId = $cookieStore.get('_s_tk_rId');
	$scope.dataLoading = false;
	
	$scope.addProduct = function() {
		$window.location = "/app/#/newProduct";
	};
	$scope.addCompositeProduct = function() {
		$window.location = "/app/#/newCompositeProduct";
	};
	$scope.showProduct = function(product) {
		
		$cookieStore.put('_d_cPi_gra',product.productId) ;
		$window.location = "/app/#/productDetails";
	};
	
	$scope.editProduct = function(product){
		
		$cookieStore.put('_e_cPi_gra',product.productId) ;
		$cookieStore.put('_e_cOi_gra',product.outletId) ;
		if(product.isComposite=="true"){
			$window.location = "/app/#/manageCompositeProduct";
		}else{
			$window.location = "/app/#/manageProduct";
		}
	};
	
	$scope.showProductPriceHistory = function(product){
		
		$cookieStore.put('_e_cPi_gra',product.productId) ;
		$cookieStore.put('_e_cPiN_gra',product.productName) ;
		$window.location = "/app/#/productPriceHistory";
	};
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.fetchData();
		}
	};
	
	$scope.fetchData = function(){
	 ProductsControllerPreLoad.loadControllerData();
		
		if($scope.data == 'NORECORDFOUND' ||$scope.data == 'No record found !'){

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
				$scope.productsList = null;
				$http.post('products/getAllProducts/'+$cookieStore.get('_s_tk_com')+'/'+"false").success(function(Response) {
					$scope.data = Response.data;
					
					if($scope.data.productBeanList!=null){
						$scope.productsList  = $scope.data.productBeanList;
					}
					if($scope.data.showProductPriceHistory!=null){
						if($scope.data.showProductPriceHistory=='true'){
							$scope.gui.showProductPriceHistory = true;
						}else{
							$scope.gui.showProductPriceHistory = false;
						}
					}
					$scope.comparisonProductsList  = $scope.data.productBeanList;
					$scope.lodAllProductsAsynchrnously();
					setTimeout(
							function() 
							{
								$('#myTable').DataTable( {
									responsive: true,
									paging: true,
									pageLength: 5,
									searching:true,
									bInfo : true,
									dom : 'Bfrtip',
									buttons :$rootScope.buttonsView
								} );
								
							}, 1);
					$scope.dataLoading = true;
				}).error(function() {
					$window.location = '/app/#/login';
				});

				$scope.lodAllProductsGraph();
				
				
			
		}
		
		$rootScope.globalPageLoader = false;
	};
	
	$scope.lodAllProductsGraph = function(){
		$scope.dataLoading = false;
		$http.post('products/getProductDashBoard/'+$cookieStore.get('_s_tk_com')).success(function(Response) {
			$scope.data = Response.data;
			 $scope.chartCustomerCount = new CanvasJS.Chart("chartContainerProductCount", {
			        theme: 'theme1',
			        height: 150,
			       
			        axisY: {
			            labelFontSize: 10,
			        },
			        axisX: {
			            labelFontSize: 10,
			            labelAngle: -45
			        },
			        data: [              
			            {
			                type: "area",
			                color: "rgba(40,175,101,0.6)",
			                dataPoints: $scope.data.dataPointsCustomerCount
			            }
			        ]
			    });
			$scope.chartCustomerCount.render(); //render the chart for the first time
			   
			$scope.dataLoadingGraph = true;
		}).error(function() {
			//$window.location = '/app/#/login';
		});
	};
	
	$scope.lodAllProductsAsynchrnously = function(){
		$scope.dataLoading = false;
		$http.post('products/getAllProducts/'+$cookieStore.get('_s_tk_com')+'/'+"true").success(function(Response) {
			$scope.data = Response.data;
			/*if($scope.data.productBeanList!=null){
				$scope.productsList  = $scope.data.productBeanList;
			}
			if($scope.data.showProductPriceHistory!=null){
				if($scope.data.showProductPriceHistory=='true'){
					$scope.gui.showProductPriceHistory = true;
				}else{
					$scope.gui.showProductPriceHistory = false;
				}
			}*/
			$scope.dataLoading = true;
		}).error(function() {
			$window.location = '/app/#/login';
		});
	};
	
	
	$scope.lodAllProducts = function(){
		$scope.dataLoading = false;
		
		for(var i=0;i<$scope.data.productBeanList.length;i++){
			if(!checkProductExist($scope.data.productBeanList[i])){
				$scope.productsList.push($scope.data.productBeanList[i]);
			}
		}
		var table = $('#myTable').DataTable();
		if(table){
			 table.destroy();
		}
		setTimeout(
				function() 
				{
					$('#myTable').DataTable( {
						responsive: true,
						paging: true,
						searching:true,
						pageLength: 5,
						bInfo : true,
						dom : 'Bfrtip',
						buttons :$rootScope.buttonsView
					} );
					
				}, 1);
		$scope.dataLoading = true;
	};

	function checkProductExist(product) {
		if($scope.comparisonProductsList!='undefined' && $scope.comparisonProductsList!=null && $scope.comparisonProductsList.length>0){
			for(var i=0;i<$scope.comparisonProductsList.length;i++){
				if($scope.comparisonProductsList[i].productId==product.productId){
					return true;
				}
			}
		}
		return false;
	}
	
	$scope.autoCompleteOptionsProducts = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.Product = [];

				var productResults = _.filter($scope.data.productBeanList, function(val) {
					if(val.productName){
						return val.productName.toLowerCase().includes(term)||val.brandName.toLowerCase().includes(term)||val.createdDate.toLowerCase().includes(term);
					}


				});
				return productResults;
			},
			renderItem : function(item) {

				var result = {
						value : item.productName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 30%'>"
								+ item.productName + "</td>"
								+ "<td style='width: 20%'>" + item.createdDate + "</td>"
								+ "<td style='width: 10%'>" + item.brandName + "</td>"
								+ "<td style='width: 10%'>" + item.supplierName + "</td>"
								+ "<td style='width: 10%'>" + item.varinatsCount + "</td>"
								+ "<td style='width: 10%'>" + item.netPrice + "</td>"
								+ "<td style='width: 10%'>" + item.inventoryCount + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};
				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.CustomerName = $scope.selectedItem;
				
				$scope.showProduct($scope.selectedItem.item);
				
			}
	};
	
	
	
	$scope.sessionValidation();
}];