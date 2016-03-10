'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pipeline', {
                parent: 'site',
                url: '/pipeline',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/tracker/pipeline/pipeline.html',
                        controller: 'PipelineController'
                    }
                },
                resolve: {
                    
                }
            });
    });
