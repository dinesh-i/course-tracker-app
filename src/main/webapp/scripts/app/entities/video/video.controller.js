'use strict';

angular.module('coursetrackerApp')
    .controller('VideoController', function ($scope, $state, Video) {

        $scope.videos = [];
        $scope.loadAll = function() {
            Video.query(function(result) {
               $scope.videos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.video = {
                videoName: null,
                author: null,
                isCompleted: null,
                id: null
            };
        };
    });
