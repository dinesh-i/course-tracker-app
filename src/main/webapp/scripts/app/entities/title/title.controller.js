'use strict';

angular.module('coursetrackerApp')
    .controller('TitleController', function ($scope, $state, Title) {

        $scope.titles = [];
        $scope.loadAll = function() {
            Title.query(function(result) {
               $scope.titles = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.title = {
                titleName: null,
                isCompleted: null,
                id: null
            };
        };
    });
