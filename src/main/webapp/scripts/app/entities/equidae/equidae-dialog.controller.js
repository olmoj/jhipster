'use strict';

angular.module('myappApp').controller('EquidaeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Equidae', 'Rider', 'Lesson',
        function($scope, $stateParams, $uibModalInstance, entity, Equidae, Rider, Lesson) {

        $scope.equidae = entity;
        $scope.riders = Rider.query();
        $scope.lessons = Lesson.query();
        $scope.load = function(id) {
            Equidae.get({id : id}, function(result) {
                $scope.equidae = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myappApp:equidaeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.equidae.id != null) {
                Equidae.update($scope.equidae, onSaveSuccess, onSaveError);
            } else {
                Equidae.save($scope.equidae, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForUnavailabilities = {};

        $scope.datePickerForUnavailabilities.status = {
            opened: false
        };

        $scope.datePickerForUnavailabilitiesOpen = function($event) {
            $scope.datePickerForUnavailabilities.status.opened = true;
        };
}]);
