'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseProvider', {
                parent: 'entity',
                url: '/courseProviders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CourseProviders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseProvider/courseProviders.html',
                        controller: 'CourseProviderController'
                    }
                },
                resolve: {
                }
            })
            .state('courseProvider.detail', {
                parent: 'entity',
                url: '/courseProvider/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CourseProvider'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseProvider/courseProvider-detail.html',
                        controller: 'CourseProviderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CourseProvider', function($stateParams, CourseProvider) {
                        return CourseProvider.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseProvider.new', {
                parent: 'courseProvider',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseProvider/courseProvider-dialog.html',
                        controller: 'CourseProviderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    courseProviderName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('courseProvider', null, { reload: true });
                    }, function() {
                        $state.go('courseProvider');
                    })
                }]
            })
            .state('courseProvider.edit', {
                parent: 'courseProvider',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseProvider/courseProvider-dialog.html',
                        controller: 'CourseProviderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CourseProvider', function(CourseProvider) {
                                return CourseProvider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseProvider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('courseProvider.delete', {
                parent: 'courseProvider',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseProvider/courseProvider-delete-dialog.html',
                        controller: 'CourseProviderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CourseProvider', function(CourseProvider) {
                                return CourseProvider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseProvider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
