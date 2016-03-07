'use strict';

angular.module('coursetrackerApp').controller('CourseProviderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'CourseProvider', 'Course',
        function($scope, $stateParams, $uibModalInstance, entity, CourseProvider, Course) {

        $scope.courseProvider = entity;
        $scope.courses = Course.query();
        $scope.load = function(id) {
            CourseProvider.get({id : id}, function(result) {
                $scope.courseProvider = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:courseProviderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.courseProvider.id != null) {
                CourseProvider.update($scope.courseProvider, onSaveSuccess, onSaveError);
            } else {
                CourseProvider.save($scope.courseProvider, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
