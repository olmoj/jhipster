'use strict';

angular.module('myappApp')
    .controller('EquidaeDetailController', function ($scope, $rootScope, $stateParams, entity, Equidae, Rider, Lesson) {
        $scope.equidae = entity;
        $scope.load = function (id) {
            Equidae.get({id: id}, function(result) {
                $scope.equidae = result;
            });
        };
        var unsubscribe = $rootScope.$on('myappApp:equidaeUpdate', function(event, result) {
            $scope.equidae = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
