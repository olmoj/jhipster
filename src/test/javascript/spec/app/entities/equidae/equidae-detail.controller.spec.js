'use strict';

describe('Controller Tests', function() {

    describe('Equidae Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEquidae, MockRider, MockLesson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEquidae = jasmine.createSpy('MockEquidae');
            MockRider = jasmine.createSpy('MockRider');
            MockLesson = jasmine.createSpy('MockLesson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Equidae': MockEquidae,
                'Rider': MockRider,
                'Lesson': MockLesson
            };
            createController = function() {
                $injector.get('$controller')("EquidaeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'myappApp:equidaeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
