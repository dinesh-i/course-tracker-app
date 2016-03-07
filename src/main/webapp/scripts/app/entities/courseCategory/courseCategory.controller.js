'use strict';

angular.module('coursetrackerApp')
    .controller('CourseCategoryController', function ($scope, $state, CourseCategory) {

        $scope.courseCategorys = [];
        $scope.loadAll = function() {
            CourseCategory.query(function(result) {
               $scope.courseCategorys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.courseCategory = {
                courseCategoryName: null,
                id: null
            };
        };
    });
