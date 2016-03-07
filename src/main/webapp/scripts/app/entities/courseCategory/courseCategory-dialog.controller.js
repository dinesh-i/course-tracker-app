'use strict';

angular.module('coursetrackerApp').controller('CourseCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CourseCategory', 'Course',
        function($scope, $stateParams, $uibModalInstance, entity, CourseCategory, Course) {

        $scope.courseCategory = entity;
        $scope.courses = Course.query();
        $scope.load = function(id) {
            CourseCategory.get({id : id}, function(result) {
                $scope.courseCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:courseCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.courseCategory.id != null) {
                CourseCategory.update($scope.courseCategory, onSaveSuccess, onSaveError);
            } else {
                CourseCategory.save($scope.courseCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
