'use strict';

angular.module('coursetrackerApp').controller('CourseDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Course', 'CourseProvider', 'CourseCategory',
        function($scope, $stateParams, $uibModalInstance, entity, Course, CourseProvider, CourseCategory) {

        $scope.course = entity;
        $scope.courseproviders = CourseProvider.query();
        $scope.coursecategorys = CourseCategory.query();
        $scope.load = function(id) {
            Course.get({id : id}, function(result) {
                $scope.course = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:courseUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.course.id != null) {
                Course.update($scope.course, onSaveSuccess, onSaveError);
            } else {
                Course.save($scope.course, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
