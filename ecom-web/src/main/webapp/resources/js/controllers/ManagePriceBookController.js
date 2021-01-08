'use strict';

/**
 * ManagePriceBookController
 * @constructor
 */
var ManagePriceBookController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$filter','$route','$timeout','$sce','SessionService','ManagePriceBookControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$filter,$route,$timeout,$sce,SessionService,ManagePriceBookControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.priceBookSuccess = false;
	$scope.priceBookError = false;
	$scope.priceBookBean = {};
	$scope.productVariantBean = {};
	$scope.productList = [];
	$scope.chanageForAll = {};
	$scope.gui = {};
	$scope.gui.shwoProductTag = false;
	$scope.validOn = {
			value: 'Yes'
	      };
	 $scope.name = 'World';
    $scope.cars = [{id:1, name: 'Audi'}, {id:2, name: 'BMW'}, {id:1, name: 'Honda'}];
    $scope.selectedCar = [];

    $scope.fruits = [{id: 1, name: 'Apple'}, {id: 2, name: 'Orange'},{id: 3, name: 'Banana'}];
    $scope.selectedFruit = null;
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ManagePriceBookControllerPreLoad.loadControllerData();
			$scope.fetchData();
			
		}
	};
	
	$scope.fetchData = function() {
	
		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
		}
		else if($scope.data == 'SYSTEMBUSY' || $scope.data == 'System is busy while handling request, Please try later !' ){
			$scope.error = true;
			$scope.errorMessage = $scope.data;
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$scope.errorMessage = 'An exception occured while validating session !';
			$window.location = '/app/#/login';

		}
		else{
			if($scope.data!=null){
				
				if($scope.data.outletBeans!=null){
					$scope.outletsList = $scope.data.outletBeans;
				}
				if($scope.data.showProductTag!=null){
					if($scope.data.showProductTag=='true'){
						$scope.gui.shwoProductTag = true;
					}else{
						$scope.gui.shwoProductTag = false;
					}
				}
				if($scope.data.customerGroupBeansList!=null){
					$scope.customerGroupList = $scope.data.customerGroupBeansList;
				}
				if($scope.data.priceBookBean!=null){
					$scope.priceBookBean = angular.copy($scope.data.priceBookBean);
					if($scope.priceBookBean.isValidOnStore!='true'){
						$scope.validOn.value = "No";
					}
					if($scope.priceBookBean.flatSale=='true'){
						$scope.gui.flatSale = true;
					}else{
						$scope.gui.flatSale = false;
					}
					if($scope.priceBookBean.active=='true'){
						$scope.gui.activePriceBook = true;
					}else{
						$scope.gui.activePriceBook = false;
					}
				}
				if($scope.data.productVariantBeansList!=null){
					$scope.productVariantBeansList = $scope.data.productVariantBeansList;
				}
				if($scope.data.priceBookProductVariantBeansList!=null){
					$scope.productList = $scope.data.priceBookProductVariantBeansList;
					$scope.setRetailPrice();
				}
				if($scope.data.productBeansList != null){
					$scope.productBeansList = $scope.data.productBeansList;
				}
				if($scope.data.productTagBeanList != null){
					$scope.productTagBeanList = $scope.data.productTagBeanList;
				}
				if($scope.data.tagBeanList != null){
					$scope.tagBeanList = $scope.data.tagBeanList;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	
	$scope.toggle = function(){
		
		if($filter('date')(new Date(), 'yyyy-MM-dd')>$filter('date')($scope.data.priceBookBean.validFrom, 'yyyy-MM-dd')){
			$scope.alertMessage = 'You can not update Flat sale info as Valid From Date is before today Date.';
			$('#alertBox').modal('show');
			if($scope.data.priceBookBean.flatSale=='true'){
				$scope.gui.flatSale = true;
			}else{
				$scope.gui.flatSale = false;
			}
			return;
		}
		
	};
	 
	$("#startDate").on("change", function(event){
		var innerText = document.getElementById("startDate1").value;
		$scope.priceBookBean.validFrom = innerText;
		$scope.$apply();
		
	});
	
	$("#endDate").on("change", function(event){
		var innerText = document.getElementById("endDate1").value;
		$scope.priceBookBean.validTo = innerText;
		$scope.$apply();
		
	});
	
	$scope.updatePriceBook = function(){
		
		$scope.priceBookSuccess = false;
		$scope.priceBookError = false;
		
		if( $filter('date')(new Date(), 'yyyy-MM-dd')>$filter('date')($scope.data.priceBookBean.validFrom, 'yyyy-MM-dd')
				&& $filter('date')($scope.data.priceBookBean.validFrom, 'yyyy-MM-dd')!=$filter('date')($scope.priceBookBean.validFrom, 'yyyy-MM-dd')){
			$scope.alertMessage = 'You can not update Valid From date as its before today date.';
			$('#alertBox').modal('show');
			var validFrom = $scope.data.priceBookBean.validFrom;
			$scope.priceBookBean.validFrom = validFrom;
			return;
		}
		
		if($filter('date')($scope.priceBookBean.validFrom, 'yyyy-MM-dd')> $filter('date')($scope.priceBookBean.validTo, 'yyyy-MM-dd')){
			$scope.alertMessage = 'From Date should be before Valid To Date.';
			$('#alertBox').modal('show');
			var validTo = $scope.data.priceBookBean.validTo;
			$scope.priceBookBean.validTo = validTo;
			var validFrom = $scope.data.priceBookBean.validFrom;
			$scope.priceBookBean.validFrom = validFrom;
			return;
		}
		if($filter('date')($scope.priceBookBean.validTo, 'yyyy-MM-dd')< $filter('date')(new Date(), 'yyyy-MM-dd')){
			$scope.alertMessage = 'Valid To Date should be today or future Date.';
			$('#alertBox').modal('show');
			var validTo = $scope.data.priceBookBean.validTo;
			$scope.priceBookBean.validTo = validTo;
			return;
		}
		if($filter('date')(new Date(), 'yyyy-MM-dd')>$filter('date')($scope.data.priceBookBean.validFrom, 'yyyy-MM-dd')
				&& $scope.priceBookBean.outletId!=$scope.data.priceBookBean.outletId){
			$scope.alertMessage = 'You can not update outlet as Valid From Date is before today Date.';
			$('#alertBox').modal('show');
			var outletId = $scope.data.priceBookBean.outletId;
			$scope.priceBookBean.outletId = outletId;
			return;
		}
		
		if($scope.validOn.value=="Yes"){
			$scope.priceBookBean.isValidOnStore = "true";
			$scope.priceBookBean.isValidOnEcom = "false"; 
		}else{
			$scope.priceBookBean.isValidOnStore = "false";
			$scope.priceBookBean.isValidOnEcom = "true";
		}
		if($scope.gui.flatSale){
			$scope.priceBookBean.flatSale = "true";
		}else{
			$scope.priceBookBean.flatSale = "false";
		}
		if($scope.gui.activePriceBook){
			$scope.priceBookBean.active = "true";
			if($scope.gui.flatSale){
				var discount = parseFloat($scope.priceBookBean.flatDiscount);
				if(isNaN(discount)){
					$scope.alertMessage = 'Discount not in valid format. Valid formats e.g (1.00,1)';
					$('#alertBox').modal('show');
					return;
				}
				/*if(discount<1){
					$scope.alertMessage = 'Discount should not be less than 1%';
					$('#alertBox').modal('show');
					return;
				}*/
			}
			
			
		}else{
			$scope.priceBookBean.active = "false";
		}
		
		$scope.loading = true;
		$http.post('managePriceBook/updatePriceBook/'+$scope._s_tk_com, $scope.priceBookBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.loading = true;
				$scope.priceBookSuccess = true;
				$scope.priceBookSuccessMessage = Response.data;
				$timeout(function(){
					$scope.priceBookSuccess = false;
					$scope.loading = false;
					$scope.priceBookBean = {};
					$window.location = Response.layOutPath;
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY'
				|| $scope.responseStatus == 'ADDRESTRICATION') {
					$scope.priceBookError = true;
					$scope.priceBookErrorMessage = Response.data;
					if($scope.responseStatus == 'INVALIDSESSION')
						$window.location = Response.layOutPath;
				}else {
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.priceBookError = true;
			$scope.priceBookErrorMessage = $scope.systemBusy;
		});
		
	};
	
	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.productVariantsBeans = [];

				var customerResults = _.filter($scope.productBeansList, function(val) {
					return val.variantAttributeName.toLowerCase().includes(term) ;

				});
				return customerResults;
			},
			renderItem : function(item) {

				var result = {
						value : item.variantAttributeName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
								+ item.variantAttributeName + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};

				return result;
			},
			itemSelected : function(item) {

				$scope.productVariantBean = item.item;
				//	$scope.airportName = [];

			}
	};
	
	$scope.autoCompleteTagOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
			
				var tagResults = _.filter($scope.tagBeanList, function(val) {
					return val.tagName.toLowerCase().includes(term) ;

				});
				
				return tagResults;
			},
			renderItem : function(item) {

				var result = {
						value : item.tagName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
								+ item.tagName + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};
				

				return result;
			},
			itemSelected : function(item) {

				$scope.productTagBean = item.item;
				$scope.isTagSelected = true;
					//

			}
	};
	
	$scope.addProductByTag = function(){
		if($scope.isTagSelected){
			//alert('tag selected');
			for(var j=0;j<$scope.productTagBeanList.length;j++){
				if($scope.productTagBeanList[j].tagId==$scope.productTagBean.tagId){
					for(var i=0;i<$scope.productBeansList.length;i++){
						$scope.productVariantBean = {};
						if($scope.productBeansList[i].productUuid==$scope.productTagBeanList[j].productUuid){
							$scope.productVariantBean = $scope.productBeansList[i];
							$scope.addProductByDiscount();
						}
					}
					/*if(!isProdcutHasVariants){
						for(var i=0;i<$scope.productBeansList.length;i++){
							$scope.productVariantBean = {};
							if($scope.productBeansList[i].productUuid==$scope.productTagBeanList[j].productUuid){
								$scope.productVariantBean = $scope.productBeansList[i];
								$scope.addProduct();
								break;

							}
						}
					}*/
				}
			}
		}else{
			//$scope.addProducts();
		}
		$scope.productTagBean = [];
		$scope.airportName1 = [];
		
	};
	
	$scope.addProduct = function(){
		if($scope.productVariantBean.isProduct.toString() == "true"){
			$scope.temp = {};
			if(!checkproductExistinProductList($scope.productVariantBean.productId)){
				$scope.temp = angular.copy($scope.productVariantBean);
				var supplyPrice = parseFloat($scope.temp.supplyPriceExclTax);
				var markUp = parseFloat($scope.temp.markupPrct);
				var result = supplyPrice*(markUp/100)+supplyPrice;
				$scope.temp.retailPrice = (result.toFixed(2)).toString();
				$scope.temp.discount = "0.00";
				$scope.productList.push($scope.temp);
				$scope.temp = {};
			}
			

		}else{
			for(var i=0;i<$scope.productVariantBeansList.length;i++){
				if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
					$scope.temp = {};
					if(!checkproductVariantExistinProductList($scope.productVariantBeansList[i].productVariantId)){
						$scope.temp = angular.copy($scope.productVariantBeansList[i]);
						var supplyPrice = parseFloat($scope.temp.supplyPriceExclTax);
						var markUp = parseFloat($scope.temp.markupPrct);
						var result = supplyPrice*(markUp/100)+supplyPrice;
						$scope.temp.retailPrice = (result.toFixed(2)).toString();
						$scope.temp.discount = "0.00";
						$scope.productList.push($scope.temp);
						$scope.temp = {};
					}
				}
			}
		}
		$scope.airportName = [];
		$scope.productVariantBean = {};
	};
	
	$scope.addProductByDiscount = function(){
		if($scope.productVariantBean.isProduct.toString() == "true"){
			$scope.temp = {};
			if(!checkproductExistinProductList($scope.productVariantBean.productId)){
				$scope.temp = angular.copy($scope.productVariantBean);
				var supplyPrice = parseFloat($scope.temp.supplyPriceExclTax);
				var markUp = parseFloat($scope.temp.markupPrct);
				var discount = parseFloat($scope.productTagBean.discount);
				if(!isNaN(discount)){
					$scope.temp.discount = discount.toString();
					var result = supplyPrice*(markUp/100)+supplyPrice;
					result = result-result*(discount/100);
					$scope.temp.retailPrice = (result.toFixed(2)).toString();
				}else{
					var result = supplyPrice*(markUp/100)+supplyPrice;
					$scope.temp.retailPrice = (result.toFixed(2)).toString();
					$scope.temp.discount = "0.00";
					
				}
				$scope.productList.push($scope.temp);
				$scope.temp = {};
			}
			

		}else{
			for(var i=0;i<$scope.productVariantBeansList.length;i++){
				if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
					$scope.temp = {};
					if(!checkproductVariantExistinProductList($scope.productVariantBeansList[i].productVariantId)){
						$scope.temp = angular.copy($scope.productVariantBeansList[i]);
						var supplyPrice = parseFloat($scope.temp.supplyPriceExclTax);
						var markUp = parseFloat($scope.temp.markupPrct);
						/*var result = supplyPrice*(markUp/100)+supplyPrice;
						$scope.temp.retailPrice = (result.toFixed(2)).toString();
						$scope.temp.discount = "0.00";
						$scope.productList.push($scope.temp);
						$scope.temp = {};*/
						
						
						
						//$scope.temp = angular.copy($scope.productVariantBean);
						//var supplyPrice = parseFloat($scope.temp.supplyPriceExclTax);
						//var markUp = parseFloat($scope.temp.markupPrct);
						var discount = parseFloat($scope.productTagBean.discount);
						if(!isNaN(discount)){
							$scope.temp.discount = discount.toString();
							var result = supplyPrice*(markUp/100)+supplyPrice;
							result = result-result*(discount/100);
							$scope.temp.retailPrice = (result.toFixed(2)).toString();
						}else{
							var result = supplyPrice*(markUp/100)+supplyPrice;
							$scope.temp.retailPrice = (result.toFixed(2)).toString();
							$scope.temp.discount = "0.00";
							
						}
						$scope.productList.push($scope.temp);
						$scope.temp = {};
					}
				}
			}
		}
		$scope.airportName = [];
		$scope.productVariantBean = {};
	};
	
	$scope.deleteProdcutFromList = function(product){
		$scope.deleteProdcut = product;
		$scope.alertMessage = '';
		$scope.alertMessage = 'Are you sure, you want to delete selective product/variant?';
		$('#alertBox1').modal('show');
		
		
		
		
	};
	
	$scope.deleteOutetFromListConfirmationModal = function(outlet){
		$scope.deleteOutlet = outlet;
		$scope.alertMessage = '';
		$scope.alertMessage = 'Are you sure, you want to delete Outlet:'+outlet.outletName+' from current bricebook?';
		$('#alertBox2').modal('show');
		
		
		
		
	};
	
	$scope.deleteOutletFromPriceBook = function(){
		$scope.priceBookSuccess = false;
		$scope.priceBookError = false;
		var newOutletGroup = '';
		var outletGroups = $scope.priceBookBean.outeletsGroup;
		var tempoutletGroups = outletGroups.split(',');
		for(var i=0;i<tempoutletGroups.length;i++){
			if(tempoutletGroups[i] != $scope.deleteOutlet.outletId){
				if(newOutletGroup== ''){
					newOutletGroup = tempoutletGroups[i];
				}else{
					newOutletGroup = newOutletGroup +','+tempoutletGroups[i];
				}
			}
		}
		if(newOutletGroup==''){
			$scope.alertMessage = '';
			$scope.alertMessage = 'You can not delete Outlet as its the only outlet in current pricebook.';
//			$('#alertBox2').modal('show');
		}else{
			$scope.priceBookBean.outeletsGroup = newOutletGroup;
			$('#alertBox2').modal('hide');
			$scope.updatePriceBook();
		}
	
	};
	
	$scope.removeOutletFromDisplayOutletList = function(outlet){
		for(var i=0;i<$scope.displayOutletsList.length;i++){
			if($scope.displayOutletsList[i].outletId==outlet.outletId){
				$scope.displayOutletsList.splice(i,1);
				break;
			}
		}
	};
	
	$scope.markInactiveSelectiveProduct = function(){
		$scope.priceBookSuccess = false;
		$scope.priceBookError = false;
		if($scope.deleteProdcut.priceBookId==null||$scope.deleteProdcut.priceBookDetailId==null){
			if($scope.deleteProdcut.isProduct.toString() == "true"){
				for(var i=0;i<$scope.productList.length;i++){
					if($scope.productList[i].productId==$scope.deleteProdcut.productId){
						$scope.productList.splice(i,1);
						$('#alertBox1').modal('hide');
						return;
					}
				}
			}else{
				for(var i=0;i<$scope.productList.length;i++){
					if($scope.productList[i].productVariantId==$scope.deleteProdcut.productVariantId){
						$scope.productList.splice(i,1);
						$('#alertBox1').modal('hide');
						return;
					}
				}
			}
		}else{
			$scope.loading = true;
			$http.post('managePriceBook/deleteProductOrVariantInPriceBookDetail/'+$scope._s_tk_com, $scope.deleteProdcut)
			.success(function(Response) {
				$scope.loading = false;
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.loading = true;
					$('#alertBox1').modal('hide');
					$scope.priceBookSuccess = true;
					$scope.priceBookSuccessMessage = Response.data;
					
					$timeout(function(){
						$scope.priceBookSuccess = false;
						$scope.loading = false;
						$scope.deleteProdcut = {};
						$route.reload();
						//$window.location = Response.layOutPath;
					    }, 1000);
					
				}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
					$scope.priceBookError = true;
					$scope.priceBookErrorMessage = Response.data;
					$window.location = Response.layOutPath;
				}else {
					$scope.priceBookError = true;
					$scope.priceBookErrorMessage = Response.data;
				}
			}).error(function() {
				$scope.loading = false;
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = $scope.systemBusy;
			});
		}
	};
	
	
	function checkproductVariantExistinProductList(variantId){
		if($scope.productList.length>0){
			for(var i=0;i<$scope.productList.length;i++){
				if($scope.productList[i].productVariantId==variantId){
					return true;
				}
			}
		}
		return false;
	}
	
	function checkproductExistinProductList(productId){
		if($scope.productList.length>0){
			for(var i=0;i<$scope.productList.length;i++){
				if($scope.productList[i].productId==productId){
					return true;
				}
			}
		}
		return false;
	}
	
	$scope.changeMarkUpForAll = function(){
		var markUp = parseFloat($scope.chanageForAll.markupPrct);
		if(!isNaN(markUp)){
			for(var i=0;i<$scope.productList.length;i++){
				var supplyPrice = parseFloat($scope.productList[i].supplyPriceExclTax);
				$scope.productList[i].markupPrct = markUp.toString();
				var result = supplyPrice*(markUp/100)+supplyPrice;
				var discount = $scope.productList[i].discount;
				if(!isNaN(discount)){
					result = result-result*(discount/100);
				}
				$scope.productList[i].retailPrice = (result.toFixed(2)).toString();
				$scope.productList[i].indivisualUpdate = true;
			}
		}
	};
	
	$scope.changeDiscountForAll = function(){
		var discount = parseFloat($scope.chanageForAll.discount);
		if(!isNaN(discount)){
			for(var i=0;i<$scope.productList.length;i++){
				var supplyPrice = parseFloat($scope.productList[i].supplyPriceExclTax);
				var markUp = parseFloat($scope.productList[i].markupPrct);
				if(!isNaN(markUp)){
					$scope.productList[i].discount = discount.toString();
					var result = supplyPrice*(markUp/100)+supplyPrice;
					result = result-result*(discount/100);
					$scope.productList[i].retailPrice = (result.toFixed(2)).toString();
					$scope.productList[i].indivisualUpdate = true;
				}

			}
		}
	};
	
	$scope.changeMarkUpAndDiscount = function(product){
		for(var i=0;i<$scope.productList.length;i++){
			if(product.variantAttributeName == $scope.productList[i].variantAttributeName || product.productId == $scope.productList[i].productId){
				var markUp = parseFloat(product.markupPrct);
				if(!isNaN(markUp)){
					var supplyPrice = parseFloat($scope.productList[i].supplyPriceExclTax);
					$scope.productList[i].markupPrct = markUp.toString();
					var result = supplyPrice*(markUp/100)+supplyPrice;
					var discount = product.discount;
					if(!isNaN(discount)){
						result = result-result*(discount/100);
						$scope.productList[i].discount = discount;
					}
					$scope.productList[i].retailPrice = (result.toFixed(2)).toString();
					$scope.productList[i].indivisualUpdate = true;
				}
				//return;
			}
		}
	};
	
	$scope.setRetailPrice = function(){
		for(var i=0;i<$scope.productList.length;i++){
			
				var markUp = parseFloat($scope.productList[i].markupPrct);
				if(!isNaN(markUp)){
					var supplyPrice = parseFloat($scope.productList[i].supplyPriceExclTax);
					$scope.productList[i].markupPrct = markUp.toString();
					var result = supplyPrice*(markUp/100)+supplyPrice;
					var discount = $scope.productList[i].discount;
					if(!isNaN(discount)){
						result = result-result*(discount/100);
					}
					$scope.productList[i].retailPrice = (result.toFixed(2)).toString();
					//$scope.productList[i].indivisualUpdate = true;
				}
			
		}
	};
	
	
	$scope.manageProductsInPriceBook = function(){
		$scope.priceBookSuccess = false;
		$scope.priceBookError = false;
		$scope.loading = true;
		$http.post('managePriceBook/manageProductsInPriceBook/'+$scope._s_tk_com+'/'+$scope.priceBookBean.priceBookId, $scope.productList)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.loading = true;
				$scope.priceBookSuccess = true;
				$scope.priceBookSuccessMessage = Response.data;
				$timeout(function(){
					$scope.priceBookSuccess = false;
					$scope.loading = false;
					$scope.priceBookBean = {};
					$window.location = Response.layOutPath;
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.priceBookError = true;
				$scope.priceBookErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.priceBookError = true;
			$scope.priceBookErrorMessage = $scope.systemBusy;
		});
	};
	
	
	
	
	
	$scope.sessionValidation();
	
}];