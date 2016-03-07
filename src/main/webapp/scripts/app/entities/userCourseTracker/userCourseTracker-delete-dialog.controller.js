'use strict';

angular.module('coursetrackerApp')
	.controller('UserCourseTrackerDeleteController', function($scope, $uibModalInstance, entity, UserCourseTracker) {

        $scope.userCourseTracker = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            UserCourseTracker.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
