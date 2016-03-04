'use strict';

angular.module('coursetrackerApp')
	.controller('VideoDeleteController', function($scope, $uibModalInstance, entity, Video) {

        $scope.video = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Video.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
