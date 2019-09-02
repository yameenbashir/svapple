'use strict';

/* Directives */

var AppDirectives = angular.module('AngularSpringApp.directives', []);

AppDirectives.directive('appVersion', [ 'version', function(version) {
	return function(scope, elm, attrs) {
		elm.text(version);
	};

} ]);
AppDirectives.directive('bindHtmlUnsafe', function( $compile ) {
    return function( $scope, $element, $attrs ) {

        var compile = function( newHTML ) { // Create re-useable compile function
            newHTML = $compile(newHTML)($scope); // Compile html
            $element.html('').append(newHTML); // Clear and append it
        };

        var htmlName = $attrs.bindHtmlUnsafe; // Get the name of the variable 
                                              // Where the HTML is stored

        $scope.$watch(htmlName, function( newHTML ) { // Watch for changes to 
                                                      // the HTML
            if(!newHTML) return;
            compile(newHTML);   // Compile it
        });

    };
});
AppDirectives.directive('dataTable', function() {
	  return {
	    restrict: 'AC',
	    link: function(scope, element, attrs) {
	      element.DataTable();
	    }
	  }
	});


AppDirectives.directive('ngValidFunc', function() {
	return {
		require : 'ngModel',
		link : function(scope, elm, attrs, ctrl) {
			ctrl.$parsers.unshift(function(viewValue) {
				if (attrs.ngValidFunc
						&& scope[attrs.ngValidFunc]
						&& scope[attrs.ngValidFunc](viewValue, scope, elm,
								attrs, ctrl)) {
					ctrl.$setValidity('custom', true);
				} else {
					ctrl.$setValidity('custom', false);
				}
				return elm.val();
			});
		}
	};
});
AppDirectives.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            element.modal('show');
          else
            element.modal('hide');
        });

        element.on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        element.on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });
AppDirectives.directive('fade', function () {
    return {
      template: '<div class="modal-fullscreen fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
               
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
        	alert( element);
          if(value == true)
            element.modal('show');
          else
            element.modal('hide');
        });

        element.on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        element.on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });
AppDirectives.directive('pwCheck', [ function() {
	return {
		require : 'ngModel',
		link : function(scope, elem, attrs, ctrl) {
			var firstPassword = '#' + attrs.pwCheck;
			elem.add(firstPassword).on('keyup', function() {
				scope.$apply(function() {
					var v = elem.val() === $(firstPassword).val();
					ctrl.$setValidity('pwmatch', v);
				});
			});
		}
	};
} ]);

/**
 * Checklist-model
 * AngularJS directive for list of checkboxes
 */
AppDirectives.directive('checklistModel', ['$parse', '$compile', function($parse, $compile) {
  // contains
  function contains(arr, item) {
    if (angular.isArray(arr)) {
      for (var i = 0; i < arr.length; i++) {
        if (angular.equals(arr[i], item)) {
          return true;
        }
      }
    }
    return false;
  }

  // add
  function add(arr, item) {
    arr = angular.isArray(arr) ? arr : [];
    for (var i = 0; i < arr.length; i++) {
      if (angular.equals(arr[i], item)) {
        return arr;
      }
    }    
    arr.push(item);
    return arr;
  }  

  // remove
  function remove(arr, item) {
    if (angular.isArray(arr)) {
      for (var i = 0; i < arr.length; i++) {
        if (angular.equals(arr[i], item)) {
          arr.splice(i, 1);
          break;
        }
      }
    }
    return arr;
  }

  // http://stackoverflow.com/a/19228302/1458162
  function postLinkFn(scope, elem, attrs) {
    // compile with `ng-model` pointing to `checked`
    $compile(elem)(scope);

    // getter / setter for original model
    var getter = $parse(attrs.checklistModel);
    var setter = getter.assign;

    // value added to list
    var value = $parse(attrs.checklistValue)(scope.$parent);

    // watch UI checked change
    scope.$watch('checked', function(newValue, oldValue) {
      if (newValue === oldValue) { 
        return;
      } 
      var current = getter(scope.$parent);
      if (newValue === true) {
        setter(scope.$parent, add(current, value));
      } else {
        setter(scope.$parent, remove(current, value));
      }
    });

    // watch original model change
    scope.$parent.$watch(attrs.checklistModel, function(newArr, oldArr) {
      scope.checked = contains(newArr, value);
    }, true);
  }

  return {
    restrict: 'A',
    priority: 1000,
    terminal: true,
    scope: true,
    compile: function(tElement, tAttrs) {
      if (tElement[0].tagName !== 'INPUT' || !tElement.attr('type', 'checkbox')) {
        throw 'checklist-model should be applied to `input[type="checkbox"]`.';
      }

      if (!tAttrs.checklistValue) {
        throw 'You should provide `checklist-value`.';
      }

      // exclude recursion
      tElement.removeAttr('checklist-model');
      
      // local scope var storing individual checkbox model
      tElement.attr('ng-model', 'checked');

      return postLinkFn;
    }
  };
}]);

