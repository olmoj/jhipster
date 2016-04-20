'use strict';

angular.module('myappApp')
    .factory('Rider', function ($resource, DateUtils) {
        return $resource('api/riders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

    angular.module('myappApp')
            .factory('RiderFromUser', function ($resource) {
                return $resource('api/riderFromUser/:id', {}, {
                    'query': { method: 'GET', isArray: true},
                    'get': {
                        method: 'GET',
                        transformResponse: function (data) {
                            data = angular.fromJson(data);
                            return data;
                        }
                    },
                    'update': { method:'PUT' }
                });
            });
