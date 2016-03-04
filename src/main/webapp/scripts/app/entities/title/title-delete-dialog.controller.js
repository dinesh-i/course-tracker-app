'use strict';

angular.module('coursetrackerApp')
	.controller('TitleDeleteController', function($scope, $uibModalInstance, entity, Title) {

        $scope.title = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Title.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
