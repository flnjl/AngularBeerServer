'use strict';

/* App Module */

var angularBeer = angular.module('AngularBeer', [
  'ngRoute',
  'BeerControllers',
  'BeerFilters'
]);

angularBeer.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/beers', {
        templateUrl: 'partials/beer-list.html',
        controller: 'BeerListCtrl'
      }).
      when('/beers/:beerId', {
        templateUrl: 'partials/beer-detail.html',
        controller: 'BeerDetailCtrl'
      }).
      when('/add', {
    	templateUrl: 'partials/add.html',
    	controller: 'BeerAddCtrl'
      }).
      when('/update/:beerId', {
    	templateUrl: 'partials/add.html',
    	controller: 'BeerUpdateCtrl'
      }).
      when('/delete/:beerId', {
    	templateUrl: 'partials/beer-list.html',
      	controller: 'BeerDeleteCtrl'
      }).
      otherwise({
        redirectTo: '/beers'
      });
  }]);