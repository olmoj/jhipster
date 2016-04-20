'use strict';

angular.module('myappApp')
    .controller('MainController', function ($scope, Principal, User, RiderFromUser) {
        $scope.users = [];
        $scope.riders = [];
        $scope.authorities = ["ROLE_ADMIN"];
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.loadAll = function() {
            User.query({page: $scope.page - 1, size: 20}, function (result) {
              if (result) {
                $scope.users = result;

                result.forEach((user) => {
                    RiderFromUser.get({id: user.id}, function(result) {
                      if (result) {
                        $scope.riders[user.id] = result;
                      }
                    });
                });
              }
          });




        };
        $scope.loadAll();
    });
