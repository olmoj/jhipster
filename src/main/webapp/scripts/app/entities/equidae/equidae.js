'use strict';

angular.module('myappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('equidae', {
                parent: 'entity',
                url: '/equidaes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myappApp.equidae.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/equidae/equidaes.html',
                        controller: 'EquidaeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('equidae');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('equidae.detail', {
                parent: 'entity',
                url: '/equidae/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myappApp.equidae.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/equidae/equidae-detail.html',
                        controller: 'EquidaeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('equidae');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Equidae', function($stateParams, Equidae) {
                        return Equidae.get({id : $stateParams.id});
                    }]
                }
            })
            .state('equidae.new', {
                parent: 'equidae',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/equidae/equidae-dialog.html',
                        controller: 'EquidaeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    requiredLvlCSO: null,
                                    requiredLvlDressage: null,
                                    requiredLvlRiding: null,
                                    unavailabilities: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('equidae', null, { reload: true });
                    }, function() {
                        $state.go('equidae');
                    })
                }]
            })
            .state('equidae.edit', {
                parent: 'equidae',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/equidae/equidae-dialog.html',
                        controller: 'EquidaeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Equidae', function(Equidae) {
                                return Equidae.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('equidae', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('equidae.delete', {
                parent: 'equidae',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/equidae/equidae-delete-dialog.html',
                        controller: 'EquidaeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Equidae', function(Equidae) {
                                return Equidae.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('equidae', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
