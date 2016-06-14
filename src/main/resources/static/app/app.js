'use strict'

var host = 'http://localhost:8080';
var urlDashboard = host + '/rest/report';
var urlLeagues = host + '/rest/leagues';
var urlTeams = host + '/rest/teams';
var urlPlayers = host + '/rest/players';
var urlMatches = host + '/rest/matches';
var urlUsers = host + '/rest/users';
var urlLogin = host + '/auth/login'
var urlLogout = host + '/auth/logout'
var urlUserTeam = host + '/users/role?role=ROLE_TEAM';
var urlLeagueTeam = host + '/rest/leagues';
var urlGetUsersLeague = host + '/rest/users/role?role=ROLE_LEAGUE';
var urlTeams = host + '/rest/teams';
var urlAllRoles = host + '/rest/users/roles';
var urlAllActionTypes = host + '/rest/actions/types';
var urlActions = host + '/rest/actions';
var urlPlayerByTeam = host + '/players/team?id=';

var module = angular.module('myApp', ['ui.router','ngMessages']);

/* Dashboard Controller */
module.controller("dashboardCtrl", function ($scope, $http) {

    $http({
        method: 'GET',
        url: urlDashboard,
    }).success(function(data) {
        $scope.reports = data;
    }).error(function(status) {

        /* Lançar mensagem de erro*/
        console.log($scope);
        console.log(status);

    });
});

