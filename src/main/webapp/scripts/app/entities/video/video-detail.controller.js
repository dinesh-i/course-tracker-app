'use strict';

angular.module('coursetrackerApp')
    .controller('VideoDetailController', function ($scope, $rootScope, $stateParams, entity, Video, Title) {
        $scope.video = entity;
        $scope.load = function (id) {
            Video.get({id: id}, function(result) {
                $scope.video = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:videoUpdate', function(event, result) {
            $scope.video = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
