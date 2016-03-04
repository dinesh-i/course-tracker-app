'use strict';

angular.module('coursetrackerApp')
    .controller('TitleDetailController', function ($scope, $rootScope, $stateParams, entity, Title, Book, Video) {
        $scope.title = entity;
        $scope.load = function (id) {
            Title.get({id: id}, function(result) {
                $scope.title = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:titleUpdate', function(event, result) {
            $scope.title = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