AppDirectives.directive('starRating',
		function() {
	return {
		restrict : 'A',
		template : '<ul class="rating">'
				 + '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
				 + '\u2605'
				 + '</li>'
				 + '</ul>',
		scope : {
			ratingValue : '=',
			max : '=',
			onRatingSelected : '&'
		},
		link : function(scope, elem, attrs) {
			var updateStars = function() {
				scope.stars = [];
				for ( var i = 0; i < scope.max; i++) {
					scope.stars.push({
						filled : i < scope.ratingValue
					});
				}
			};
			
			scope.toggle = function(index) {
				scope.ratingValue = index + 1;
				scope.onRatingSelected({
					rating : index + 1
				});
			};
			
			scope.$watch('ratingValue',
				function(oldVal, newVal) {
					if (newVal) {
						updateStars();
					}
				}
			);
		}
	};

});
AppDirectives.directive('googlePlaces', function(){
    return {
        restrict:'E',
        replace:true,
        // transclude:true,
        scope: {location:'='},
        template: '<input id="google_places_ac" name="google_places_ac" type="text" class="input-block-level"/>',
        link: function($scope, elm, attrs){
            var autocomplete = new google.maps.places.Autocomplete($("#google_places_ac")[0], {});
            google.maps.event.addListener(autocomplete, 'place_changed', function() {
                var place = autocomplete.getPlace();
                var country,city = '';
                place.address_components.forEach(function (item) {
                   
                    if (item.types.indexOf("locality") > -1) {
                    	city =  item.long_name ;
                    }
                    if (item.types.indexOf("country") > -1) {
                    	country = item.long_name ;
                    }
                    
                });
                $scope.location = place.geometry.location.lat() + '#@#' + place.geometry.location.lng() + '#@#' +place.name+ ' '+place.vicinity + '#@#' + city  + '#@#' + country ;
                $scope.$apply();
            });
        }
    }
});
AppDirectives.directive('myMap', function($rootScope) {
    // directive link function
    var link = function(scope, element, attrs) {
        var map, infoWindow;
        var markers = [];
        // map config
        var mapOptions = {
            center: new google.maps.LatLng($rootScope.locationX, $rootScope.locationY),
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            scrollwheel: false
        };
        
        // init the map
        function initMap() {
            if (map === void 0) {
                map = new google.maps.Map(element[0], mapOptions);
            }
        }    
        
        // place a marker
        function setMarker(map, position, title, content) {
            var marker;
            var markerOptions = {
                position: position,
                map: map,
                title: title,
                icon: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png'
            };

            marker = new google.maps.Marker(markerOptions);
            markers.push(marker); // add marker to array
            map.setCenter(marker.getPosition());  
            google.maps.event.addListener(marker, 'click', function () {
                // close window if not undefined
                if (infoWindow !== void 0) {
                    infoWindow.close();
                }
                // create new window
                var infoWindowOptions = {
                    content: content
                };
                infoWindow = new google.maps.InfoWindow(infoWindowOptions);
                infoWindow.open(map, marker);
            });
        }
        
        // show the map and place some markers
        initMap();
 
        setMarker(map, new google.maps.LatLng($rootScope.locationX, $rootScope.locationY), $rootScope.workplaceName, 'More content');
     };
    
    return {
        restrict: 'A',
        template: '<div id="gmaps"></div>',
        replace: true,
        link: link
    };
});
AppDirectives.directive('orgchart', function() {
      return {
        restrict: 'E',
        link: function($scope, $elm) {

          // Instantiate and draw our chart, passing in some options.
          var chart = new google.visualization.OrgChart($elm[0]);
         
          chart.draw($scope.orgChartData);
          var compile = function( newHTML ) { // Create re-useable compile function
              newHTML = $compile(newHTML)($scope); // Compile html
              $element.html('').append(newHTML); // Clear and append it
          };

   
            
        }
    }
  });
