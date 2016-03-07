'use strict';

angular.module('coursetrackerApp')
	.controller('CourseCategoryDeleteController', function($scope, $uibModalInstance, entity, CourseCategory) {

        $scope.courseCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CourseCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
