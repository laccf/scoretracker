angular.module('myApp.league', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/league', {
            templateUrl: 'leagues.html',
            controller: 'LeagueCtrl'
        });
    }])
    .controller('LeagueCtrl', [function() {

    }]);


