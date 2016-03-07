'use strict';

angular.module('coursetrackerApp')
    .controller('UserCourseTrackerDetailController', function ($scope, $rootScope, $stateParams, entity, UserCourseTracker) {
        $scope.userCourseTracker = entity;
        $scope.load = function (id) {
            UserCourseTracker.get({id: id}, function(result) {
                $scope.userCourseTracker = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:userCourseTrackerUpdate', function(event, result) {
            $scope.userCourseTracker = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
