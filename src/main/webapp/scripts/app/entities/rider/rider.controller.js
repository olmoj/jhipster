'use strict';

angular.module('myappApp')
    .controller('RiderController', function ($scope, $state, Rider) {

        $scope.riders = [];
        $scope.loadAll = function() {
            Rider.query(function(result) {
               $scope.riders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rider = {
                lvlCSO: null,
                lvlDressage: null,
                lvlRiding: null,
                availabilities: null,
                id: null
            };
        };
    });
