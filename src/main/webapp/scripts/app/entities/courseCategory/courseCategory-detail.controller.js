'use strict';

angular.module('coursetrackerApp')
    .controller('CourseCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, CourseCategory, Course) {
        $scope.courseCategory = entity;
        $scope.load = function (id) {
            CourseCategory.get({id: id}, function(result) {
                $scope.courseCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('coursetrackerApp:courseCategoryUpdate', function(event, result) {
            $scope.courseCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
