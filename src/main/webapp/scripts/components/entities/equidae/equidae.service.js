'use strict';

angular.module('myappApp')
    .factory('Equidae', function ($resource, DateUtils) {
        return $resource('api/equidaes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.unavailabilities = DateUtils.convertLocaleDateFromServer(data.unavailabilities);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.unavailabilities = DateUtils.convertLocaleDateToServer(data.unavailabilities);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.unavailabilities = DateUtils.convertLocaleDateToServer(data.unavailabilities);
                    return angular.toJson(data);
                }
            }
        });
    });
