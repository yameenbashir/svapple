'use strict';

/**
 * ManageProductController
 * @constructor
 */
var ManageProductController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','$route','$sce','SessionService','ManageProductControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,$route,$sce,SessionService,ManageProductControllerPreLoad) {


	$scope.tagBean = {};
	$scope.gui = {};
	$scope.varientAttributeBean ={};
	$scope.compositProduct = {};
	$scope.productTypeBean = {};
	$scope.supplierBean = {};
	$scope.brandBean = {};
	$scope.tempproductBean = {};
	$scope.productPriceHistoryBean = {};
	$scope.compositProduct.compositQunatity ="1";
	$scope.tagList = [];
	$scope.tagDisplayList = [];
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.productSuccess = false;
	$scope.productError = false;
	$scope.loading = false;
	$scope.loadingVarient = false;
	$scope.productAddError = false;
	$scope.gui.productCanBeSold  = true;
	$scope.gui.trackingProduct = true;
	$scope.gui.varientProducts = false;
	$scope.gui.standardProduct = true;
	$scope.showNewVarientAttriubuteModal = false;
	$scope.checkDuplicate = false;
	$scope.hideAddVariantAttributesPanel = false;
	$scope.gui.shwoProductTag = false;
	$scope.RateArea = true;
	$scope.gui.showDutyCalculator = false;
	$scope.showHideRateText = "Hide Assessment";
	$scope.varientWarningMessage = "Please select Varient Attribute";
	$scope.verientsOutletList = [];
	$scope.productVariantsCollection = [];
	$scope.productVariantValuesCollection = [];
	$scope.productVariantValuesCollectionOne = [];
	$scope.productVariantValuesCollectionTwo = [];
	$scope.productVariantValuesCollectionThree = [];
	$scope.compositProductCollection = [];

	$scope.valueCollectionOne = [];
	$scope.valueCollectionTwo = [];
	$scope.valueCollectionThree = [];
	$scope.productVariantValuesCollectionOldOne = [];
	$scope.productVariantValuesCollectionOldTwo = [];
	$scope.productVariantValuesCollectionOldThree = [];

	$scope.myObj = {};
	$scope.myObj.id = "1";
	$scope.myObj.value = "";
	$scope.myObj.varientAttributeId = "-1";
	$scope.productVariantsCollection.push($scope.myObj);
	
	$scope.addCompositProductDynamically = function(){
		$scope.compositProductTemp = {};

		var productInfo = $scope.compositProduct.productId.split(":");
		var isExist = false;
		for(var i=0;i<$scope.compositProductCollection.length;i++){
			if(productInfo[2]==$scope.compositProductCollection[i].productVariantName){
				isExist = true;
			}
		}
		if(!isExist){
			$scope.compositProductTemp.id = ($scope.compositProductCollection.length+1).toString();
			$scope.compositProductTemp.productId = productInfo[0];
			$scope.compositProductTemp.productName = productInfo[1];
			$scope.compositProductTemp.productVariantName = productInfo[2];
			$scope.compositProductTemp.productVariantId = productInfo[3];
			$scope.compositProductTemp.uuid = generateUUID();
			$scope.compositProductTemp.compositQunatity = $scope.compositProduct.compositQunatity;
			$scope.compositProductCollection.push($scope.compositProductTemp);
			$scope.compositProductTemp = {};
			$scope.compositProduct = {};
			$scope.compositProduct.compositQunatity ="1";
			$scope.compositProduct.productId = "0";
		}else{
			alert('Product already added.');
		}
	};
	
	$scope.checkSkuExist = function(){
		$scope.checkDuplicate = true;
		if($scope.productBean.sku!=null && !$scope.productBean.sku==''){
			if($scope.productBarCodeMap[$scope.productBean.sku]==true){
				$scope.barCode = true;
				
			}else{
				$scope.barCode = false;
			}
		}
		$scope.checkDuplicate = false;
	};

	$scope.changeHandler  = function(productBean){
		//$scope.productBean.sku = productBean.productName;
		$scope.productBean.productHandler = productBean.productName;
	};
	$scope.removeFromOne = function(value){
		for(var i=0;i<$scope.productVariantValuesCollectionOne.length;i++){
			if($scope.productVariantValuesCollectionOne[i].value==value){
				$scope.productVariantValuesCollectionOne.splice(i,1);
				for(var j=0;j<$scope.valueCollectionOne.length;j++){
					if($scope.valueCollectionOne[j]==value){
						$scope.valueCollectionOne.splice(j,1);
					}
				}
			}
		}
		if($scope.productVariantValuesCollection.length>1 ||
				($scope.productVariantValuesCollection.length==1 && count_letters($scope.productVariantValuesCollection[0].varientName,'/')>1)){
			$scope.preparcartesianProduct();
		}else{
			for(var k=0;k<$scope.productVariantValuesCollection.length;k++){
				if($scope.productVariantValuesCollection[k].varientName.includes(value)){
					if($scope.productVariantValuesCollection[k].varientName.includes("/")){
						$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace(value+"/", "");
						for(var i=0;i<$scope.productVariantValuesCollection[k].varientsOutletList.length;i++){
							$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace(value+"-", "");
						}
					}else{
						$scope.productVariantValuesCollection.splice(k,1);
						k--;
					}
				}
			}
		}
	};
	
	$scope.removeFromTwo = function(value){
		for(var i=0;i<$scope.productVariantValuesCollectionTwo.length;i++){
			if($scope.productVariantValuesCollectionTwo[i].value==value){
				$scope.productVariantValuesCollectionTwo.splice(i,1);
				for(var j=0;j<$scope.valueCollectionTwo.length;j++){
					if($scope.valueCollectionTwo[j]==value){
						$scope.valueCollectionTwo.splice(j,1);
					}
				}
			}
		}
		if($scope.productVariantValuesCollection.length>1 ||
				($scope.productVariantValuesCollection.length==1 && count_letters($scope.productVariantValuesCollection[0].varientName,'/')>1)){
			$scope.preparcartesianProduct();
		}else{
			for(var k=0;k<$scope.productVariantValuesCollection.length;k++){
				if($scope.productVariantValuesCollection[k].varientName.includes(value)){
					if($scope.productVariantValuesCollection[k].varientName.includes("/")){
						var slash = count_letters($scope.productVariantValuesCollection[k].varientName,'/');
						if(slash==1){
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace(value, "");
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace("/", "");
							for(var i=0;i<$scope.productVariantValuesCollection[k].varientsOutletList.length;i++){
								$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace("-"+value, "");
								//$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace("-", "");
							}
						}else if(slash==2){
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace("/"+value, "");
							for(var i=0;i<$scope.productVariantValuesCollection[k].varientsOutletList.length;i++){
								$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace("-"+value, "");
							}
						}else{
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace(value, "");
						}
					}else{
						$scope.productVariantValuesCollection.splice(k,1);
						k--;
					}
				}
			}
		}
	};
	
	
	
	$scope.removeFromThree = function(value){
		for(var i=0;i<$scope.productVariantValuesCollectionThree.length;i++){
			if($scope.productVariantValuesCollectionThree[i].value==value){
				$scope.productVariantValuesCollectionThree.splice(i,1);
				for(var j=0;j<$scope.valueCollectionThree.length;j++){
					if($scope.valueCollectionThree[j]==value){
						$scope.valueCollectionThree.splice(j,1);
					}
				}
			}
		}
		if($scope.productVariantValuesCollection.length>1 ||
				($scope.productVariantValuesCollection.length==1 && count_letters($scope.productVariantValuesCollection[0].varientName,'/')>1)){
			$scope.preparcartesianProduct();
		}else{
			for(var k=0;k<$scope.productVariantValuesCollection.length;k++){
				if($scope.productVariantValuesCollection[k].varientName.includes(value)){
					if($scope.productVariantValuesCollection[k].varientName.includes("/")){
						var slash = count_letters($scope.productVariantValuesCollection[k].varientName,'/');
						if(slash==1){
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace(value, "");
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace("/", "");
							for(var i=0;i<$scope.productVariantValuesCollection[k].varientsOutletList.length;i++){
								$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace(value, "");
								$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace("-", "");
							}
						}else if(slash==2){
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace("/"+value, "");
							for(var i=0;i<$scope.productVariantValuesCollection[k].varientsOutletList.length;i++){
								$scope.productVariantValuesCollection[k].varientsOutletList[i].sku = $scope.productVariantValuesCollection[k].varientsOutletList[i].sku.replace("-"+value, "");
							}
						}else{
							$scope.productVariantValuesCollection[k].varientName = $scope.productVariantValuesCollection[k].varientName.replace(value, "");
						}
					}else{
						$scope.productVariantValuesCollection.splice(k,1);
						k--;
					}
				}
			}
		}
		
	};
	
	function count_letters(input,input_letter) {
		var counter = 0;
		for (var i = 0; i < input.length; i++) {
		    var index_of_sub = input.indexOf(input_letter, i);
		    if (index_of_sub > -1) {
		        counter++;
		        i = index_of_sub;
		    }
		}
		return counter;
	}
	
	$scope.addTagDynamically = function(tagName){
		
		/*if(!varifyValueExist($scope.tagList,tagName)){
			$scope.tempTag = {};
			$scope.tempTag.value = tagName;
			$scope.tempTag.tagId = ($scope.tagList.length+1).toString();
			$scope.tempTag.productTagId= $scope.tempTag.tagId;
			$scope.tagList.push($scope.tempTag);
			$scope.tempTag = {};
			$scope.tagBean = {};
		}*/
		
	};

	$scope.hidePanel= function(value){
		if(value=='2'){
			if($scope.productVariantValuesCollectionThree.length>0){
				$scope.valueCollectionTwo = angular.copy($scope.valueCollectionThree);
				$scope.productVariantValuesCollectionTwo = angular.copy($scope.productVariantValuesCollectionThree);
			}
			$scope.productVariantValuesCollectionThree = [];
			$scope.valueCollectionThree = [];
		}
		for(var i=0;i<$scope.productVariantsCollection.length;i++){
			if($scope.productVariantsCollection[i].id == value){
				$scope.productVariantsCollection.splice(i,1);
			}
		}
		for(var j=0;j<$scope.productVariantsCollection.length;j++){
			$scope.productVariantsCollection[j].id = j+1;
		}
		if($scope.productVariantValuesCollectionThree.length>0 || $scope.productVariantValuesCollectionTwo.length>0){
			$scope.preparcartesianProduct();
		}
		$scope.verifyVariantsInfoCompleted();
		
	};
	
	$scope.preparcartesianProduct = function(){
		var allArrays = [];
		if($scope.valueCollectionOne.length<1){
			$scope.valueCollectionOne.push(" ");
		}
		if($scope.valueCollectionTwo.length<1){
			$scope.valueCollectionTwo.push(" ");
		}
		if($scope.valueCollectionThree.length<1){
			$scope.valueCollectionThree.push(" ");
		}
		allArrays.push($scope.valueCollectionOne);
		allArrays.push($scope.valueCollectionTwo);
		allArrays.push($scope.valueCollectionThree);
		var combinationsArray=allPossibleCases(allArrays);
		//alert(cominationsArray);
		$scope.verifyVariantsInfoCompleted();
		makeFinalProductVariantValuesCollection(combinationsArray);
		for(var k=0;k<$scope.productVariantsCollection.length;k++){
			$scope.productVariantsCollection[k].value="";
		}
	};

	$scope.addAttributeDynamically = function(){
		var count = $scope.productVariantsCollection.length;
		$scope.tempObj = {};
		$scope.tempObj.value = "";
		$scope.tempObj.varientAttributeId = "-1";
		$scope.tempObj.id = (count+1).toString();
		$scope.productVariantsCollection.push($scope.tempObj);
		$scope.tempObj = {};
		$scope.verifyVariantsInfoCompleted();
	};

	$scope.addVarientValuesDynamically = function(varientObject){
		var valueArray = varientObject.value.split(",");
		if(valueArray!=null && valueArray.length>0){
			var valueEntertained = false;
			for(var i=0;i<valueArray.length;i++){
				if(valueArray[i]!=''){
					
					$scope.tempObject = {};

					if(varientObject.id=="1"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];	
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
							$scope.productVariantValuesCollectionOne.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionOne[0]==" "){
								$scope.valueCollectionOne.splice(0,1);
							}
							$scope.valueCollectionOne.push(valueArray[i]);
						}

					}
					if(varientObject.id=="2"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];	
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
							$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionTwo[0]==" "){
								$scope.valueCollectionTwo.splice(0,1);
							}
							$scope.valueCollectionTwo.push(valueArray[i]);
						}
					}
					if(varientObject.id=="3"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionThree.length+1;
							$scope.productVariantValuesCollectionThree.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionThree[0]==" "){
								$scope.valueCollectionThree.splice(0,1);
							}
							$scope.valueCollectionThree.push(valueArray[i]);
						}
					}

					$scope.tempObject = {};
				}

			}
			if(valueEntertained){
				$scope.preparcartesianProduct();
			}
		}
	};

	function varifyValueExist(varientValuescollection,value){
		$scope.variantError = false;
		for (var index = 0; index <varientValuescollection.length; index++) {
			if((varientValuescollection[index].value).toLowerCase() ==  value.toLowerCase()){
				$scope.variantError = true;
				$scope.variantErrorMessage = "Value "+value+" alreday exist";
				$timeout(function(){
					$scope.variantError = false;
				}, 1000);
				return true;
			}
		}
		return false;
	};
	
	function makeFinalProductVariantValuesCollection(combinationsArray){
		$scope.productVariantValuesCollection = [];
		//var sku = $scope.gui.sku-$scope.productBean.productVariantsBeans.length;
		var sku = $scope.gui.sku;
		for(var i=0;i<$scope.verientsOutletList.length;i++){
			$scope.verientsOutletList[i].supplierCode = $scope.productBean.supplierId;
			if($scope.productBean.supplyPriceExclTax=='' ||  typeof $scope.productBean.supplyPriceExclTax == 'undefined' ){
				$scope.verientsOutletList[i].supplyPriceExclTax = "0";
			}else{
				$scope.verientsOutletList[i].supplyPriceExclTax = $scope.productBean.supplyPriceExclTax;
			}
			if($scope.productBean.markupPrct == '' || typeof $scope.productBean.markupPrct == 'undefined'){
				$scope.verientsOutletList[i].markupPrct = "0";
			}else{
				$scope.verientsOutletList[i].markupPrct = $scope.productBean.markupPrct;
			}
			if($scope.productBean.retailPriceExclTax == ''|| typeof $scope.productBean.retailPriceExclTax == 'undefined'){
				$scope.verientsOutletList[i].retailPriceExclTax = "0";
			}else{
				$scope.verientsOutletList[i].retailPriceExclTax = $scope.productBean.retailPriceExclTax;
			}
			
		}
		$scope.sku = $scope.data.sku+1;
		for(var index=0;index<combinationsArray.length;index++){
			var uuid = generateUUID();
			$scope.temp = {};
		//	$scope.temp.varientName = combinationsArray[index];
			$scope.temp.uUid = uuid;
			var temvariantName = combinationsArray[index].split("/");
			for(var i=0;i<$scope.verientsOutletList.length;i++){
				if(temvariantName[0] !=" "){
					if($scope.productBean.productName){
						$scope.verientsOutletList[i].sku  = $scope.productBean.productName.substr(0,5)+"-"+sku ;
					}else{
						$scope.verientsOutletList[i].sku  = "SKU"+"-"+sku ;
					}
					
					$scope.temp.varientName = temvariantName[0];
				}
				if(temvariantName[1] !=" "){
					if($scope.productBean.productName){
						$scope.verientsOutletList[i].sku  = $scope.productBean.productName.substr(0,5)+"-"+sku ;
					}else{
						$scope.verientsOutletList[i].sku  = "SKU"+"-"+sku ;
					}
					$scope.temp.varientName = temvariantName[0]+'/'+temvariantName[1];
					
				}if(temvariantName[2] !=" "){
					if($scope.productBean.productName){
						$scope.verientsOutletList[i].sku  = $scope.productBean.productName.substr(0,5)+"-"+sku ;
					}else{
						$scope.verientsOutletList[i].sku  = "SKU"+"-"+sku ;
					}
					$scope.temp.varientName = temvariantName[0]+'/'+temvariantName[1]+'/'+temvariantName[2];
				}
				sku = sku+1;
			}
			$scope.temp.varientsOutletList = angular.copy($scope.verientsOutletList);
			$scope.temp.varientId = $scope.productVariantValuesCollection.length+1;
			$scope.productVariantValuesCollection.push($scope.temp);
			$scope.temp = {};
		}
		if($scope.productBean.productVariantsBeans.length > 0){
			
			for(var j=0;j<$scope.productBean.productVariantsBeans.length;j++){
				for(var i=0;i<$scope.productVariantValuesCollection.length;i++){
					if($scope.productVariantValuesCollection[i].varientName==$scope.productBean.productVariantsBeans[j].variantAttributeName){
						$scope.productVariantValuesCollection.splice(i,1)
					}
				}
			}
		}
		if(!$scope.disableAddproductButton){
			$scope.verifyDuplicateBarCodeForAllVariants();
		}

	};
	
	$scope.checkProductVariantSkuExist = function(sku){
		if(sku!=null && !sku==''){
			if($scope.productVariantBarCodeMap[sku]==true){
				$scope.disableAddproductButton = true;
				return true;
				
			}else{
				return false;
			}
		}
	
	};
	
	$scope.checkDuplicateVariantBarCode = function(verientValue,outlet){
		
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){
						if( outlet.sku == '' || typeof outlet.sku == 'undefined'){
							$scope.productVariantValuesCollection[index].varientsOutletList[i].sku = "";
							return;
						}
						if($scope.checkProductVariantSkuExist($scope.productVariantValuesCollection[index].varientsOutletList[i].sku)){
							$scope.productVariantValuesCollection[index].varientsOutletList[i].isDuplicateBarCode = "true";
						}else{
							$scope.productVariantValuesCollection[index].varientsOutletList[i].isDuplicateBarCode = "false";
						}
					}
				}
			}
		}
		$scope.verifyDuplicateBarCodeForAllVariants();
		
	};
	
	$scope.verifyDuplicateBarCodeForAllVariants = function(){
		var noDuplicateFound = false;
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
				
				if($scope.checkProductVariantSkuExist($scope.productVariantValuesCollection[index].varientsOutletList[i].sku)){
					$scope.productVariantValuesCollection[index].varientsOutletList[i].isDuplicateBarCode = "true";
					noDuplicateFound = true;
				}
			}
		}
		$scope.disableAddproductButton = noDuplicateFound;
		
	};

	$scope.deleteProductVerientValue= function(verientValue,outlet){
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){
					//	$scope.productVariantValuesCollection[index].varientsOutletList[i].splice(i,1);
						delete($scope.productVariantValuesCollection[index].varientsOutletList[i]);
					}
				}
			}
		}
		/*var tempList = $scope.productVariantValuesCollection;
		$scope.productVariantValuesCollection = [];
		$scope.productVariantValuesCollection = tempList;*/
	};

	$scope.evaluateVariantRetailPrice = function(verientValue,outlet){
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){
						var supplyPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].supplyPriceExclTax);
						var markUp = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct);
						if(isNaN(markUp)){
							markUp = 0.0;
							$scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct = "0";
						}
						var result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
						$scope.productVariantValuesCollection[index].varientsOutletList[i].retailPriceExclTax = result.toString();
					}
				}
			}
		}
	};
	
	$scope.adjustMarkUpByChangeOfVariantRetailPrice = function(verientValue,outlet){
		
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){
						
						var retailPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].retailPriceExclTax);
						if(!isNaN(retailPrice)){
							var supplyPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].supplyPriceExclTax);
							var markUp = round(parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct),5);
							if(isNaN(markUp)){
								markUp = 0.0;
							}
							markUp = round(parseFloat((retailPrice-supplyPrice)*100/supplyPrice),2);
							$scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct = markUp.toString();
							
						}
					}
				}
			}
		}
	};
	
	$scope.updateAllProductVariantsPriceByChangeOfProductPrice = function(){

		if($scope.productVariantValuesCollection!=null && $scope.productVariantValuesCollection.length>0){
			for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					$scope.productVariantValuesCollection[index].varientsOutletList[i].supplyPriceExclTax = $scope.productBean.supplyPriceExclTax;
					$scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct =$scope.productBean.markupPrct;
					$scope.productVariantValuesCollection[index].varientsOutletList[i].retailPriceExclTax = $scope.productBean.retailPriceExclTax;
				}
			}
		}
		$scope.updateAllProductVariantsPriceByChangeOfProductPrice1();
	};

	function allPossibleCases(arr) {
		if (arr.length === 0) {
			return [];
		} 
		else if (arr.length ===1){
			return arr[0];
		}
		else {
			var result = [];
			var allCasesOfRest = allPossibleCases(arr.slice(1));  // recur with the rest of array
			for (var c in allCasesOfRest) {
				for (var i = 0; i < arr[0].length; i++) {
					result.push(arr[0][i] +'/'+ allCasesOfRest[c]);
				}
			}
			return result;
		}

	};

	function generateUUID() {
	    var d = new Date().getTime();
	    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = (d + Math.random()*16)%16 | 0;
	        d = Math.floor(d/16);
	        return (c=='x' ? r : (r&0x3|0x8)).toString(16);
	    });
	    return uuid;
	};

	$scope.sesssionValidation = function(){

		if(SessionService.validate()){
			
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			
			//$scope.fetchAllTags();
			$scope.fetchData();
			/*$scope.getAllSuppliers();
			$scope.fetchAllProductTypes();
			$scope.fetchAllBrands();
			$scope.fetchOutlets();
			$scope.fetchAllVarientAttributes();
			$scope.fetchAllProducts();*/

		}
	};
	$scope.fetchData = function(){
		$scope.data = ManageProductControllerPreLoad.loadControllerData();

		if($scope.data == 'NORECORDFOUND'){

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

				if($scope.data.tagBeanList!=null){
					$scope.tagList = $scope.data.tagBeanList;
				}
				if($scope.data.productConfigurationBean!=null){
					$scope.productConfigurationBean = $scope.data.productConfigurationBean;
				}
				if($scope.data.showProductTag!=null){
					if($scope.data.showProductTag=='true'){
						$scope.gui.shwoProductTag = true;
					}else{
						$scope.gui.shwoProductTag = false;
					}
				}
				if($scope.data.showDutyCalculator!=null){
					if($scope.data.showDutyCalculator=='true'){
						$scope.gui.showDutyCalculator = true;
					}else{
						$scope.gui.showDutyCalculator = false;
					}
				}
				if($scope.data.supplierBeans!=null){
					$scope.supplierList = $scope.data.supplierBeans;
				}
				if($scope.data.productTypeBeanList!=null){
					$scope.productTypeList = $scope.data.productTypeBeanList;
				}
				if($scope.data.brandBeanList!=null){
					$scope.brandList = $scope.data.brandBeanList;
				}
				if($scope.data.outletBeans!=null){
					if($scope.data.productBean.outletBeans!=null){
						$scope.outletList = $scope.data.productBean.outletBeans;
					}else{
						$scope.outletList = $scope.data.outletBeans;
					}
					for(var i=0;i<$scope.outletList.length;i++){
						$scope.outletList[i].taxAmount = 0.0;
						$scope.outletList[i].retailPrice = 0.0;
						}
					
					if($scope.data.productTemplateForAllOutlets=="true"){
						$scope.verientsOutletList = $scope.data.outletBeans;
					}else{
						for(var i=0;i<$scope.data.outletBeans.length;i++){
							if($scope.data.outletBeans[i].isHeadOffice=="true"){
								
								$scope.verientsOutletList.push($scope.data.outletBeans[i]);
								break;
							}
						}
						
					}
				}
				
				if($scope.data.variantAttributeBeanList!=null){
					$scope.varientAttributesList = null;
					$scope.varientAttributesList = $scope.data.variantAttributeBeanList;
				}
				
				
				if($scope.data.productVariantSku!=null){
					$scope.gui.sku = $scope.data.productVariantSku;
				}
				if($scope.data.productBarCodeMap!=null){
					$scope.productBarCodeMap = $scope.data.productBarCodeMap;
				}
				if($scope.data.productVariantBarCodeMap!=null){
					$scope.productVariantBarCodeMap = $scope.data.productVariantBarCodeMap;
				}
				
				/*if($scope.data.productVariantBeanList!=null){
					$scope.productVariantBeanList = null;
					$scope.productVariantBeanList = $scope.data.productVariantBeanList;
				}*/
				if($scope.data.productBean!=null){
					$scope.productBean = {};
					$scope.tempproductBean = {};
					$scope.productBean = $scope.data.productBean;
					$scope.tempproductBean = angular.copy($scope.data.productBean);
					$scope.compositProductCollection = angular.copy($scope.productBean.compositProductCollection);
					if($scope.productBean.productVariantsBeans.length>0){
						//$scope.hideAddVariantAttributesPanel = true;
						var count = count_letters($scope.productBean.productVariantsBeans[0].variantAttributeName,'/');
						if(count==1){
							$scope.addAttributeDynamically();
							$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
							$scope.productVariantsCollection[1].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId2; 
							for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
								var attributeList = $scope.productBean.productVariantsBeans[i].variantAttributeName.split('/');
								if(!varifyValueExist($scope.productVariantValuesCollectionOne,attributeList[0])){
									$scope.tempObject = {};
									$scope.tempObject.value = attributeList[0];
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
									$scope.productVariantValuesCollectionOne.push($scope.tempObject);
									$scope.valueCollectionOne.push($scope.tempObject.value);
									$scope.tempObject = {};
								}
								if(!varifyValueExist($scope.productVariantValuesCollectionTwo,attributeList[1])){
									$scope.tempObject.value = attributeList[1];
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
									$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
									$scope.valueCollectionTwo.push($scope.tempObject.value);
									$scope.tempObject = {};
								}
							}


						}else if(count==2){
							$scope.addAttributeDynamically();
							$scope.addAttributeDynamically();
							$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
							$scope.productVariantsCollection[1].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId2;
							$scope.productVariantsCollection[2].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId3;
							for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
								var attributeList = $scope.productBean.productVariantsBeans[i].variantAttributeName.split('/');
								if(!varifyValueExist($scope.productVariantValuesCollectionOne,attributeList[0])){
									$scope.tempObject = {};
									$scope.tempObject.value = attributeList[0];
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
									$scope.productVariantValuesCollectionOne.push($scope.tempObject);
									$scope.valueCollectionOne.push($scope.tempObject.value);
									$scope.tempObject = {};
								}
								if(!varifyValueExist($scope.productVariantValuesCollectionTwo,attributeList[1])){
									$scope.tempObject.value = attributeList[1];
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
									$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
									$scope.valueCollectionTwo.push($scope.tempObject.value);
									$scope.tempObject = {};
								}
								if(!varifyValueExist($scope.productVariantValuesCollectionThree,attributeList[2])){
									$scope.tempObject.value = attributeList[2];
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionThree.length+1;
									$scope.productVariantValuesCollectionThree.push($scope.tempObject);
									$scope.valueCollectionThree.push($scope.tempObject.value);
									$scope.tempObject = {};
								}
							}
						}else{
							$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
							for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
								
								$scope.tempObject = {};
								$scope.tempObject.value = $scope.productBean.productVariantsBeans[i].variantAttributeName;
								$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
								$scope.productVariantValuesCollectionOne.push($scope.tempObject);
								$scope.valueCollectionOne.push($scope.tempObject.value);
								$scope.tempObject = {};
							}
							
						}
					}
					$scope.productVariantValuesCollectionOldOne = angular.copy($scope.productVariantValuesCollectionOne);
					$scope.productVariantValuesCollectionOldTwo = angular.copy($scope.productVariantValuesCollectionTwo);
					$scope.productVariantValuesCollectionOldThree = angular.copy($scope.productVariantValuesCollectionThree);
					
					if($scope.productBean.productCanBeSold=="true"){
						$scope.gui.productCanBeSold = true;
					}else{

						$scope.gui.productCanBeSold = false;
					}
					if($scope.productBean.trackingProduct=="true"){
						$scope.gui.trackingProduct = true;
					}else{

						$scope.gui.trackingProduct = false;
					}
					if($scope.productBean.varientProducts=="true"){
						$scope.gui.varientProducts = true;
					}else{

						$scope.gui.varientProducts = false;
					}
					if($scope.productBean.standardProduct=="true"){
						$scope.gui.standardProduct = true;
					}else{

						$scope.gui.standardProduct = false;
					}
					if($scope.data.productBarCodeMap!=null){
						$scope.productBarCodeMap = $scope.data.productBarCodeMap;
					}
					if($scope.data.productBean.productTagBeanList!=null){
						$scope.tagDisplayList = $scope.data.productBean.productTagBeanList;
					}

					$scope.evaluateRetailPrice(false);
				}
			}

		}

		$rootScope.manageProductLoadedFully = false;
		$rootScope.globalPageLoader = false;
		 $scope.setDefaultDutyCalcuation();

	};
	$scope.showHidePriceText = "Hide taxes by outlet";
	$scope.showHidePriceFlag = false; 

	//$scope.allowSale = true; 
	$scope.trackInventory = true;
	$scope.mutltiProduct = function(){
		$scope.gui.standardProduct = false;
		$scope.disableAddproductButton = false;
		$scope.sameProductVariantAttribute = false;

	};
	$scope.singleProduct = function(){
		$scope.gui.standardProduct = true;
		$scope.gui.varientProducts = true;
		$scope.verifyVariantsInfoCompleted();

	};
	$scope.trackingInventory = function(){
		if($scope.gui.trackingProduct){
			$scope.gui.trackingProduct = false;

		}else{
			$scope.gui.trackingProduct = true;
		}

	};
	//$scope.variantProduct = false; 
	$scope.productVariants = function(){
		if($scope.gui.varientProducts){
			$scope.gui.varientProducts = false;
			$scope.gui.trackingProduct = true; 
			$scope.disableAddproductButton = false;
			$scope.sameProductVariantAttribute = false;
		}else{
			$scope.gui.varientProducts = true;
			$scope.gui.trackingProduct = false; 
			$scope.verifyVariantsInfoCompleted();
		}

	};
	$scope.showHidePrice = function(){
		if($scope.showHidePriceText == "Hide taxes by outlet"){
			$scope.showHidePriceText = "Show taxes by outlet";
			$scope.showHidePriceFlag = true;

		}else{
			$scope.showHidePriceText = "Hide taxes by outlet";
			$scope.showHidePriceFlag = false;
		}
	};
	
	$scope.verifyVariantsInfoCompleted = function(){
		if($scope.gui.varientProducts){
			$scope.sameProductVariantAttribute = false;
			if($scope.productVariantsCollection.length==1){
				if($scope.productVariantsCollection[0].varientAttributeId=='' ||$scope.productVariantsCollection[0].varientAttributeId=='0'
					||$scope.productVariantsCollection[0].varientAttributeId == '-1' || $scope.productVariantValuesCollectionOne.length<1){
					$scope.disableAddproductButton = true;
				}
				else{
					$scope.disableAddproductButton = false;
				}
			}else if($scope.productVariantsCollection.length==2){
				if($scope.productVariantsCollection[0].varientAttributeId == $scope.productVariantsCollection[1].varientAttributeId){
					$scope.sameProductVariantAttribute = true;
					$scope.disableAddproductButton = true;
				}else{
					$scope.sameProductVariantAttribute = false;
					if($scope.productVariantsCollection[0].varientAttributeId=='' ||$scope.productVariantsCollection[0].varientAttributeId=='0'
						||$scope.productVariantsCollection[0].varientAttributeId == '-1'|| $scope.productVariantsCollection[1].varientAttributeId=='' 
							||$scope.productVariantsCollection[1].varientAttributeId=='0'
								||$scope.productVariantsCollection[1].varientAttributeId == '-1' || $scope.productVariantValuesCollectionOne.length<1||
								$scope.productVariantValuesCollectionTwo.length<1){
						$scope.disableAddproductButton = true;
					}
					else{
						$scope.disableAddproductButton = false;
					}
				}
			}else if($scope.productVariantsCollection.length==3){
				if($scope.productVariantsCollection[0].varientAttributeId == $scope.productVariantsCollection[1].varientAttributeId
						||$scope.productVariantsCollection[0].varientAttributeId == $scope.productVariantsCollection[2].varientAttributeId
						||$scope.productVariantsCollection[1].varientAttributeId == $scope.productVariantsCollection[2].varientAttributeId){
					$scope.sameProductVariantAttribute = true;
					$scope.disableAddproductButton = true;
				}else{
					$scope.sameProductVariantAttribute = false;
					if($scope.productVariantsCollection[0].varientAttributeId=='' ||$scope.productVariantsCollection[0].varientAttributeId=='0'
						||$scope.productVariantsCollection[0].varientAttributeId == '-1'|| $scope.productVariantsCollection[1].varientAttributeId=='' ||$scope.productVariantsCollection[1].varientAttributeId=='0'
							||$scope.productVariantsCollection[1].varientAttributeId == '-1' 
								||$scope.productVariantsCollection[2].varientAttributeId=='' ||$scope.productVariantsCollection[2].varientAttributeId=='0'
									||$scope.productVariantsCollection[2].varientAttributeId == '-1'|| $scope.productVariantValuesCollectionOne.length<1||
									$scope.productVariantValuesCollectionTwo.length<1||$scope.productVariantValuesCollectionThree.length<1){
						$scope.disableAddproductButton = true;
					}
					else{
						$scope.disableAddproductButton = false;
					}
				}
			}
		}else{
			$scope.disableAddproductButton = false;
		}

	};
	
	$scope.autoCompleteOptionsTags = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();

				var customerResults = _.filter($scope.tagList, function(val) {
					if(val.tagName){
						return val.tagName.toLowerCase().includes(term);
					}


				});
				return customerResults;
			},
			renderItem : function(item) {

				var result = {
						value : item.tagName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
								+ item.tagName + "</td>"
								+ "<td style='width: 10%'>" + 'Tag' + "</td>"
								+ "</tr>" + "</tbody>" + "</table>")
				};
				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.CustomerName = "";
				$scope.selectedItem.item.productId = "-1";
				$scope.selectTag = false;
				var found = false;
				for(var i=0;i<$scope.tagDisplayList.length;i++){
					if($scope.tagDisplayList[i].tagId==$scope.selectedItem.item.tagId){
						found = true;
						break;
					}
				}
				if(!found){
					$scope.tagDisplayList.push($scope.selectedItem.item);
					$scope.productSuccess = false;
					$scope.productError = false;
					$scope.loading = true;
					$http.post('productTags/addProductTag/'+$scope._s_tk_com+"/"+$scope.productBean.productId, $scope.tagDisplayList)
					.success(function(Response) {
						$scope.loading = false;

						$scope.responseStatus = Response.status;
						if ($scope.responseStatus == 'SUCCESSFUL') {
							$scope.tagBean = {};
							$scope.productSuccess = true;
							$scope.productSuccessMessage = Response.data;
							$timeout(function(){
								$scope.tagSuccess = false;
								$timeout(function(){
									$route.reload();
								}, 500);
							}, 2000);

						}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
							$scope.productError = true;
							$scope.productErrorMessage = Response.data;
							//$window.location = Response.layOutPath;
						}else {
							$scope.productError = true;
							$scope.productErrorMessage = Response.data;
						}
					}).error(function() {
						$scope.loading = false;
						$scope.productError = true;
						$scope.productErrorMessage = $scope.systemBusy;
					});
					
				}
			}
	};
	
	$scope.removeFromDisplayTagList = function(tag){
		for(var i=0;i<$scope.tagDisplayList.length;i++){
			if($scope.tagDisplayList[i].tagId==tag.tagId && $scope.tagDisplayList[i].productId=='-1'){
				$scope.tagDisplayList.splice(i,1);
				break;
			}else if($scope.tagDisplayList[i].tagId==tag.tagId && $scope.tagDisplayList[i].productId!='-1'){
				//alert('remove from db');
				
				$scope.productSuccess = false;
				$scope.productError = false;
				$scope.loading = true;
				$http.post('productTags/deleteProductTag/'+$scope._s_tk_com, $scope.tagDisplayList[i])
				.success(function(Response) {
					$scope.loading = false;

					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.tagBean = {};
						$scope.productSuccess = true;
						$scope.productSuccessMessage = Response.data;
						$timeout(function(){
							$scope.tagSuccess = false;
							$timeout(function(){
								$route.reload();
							}, 500);
						}, 2000);

					}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
						$scope.productError = true;
						$scope.productErrorMessage = Response.data;
						//$window.location = Response.layOutPath;
					}else {
						$scope.productError = true;
						$scope.productErrorMessage = Response.data;
					}
				}).error(function() {
					$scope.loading = false;
					$scope.productError = true;
					$scope.productErrorMessage = $scope.systemBusy;
				});
				break;
			}
		}
	};



	$scope.addTag = function() {
		if($scope.tagBean==null || $scope.tagBean=='' || !$scope.tagBean){
			return;
		}
		var found= false;
		for(var i=0;i<$scope.tagList.length;i++){
			if($scope.tagList[i].tagName.toLowerCase()==$scope.tagBean.tagName.toLowerCase()){
				found = true;
				break;
			}
		}
		if(found){
			$scope.tagError = true;
			$scope.tagErrorMessage = "Tag "+$scope.tagBean.tagName+" alreday exist";
			$timeout(function(){
				$scope.tagError = false;
			}, 1000);
			return;
		}
		$scope.productSuccess = false;
		$scope.productError = false;
		$scope.loading = true;
		$http.post('productTags/addTag/'+$scope._s_tk_com, $scope.tagBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.tagBean = {};
				$scope.tagList= null;
				$scope.tagList = Response.data;
				$scope.productSuccessMessage = 'Request Processed Successfully!';
				$timeout(function(){
					$scope.productSuccess = false;
					
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				//$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};

	$scope.addVarientAttribute = function(varientAttributeBean) {
		
		$scope.variantAttributeAddError = false;
		if($scope.varientAttributesList!=null){
			for(var index=0;index<$scope.varientAttributesList.length;index++){
				if($scope.varientAttributesList[index].attributeName.toLowerCase() == varientAttributeBean.attributeName.toLowerCase()){
					$scope.variantAttributeAddError = true;
					$scope.variantAttributeAddErrorMessage = 'Attribute Already exist.';
					$timeout(function(){
						$scope.variantAttributeAddError = false;
					}, 900);
					return;
				}
			}
		}
		
		
		$scope.varientAttributeBean = angular.copy(varientAttributeBean);
		varientAttributeBean = null;
		$scope.productSuccess = false;
		$scope.productError = false;
		$scope.loadingVarient = true;
		$http.post('newProduct/addVariantAttribute/'+$scope._s_tk_com, $scope.varientAttributeBean)
		.success(function(Response) {
			$scope.loadingVarient = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.varientAttributeBean = {};
				$scope.productSuccess = true;
				$scope.productSuccessMessage = Response.data;
				$scope.fetchAllVarientAttributes();
				$timeout(function(){
					$scope.productSuccess = false;
				}, 2000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				//$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loadingVarient = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};

	$scope.fetchAllTags = function() {
		$http.post('productTags/getAllTags/'+$scope._s_tk_com).success(function(Response) {
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.tagList = Response.data;

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
	
	$scope.fetchAllVarientAttributes = function() {
		$http.post('newProduct/getAllVariantAttributes/'+$scope._s_tk_com).success(function(Response) {
			$scope.showNewVarientAttriubuteModal = false;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.varientAttributesList = null;
				$scope.varientAttributesList = Response.data;
				for(var index=0; index<$scope.productVariantsCollection.length; index++){
					if($scope.productVariantsCollection[index].varientAttributeId == "0"){
						$scope.productVariantsCollection[index].varientAttributeId = $scope.varientAttributesList[$scope.varientAttributesList.length-1].varientAttributeId;
						
					}
				}
				$scope.verifyVariantsInfoCompleted();
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
			$scope.showNewVarientAttriubuteModal = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});

	};

	$scope.showAddVarientAttributePopup = function(value){
			
		$scope.productSuccess = false;
		$scope.variantAttributeAddError = false;
		if(value=='0'){
			$scope.showNewVarientAttriubuteModal = true;
		}else{
			$scope.verifyVariantsInfoCompleted();
		}

	};
	$scope.showAddProductTypePopup = function(value){
		$scope.productSuccess = false;
		if(value=='0'){
			$scope.showAddProducTypeModal = true;
		}
	};
	
	$scope.showAddSupplierPopup = function(value){
		$scope.productSuccess = false;
		if(value=='0'){
			$scope.showAddSupplierModal = true;
		}
	};
	$scope.showAddBrandPopup = function(value){
		$scope.productSuccess = false;
		if(value=='0'){
			$scope.showAddBrandModal = true;
		}
	};
	
	$scope.addProductType = function() {
		$scope.productSuccess = false;
		$scope.productTypeError = false;
		$scope.loading = true;
		
		$http.post('productType/addProductType/'+$scope._s_tk_com, $scope.productTypeBean)
		.success(function(Response) {
			$scope.loading = false;
			$scope.productBean.productTypeId = $scope.tempproductBean.productTypeId;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.showAddProducTypeModal = false;
				$scope.productTypeBean = {};
				$scope.productTypeList = null;
				$scope.productTypeList = Response.data;
				$scope.productSuccess = true;
				$scope.productSuccessMessage = 'Request Processed successfully!';
				$scope.showAddSalesTaxModal = false;
				$timeout(function(){
					$scope.productSuccess = false;
					
				}, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productTypeError = true;
				$scope.productTypeErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productTypeError = true;
			$scope.productTypeErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.addSupplier = function() {
		$scope.productSuccess = false;
		$scope.Error = false;
		$scope.loading = true;
		
		$http.post('newProduct/addSupplier/' + $scope._s_tk_com,
				$scope.supplierBean).success(
				function(Response) {
					$scope.loading = false;
					$scope.productBean.supplierId = $scope.tempproductBean.supplierId;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						
						$scope.supplierBean = {};
						$scope.supplierList = null;
						$scope.supplierList = Response.data;
						//var supplierLength = $scope.supplierList.length;
						//$scope.productBean.supplierId = $scope.supplierList[supplierLength-1].supplierId; 
						$scope.productSuccess = true;
						$scope.productSuccessMessage = 'Request Processed successfully!';
						$scope.showAddSupplierModal = false;
						$timeout(function(){
							$scope.productSuccess = false;
							
						}, 1000);
					} else if ($scope.responseStatus == 'INVALIDSESSION'
							|| $scope.responseStatus == 'SYSTEMBUSY') {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.Error = true;
						$scope.ErrorMessage = Response.data;
					}
				}).error(function() {
			$scope.loading = false;
			$scope.Error = true;
			$scope.ErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.addBrand = function() {
		$scope.productSuccess = false;
		$scope.brandError = false;
		$scope.loading = true;
		
		$http.post('newProduct/addBrand/'+$scope._s_tk_com, $scope.brandBean)
		.success(function(Response) {
			$scope.loading = false;
			$scope.productBean.brandId = $scope.tempproductBean.brandId;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.brandBean = {};
				$scope.brandList = null;
				$scope.brandList = Response.data;
				$scope.showAddBrandModal = false;
				$scope.productSuccess = true;
				$scope.productSuccessMessage = 'Request Processed successfully!';
				$timeout(function(){
					$scope.productSuccess = false;
					
				}, 1000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.brandError = true;
				$scope.brandErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.brandError = true;
			$scope.brandErrorMessage = $scope.systemBusy;
		});
	
	};

	$scope.evaluateRetailPrice = function(value){
		var supplyPrice = parseFloat($scope.productBean.supplyPriceExclTax);
		var markUp = parseFloat($scope.productBean.markupPrct);
		if(isNaN(markUp)){
			markUp = 0.0;
			$scope.productBean.markupPrct = "0";
		}
		var retailPrice = parseFloat($scope.productBean.retailPriceExclTax);
		var result = supplyPrice*(markUp/100)+supplyPrice;
		$scope.productBean.retailPriceExclTax = (result.toFixed(2)).toString();
		if($scope.outletList!=null && result>0){
			for(var i=0;i<$scope.outletList.length;i++){
				var salesTax = parseFloat($scope.outletList[i].defaultTax);
				var taxAmount = result*(salesTax/100);
				$scope.outletList[i].taxAmount = (taxAmount.toFixed(2)).toString();
				$scope.outletList[i].retailPrice = ((taxAmount+result).toFixed(2)).toString();
//				$scope.productBean.outletId = $scope.outletList[i].outletId;
			}
		}
		if(value){
			$scope.updateAllProductVariantsPriceByChangeOfProductPrice();
		}
		
	};
	
	$scope.adjustMarkUpByChangeOfRetailPrice = function(){
		var retailPrice = parseFloat($scope.productBean.retailPriceExclTax);
		if(!isNaN(retailPrice)){
			var supplyPrice = parseFloat($scope.productBean.supplyPriceExclTax);
			var markUp = parseFloat($scope.productBean.markupPrct);
			if(isNaN(markUp)){
				markUp = 0.0;
			}
			markUp = round((retailPrice-supplyPrice)*100/supplyPrice,5);
			$scope.productBean.markupPrct = markUp.toString();
			var result = supplyPrice*(markUp/100)+supplyPrice;
			if($scope.outletList!=null && result>0){
				for(var i=0;i<$scope.outletList.length;i++){
					var salesTax = parseFloat($scope.outletList[i].defaultTax);
					var taxAmount = result*(salesTax/100);
					$scope.outletList[i].taxAmount = (taxAmount.toFixed(2)).toString();
					$scope.outletList[i].retailPrice = ((taxAmount+result).toFixed(2)).toString();
				}
			}
			$scope.updateAllProductVariantsPriceByChangeOfProductPrice();
		}
	};
	
	function round(value, decimals) {
		  return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
		}
	
	$scope.evaluateVariantRetailPrice1 = function(productVariantBean){
		for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
			if($scope.productBean.productVariantsBeans[i].productVariantId == productVariantBean.productVariantId){
				var supplyPrice = parseFloat($scope.productBean.productVariantsBeans[i].supplyPriceExclTax);
				var markUp = parseFloat($scope.productBean.productVariantsBeans[i].markupPrct);
				if(isNaN(markUp)){
					markUp = 0.0;
					$scope.productBean.productVariantsBeans[i].markupPrct = "0";
				}
				var result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
				$scope.productBean.productVariantsBeans[i].retailPriceExclTax = result.toString();
			}
		}
	};
	
	
	$scope.adjustMarkUpByChangeOfVariantRetailPrice1 = function(productVariantBean){

		for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
			if($scope.productBean.productVariantsBeans[i].productVariantId == productVariantBean.productVariantId){
				var retailPrice = parseFloat($scope.productBean.productVariantsBeans[i].retailPriceExclTax);
				if(!isNaN(retailPrice)){
					var supplyPrice = parseFloat($scope.productBean.productVariantsBeans[i].supplyPriceExclTax);
					var difference = retailPrice-supplyPrice;
					var markUp = parseFloat($scope.productBean.productVariantsBeans[i].markupPrct);
					if(isNaN(markUp)){
						markUp = 0.0;
					}
					markUp = round((retailPrice-supplyPrice)*100/supplyPrice,5);
					$scope.productBean.productVariantsBeans[i].markupPrct = markUp.toString();

				}
			}
		}
	};
	
	$scope.updateAllProductVariantsPriceByChangeOfProductPrice1 = function(){
		if($scope.productBean.productVariantsBeans!=null && $scope.productBean.productVariantsBeans.length>0){
			for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
				$scope.productBean.productVariantsBeans[i].supplyPriceExclTax = $scope.productBean.supplyPriceExclTax;
				$scope.productBean.productVariantsBeans[i].markupPrct =$scope.productBean.markupPrct;
				$scope.productBean.productVariantsBeans[i].retailPriceExclTax = $scope.productBean.retailPriceExclTax;
			}
		}
	};
	
	
	
	$scope.deleteProductVariant = function(productVariantBean){
		
		$scope.productVariantId = angular.copy(productVariantBean.productVariantId);
		$scope.alertMessage = 'Are you sure,you want to delete Product Variant?';
		$('#alertBox1').modal('show');
		
	};
	
	$scope.markInActiveProductVariant = function(){
		$http.post('manageProduct/markInActiveProductVariant/'+$scope._s_tk_com+'/'+$scope.productVariantId)
		.success(function(Response) {
			$('#alertBox1').modal('hide');
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productSuccess = true;
				$scope.productSuccessMessage = Response.data;
				$scope.showAddSalesTaxModal = false;
				$timeout(function(){
					$scope.productSuccess = false;
					$route.reload();
				}, 1000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$('#alertBox1').modal('hide');
			$scope.loading = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.deleteCompositeProductVariant = function(compositProduct){
		
		$scope.compositeProductVariantUuid = angular.copy(compositProduct.uuid);
		$scope.alertMessage = 'Are you sure,you want to delete Composite product Variant?';
		$('#alertBox2').modal('show');
		
	};
	
	$scope.markInActiveCompositeProductVariant = function(){
		$http.post('manageProduct/markInActiveCompositeProductVariant/'+$scope._s_tk_com+'/'+$scope.compositeProductVariantUuid)
		.success(function(Response) {
			$('#alertBox2').modal('hide');
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productSuccess = true;
				$scope.productSuccessMessage = Response.data;
				$scope.showAddSalesTaxModal = false;
				$timeout(function(){
					$scope.productSuccess = false;
					$route.reload();
				}, 1000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$('#alertBox2').modal('hide');
			$scope.loading = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.updateProduct = function(dataUrl, name) {
		 $scope.imageData =  {
		            file: dataUrl,
		            id : name
		        };
		$scope.productSuccess = false;
		$scope.productError = false;
		$scope.productAddError = false;
		if($scope.gui.varientProducts){
			$scope.verifyVariantsInfoCompleted();
			if(!$scope.disableAddproductButton){
				$scope.verifyDuplicateBarCodeForAllVariants();
			}
			if($scope.disableAddproductButton){
				return;
			}
		}
		if($scope.productBean.productVariantsBeans.length > 0){
			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;
			for(var j=0;j<$scope.productBean.productVariantsBeans.length;j++){
				for(var i=0;i<$scope.productVariantValuesCollection.length;i++){
					if($scope.productVariantValuesCollection[i].varientName==$scope.productBean.productVariantsBeans[j].variantAttributeName){
						$scope.productVariantValuesCollection.splice(i,1);
					}
				}
			}
			/*if($scope.productVariantValuesCollectionOne.length>0 && $scope.productVariantValuesCollectionOldOne.length>0){
				for(var i=0;i<$scope.productVariantValuesCollectionOldOne.length;i++){
					for(var j=0;j<$scope.productVariantValuesCollectionOne.length;j++){
						if($scope.productVariantValuesCollectionOne[j].value==$scope.productVariantValuesCollectionOldOne[i].value){
							$scope.productVariantValuesCollectionOne.splice(j,1);
						}
					}
				}
			}*/
			/*if($scope.productVariantValuesCollectionTwo.length>0 && $scope.productVariantValuesCollectionOldTwo.length>0){
				for(var i=0;i<$scope.productVariantValuesCollectionOldTwo.length;i++){
					for(var j=0;j<$scope.productVariantValuesCollectionTwo.length;j++){
						if($scope.productVariantValuesCollectionTwo[j].value==$scope.productVariantValuesCollectionOldTwo[i].value){
							$scope.productVariantValuesCollectionTwo.splice(j,1);
						}
					}
				}
			}
			if($scope.productVariantValuesCollectionThree.length>0 && $scope.productVariantValuesCollectionOldThree.length>0){
				for(var i=0;i<$scope.productVariantValuesCollectionOldThree.length;i++){
					for(var j=0;j<$scope.productVariantValuesCollectionThree.length;j++){
						if($scope.productVariantValuesCollectionThree[j].value==$scope.productVariantValuesCollectionOldThree[i].value){
							$scope.productVariantValuesCollectionThree.splice(j,1);
						}
					}
				}
			}*/
			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;	
			$scope.productBean.productVariantValuesCollection = $scope.productVariantValuesCollection;
			$scope.productBean.productVariantAttributeCollection = $scope.productVariantsCollection;
			
		}else{
			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;
			$scope.productBean.productVariantValuesCollection = $scope.productVariantValuesCollection;
			$scope.productBean.productVariantAttributeCollection = $scope.productVariantsCollection;
		}
		
		$scope.productBean.varientAttributeId = $scope.productVariantsCollection[0].varientAttributeId;
		//$scope.productBean.tagList = $scope.tagDisplayList;
		$scope.productBean.outletList = $scope.outletList;
		$scope.productBean.productCanBeSold  = $scope.gui.productCanBeSold;
		$scope.productBean.trackingProduct = $scope.gui.trackingProduct;
		$scope.productBean.varientProducts = $scope.gui.varientProducts;
		$scope.productBean.standardProduct = $scope.gui.standardProduct;

		
		$scope.productBean.compositProductCollection = $scope.compositProductCollection;
		$scope.productBean.imageData = $scope.imageData;
		$scope.productBean.productPriceHistoryBean = $scope.productPriceHistoryBean;
		$scope.setDefaultDutyCalcuation();
		$scope.loading = true;
		$http.post('manageProduct/updateProduct/'+$scope._s_tk_com, $scope.productBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productSuccess = true;
				$scope.productSuccessMessage = Response.data;
				$scope.showAddSalesTaxModal = false;
				$timeout(function(){
					$scope.productSuccess = false;
					$window.location = Response.layOutPath;
				}, 1000);

			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};
	
	$scope.fetchAllProducts = function() {
		$http.post('newProduct/getAllProductsForDropDown/'+$scope._s_tk_com).success(function(Response) {
			$scope.showNewVarientAttriubuteModal = false;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus== 'SUCCESSFUL') {
				$scope.productsList = null;
				$scope.productsList = Response.data;
				
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
			$scope.showNewVarientAttriubuteModal = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});

	};
	$scope.showHideRateArea = function(){
    	if($scope.showHideRateText == "Hide Assessment"){
			$scope.showHideRateText = "Show Assessment";
			$scope.RateArea = false;

		}else{
			$scope.showHideRateText = "Hide Assessment";
			$scope.RateArea = true;
		}
    };
    
	$scope.showDutyCalcuator = function(){
		$scope.showDutyCalculationModal = true;
	};
	
	 $scope.setDefaultDutyCalcuation = function(){
	    	
	    	if(isNaN($scope.productPriceHistoryBean.dollarRate)){
	    		$scope.productPriceHistoryBean.dollarRate = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.dUnitValueDeclared)){
	    		$scope.productPriceHistoryBean.dUnitValueDeclared = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.dUnitValueAssessed)){
	    		$scope.productPriceHistoryBean.dUnitValueAssessed = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.dTotalValueDeclared)){
	    		$scope.productPriceHistoryBean.dTotalValueDeclared = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.dTotalValueAssessed)){
	    		$scope.productPriceHistoryBean.dTotalValueAssessed = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.pCustomValueDeclared)){
	    		$scope.productPriceHistoryBean.pCustomValueDeclared = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.pCustomValueAssessed)){
	    		$scope.productPriceHistoryBean.pCustomValueAssessed = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.numberOfUnits)){
	    		$scope.productPriceHistoryBean.numberOfUnits = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.customDutyPrct)){
	    		$scope.productPriceHistoryBean.customDutyPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.customDutyValue)){
	    		$scope.productPriceHistoryBean.customDutyValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.salesTaxPrct)){
	    		$scope.productPriceHistoryBean.salesTaxPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.salesTaxValue)){
	    		$scope.productPriceHistoryBean.salesTaxValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.regularityDutyPrct)){
	    		$scope.productPriceHistoryBean.regularityDutyPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.regularityDutyValue)){
	    		$scope.productPriceHistoryBean.regularityDutyValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.additionalCustomDutyPrct)){
	    		$scope.productPriceHistoryBean.additionalCustomDutyPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.additionalCustomDutyValue)){
	    		$scope.productPriceHistoryBean.additionalCustomDutyValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.additionalSalesTaxPrct)){
	    		$scope.productPriceHistoryBean.additionalSalesTaxPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.additionalSalesTaxValue)){
	    		$scope.productPriceHistoryBean.additionalSalesTaxValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.excisePrct)){
	    		$scope.productPriceHistoryBean.excisePrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.exciseValue)){
	    		$scope.productPriceHistoryBean.exciseValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.totalValue)){
	    		$scope.productPriceHistoryBean.totalValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.itPrct)){
	    		$scope.productPriceHistoryBean.itPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.itValue)){
	    		$scope.productPriceHistoryBean.itValue = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.ftaPrct)){
	    		$scope.productPriceHistoryBean.ftaPrct = 0;
	    	}
	    	if(isNaN($scope.productPriceHistoryBean.ftaValue)){
	    		$scope.productPriceHistoryBean.ftaValue = 0;
	    	}
	    };
	    
	    
	    $scope.calculateAssessedValues = function(){
	    	
	    	
	    	$scope.setDefaultDutyCalcuation();
	    	
	    	$scope.productPriceHistoryBean.dTotalValueAssessed = $scope.productPriceHistoryBean.dUnitValueAssessed*$scope.productPriceHistoryBean.numberOfUnits;
	    	$scope.productPriceHistoryBean.pCustomValueAssessed = $scope.productPriceHistoryBean.dTotalValueAssessed*$scope.productPriceHistoryBean.dollarRate;
	    	
	    	$scope.productPriceHistoryBean.customDutyValue  = round(parseFloat($scope.productPriceHistoryBean.pCustomValueAssessed*$scope.productPriceHistoryBean.customDutyPrct)/100,2);
	    	$scope.productPriceHistoryBean.regularityDutyValue = round(parseFloat($scope.productPriceHistoryBean.pCustomValueAssessed*$scope.productPriceHistoryBean.regularityDutyPrct)/100,2);
	    	$scope.productPriceHistoryBean.additionalCustomDutyValue = round(parseFloat($scope.productPriceHistoryBean.pCustomValueAssessed*$scope.productPriceHistoryBean.additionalCustomDutyPrct)/100,2);
	    	$scope.productPriceHistoryBean.additionalSalesTaxValue  = round(parseFloat(($scope.productPriceHistoryBean.pCustomValueAssessed+$scope.productPriceHistoryBean.customDutyValue+$scope.productPriceHistoryBean.additionalCustomDutyValue+$scope.productPriceHistoryBean.regularityDutyValue)*$scope.productPriceHistoryBean.additionalSalesTaxPrct)/100,2);
	    	$scope.productPriceHistoryBean.salesTaxValue  = round(parseFloat(($scope.productPriceHistoryBean.pCustomValueAssessed+$scope.productPriceHistoryBean.customDutyValue+$scope.productPriceHistoryBean.additionalCustomDutyValue+$scope.productPriceHistoryBean.regularityDutyValue)*$scope.productPriceHistoryBean.salesTaxPrct)/100,2);
	    	$scope.productPriceHistoryBean.itValue = round(parseFloat(($scope.productPriceHistoryBean.pCustomValueAssessed+$scope.productPriceHistoryBean.customDutyValue+$scope.productPriceHistoryBean.additionalCustomDutyValue+$scope.productPriceHistoryBean.salesTaxValue+$scope.productPriceHistoryBean.additionalSalesTaxValue+$scope.productPriceHistoryBean.regularityDutyValue)*$scope.productPriceHistoryBean.itPrct)/100,2);
	    	$scope.productPriceHistoryBean.exciseValue = round(parseFloat($scope.productPriceHistoryBean.pCustomValueAssessed*$scope.productPriceHistoryBean.excisePrct)/100,2);
	    	$scope.productPriceHistoryBean.totalValue = round(parseFloat($scope.productPriceHistoryBean.customDutyValue+$scope.productPriceHistoryBean.salesTaxValue+$scope.productPriceHistoryBean.additionalSalesTaxValue+$scope.productPriceHistoryBean.itValue+$scope.productPriceHistoryBean.additionalCustomDutyValue+$scope.productPriceHistoryBean.regularityDutyValue),2);
	    	
	    	
	    };
	    $scope.addSupplierPrice = function(){
	    	$scope.productBean.supplyPriceExclTax = round(parseFloat(($scope.productPriceHistoryBean.totalValue+$scope.productPriceHistoryBean.pCustomValueAssessed)/$scope.productPriceHistoryBean.numberOfUnits),2);
	    	$scope.evaluateRetailPrice(false);
	    	$scope.showDutyCalculationModal = false;
	    };
	    
	    $scope.addAverageSupplierPrice = function(){
	    	var averageSupplierPrice =round(parseFloat(($scope.productPriceHistoryBean.totalValue+$scope.productPriceHistoryBean.pCustomValueAssessed)/$scope.productPriceHistoryBean.numberOfUnits),2);
	    	
	    	$scope.productBean.supplyPriceExclTax = round(parseFloat($scope.productBean.supplyPriceExclTax+averageSupplierPrice)/2,2);
	    	$scope.evaluateRetailPrice(false);
	    	$scope.showDutyCalculationModal = false;
	    };
	
	$scope.sesssionValidation();
}];