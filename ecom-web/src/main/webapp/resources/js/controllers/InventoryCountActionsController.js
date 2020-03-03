'use strict';

/**
 * InventoryCountActionsController
 * @constructor
 */
var InventoryCountActionsController = ['$scope', '$http', '$window', '$timeout', '$cookieStore', '$route', '$rootScope', 'SessionService','InventoryCountActionsControllerPreLoad',function($scope, $http, $window, $timeout, $cookieStore, $route, $rootScope, SessionService,InventoryCountActionsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.disableProcess = false;
	$scope.showConfirmCancelPopup = false;
	$scope.inventoryCountDetailBeansList = []; 

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.inventoryCountBean = $cookieStore.get('_ct_sc_ost');
			$scope.data = InventoryCountActionsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			if($scope.inventoryCountBean.statusId == "3" || $scope.inventoryCountBean.statusId == "8"){ //3 completed and 8 closed
				$scope.disableProcess = true;
			}
			$scope.isAdmin();
		}
	};
	
	$scope.fetchData = function() {
		$rootScope.inventoryCountActionsLoadedFully = false;
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
				if($scope.data != "No record found !"){
					$scope.inventoryCountDetailBeansList = $scope.data;
					var printHeader =  '<div style="margin-left: 10px  !important;">'
						+'<div class="row !important;">'
		        		+'<div class="col-md-2 !important;"></div> '
		        		+'<div class="col-md-2 !important;"><h4><label> Name / Reference: </label> </h4></div>'
		        		+'<div class="col-md-2 !important;"><h4>'+$scope.inventoryCountBean.inventoryCountRefNo+' </h4></div> '
		        		+'<div class="col-md-6 !important;"></div> '
		        		+'</div>'
		        		+'<div class="row">'
		        		+'<div class="col-md-2"></div> '
		        		+'<div class="col-md-2"><h4><label> Type: </label> </h4></div>'
		        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.inventoryCountTypeDesc+' </h4></div> '
		        		+'<div class="col-md-6"></div> '
		        		+'</div>'
		        		+'<div class="row">'
		        		+'<div class="col-md-2"></div> '
		        		+'<div class="col-md-2"><h4><label> Outlet: </label> </h4></div>'
		        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.outletName+' </h4></div> '
		        		+'<div class="col-md-6"></div> '
		        		+'</div>'
		        		+'<div class="row">'
		        		+'<div class="col-md-2"></div> '
		        		+'<div class="col-md-2"><h4><label> Remarks: </label> </h4></div>'
		        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.remarks+' </h4></div> '
		        		+'<div class="col-md-6"></div> '
		        		+'</div>'
		        		+'<div class="row">'
		        		+'<div class="col-md-2"></div> '
		        		+'<div class="col-md-2"><h4><label> Diff in count: </label> </h4></div>'
		        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.countDiff+' </h4></div> '
		        		+'<div class="col-md-6"></div> '
		        		+'</div>'
		        		+'<div class="row">'
		        		+'<div class="col-md-2"></div> '
		        		+'<div class="col-md-2"><h4><label> Diff in Price: </label> </h4></div>'
		        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.priceDiff+' </h4></div> '
		        		+'<div class="col-md-6"></div> '
		        		+'</div>'
		        		+'<div>';
					setTimeout(
							function() 
							{
								$('#myTable').DataTable(
										{
											responsive : true,
											dom : 'Bfrtip',
											buttons : {
												buttons : [
														{
														customize: function ( win ) {
											                    $(win.document.body)
										                        .css( 'font-size', '10pt' )
										                        .prepend(printHeader);},
															extend : 'print',
															text : '<i class="fa fa-print"></i> Print',
															title : $('h1').text(),
															key : {
																key : 'p',
																altkey : true
															},
															exportOptions : {
																columns : ':not(.no-print)'
															},
															footer : true,
															autoPrint : true
														},
														{
															extend : 'excel',
															text : '<i class="fa fa-book"></i> Excel',
															title : $('h1').text(),
															key : {
																key : 'p',
																altkey : true
															},
															exportOptions : {
																columns : ':not(.no-print)'
															},
															footer : true,
															autoPrint : false
														},
														{
															extend : 'pdf',
															text : '<i class="fa fa-file-pdf-o"></i> PDF',
															title : $('h1').text(),
															exportOptions : {
																columns : ':not(.no-print)'
															},
															footer : true
														} ],
												dom : {
													container : {
														className : 'dt-buttons'
													},
													button : {
														className : 'btn btn-default'
													}
												}
											}
										});
						
							}, 100);
				}
			}
		}
	};
	/*			var printHeader =  '<div style="margin-left: 10px  !important;"><div class="row !important;">'
        		+'<div class="col-md-2 !important;"></div> '
        		+'<div class="col-md-2 !important;"><h4><label> Name / Reference: </label> </h4></div>'
        		+'<div class="col-md-2 !important;"><h4>'+$scope.inventoryCountBean.inventoryCountRefNo+' </h4></div> '
        		+'<div class="col-md-6 !important;"></div> '
        		+'</div>'
        		+'<div class="row">'
        		+'<div class="col-md-2"></div> '
        		+'<div class="col-md-2"><h4><label> Outlet: </label> </h4></div>'
        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.outletName+' </h4></div> '
        		+'<div class="col-md-6"></div> '
        		+'</div>'
        		+'<div class="row">'
        		+'<div class="col-md-2"></div> '
        		+'<div class="col-md-2"><h4><label> Type: </label> </h4></div>'
        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.inventoryCountTypeDesc+' </h4></div> '
        		+'<div class="col-md-6"></div> '
        		+'</div>'
        		+'<div class="row">'
        		+'<div class="col-md-2"></div> '
        		+'<div class="col-md-2"><h4><label> Remarks: </label> </h4></div>'
        		+'<div class="col-md-2"><h4>'+$scope.inventoryCountBean.remarks+' </h4></div> '
        		+'<div class="col-md-6"></div> '
        		+'</div>';
				setTimeout(
				function() 
				{
					$('#myTable').DataTable(
							{
								responsive : true,
								dom : 'Bfrtip',
								caption : "Mike Smith",
								buttons : {
									buttons : [
											{
											customize: function ( win ) {
								                    $(win.document.body)
							                        .css( 'font-size', '10pt' )
							                        .prepend(printHeader);},
												extend : 'print',
												text : '<i class="fa fa-print"></i> Print',
												title : $('h1').text(),
												key : {
													key : 'p',
													altkey : true
												},
												exportOptions : {
													columns : ':not(.no-print)'
												},
												footer : true,
												autoPrint : true
											},
											{
												extend : 'excel',
												text : '<i class="fa fa-book"></i> Excel',
												title : $('h1').text(),
												key : {
													key : 'p',
													altkey : true
												},
												exportOptions : {
													columns : ':not(.no-print)'
												},
												footer : true,
												autoPrint : false
											},
											{
												extend : 'pdf',
												text : '<i class="fa fa-file-pdf-o"></i> PDF',
												title : $('h1').text(),
												exportOptions : {
													columns : ':not(.no-print)'
												},
												footer : true
											} ],
									dom : {
										container : {
											className : 'dt-buttons'
										},
										button : {
											className : 'btn btn-default'
										}
									}
								}
							});

				}, 100);
			}
		}
	};*/

	$scope.isAdmin = function(){
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "true"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	};

	/*$scope.calculateGrandTotal = function(){
		$scope.grandTotal = "0";
		if ($scope.inventoryCountDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.inventoryCountDetailBeansList.length; i++) {
				if(isNaN($scope.inventoryCountDetailBeansList[i].total)){
					$scope.inventoryCountDetailBeansList[i].total = "0";
				}
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.inventoryCountDetailBeansList[i].total);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
			}
		}
	};

	$scope.calculateItemCount = function(){
		$scope.inventoryCountBean.itemCount = 0;
		if($scope.inventoryCountDetailBeansList != null){
			for (var i = 0; i < $scope.inventoryCountDetailBeansList.length; i++) {
				$scope.inventoryCountBean.itemCount = parseInt($scope.inventoryCountBean.itemCount) + parseInt($scope.inventoryCountDetailBeansList[i].orderProdQty);
				if(isNaN($scope.inventoryCountBean.itemCount)){
					$scope.inventoryCountBean.itemCount = "0";
				}
			}
		}
	};
*/


	$scope.showCancelInventoryCount = function(){
		$scope.showConfirmCancelPopup = true;
	};	
	$scope.cancelInventoryCount = function(){		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.inventoryCountBean.statusId = "8"; // Initiated status at Inventory Count page
		$scope.inventoryCountBean.diliveryDueDate = new Date($scope.inventoryCountBean.diliveryDueDate);
		$http.post('inventoryCount/updateInventoryCount/'+$scope._s_tk_com, $scope.inventoryCountBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.inventoryCountBean.inventoryCountId = Response.data;
				$scope.showConfirmCancelPopup = false;
				$timeout(function(){
					$scope.success = false;
					$window.location = Response.layOutPath;
				}, 1000);
			}
			else if($scope.responseStatus == 'SYSTEMBUSY'
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

	$scope.inventoryCountEditProducts = function() {
		$window.location = "/app/#/inventoryCountEditProducts";
	};
	$scope.inventoryCountEditDetails = function() {
		$window.location = "/app/#/inventoryCountEditDetails";
	};
	$scope.sessionValidation();
	$rootScope.globalPageLoader = false;
}];