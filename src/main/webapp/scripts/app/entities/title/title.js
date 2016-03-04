'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('title', {
                parent: 'entity',
                url: '/titles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Titles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/title/titles.html',
                        controller: 'TitleController'
                    }
                },
                resolve: {
                }
            })
            .state('title.detail', {
                parent: 'entity',
                url: '/title/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/title/title-detail.html',
                        controller: 'TitleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Title', function($stateParams, Title) {
                        return Title.get({id : $stateParams.id});
                    }]
                }
            })
            .state('title.new', {
                parent: 'title',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/title/title-dialog.html',
                        controller: 'TitleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    titleName: null,
                                    isCompleted: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('title', null, { reload: true });
                    }, function() {
                        $state.go('title');
                    })
                }]
            })
            .state('title.edit', {
                parent: 'title',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/title/title-dialog.html',
                        controller: 'TitleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Title', function(Title) {
                                return Title.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('title', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('title.delete', {
                parent: 'title',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/title/title-delete-dialog.html',
                        controller: 'TitleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Title', function(Title) {
                                return Title.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('title', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