AppDirectives.directive( 'compileData', function ( $compile ) {
	  return {
	    scope: true,
	    link: function ( scope, element, attrs ) {

	      var elmnt;

	      attrs.$observe( 'template', function ( myTemplate ) {
	        if ( angular.isDefined( myTemplate ) ) {
	          // compile the provided template against the current scope
	          elmnt = $compile( myTemplate )( scope );

	            element.html(""); // dummy "clear"

	          element.append( elmnt );
	        }
	      });
	    }
	  };
	});
AppDirectives.directive('autoTranslate', function($interpolate, $compile) {
    return function(scope, element, attr) {
      var html = element.html();
      debugger;
      html = html.replace(/\[\[(\w+)\]\]/g, function(_, text) {
        return '<span translate="' + text + '"></span>';
      });
      element.html(html);
      $compile(element.contents())(scope); //<---- recompilation 
    }
  })
AppDirectives.directive('myLink', function() {
  return {
    restrict: 'A',
    scope: {
      enabled: '=myLink'
    },
    link: function(scope, element, attrs) {
      element.bind('click', function(event) {
        if(!scope.enabled) {
          event.preventDefault();
        }
      });
    }
  };
});
AppDirectives.directive('barcode', function(){
	  return{
		    restrict: 'AE',
		    template: '<img id="barcodeImage" style="font-weight: 400;font-size: 25px;height: 70px; margin-top: 15px;" src="{{src}}"/>',
		    scope: {
		      food: '='
		    },
		    link: function($scope){
		      $scope.$watch('food', function(barcodeValues){
		        var barcode = new bytescoutbarcode128();
		        var space= "  ";

		    		barcode.valueSet([$scope.food.selectproduct].join(space));
		    		barcode.setMargins(0, 0, 0, 0);
		    		barcode.setBarWidth(6);
		    		
		    
		    		var width = barcode.getMinWidth();
		    
		    		barcode.setSize(width, 100);

		    		$scope.src = barcode.exportToBase64(width, 100, 0);
		      }, true);
		    }
		  }
		});
AppDirectives.directive('barcodeproducts', function(){
	  return{
		    restrict: 'AE',
		    template: '<img id="barcodeImage" style="margin-top: 0px;" src="{{src}}"/>',
		    scope: {
		      food: '='
		    },
		    link: function($scope){
		      $scope.$watch('food', function(barcodeValues){
		        var barcode = new bytescoutbarcode128();
		        var space= "  ";

		    		barcode.valueSet([$scope.food.selectproduct].join(space));
		    		barcode.setMargins(0, 0, 0, 0);
		    		barcode.setBarWidth(4);
		    
		    		var width = barcode.getMinWidth();
		    
		    		barcode.setSize(width, 100);

		    		$scope.src = barcode.exportToBase64(width, 100, 0);
		      }, true);
		    }
		  }
		});
AppDirectives.directive('focus', function () {
	  return function (scope, element, attrs) {
		    attrs.$observe('focus', function (newValue) {
		      newValue === 'true' && element[0].focus();
		      // or, if you don't like side effects (see @Christophe's comment):
		      //if(newValue === 'true')  element[0].focus();
		    });
		  }
		});

AppDirectives.directive('mcToggleActive', function() {
	return {
		link: function(scope, element, attrs) {
			element.find('li').on('click', function() {
                $(this).addClass('active').siblings().removeClass('active');
            });
		}
	}
});
AppDirectives.directive('fdInput', ['$timeout','$rootScope', function ($timeout,$rootScope) {
    return {
        link: function (scope, element, attrs,$rootScope) {
            element.on('change', function  (evt,$rootScope) {               
                alert(evt.target.files[0].name);               
                alert($('#f1').val());   
                $rootScope.mdfile=$('#f1').val();
            });
        }
    }
}]);

AppDirectives.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                scope.$apply(function () {
                    scope.fileread = changeEvent.target.files[0];
                    // or all selected files:
                    // scope.fileread = changeEvent.target.files;
                });
            });
        }
        
        
    }
}]);

