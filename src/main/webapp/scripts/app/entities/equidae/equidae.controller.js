'use strict';

angular.module('myappApp')
    .controller('EquidaeController', function ($scope, $state, Equidae) {

        $scope.equidaes = [];
        $scope.loadAll = function() {
            Equidae.query(function(result) {
               $scope.equidaes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.equidae = {
                name: null,
                requiredLvlCSO: null,
                requiredLvlDressage: null,
                requiredLvlRiding: null,
                unavailabilities: null,
                id: null
            };
        };
    });
