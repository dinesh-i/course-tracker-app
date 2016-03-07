'use strict';

angular.module('coursetrackerApp').controller('UserCourseTrackerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserCourseTracker',
        function($scope, $stateParams, $uibModalInstance, entity, UserCourseTracker) {

        $scope.userCourseTracker = entity;
        $scope.load = function(id) {
            UserCourseTracker.get({id : id}, function(result) {
                $scope.userCourseTracker = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:userCourseTrackerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.userCourseTracker.id != null) {
                UserCourseTracker.update($scope.userCourseTracker, onSaveSuccess, onSaveError);
            } else {
                UserCourseTracker.save($scope.userCourseTracker, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
