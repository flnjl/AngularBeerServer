'use strict';

/* Controllers */

angular
  .module('BeerControllers', [])
  .controller('BeerListCtrl', ['$scope', '$http', function($scope, $http) {

    $http.get('/AngularBeerServer/BeerList').success(function(data) {
      $scope.beers = data;
    });

    $scope.orderProp = 'alcohol';
  }])
  .controller('BeerDetailCtrl', ['$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
    $http.get('/AngularBeerServer/Beer?id=' + $routeParams.beerId).success(function(data) {
      $scope.beer = data;
    });
  }])
  .controller('BeerAddCtrl', ['$scope', '$routeParams', '$http', '$location', function($scope, $routeParams, $http, $location) {	  
	  $scope.beerMsg = {
			  "name": {},
	  }
	  
	  $scope.processForm = function() {
		  $http.post(
				  '/AngularBeerServer/Beer',
				  $scope.beer
		  )
		  .success(function(data) {
			    if (!data.success) {
			      // if not successful, bind errors to error variables
			      for (var i = 0; i < data.errors.length; i++) {
			    	  $scope.beerMsg.name.$invalid = true;
			      }
			    } else {
			      $location.path('/beers/' + data.beer);
			    }
		});
	  }
  }])
  .controller('BeerUpdateCtrl', ['$scope', '$routeParams', '$http', '$location', function($scope, $routeParams, $http, $location) {	  
	  $http.get('/AngularBeerServer/Beer?id=' + $routeParams.beerId).success(function(data) {
	      $scope.beer = data;
	  });
	  
	  $scope.beerMsg = {
			  "name": {},
	  }
	  
	  $scope.processForm = function() {
		  $http.post(
				  '/AngularBeerServer/Beer',
				  $scope.beer
		  )
		  .success(function(data) {
			    if (!data.success) {
			      // if not successful, bind errors to error variables
			      for (var i = 0; i < data.errors.length; i++) {
			    	  $scope.beerMsg.name.$invalid = true;
			      }
			    } else {
			      $location.path('/beers/' + data.beer);
			    }
		});
	  }
  }])
  .controller('BeerDeleteCtrl', ['$scope', '$routeParams', '$http', '$location', function($scope, $routeParams, $http, $location) {	  
	    $http.get('/AngularBeerServer//BeerDelete?id=' + $routeParams.beerId).success(function(data) {
	      $location.path('/beers');
	    });
  }])
  ;


