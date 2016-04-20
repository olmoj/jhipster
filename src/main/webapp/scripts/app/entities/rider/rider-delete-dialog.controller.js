'use strict';

angular.module('myappApp')
	.controller('RiderDeleteController', function($scope, $uibModalInstance, entity, Rider) {

        $scope.rider = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Rider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
