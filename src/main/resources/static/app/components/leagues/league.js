angular.module('myApp.team', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/team', {
            templateUrl: 'teams.html',
            controller: 'TeamCtrl'
        });
    }])
    .controller('TeamCtrl', [function() {

    }]);


