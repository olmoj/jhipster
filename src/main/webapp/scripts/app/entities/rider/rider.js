'use strict';

angular.module('myappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rider', {
                parent: 'entity',
                url: '/riders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myappApp.rider.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rider/riders.html',
                        controller: 'RiderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rider');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rider.detail', {
                parent: 'entity',
                url: '/rider/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'myappApp.rider.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rider/rider-detail.html',
                        controller: 'RiderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rider');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Rider', function($stateParams, Rider) {
                        return Rider.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rider.new', {
                parent: 'rider',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rider/rider-dialog.html',
                        controller: 'RiderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    lvlCSO: null,
                                    lvlDressage: null,
                                    lvlRiding: null,
                                    availabilities: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rider', null, { reload: true });
                    }, function() {
                        $state.go('rider');
                    })
                }]
            })
            .state('rider.edit', {
                parent: 'rider',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rider/rider-dialog.html',
                        controller: 'RiderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Rider', function(Rider) {
                                return Rider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rider.delete', {
                parent: 'rider',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rider/rider-delete-dialog.html',
                        controller: 'RiderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Rider', function(Rider) {
                                return Rider.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rider', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
