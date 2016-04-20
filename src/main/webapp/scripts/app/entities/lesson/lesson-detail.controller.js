'use strict';

angular.module('myappApp')
    .controller('LessonDetailController', function ($scope, $rootScope, $stateParams, entity, Lesson, Rider, Equidae) {
        $scope.lesson = entity;
        $scope.load = function (id) {
            Lesson.get({id: id}, function(result) {
                $scope.lesson = result;
            });
        };
        var unsubscribe = $rootScope.$on('myappApp:lessonUpdate', function(event, result) {
            $scope.lesson = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
