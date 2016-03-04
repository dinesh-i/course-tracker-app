'use strict';

angular.module('coursetrackerApp')
    .controller('BookDetailController', function ($scope, $rootScope, $stateParams, entity, Book, Title) {
        $scope.book = entity;
        $scope.load = function (id) {
            Book.get({id: id}, function(result) {
                $scope.book = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:bookUpdate', function(event, result) {
            $scope.book = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
