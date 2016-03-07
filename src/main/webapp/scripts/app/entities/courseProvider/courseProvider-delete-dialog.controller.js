'use strict';

angular.module('coursetrackerApp')
	.controller('CourseProviderDeleteController', function($scope, $uibModalInstance, entity, CourseProvider) {

        $scope.courseProvider = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CourseProvider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
