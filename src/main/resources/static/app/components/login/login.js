
// angular.module('myApp.login', ['ngRoute'])
//
//     .config(['$routeProvider', function($routeProvider) {
//         $routeProvider.when('/login', {
//             templateUrl: 'login.html',
//             controller: 'LoginCtrl'
//         });
//     }])
//     .controller('LoginCtrl', [function() {
//
//     }]);



// .controller('LoginCtrl', function ($scope, $rootScope, AUTH_EVENTS, AuthService) {
//     $scope.credentials = {
//         username: '',
//         password: ''
//     };
//     $scope.login = function (credentials) {
//         AuthService.login(credentials).then(function (user) {
//             $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
//             $scope.setCurrentUser(user);
//         }, function () {
//             $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
//         });
//     };
// });