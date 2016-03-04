'use strict';

angular.module('coursetrackerApp')
	.controller('BookDeleteController', function($scope, $uibModalInstance, entity, Book) {

        $scope.book = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Book.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
