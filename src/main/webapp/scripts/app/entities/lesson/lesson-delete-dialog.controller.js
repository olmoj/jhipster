'use strict';

angular.module('myappApp')
	.controller('LessonDeleteController', function($scope, $uibModalInstance, entity, Lesson) {

        $scope.lesson = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Lesson.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
