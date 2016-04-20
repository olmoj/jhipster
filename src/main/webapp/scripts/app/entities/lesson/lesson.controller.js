'use strict';

angular.module('myappApp')
    .controller('LessonController', function ($scope, $state, Lesson) {

        $scope.lessons = [];
        $scope.loadAll = function() {
            Lesson.query(function(result) {
               $scope.lessons = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.lesson = {
                date: null,
                type: null,
                level: null,
                id: null
            };
        };
    });
