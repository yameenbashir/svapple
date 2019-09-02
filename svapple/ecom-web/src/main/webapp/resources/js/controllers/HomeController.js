'use strict';

/**
 * HomeController
 * @constructor
 */
var HomeController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','HomeControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,HomeControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	//SessionService.validate();	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.homeBean = HomeControllerPreLoad.loadControllerData();
			$scope.outletName = $scope.homeBean.outletName;
			$scope.dataPointsRevenue = $scope.homeBean.dataPointsRevenue;
			$scope.dataPointsSaleCount = $scope.homeBean.dataPointsSaleCount;
			$scope.dataPointsCustomerCount = $scope.homeBean.dataPointsCustomerCount;
			$scope.dataPointsDiscount = $scope.homeBean.dataPointsDiscount;
			$scope.dataPointsBasketSize = $scope.homeBean.dataPointsBasketSize;
			$scope.dataPointsGrossProfit = $scope.homeBean.dataPointsGrossProfit;
			$scope.dataPointsBasketValue = $scope.homeBean.dataPointsBasketValue;
			$scope.dataPointsDiscountPercent = $scope.homeBean.dataPointsDiscountPercent;
			$scope.todayRevenue = $scope.homeBean.todayRevenue;
			$scope.revenuePercentage = $scope.homeBean.revenuePercentage;
			$scope.todaySalesCount = $scope.homeBean.todaySalesCount;
			$scope.salesCountPercentage = $scope.homeBean.salesCountPercentage;
			$scope.todayCustomerCount = $scope.homeBean.todayCustomerCount;
			$scope.customerCountPercentage = $scope.homeBean.customerCountPercentage;
			$scope.todayGrossProfit = $scope.homeBean.todayGrossProfit;
			$scope.grossProfitPercentage = $scope.homeBean.grossProfitPercentage;
			$scope.todayDiscount = $scope.homeBean.todayDiscount;
			$scope.discountPercentage = $scope.homeBean.discountPercentage;
			$scope.todayBasketSize = $scope.homeBean.todayBasketSize;
			$scope.basketSizePercentage = $scope.homeBean.basketSizePercentage;
			$scope.todayBasketValue = $scope.homeBean.todayBasketValue;
			$scope.basketValuePercentage = $scope.homeBean.basketValuePercentage;
			$scope.todayDiscountPec = $scope.homeBean.todayDiscountPec;
			$scope.discountPecPercentage = $scope.homeBean.discountPecPercentage;
			$scope.hideSalesDetails = $scope.homeBean.hideSalesDetails;
			$scope.chartRevenue = new CanvasJS.Chart("chartContainerRevenue", {
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
			                backgroundColor: "#F5DEB3",
			                dataPoints: $scope.dataPointsRevenue
			            }
			        ]
			    });
			 $scope.chartSaleCount = new CanvasJS.Chart("chartContainerSaleCount", {
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
				             backgroundColor: "#F5DEB3",
				             dataPoints: $scope.dataPointsSaleCount
			            }
			        ]
			    });
			 $scope.chartCustomerCount = new CanvasJS.Chart("chartContainerCustomerCount", {
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
			                dataPoints: $scope.dataPointsCustomerCount
			            }
			        ]
			    });
			 $scope.chartGrossProfit = new CanvasJS.Chart("chartContainerGrossProfit", {
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
			                dataPoints: $scope.dataPointsGrossProfit
			            }
			        ]
			    });
			 $scope.chartDiscount = new CanvasJS.Chart("chartContainerDiscount", {
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
			                dataPoints: $scope.dataPointsDiscount
			            }
			        ]
			    });
			 $scope.chartDiscountPercent = new CanvasJS.Chart("chartContainerDiscountPercent", {
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
			                dataPoints: $scope.dataPointsDiscountPercent
			            }
			        ]
			    });
			 $scope.chartBasketValue = new CanvasJS.Chart("chartContainerBasketValue", {
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
			                dataPoints:$scope.dataPointsBasketValue
			            }
			        ]
			    });
			 $scope.chartBasketSize = new CanvasJS.Chart("chartContainerBasketSize", {
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
			                dataPoints: $scope.dataPointsBasketSize
			            }
			        ]
			    });
		    $scope.chartRevenue.render(); //render the chart for the first time
		    $scope.chartSaleCount.render(); //render the chart for the first time
		    $scope.chartCustomerCount.render(); //render the chart for the first time
		    $scope.chartGrossProfit.render(); //render the chart for the first time
		    $scope.chartDiscount.render(); //render the chart for the first time
		    $scope.chartDiscountPercent.render(); //render the chart for the first time
		    $scope.chartBasketValue.render(); //render the chart for the first time
		    $scope.chartBasketSize.render(); //render the chart for the first time
		    $rootScope.globalPageLoader = false;
		//	$scope.fetchData();
		}
	};


	
             
	    $scope.changeChartType = function(chartType) {
	        $scope.chart.options.data[0].type = chartType;
	        $scope.chart.render(); //re-render the chart to display the new layout
	    }

	    $scope.sessionValidation();
		
}];