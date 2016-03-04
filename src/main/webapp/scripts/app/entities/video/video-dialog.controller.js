'use strict';

angular.module('coursetrackerApp').controller('VideoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Video', 'Title',
        function($scope, $stateParams, $uibModalInstance, entity, Video, Title) {

        $scope.video = entity;
        $scope.titles = Title.query();
        $scope.load = function(id) {
            Video.get({id : id}, function(result) {
                $scope.video = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:videoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.video.id != null) {
                Video.update($scope.video, onSaveSuccess, onSaveError);
            } else {
                Video.save($scope.video, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
