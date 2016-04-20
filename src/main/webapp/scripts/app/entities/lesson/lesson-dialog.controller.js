'use strict';

angular.module('myappApp').controller('LessonDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lesson', 'Rider', 'Equidae',
        function($scope, $stateParams, $uibModalInstance, entity, Lesson, Rider, Equidae) {

        $scope.lesson = entity;
        $scope.riders = Rider.query();
        $scope.equidaes = Equidae.query();
        $scope.load = function(id) {
            Lesson.get({id : id}, function(result) {
                $scope.lesson = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myappApp:lessonUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lesson.id != null) {
                Lesson.update($scope.lesson, onSaveSuccess, onSaveError);
            } else {
                Lesson.save($scope.lesson, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
