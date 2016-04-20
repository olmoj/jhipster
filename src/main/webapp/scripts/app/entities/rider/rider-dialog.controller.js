'use strict';

angular.module('myappApp').controller('RiderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Rider', 'Equidae', 'User', 'Lesson',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Rider, Equidae, User, Lesson) {

        $scope.rider = entity;
        $scope.equidaes = Equidae.query();
        $scope.users = User.query();
        $scope.lessons = Lesson.query();
        $scope.load = function(id) {
            Rider.get({id : id}, function(result) {
                $scope.rider = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('myappApp:riderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rider.id != null) {
                Rider.update($scope.rider, onSaveSuccess, onSaveError);
            } else {
                Rider.save($scope.rider, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
