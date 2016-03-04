'use strict';

angular.module('coursetrackerApp').controller('BookDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Book', 'Title',
        function($scope, $stateParams, $uibModalInstance, entity, Book, Title) {

        $scope.book = entity;
        $scope.titles = Title.query();
        $scope.load = function(id) {
            Book.get({id : id}, function(result) {
                $scope.book = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('coursetrackerApp:bookUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.book.id != null) {
                Book.update($scope.book, onSaveSuccess, onSaveError);
            } else {
                Book.save($scope.book, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
