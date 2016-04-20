'use strict';

angular.module('myappApp')
    .controller('RiderDetailController', function ($scope, $rootScope, $stateParams, entity, Rider, Equidae, User, Lesson) {
        $scope.rider = entity;
        $scope.load = function (id) {
            Rider.get({id: id}, function(result) {
                $scope.rider = result;
            });
        };
        var unsubscribe = $rootScope.$on('myappApp:riderUpdate', function(event, result) {
            $scope.rider = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