/* Leagues and League Controller */
module.controller("leaguesCtrl", function ($scope, $http) {

    $scope.usersToSelect = {
        userSelected: null,
        userAvailableOptions: null
    };

    $http({
        method: 'GET',
        url: urlLeagues,
    }).success(function(data) {
        $scope.leagues = data;
    }).error(function(status) {

        $scope.errorMsgLeagues = status.error;
        
        console.log($scope);
        console.log(status);

    });
   
    $http({
        method: 'GET',
        url: urlGetUsersLeague,
    }).success(function(data) {
        $scope.usersToSelect.userAvailableOptions = data;
    }).error(function(status) {
        $scope.errorMsgLeagues = status.error;
        console.log($scope);
        console.log(status);
    });

    $scope.addLeague = function () {

        var league = {
            name: $scope.leagueName,
            leagueAdmin:{
                id: $scope.usersToSelect.userSelected
            },
            date: $scope.leagueDate,
            teams: null,
            logo: $scope.leagueLogo
        };

        $http({
            method: 'POST',
            url: urlLeagues,
            data: league
        }).
        success(function(savedLeague) {
            $scope.leagues.push(savedLeague);
        }).
        error(function(status) {

            $scope.errorMsgLeagues = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.removeLeague = function (index) {

        var leagueToDelete = $scope.leagues[index];
        var url = urlLeagues + '/' + leagueToDelete.id;

        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.leagues.splice(index, 1);

        }).error(function (data, status) {

            $scope.errorMsgLeagues = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.selectedLeague = {};
    
    $scope.getTemplate = function (league){
        if(league.id === $scope.selectedLeague.id) return 'editLeague';
        else return 'displayLeague';
    };

    $scope.editLeague = function (league){
        $scope.selectedLeague = angular.copy(league); 
    };
    
    $scope.saveLeague = function(index){

        $http({
            method: 'PUT',
            url: urlLeagues,
            data: $scope.selectedLeague,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(editedLeague) {
            $scope.leagues[index] = angular.copy(editedLeague);
            $scope.reset();
        }).
        error(function(status) {
            $scope.reset();
            $scope.errorMsgLeagues = status.error;
            console.log($scope);
            console.log(status);

        });
    };
    
    $scope.reset = function(){
      $scope.selectedLeague = {};  
    };
    
});
module.controller("leagueCtrl", function ($scope, $http, $stateParams) {

    var url = urlLeagues + '/' + $stateParams.leagueId;

    $http({
        method: 'GET',
        url: url
    }).
    success(function(data) {
        $scope.league = data;
    }).
    error(function(status) {
        $scope.errorMsgLeague = status.error;
    });
});

/* Teams and Team Controller */
module.controller("teamsCtrl", function ($scope, $http) {

    $scope.usersToSelect = {
        userSelected: null,
        userAvailableOptions: null
    };
    $scope.leaguesToSelect = {
        leagueSelected: null,
        leagueAvailableOptions: null
    };

    $http({
        method: 'GET',
        url: urlUserTeam,
    }).success(function(data) {
        $scope.usersToSelect.userAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgTeams = status.error;
        console.log($scope);
        console.log(status);
    });
    $http({
        method: 'GET',
        url: urlLeagueTeam,
    }).success(function(data) {
        $scope.leaguesToSelect.leagueAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgTeams = status.error;
        console.log($scope);
        console.log(status);
    });

    $http({
        method: 'GET',
        url: urlTeams,
    }).success(function(data) {
        $scope.teams = data;
    }).error(function(status) {

        $scope.errorMsgTeams = status.error;
        console.log($scope);
        console.log(status);

    });

    $scope.addTeam = function () {
        var team = {
            name: $scope.teamName,
            address: $scope.teamAddress,
            league: {
                id: $scope.leaguesToSelect.leagueSelected
            },
            teamAdmin: {
                id: $scope.usersToSelect.userSelected
            },
            logo: $scope.teamLogo
        };

        $http({
            method: 'POST',
            url: urlTeams,
            data: team
        }).
        success(function(savedTeam) {
            $scope.teams.push(savedTeam);
        }).
        error(function(status) {

            $scope.errorMsgTeams = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.removeTeam = function (index) {

        var teamToDelete = $scope.teams[index];
        var url = urlTeams + '/' + teamToDelete.id;

        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.teams.splice(index, 1);

        }).error(function (data, status) {

            $scope.errorMsgTeams = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.selectedTeam = {};

    $scope.getTemplate = function (team){
        if(team.id === $scope.selectedTeam.id) return 'editTeam';
        else return 'displayTeam';
    };

    $scope.editTeam = function (team){
        $scope.selectedTeam = angular.copy(team);
    };

    $scope.saveTeam = function(index){

        $http({
            method: 'PUT',
            url: urlTeams,
            data: $scope.selectedTeam,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(editedTeam) {
            $scope.teams[index] = angular.copy(editedTeam);
            $scope.reset();
        }).
        error(function(status) {
            $scope.reset();
            $scope.errorMsgTeams = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.reset = function(){
        $scope.selectedTeam = {};
    };
});
module.controller("teamCtrl", function ($scope, $http, $stateParams) {

    var url = urlTeams + '/' + $stateParams.teamId;

    $http({
        method: 'GET',
        url: url
    }).
    success(function(data) {
        $scope.team = data;
    }).
    error(function(status) {
        $scope.errorMsgTeam = status.error;
        console.log(status);
    });
});

/* Players and Player Controller */
module.controller("playersCtrl", function ($scope, $http) {

    $scope.teamsToSelect = {
        teamSelected: null,
        teamAvailableOptions: null
    };
    
    $http({
        method: 'GET',
        url: urlPlayers,
    }).success(function(data) {
        $scope.players = data;
    }).error(function(status) {

        $scope.errorMsgPlayers = status.error;
        console.log($scope);
        console.log(status);

    });
    
    $http({
        method: 'GET',
        url: urlTeams,
    }).success(function(data) {
        $scope.teamsToSelect.teamAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgPlayers = status.error;
        console.log($scope);
        console.log(status);
    });

    $scope.addPlayer = function () {
       
        var player = {
            name: $scope.playerName,
            lastName: $scope.playerLastName,
            height: $scope.playerHeight,
            weight: $scope.playerWeight,
            image: $scope.playerImage,
            team: {
              id: $scope.teamsToSelect.teamSelected
            }
        };

        $http({
            method: 'POST',
            url: urlPlayers,
            data : player
        }).
        success(function(savedPlayer) {
            $scope.players.push(savedPlayer);
        }).
        error(function(status) {

            $scope.errorMsgPlayers = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.removePlayer = function (index) {

        var playerToDelete = $scope.players[index];
        var url = urlPlayers + '/' + playerToDelete.id;

        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.players.splice(index, 1);

        }).error(function (data, status) {

            $scope.errorMsgPlayers = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.selectedPlayer = {};

    $scope.getTemplate = function (player){
        if(player.id === $scope.selectedPlayer.id) return 'editPlayer';
        else return 'displayPlayer';
    };

    $scope.editPlayer = function (player){
        $scope.selectedPlayer = angular.copy(player);
    };

    $scope.savePlayer = function(index){

        $http({
            method: 'PUT',
            url: urlPlayers,
            data: $scope.selectedPlayer,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(editedPlayer) {
            $scope.players[index] = angular.copy(editedPlayer);
            $scope.reset();
        }).
        error(function(status) {
            $scope.reset();
            $scope.errorMsgPlayers = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.reset = function(){
        $scope.selectedPlayer = {};
    };


});
module.controller("playerCtrl", function ($scope, $http, $stateParams) {

    var url = urlPlayers + '/' + $stateParams.playerId;

    $http({
        method: 'GET',
        url: url
    }).
    success(function(data) {
        $scope.player = data;
    }).
    error(function(status) {
        $scope.errorMsgPlayer = status.error;
        console.log(status);
    });

});

/* Matches and Match Controller */
module.controller("matchesCtrl", function ($scope, $http) {

    $http({
        method: 'GET',
        url: urlMatches,
    }).success(function(data) {
        $scope.matches = data;
    }).error(function(status) {

        $scope.errorMsgMatches = status.error;
        console.log($scope);
        console.log(status);

    });

    $scope.addMatch = function () {
        var match = {
            teamHome:{
                id: $scope.teamHomeId
            },
            teamVisitor: {
                id: $scope.teamVisitorId
            },
            dateOfMatch: $scope.dateOfMatch,
            delegate:{
                id: $scope.delegateId
            }
        };

        $http({
            method: 'POST',
            url: urlMatches,
            data : match
        }).
        success(function(savedMatch) {
            $scope.matches.push(savedMatch);
        }).
        error(function(status) {

            $scope.errorMsgMatches = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.removeMatch = function (index) {

        var matchToDelete = $scope.matches[index];
        var url = urlMatches + '/' + matchToDelete.id;

        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.matches.splice(index, 1);

        }).error(function (status) {

            $scope.errorMsgMatches = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.selectedMatch = {};

    $scope.getTemplate = function (match){
        if(match.id === $scope.selectedMatch.id) return 'editMatch';
        else return 'displayMatch';
    };

    $scope.editMatch = function (match){
        $scope.selectedMatch = angular.copy(match);
    };

    $scope.saveMatch = function(index){

        $http({
            method: 'PUT',
            url: urlMatches,
            data: $scope.selectedMatch,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(editedMatch) {
            $scope.matches[index] = angular.copy(editedMatch);
            $scope.reset();
        }).
        error(function(status) {
            $scope.reset();
            $scope.errorMsgMatches = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.reset = function(){
        $scope.selectedMatch = {};
    };


});
module.controller("matchCtrl", function ($scope, $http, $stateParams) {

    var url = urlMatches + '/' + $stateParams.matchId;



    $scope.actionsToSelect = {
        actionSelected: null,
        actionAvailableOptions: null
    };

    $scope.playersToSelect = {
        playerSelected: null,
        playerAvailableOptions: null
    };
    
    $http({
        method: 'GET',
        url: url
    }).
    success(function(data) {
        $scope.match = data;
    }).
    error(function(status) {
        $scope.errorMsgMatch = status.error;
    });

    $http({
        method: 'GET',
        url: urlAllActionTypes,
    }).success(function(data) {
        $scope.actionsToSelect.actionAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgMatch = status.error;
        console.log($scope);
        console.log(status);
    });

    $http({
        method: 'GET',
        url: urlPlayers,
    }).success(function(data) {
        $scope.playersToSelect.playerAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgMatch = status.error;
        console.log($scope);
        console.log(status);
    });


    $scope.addAction = function () {

        var actionsArray=$scope.match.actions;

        var addedAction  =                {
            minute: $scope.actionMinute,
            actionType: $scope.actionsToSelect.actionSelected,
            player: {
                id: $scope.playersToSelect.playerSelected
            }
        }

        actionsArray.push(addedAction);

        var addedMatch =
        {
            id: $scope.match.id,
            actions: actionsArray,
            league:{
                id: $scope.match.league.id
            },
            teamHome:{
                id: $scope.match.teamHome.id
            },
            teamVisitor:{
                id: $scope.match.teamVisitor.id
            },
            delegate:{
                id: $scope.match.delegate.id
            }
        };

        $http({
            method: 'PUT',
            url: urlMatches,
            data: addedMatch,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(addedMatch) {
            $scope.match.actions.push();
            $scope.match.actions.push(addedAction);
        }).
        error(function(status) {
            $scope.errorMsgMatch = status.error;
            console.log($scope);
            console.log(status);

        });

    };

    $scope.removeAction = function (index) {

        var actionToDelete = $scope.match.actions[index];
        var url = urlActions + '/' + actionToDelete.id;

        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.match.actions.splice(index, 1);

        }).error(function (status) {

            $scope.errorMsgMatch = status.error;
            console.log($scope);
            console.log(status);
        });
    };
    
    
});

/* Users and User Controller */
module.controller("usersCtrl", function ($scope, $http) {
/**/
    $scope.rolesToSelect = {
        roleSelected: null,
        roleAvailableOptions: null
    };

    $http({
        method: 'GET',
        url: urlAllRoles,
    }).success(function(data) {
        $scope.rolesToSelect.roleAvailableOptions = data;
    }).error(function(status) {

        $scope.errorMsgUsers = status.error;
        console.log($scope);
        console.log(status);
    });

    $http({
        method: 'GET',
        url: urlUsers,
    }).
    success(function(data) {
        $scope.users = data;
    }).
    error(function(status) {

        $scope.errorMsgUsers = status.error;
        console.log($scope);
        console.log(status);

    });

    $scope.addUser = function () {

        var user = {
            name: $scope.userName,
            username: $scope.userUsername,
            password: $scope.userPass,
            email: $scope.userEmail,
            phone: $scope.userPhone
        };

        var url = '/users';

        $http({
            method: 'POST',
            url: urlUsers,
            data: user
        }).
        success(function(savedUser) {
            $scope.users.push(savedUser);
        }).
        error(function(status) {

            $scope.errorMsgUsers = status.error;
            console.log($scope);
            console.log(status);

        });
    };

    $scope.removeUser = function (index) {

        var userToDelete = $scope.users[index]
        var url = urlUsers + '/' + userToDelete.id;
        $http({
            method: 'DELETE',
            url: url
        }).
        success(function() {
            $scope.users.splice(index, 1);
            
        }).error(function (data, status) {

            $scope.errorMsgUsers = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.selectedUser = {};

    $scope.getTemplate = function (user){
        if(user.id === $scope.selectedUser.id) return 'editUser';
        else return 'displayUser';
    };

    $scope.editUser = function (user){
        $scope.selectedUser = angular.copy(user);
    };

    $scope.saveUser = function(index){

        $http({
            method: 'PUT',
            url: urlUsers,
            data: $scope.selectedUser,
            headers: {'Content-Type': 'application/json'}
        }).
        success(function(editedUser) {
            $scope.users[index] = angular.copy(editedUser);
            $scope.reset();
        }).
        error(function(status) {
            $scope.errorMsgUsers = status.error;
            $scope.reset();
            console.log($scope);
            console.log(status);

        });
    };

    $scope.reset = function(){
        $scope.selectedUser = {};
    };

});
module.controller("userCtrl", function ($scope, $http, $stateParams) {

    var url = urlUsers + '/' + $stateParams.userId;
    $http({
        method: 'GET',
        url: url
    }).
    success(function(data) {
        $scope.user = data;
    }).
    error(function(status) {
        $scope.errorMsgUser = status.error;
    });
});

module.controller("profileCtrl", function ($scope, $http) {
    $http.get('components/tests-json/profile.json').success(function (data) {
        var user = data;

        $scope.userId = user.id;
        $scope.userName = user.name;
        $scope.userUsername = user.username;
        $scope.userPhone = user.phone;
        $scope.userEmail = user.email;
        $scope.userPassword = user.password;
    });

    $scope.editProfile = function () {
        var user = {
            id: $scope.userId,
            name: $scope.userName,
            username: $scope.userUsername,
            password: $scope.userPass,
            email: $scope.userEmail,
            phone: $scope.userPhone
        };

        $http.put(user).success(function(){
            $scope.user.push(user);
        }).onerror(function(status){
            $scope.errorMsgProfile = status.error;
        });

    };
});

module.controller("loginCtrl", function ($scope, $http, $state) {


    $scope.auth = {
        username: $scope.usernameSubmit,
        password: $scope.passwordSubmit
    };

    $scope.submit = function(){

        $scope.auth.username = $scope.usernameSubmit;
        $scope.auth.password = $scope.passwordSubmit;

        $http({
            method: 'POST',
            crossDomain: true,
            url: urlLogin,
            data: $.param($scope.auth),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).
        success(function(savedLeague) {
            $state.go('dashboard');
        }).
        error(function(status) {
            $scope.errorMsgLogin = status.error;
            console.log($scope);
            console.log(status);
        });
    };

    $scope.logout = function(){

        $scope.auth.username = $scope.usernameSubmit;
        $scope.auth.password = $scope.passwordSubmit;

        $http({
            method: 'POST',
            crossDomain: true,
            url: urlLogout,
            /*data: $.param($scope.auth),*/
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).
        success(function(savedLeague) {
            $state.go('login');
        }).
        error(function(status) {
            $scope.errorMsgLogin = status.error;
            console.log($scope);
            console.log(status);
        });
    };
});

module.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $urlRouterProvider.otherwise('/dashboard');

    $stateProvider

        .state('dashboard', {
            url: '/dashboard',
            templateUrl: 'dashboard.html',
        })
        .state('leagues', {
            url: '/leagues',
            templateUrl: 'components/leagues/leagues.html'
        })
        .state('league', {
            url: '/leagues/:leagueId',
            templateUrl: 'components/leagues/league.html'
        })
        .state('teams', {
            url: '/teams',
            templateUrl: 'components/teams/teams.html'
        })
        .state('team', {
            url: '/teams/:teamId',
            templateUrl: 'components/teams/team.html'
        })
        .state('players', {
            url: '/players',
            templateUrl: 'components/players/players.html'
        })
        .state('player', {
            url: '/players/:playerId',
            templateUrl: 'components/players/player.html'
        })
        .state('matches', {
            url: '/matches',
            templateUrl: 'components/matches/matches.html'
        })
        .state('match', {
            url: '/matches/:matchId',
            templateUrl: 'components/matches/match.html'
        })
        .state('users', {
            url: '/users',
            templateUrl: 'components/users/users.html'
        })
        .state('user', {
            url: '/users/:userId',
            templateUrl: 'components/users/user.html'
        })
        .state('profile', {
            url: '/profile/:id',
            templateUrl: 'components/users/profile.html'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'components/login/login.html'
        })
});
