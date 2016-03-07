'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userCourseTracker', {
                parent: 'entity',
                url: '/userCourseTrackers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserCourseTrackers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userCourseTracker/userCourseTrackers.html',
                        controller: 'UserCourseTrackerController'
                    }
                },
                resolve: {
                }
            })
            .state('userCourseTracker.detail', {
                parent: 'entity',
                url: '/userCourseTracker/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'UserCourseTracker'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userCourseTracker/userCourseTracker-detail.html',
                        controller: 'UserCourseTrackerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'UserCourseTracker', function($stateParams, UserCourseTracker) {
                        return UserCourseTracker.get({id : $stateParams.id});
                    }]
                }
            })
            .state('userCourseTracker.new', {
                parent: 'userCourseTracker',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userCourseTracker/userCourseTracker-dialog.html',
                        controller: 'UserCourseTrackerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('userCourseTracker', null, { reload: true });
                    }, function() {
                        $state.go('userCourseTracker');
                    })
                }]
            })
            .state('userCourseTracker.edit', {
                parent: 'userCourseTracker',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userCourseTracker/userCourseTracker-dialog.html',
                        controller: 'UserCourseTrackerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['UserCourseTracker', function(UserCourseTracker) {
                                return UserCourseTracker.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userCourseTracker', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('userCourseTracker.delete', {
                parent: 'userCourseTracker',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/userCourseTracker/userCourseTracker-delete-dialog.html',
                        controller: 'UserCourseTrackerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['UserCourseTracker', function(UserCourseTracker) {
                                return UserCourseTracker.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('userCourseTracker', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
