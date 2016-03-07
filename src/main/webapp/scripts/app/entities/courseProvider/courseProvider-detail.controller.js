'use strict';

angular.module('coursetrackerApp')
    .controller('CourseProviderDetailController', function ($scope, $rootScope, $stateParams, entity, CourseProvider, Course) {
        $scope.courseProvider = entity;
        $scope.load = function (id) {
            CourseProvider.get({id: id}, function(result) {
                $scope.courseProvider = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:courseProviderUpdate', function(event, result) {
            $scope.courseProvider = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
