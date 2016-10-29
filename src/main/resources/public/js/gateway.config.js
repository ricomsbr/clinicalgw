;
(function() {
	'use strict';
	angular.module('gateway').config(GatewayConfig);

	GatewayConfig.$inject = [
	    '$mdThemingProvider', '$httpProvider' ];

	function GatewayConfig($mdThemingProvider, $httpProvider) {
		$mdThemingProvider.theme('default').primaryPalette('blue')
		        .accentPalette('pink');
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	}
})();