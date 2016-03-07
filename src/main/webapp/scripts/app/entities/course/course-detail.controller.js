'use strict';

angular.module('coursetrackerApp')
    .controller('CourseDetailController', function ($scope, $rootScope, $stateParams, entity, Course, CourseProvider, CourseCategory) {
        $scope.course = entity;
        $scope.load = function (id) {
            Course.get({id: id}, function(result) {
                $scope.course = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:courseUpdate', function(event, result) {
            $scope.course = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
