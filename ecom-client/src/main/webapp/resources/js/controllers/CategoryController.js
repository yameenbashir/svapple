'use strict';

/**
 * CategoryController
 * 
 * @constructor
 */
var CategoryController = function($scope, $http, $window, $cookieStore,
		$rootScope, $timeout, CategoryControllerPreLoad, $sce) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.productsBean = CategoryControllerPreLoad.loadControllerData();
	$scope.url = $sce.trustAsResourceUrl('https://www.angularjs.org');
	
	$rootScope.productSelected = function(product) {

		$rootScope.selectedProduct = product;

		$scope.quickView = true;

	}
	$scope.cartadd = function(productBean) {
		
	}
	$scope.cartadd = function(productBean) {
		addProductNotice(
				'Product added to Cart',
				'<img src="image/demo/shop/product/e11.jpg" alt="">',
				'<h3><a href="#">'+productBean.productName+'</a> added to <a href="#">shopping cart</a>!</h3>',
				'success');
		productBean.quantity = 1;
		$scope.updateCart(productBean);
	}
	$scope.updateCart = function(productBean) {
		
		$rootScope.cart.products.push(productBean);
		$rootScope.cart.amount = parseInt($rootScope.cart.amount)+ parseInt(productBean.retailPriceInclTax);
	}
	
	$scope.wishlistadd = function(product_id) {
		addProductNotice(
				'Product added to Wishlist',
				'<img src="image/demo/shop/product/e11.jpg" alt="">',
				'<h3>You must <a href="#">login</a>  to save <a href="#">Apple Cinema 30"</a> to your <a href="#">wish list</a>!</h3>',
				'success');
	}
	$scope.compareadd = function(product_id) {
		addProductNotice(
				'Product added to compare',
				'<img src="image/demo/shop/product/e11.jpg" alt="">',
				'<h3>Success: You have added <a href="#">Apple Cinema 30"</a> to your <a href="#">product comparison</a>!</h3>',
				'success');
	}

	/*
	 * --------------------------------------------------- jGrowl – jQuery
	 * alerts and message box --------------------------------------------------
	 */
	function addProductNotice(title, thumb, text, type) {
		$.jGrowl.defaults.closer = false;
		// Stop jGrowl
		// $.jGrowl.defaults.sticky = true;
		var tpl = thumb + '<h3>' + text + '</h3>';
		$.jGrowl(tpl, {
			life : 4000,
			header : title,
			speed : 'slow',
			theme : type
		});
	}
};