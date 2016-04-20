'use strict';

angular.module('myappApp')
	.controller('EquidaeDeleteController', function($scope, $uibModalInstance, entity, Equidae) {

        $scope.equidae = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Equidae.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
