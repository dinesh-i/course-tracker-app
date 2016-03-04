'use strict';

angular.module('coursetrackerApp').controller('TitleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Title', 'Book', 'Video',
        function($scope, $stateParams, $uibModalInstance, entity, Title, Book, Video) {

        $scope.title = entity;
        $scope.books = Book.query();
        $scope.videos = Video.query();
        $scope.load = function(id) {
            Title.get({id : id}, function(result) {
                $scope.title = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:titleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.title.id != null) {
                Title.update($scope.title, onSaveSuccess, onSaveError);
            } else {
                Title.save($scope.title, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
